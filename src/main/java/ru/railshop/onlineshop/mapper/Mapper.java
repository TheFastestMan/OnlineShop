package ru.railshop.onlineshop.mapper;

public interface Mapper<T, F> {
    T mapFrom(F f);
}
