package br.com.pipas.test.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pipas.test.backend.model.EmptyJsonResponse;
import br.com.pipas.test.backend.model.ScoreResponse;
import br.com.pipas.test.backend.model.UserScore;
import br.com.pipas.test.backend.service.UserScoreService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@Slf4j
public class UserScoreController {

	@Autowired
	private UserScoreService service;

	@PostMapping(path = "/score", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity createScore(@Valid @RequestBody UserScore userScore) {
		try {
			service.save(userScore);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("unexpected error", e);
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{userId}/position", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity getPosition(@PathVariable Integer userId) {
		try {
			log.info("Getting position for userId: {}", userId);
			Optional<ScoreResponse> optional = service.getPosition(userId);
			if (optional.isPresent()) {
				return ResponseEntity.ok().body(optional.get());
			}
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("unexpected error", e);
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/highscorelist", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity getHighScoreList() {
		try {
			HashMap<String, List<ScoreResponse>> map = new HashMap<>();
			map.put("highscores", service.getHighScoreList());

			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			log.error("unexpected error", e);
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
