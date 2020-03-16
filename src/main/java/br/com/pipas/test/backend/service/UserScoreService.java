package br.com.pipas.test.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pipas.test.backend.model.ScoreResponse;
import br.com.pipas.test.backend.model.UserScore;
import br.com.pipas.test.backend.repository.UserScoreRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserScoreService {

	@Autowired
	private UserScoreRepository repository;

	public void save(UserScore userScore) {
		Integer points = userScore.getPoints() != null ? userScore.getPoints() : 0;
		Optional<UserScore> optional = repository.getUnsorted(userScore);
		if(optional.isPresent()) {
			UserScore tmp = optional.get();
			points += tmp.getPoints();
			repository.remove(tmp);
		}
		userScore.setPoints(points);
		log.info("Saving User Score: {}", userScore);
		
		repository.save(userScore);

	}

	public Optional<ScoreResponse> getPosition(Integer userId) {

		return repository.getUserScoreById(userId);

	}

	public List<ScoreResponse> getHighScoreList() {

		List<UserScore> list = repository.getHighScoreList();
		List<UserScore> temp = list;
		return list.stream().map(u -> {
			return new ScoreResponse(u.getUserId(), u.getPoints(), temp.indexOf(u) + 1);
		}).collect(Collectors.toList());

	}

}
