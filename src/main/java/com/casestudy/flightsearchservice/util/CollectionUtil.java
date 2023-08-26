package com.casestudy.flightsearchservice.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class CollectionUtil {

    public <T, R> List<R> map(final Collection<T> collection, final Function<? super T, ? extends R> mapper) {
        return filterNullObjects(collection)
                .map(mapper)
                .collect(Collectors.toList());
    }

    public <T> Stream<T> filterNullObjects(final Collection<T> collection) {
        return collection
                .stream()
                .filter(Objects::nonNull);
    }
}
