package com.casestudy.flightsearchservice.controller;

import com.casestudy.flightsearchservice.dto.common.PageResponse;
import com.casestudy.flightsearchservice.dto.common.PageableRequest;
import com.casestudy.flightsearchservice.dto.user.UserResponseDto;
import com.casestudy.flightsearchservice.dto.user.UserRoleChangeRequest;
import com.casestudy.flightsearchservice.model.entity.User;
import com.casestudy.flightsearchservice.service.UserService;
import com.casestudy.flightsearchservice.util.CollectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "user")
public class UserController extends AbstractController{

    private final UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        super(modelMapper);
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> findByEmail(@PathVariable String email){
        User foundUser = userService.findByEmail(email);
        return ResponseEntity.ok(
                getModelMapper().map(foundUser, UserResponseDto.class)
        );
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id){
        User foundUser = userService.findById(id);
        return ResponseEntity.ok(
                getModelMapper().map(foundUser, UserResponseDto.class)
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<PageResponse<UserResponseDto>> findAll(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        PageableRequest pageableRequest = PageableRequest.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
        Page<User> allUserPage = userService.findAll(pageableRequest);
        List<UserResponseDto> userResponseList = CollectionUtil.map(
                allUserPage.getContent(),
                (user) -> getModelMapper().map(user, UserResponseDto.class)
        );
        PageResponse<UserResponseDto> pageResponse = PageResponse.<UserResponseDto>pageResponseBuilder()
                .data(userResponseList)
                .pageSize(allUserPage.getSize())
                .pageNumber(allUserPage.getNumber())
                .count(allUserPage.getTotalElements())
                .build();
        return ResponseEntity.ok(
                pageResponse
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/role")
    public ResponseEntity<UserResponseDto> setUserRole(@RequestBody UserRoleChangeRequest userRoleChangeRequest){
        User updatedUser = userService.setUserRole(
                userRoleChangeRequest.getId(),
                userRoleChangeRequest.getUserRole()
        );
        return ResponseEntity.ok(
                getModelMapper().map(updatedUser, UserResponseDto.class)
        );
    }
}
