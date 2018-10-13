package service;

import POJO.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> list();
}
