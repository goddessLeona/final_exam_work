/*
package com.petra.final_exam_work.config;

import com.petra.final_exam_work.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// use in the development face to hash password added manually in db
@Component
public class PasswordMigrationRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public PasswordMigrationRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){

        userRepository.findAll().forEach(user -> {

            // prevent double hashing
            if(!user.getPassword().startsWith("$2a$")){
                user.setPassword(
                        passwordEncoder.encode(user.getPassword())
                );

                userRepository.save(user);

                System.out.println(
                        "Password encoded for user: " + user.getUsername()
                );
            }
        });

        System.out.println("Password migration finished");
    }
}

 */
