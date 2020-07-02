package com.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;

//Перечисление с ролями пользователей
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name(); //Строковое представление элемента множества
    }
}
