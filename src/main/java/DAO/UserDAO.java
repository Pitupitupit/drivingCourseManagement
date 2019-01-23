package DAO;

import POJO.User;

import java.util.List;

public interface UserDAO {
    public void save(User user);
    public List<User> list();
    public List<User> listByRole(String role);
    public User getUserById(long id);
    public User getUserByUsername(String username);
    public void updateUser(User user);
    public void setInactive(long id);
}
