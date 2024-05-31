package lk.ijse.shoeshop;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@Slf4j
public class ShoeshopApplication {
	@Bean
	ModelMapper modelMapper(){return new ModelMapper();}

	public static void main(String[] args) {
		log.info("Starting KicksHub Application...");
		SpringApplication.run(ShoeshopApplication.class, args);
		log.info("KicksHub Application Started.");
	}

}
