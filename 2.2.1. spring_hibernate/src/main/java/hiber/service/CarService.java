package hiber.service;

import hiber.model.Car;
import hiber.model.User;

public interface CarService {
    void addCar(Car car);
    void setCarForLastUser(Car car);
    User getUserFromCar (String model, int series);
}
