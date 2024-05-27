package com.aluraChallenge.LiterAlura;

import com.aluraChallenge.LiterAlura.principal.Principal;
import com.aluraChallenge.LiterAlura.repository.AutoresRepository;
import com.aluraChallenge.LiterAlura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LibrosRepository repositoryL;
	@Autowired
	private AutoresRepository repositoryA;

	public static void main(String[] args) {SpringApplication.run(LiterAluraApplication.class, args);}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositoryL,repositoryA);
		principal.menu();
	}
}
