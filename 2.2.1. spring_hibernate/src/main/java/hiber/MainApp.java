package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
   public static void main(String[] args){
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.addUser(new User("User1", "Lastname1", "user1@mail.ru", new Car("Model1", 1)));
      userService.addUser(new User("User2", "Lastname2", "user2@mail.ru", new Car("Model1", 2)));
      userService.addUser(new User("User3", "Lastname3", "user3@mail.ru",new Car("Model2", 1)));
      userService.addUser(new User("User4", "Lastname4", "user4@mail.ru"));

      List<User> users = userService.getListUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         System.out.println("Car = " + user.getCar());
         System.out.println();
      }
      String model = "Model1";
      int series = 2;
      User user = userService.getUserFromCar(model, series);
      printUserFromCar(user, model, series);
      model = "Model2";
      series = 1;
      user = userService.getUserFromCar(model, series);
      printUserFromCar(user, model, series);
      model = "Model2";
      series = 3;
      user = userService.getUserFromCar(model, series);
      printUserFromCar(user, model, series);

      context.close();
   }

   public static void printUserFromCar(User user, String model, int series) {
      if (user == null) {
         System.out.println("Нет владельцев машины " + model + " серии " + series);
      } else {
         System.out.println("Машиной " + model + " серии " + series + " владеет " + user);
      }
   }
}
