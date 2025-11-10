package com.exemple.demo;


import com.exemple.demo.Comptes.Client;
import com.exemple.demo.Comptes.Compte;
import com.exemple.demo.Comptes.TypeCompte;
import com.exemple.demo.repository.ClientRepository;
import com.exemple.demo.repository.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Date;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository, ClientRepository clientRepository, RepositoryRestConfiguration restConfiguration) {
        return args -> {

            restConfiguration.exposeIdsFor(Compte.class);
            restConfiguration.exposeIdsFor(Client.class);


            Client c1 = clientRepository.save(new Client(null, "Amal", "amal@example.com", null));
            Client c2 = clientRepository.save(new Client(null, "Ali", "ali@example.com", null));

            // Create and save accounts associated with clients
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c2));

            // Display all accounts
            compteRepository.findAll().forEach(c -> {
                System.out.println(c.toString());
            });
        };
    }

}
