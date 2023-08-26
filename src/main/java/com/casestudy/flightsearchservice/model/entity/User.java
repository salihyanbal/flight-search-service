package com.casestudy.flightsearchservice.model.entity;

import com.casestudy.flightsearchservice.model.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User extends AbstractIdEntity{

    @Column(name = "first_name", columnDefinition = "varchar(100)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "varchar(100)")
    private String lastName;

    @Column(name = "email", columnDefinition = "varchar(150)", unique = true)
    private String email;

    @Column(name = "password", columnDefinition = "varchar(80)")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRoleEnum role;
}
