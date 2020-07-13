package com.ideaas.botlivery.service.interfaces;

import com.ideaas.botlivery.domain.User;

public interface UserService {
    User get(String id);

    User save(User user);
}
