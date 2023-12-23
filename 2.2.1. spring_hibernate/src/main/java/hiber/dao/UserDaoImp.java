package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public void add(Car car) {
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
      String sql = "FROM Car WHERE model =: model AND series =: series";
      Car car = (Car) sessionFactory.getCurrentSession().createQuery(sql)
              .setParameter("model", model).setParameter("series", series)
              .setMaxResults(1).getSingleResult();
      sql = "FROM User WHERE Car =: car";
      return (User) sessionFactory.getCurrentSession().createQuery(sql)
              .setParameter("Car", car).getSingleResult();
   }
}
