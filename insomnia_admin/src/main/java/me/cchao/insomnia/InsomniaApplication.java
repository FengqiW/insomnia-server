package me.cchao.insomnia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InsomniaApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsomniaApplication.class, args);
    }
}
