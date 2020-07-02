package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

//Контроллер регистрации
@Controller //Класс-контроллер обрабатывает HTTP-запросы
public class RegistrationController {
    @Autowired //Автосвязывание
    private UserRepo userRepo; //Репозиторий для работы с профилями пользователей

    @GetMapping("/registration") //Обработка GET запросов на адрес /registration вызовом метода registration()
    public String registration() {

        return "registration"; //Возвращает имя View (веб-страницы)
    }

    @PostMapping("/registration") //Обработка POST запроса на /registration
    public String addUser(User user, Map<String, Object> model) {
        //Проверки на существование пользователя с такими данными
        User userFromDb = userRepo.findByUsername(user.getUsername()); //Поиск такого username в базе

        if (userFromDb != null) {
            //Username занят
            model.put("message", "User exists!");
            return "registration"; //обновление страницы
        }

        //Username не занят
        user.setActive(true); //Активация пользователя
        user.setRoles(Collections.singleton(Role.USER)); //Добавление роли "Пользователь"
        userRepo.save(user);

        return "redirect:/login"; //Редирект на страницу авторизации
    }
}
