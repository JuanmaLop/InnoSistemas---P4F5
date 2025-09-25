package com.innosistemas.FabricaInnosistemasF5;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.innosistemas.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@ComponentScan(basePackages = {"com.innosistemas"})
@EnableJpaRepositories(basePackages = {"com.innosistemas.repository"})
@EntityScan(basePackages = {"com.innosistemas.entity"})
public class FabricaInnosistemasF5Application {

	public static void main(String[] args) {
		SpringApplication.run(FabricaInnosistemasF5Application.class, args);
	}

}
