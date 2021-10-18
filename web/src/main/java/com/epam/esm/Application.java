package com.epam.esm;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Optional;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        User user = new User();
        user.setUsername("Ruslanchess");
        user.setPassword("rusned");
        user.setRoles(Collections.singleton(UserRole.ADMIN));

        Optional<User> optionalUser = userService.save(user);
        if (optionalUser.isPresent()){
            System.out.println(user.getUsername());
        }

         */
    }
}
