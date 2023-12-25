package com.mj.kimsnote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KimsNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(KimsNoteApplication.class, args);
    }

}
