package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class CarDaoImpl implements CarDao{
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void addCar(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    public void setCarForLastUser(Car car) {
        User user = (User) sessionFactory.getCurrentSession().createQuery("FROM User ORDER BY id DESC")
                .setMaxResults(1).getSingleResult();
        if (user != null) {
            user.setCar(car);
        }
    }

    @Override
    public User getUserFromCar(String model, int series) {
        User user = null;
        try {
            Car car = (Car) sessionFactory.getCurrentSession()
                    .createQuery("from Car where model = :model AND series = :series")
                    .setParameter("model", model).setParameter("series", series)
                    .setMaxResults(1).getSingleResult();
            System.out.println(car);
//                    "FROM User WHERE Car =: c"
            String sql = "FROM User WHERE Car = :car";
            return (User) sessionFactory.getCurrentSession().createQuery(sql)
                    .setParameter("car", car).getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Машина " + model + " серии " + series + " не найдена");
        }
        return user;
    }
}
