
package com.ds.banque;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ds.banque.entitie.Compte;
import com.ds.banque.repository.CompteRepository;

@SpringBootApplication
public class BanqueInterface {

    public static void main(String[] args) {
        SpringApplication.run(BanqueInterface.class, args);
    }

  //  @Bean
  //  CommandLineRunner initData(CompteRepository repo) {
      //  return args ->
        
       //     repo.save(new Compte(Takwa", 4000));
           // repo.save(new Compte("Eya", 7000));
          //  repo.save(new Compte("Ahmed", 5000));
 //       }
    }


