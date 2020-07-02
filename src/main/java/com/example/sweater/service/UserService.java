package com.example.sweater.service;

import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired //Автосвязывание сервиса и репозитория доступа к профилям пользоввателей
    private UserRepo userRepo;

    @Override //Переопределение метода возврата профиля пользователя по username
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
