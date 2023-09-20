package ru.railshop.onlineshop.dto;

import lombok.Builder;

@Builder
public record UserDto (Long id, String description){

    public Long id() {
        return id;
    }

    public String description() {
        return description;
    }
}
