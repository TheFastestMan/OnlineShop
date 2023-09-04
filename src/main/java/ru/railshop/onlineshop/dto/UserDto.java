package ru.railshop.onlineshop.dto;

public record UserDto (Long id, String description){

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String description() {
        return description;
    }
}
