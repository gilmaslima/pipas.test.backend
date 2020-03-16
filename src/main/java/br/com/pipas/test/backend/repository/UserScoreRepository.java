package br.com.pipas.test.backend.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pipas.test.backend.model.ScoreResponse;
import br.com.pipas.test.backend.model.UserScore;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserScoreRepository {

	@Autowired
	private Set<UserScore> data;
	

	private Comparator<UserScore> comparator;

	@PostConstruct
	public void init() {
		comparator = new Comparator<UserScore>() {

			@Override
			public int compare(UserScore arg0, UserScore arg1) {
				return arg1.getPoints().compareTo(arg0.getPoints());
			}
		};

	}

	public boolean save(UserScore userScore) {
		log.info("Saving user score: {}", userScore);
		return data.add(userScore);
	}

	public Optional<ScoreResponse> getUserScoreById(Integer userId) {
		List<UserScore> list = data.stream().sorted(comparator).collect(Collectors.toList());
		Optional<UserScore> optional = list.stream().filter(userScore -> userScore.getUserId().equals(userId)).findFirst();
		if(optional.isPresent()) {
			UserScore temp = optional.get();
			Integer position = list.indexOf(temp) + 1;
			ScoreResponse score = ScoreResponse.createFromUserScore(temp, position);
			return Optional.of(score);
		}
		log.info("Score not found for: {}", userId);
		return Optional.ofNullable(null);
	}

	public List<UserScore> getHighScoreList() {

		List<UserScore> temp = data.stream().sorted(comparator).collect(Collectors.toList());
		if (!temp.isEmpty() && temp.size() > 20) {
			temp = temp.subList(0, 20);
		}
		log.info("Retrieving high score list: {}", temp);
		return temp;
	}

	public Optional<UserScore> getUnsorted(UserScore userScore) {
		return data.stream().filter(u -> u.equals(userScore)).findFirst();
	}

	public void remove(UserScore userScore) {
		log.info("Removing userScore: {}", userScore);
		data.remove(userScore);
		
	}

	
	
}
