package br.com.kaikedev.userservice.Runner;

import br.com.kaikedev.userservice.Entity.Roles;
import br.com.kaikedev.userservice.Entity.UserEntity;
import br.com.kaikedev.userservice.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
    private UserRepository userRepository;

    DatabaseInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Inserindo os dados na base com a biblioteca Faker
    @Override
    public void run(String... args) throws Exception {
        var initialSize = userRepository.findAll().size();

        if (initialSize < 10000) {
            Faker faker = new Faker();

            List<UserEntity> users = new ArrayList<>();

            for (int i = initialSize; i < 10000; i++) {
                UserEntity user = new UserEntity();
                user.setName(faker.name().fullName());
                user.setEmail(faker.internet().emailAddress());
                user.setPhone(faker.phoneNumber().phoneNumber());
                if (i % 1000 == 0 ){
                    user.setRoles(Roles.ADMIN);
                } else {
                    user.setRoles(Roles.USER);
                }
                users.add(user);
            }


            userRepository.bulkInsert(users);

            log.info("Database initialization complete.");
            log.info("Number of users: {}", userRepository.findAll().size());
        }


    }
}
