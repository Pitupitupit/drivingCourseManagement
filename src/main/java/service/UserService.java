package service;

import POJO.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> list();
    List<User> listByRole(String role);
    User getUserById(long id);
    User getUserByUsername(String username);
    void updateUser(User user);
    void setInactive(long id);
}
