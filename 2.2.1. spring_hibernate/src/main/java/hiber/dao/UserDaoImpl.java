package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

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
   public User getUserFromCar(String model, int series) {
      User user = null;
      try {
         Query<User> query = sessionFactory.getCurrentSession()
                 .createQuery("select u from User u left join fetch u.car c where c.model = :model AND c.series = :series", User.class)
                 .setParameter("model", model).setParameter("series", series);
         user = query.setMaxResults(1).getSingleResult();
      } catch (NoResultException e) {
         System.out.println("Машина " + model + " серии " + series + " не найдена");
      }
      return user;
   }
}
