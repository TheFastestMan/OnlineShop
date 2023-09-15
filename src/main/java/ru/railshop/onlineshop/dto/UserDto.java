package ru.railshop.onlineshop.dto;


public record UserDto (Long id, String description){


    public Long id() {
        return id;
    }

    public String description() {
        return description;
    }
}
