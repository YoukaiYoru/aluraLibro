package org.backend.libro.aluralibros;

import org.backend.libro.aluralibros.Principal.Principal;
import org.backend.libro.aluralibros.Repository.AutoresRepository;
import org.backend.libro.aluralibros.Repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluraLibrosApplication implements CommandLineRunner {

    @Autowired
    private LibrosRepository repository;
    @Autowired
    private AutoresRepository autoresRepository;


    public static void main(String[] args) {
        SpringApplication.run(AluraLibrosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repository, autoresRepository);
        principal.mostrarElMenu();
    }
}
