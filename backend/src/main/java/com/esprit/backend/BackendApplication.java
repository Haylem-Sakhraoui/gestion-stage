package com.esprit.backend;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;


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
		registry.addMapping("/api/**") // Spécifiez le chemin de votre API
				.allowedOrigins("http://localhost:4200") // Autorisez les requêtes depuis ce domaine
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Autorisez les méthodes HTTP spécifiées
				.allowCredentials(true); // Autorisez l'envoi des cookies
	}

//	@Bean
//	public JavaMailSender javaMailSender() {
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		// Configure mailSender properties (host, port, username, password, etc.)
//		mailSender.setHost("smtp-mail.outlook.com");
//		mailSender.setPort(587);
//		mailSender.setUsername("haylem.sakhraoui@esprit.tn");
//		mailSender.setPassword("Voshs@1999");
//		return mailSender;
//	}

//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/api/**") // Spécifiez le chemin de votre API
//				.allowedOrigins("http://localhost:4200") // Autorisez les requêtes depuis ce domaine
//				.allowedMethods("GET", "POST", "PUT", "DELETE") // Autorisez les méthodes HTTP spécifiées
//				.allowCredentials(true); // Autorisez l'envoi des cookies
//	}


}
