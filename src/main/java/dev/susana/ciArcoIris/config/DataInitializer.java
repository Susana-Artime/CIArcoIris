package dev.susana.ciArcoIris.config;

import dev.susana.ciArcoIris.users.User;
import dev.susana.ciArcoIris.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer  implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        try{

            if (!userRepository.existsByEmail("conchalopez@ciarcoiris.com")) {
            
                User director = new User();
                director.setUsername("Concha_Lopez");
                director.setEmail("conchalopez@ciarcoiris.com");
                director.setRole("Directora");
                director.setPassword(passwordEncoder.encode("ciarcoiris#direc1"));
                director.setPhone("680385672");
                userRepository.save(director);
                System.out.println("Directora creada en la base de datos.");

            } else {
                System.out.println("La directora ya existe en la base de datos.");
            }
            

        } catch(Exception e){

            System.out.println("Error de creación");
            

        }
        
    }

}