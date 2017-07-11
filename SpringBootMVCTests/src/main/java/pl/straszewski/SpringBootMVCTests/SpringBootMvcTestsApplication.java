package pl.straszewski.SpringBootMVCTests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@SpringBootApplication
@EnableAutoConfiguration
public class SpringBootMvcTestsApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBootMvcTestsApplication.class, args);
	}
}
