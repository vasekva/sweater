package com.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

//Хранит профили пользователей
@Entity  //Класс-сущность базы данных (Может хранить строку из таблицы)
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Автоматическая генерация ID
    private Long id; //Первичный ключ таблицы генерируется автоматически
    private String username;
    private String password;
    private boolean active;

    //Автоматическая генерация дополнительной таблицы для enum
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER) //Загрузка "жадным способом" (легкие данные)
    //Данная коллекция хранится в отдельной таблице, которая не бьла описана в мэпе
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id")) //Таблицы соединяются через usr_id
    @Enumerated(EnumType.STRING) //Данная коллекция - перечисление, которое должно храниться в виде строки
    private Set<Role> roles; //Список ролей пользователя

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isActive() {
        return active;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
