package com.gmaslowski.dam.camel.domain.mapper;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public abstract class Mapper<FROM_OBJECT, TO_OBJECT> {

    public abstract TO_OBJECT map(FROM_OBJECT object);

    Collection<TO_OBJECT> mapCollection(Collection<FROM_OBJECT> objects) {
        return objects.stream().map(this::map).collect(toList());
    }
}
