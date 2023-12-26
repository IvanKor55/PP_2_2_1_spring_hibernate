package hiber.dao;

import hiber.model.Car;
import hiber.model.User;

public interface CarDao {
    void addCar(Car car);
    void setCarForLastUser(Car car);
    User getUserFromCar (String model, int series);
}
