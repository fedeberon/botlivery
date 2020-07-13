package com.ideaas.botlivery.service;

import com.ideaas.botlivery.dao.UserDao;
import com.ideaas.botlivery.domain.User;
import com.ideaas.botlivery.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao dao;

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public User get(String id){
        Optional<User> user = dao.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public User save(User user){
        return dao.save(user);
    }
}
