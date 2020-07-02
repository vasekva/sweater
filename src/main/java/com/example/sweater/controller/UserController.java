package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user") //Общий мэппинг для всех методов класса
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo; //Доступ к профилям пользователей

    @GetMapping
    //Список пользователей системы
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll()); //Поместить в модель всех пользователей системы

        return "userList";
    }

    @GetMapping("{user}") // user/{userID}
    //Получение и правка данных конкретного пользователя
    public String userEditForm(
            @PathVariable User user,
            Model model
    ){ //Spring сам сопоставит получаемый id и пользователя
        model.addAttribute("user", user); //Поместить проофиль пользователя в модель
        model.addAttribute("roles", Role.values()); //Поместить все доступные роли в модель
        return "userEditor";
    }

    @PostMapping
    //Сохранение внесенных в профиль пользователя изменений
    public String userSave(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam("userId") User user,
            @RequestParam Map<String, String> form
    ) {
        //Проверка данных пользователя
        user.setUsername(username);
        user.setPassword(password);

        //Множество возможных ролей
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        //Форма возвращает перечисление ролей, обходим их
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user); //Сохранение изменений

        return "redirect:/user";
    }
}
