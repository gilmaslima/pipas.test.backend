package br.com.pipas.test.backend.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.pipas.test.backend.model.UserScore;

@Configuration
public class DataConfig {

	
	@Bean
	public Set<UserScore> getData(){
		return new HashSet<UserScore>();
	}
}
