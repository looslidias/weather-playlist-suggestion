package com.looslidias.playlistsuggestion.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@SpringBootApplication
@ComponentScan("com.looslidias.playlistsuggestion")
@EnableJpaRepositories("com.looslidias.playlistsuggestion")
@EntityScan("com.looslidias.playlistsuggestion")
@EnableCaching
public class PlaylistSuggestionWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaylistSuggestionWorkerApplication.class, args);
    }
}

