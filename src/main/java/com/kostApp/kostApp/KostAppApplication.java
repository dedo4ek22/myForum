package com.kostApp.kostApp;

import com.kostApp.kostApp.models.Discussion;
import com.kostApp.kostApp.repositories.DiscussionRepository;
import com.kostApp.kostApp.services.DiscussionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication
public class KostAppApplication {


	public static void main(String[] args) {
		SpringApplication.run(KostAppApplication.class, args);
		}

		@Bean
		public ModelMapper modelMapper(){
		return new ModelMapper();
		}
	}





