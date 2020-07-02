package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

//Главный контроллер
@Controller //Класс контроллер обрабатывает HTTP-запросы
public class MainController {
    @Autowired //Автосвязывание
    private MessageRepo messageRepo; //Репозиторий для работы с сообщениями

    //Получает путь из application.context и вставляет его в переменную
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/") //Обработка GET запросов на корневой адрес вызовом метода greeting()
    public String greeting(Map<String, Object> model) {
        return "greeting"; //Возвращает имя View (веб-страницы)
    }

    @GetMapping("/main") //Обработка GET запросов на /main
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        //Принимаем на вход фильтр для сообщений и модель

        //Вывод сообщений
        Iterable<Message> messages;

        //Фильтр сообщений
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter); //Получение всех данные из таблицы
        } else {
            messages = messageRepo.findAll();  //Получить все данные из таблицы по фильтру
        }

        //Передача данных в модель
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main"; //Возвращает имя View (веб-страницы)
    }

    @PostMapping("/main") //Обработка POST запроса на /main
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model,
            @RequestParam("file") MultipartFile file
                    ) throws IOException {
        //Принимает на вход профиль авторизированного пользователя, две строки, файл и модель

        Message message = new Message(text, tag, user);

        //Если файл существует, добавляем его к сообщению
        if (file != null && !file.getOriginalFilename().isEmpty() ) {
            File uploadDir = new File(uploadPath); //Объект директории загрузки

            //Создать директорию, если ее не сущесвует
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            //Уникальный идентификатор файла
            String uuidFile = UUID.randomUUID().toString();;
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            //Загрузка файла
            file.transferTo(new File(uploadPath + "/" + resultFileName));

            //Добавляем файл к сообщению
            message.setFilename(resultFileName);
        }

        messageRepo.save(message); //Сохранить сообщение в БД

        Iterable<Message> messages = messageRepo.findAll(); //Получение всех данных из таблицы

        model.put("messages", messages); //Передача данных в модель
        model.put("filter", ""); //Пустой фильтр

        return "main"; //Возвращает имя View (веб-страницы)
    }

}
