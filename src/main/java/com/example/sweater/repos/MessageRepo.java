package com.example.sweater.repos;

import com.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//Доступ к сообщениям
//Интерфейс для работы с БД
//Наследует CRUD-функционал
public interface MessageRepo extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);

}
