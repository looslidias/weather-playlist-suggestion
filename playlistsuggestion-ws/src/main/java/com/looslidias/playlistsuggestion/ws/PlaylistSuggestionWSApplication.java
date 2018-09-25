package com.looslidias.playlistsuggestion.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@SpringBootApplication
@ComponentScan("com.looslidias.playlistsuggestion")
@EnableCaching
public class PlaylistSuggestionWSApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaylistSuggestionWSApplication.class, args);
	}
}
