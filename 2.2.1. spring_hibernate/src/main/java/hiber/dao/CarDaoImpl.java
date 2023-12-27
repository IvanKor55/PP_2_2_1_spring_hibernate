package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarDaoImpl implements CarDao{
    private final String GET_CAR = "FROM Car WHERE model = :model AND series = :series";
    private final String GET_USER_FROM_CAR = "select u FROM User u WHERE u.car = :car";
    private final String GET_LAST_USER = "FROM User ORDER BY id DESC";
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void addCar(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    public void setCarForLastUser(Car car) {
        User user = (User) sessionFactory.getCurrentSession().createQuery(GET_LAST_USER)
                .setMaxResults(1).getSingleResult();
        if (user != null) {
            user.setCar(car);
        }
    }

    @Override
    public User getUserFromCar(String model, int series) {
        List<Car> car = sessionFactory.getCurrentSession()
                .createQuery(GET_CAR)
                .setParameter("model", model).setParameter("series", series)
                .setMaxResults(1).getResultList();
        if (car.isEmpty()) {
            System.out.println("Машина " + model + " серии " + series + " не найдена");
            return null;
        } else {
            return (User) sessionFactory.getCurrentSession()
                    .createQuery(GET_USER_FROM_CAR)
                    .setParameter("car", car.get(0)).getSingleResult();
        }
    }
}
