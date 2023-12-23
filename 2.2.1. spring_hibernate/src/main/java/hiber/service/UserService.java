package hiber.service;

import hiber.model.Car;
import hiber.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();
    void add(Car car);
    void setCarForLastUser(Car car);
    User getUserFromCar (String model, int series);
}
