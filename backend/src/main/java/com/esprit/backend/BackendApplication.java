package com.esprit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages ={
		"com.esprit.backend.Controller",
		"com.esprit.backend.Services",
		"com.esprit.backend.Repository",
		"com.esprit.backend.auth",
		"com.esprit.backend.Configuration"
})
@EnableJpaRepositories(basePackages = {"com.esprit.backend.Repository"})
@EntityScan(basePackages = {"com.esprit.backend.Entity"})
@EnableAspectJAutoProxy
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Spécifiez le chemin de votre API
				.allowedOrigins("http://localhost:4200") // Autorisez les requêtes depuis ce domaine
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Autorisez les méthodes HTTP spécifiées
				.allowCredentials(true); // Autorisez l'envoi des cookies
	}
}
