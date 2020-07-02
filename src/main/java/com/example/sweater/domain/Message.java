package com.example.sweater.domain;

import javax.persistence.*;

//Хранит сообщения пользователей
@Entity  //Класс-сущность базы данных (Может хранить строку из таблицы)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;
    private String filename;

    //Мэппинг поля автора сообщения (связь двух таблиц)
    //Связь, при которой одному пользователю соответствует множество сообщений (связь между таблицами)
    @ManyToOne(fetch = FetchType.EAGER) //Жадная загрузка
    @JoinColumn(name = "user_id") //В таблице messages создается поле usr_id для связи
    private User author;

    //Конструкторы
    public Message() {
    }

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public Integer getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public String getTag() {
        return tag;
    }
    public User getAuthor() {
        return author;
    }
    public String getFilename() {
        return filename;
    }


    public void setId(Integer id) {
        this.id = id;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
}
