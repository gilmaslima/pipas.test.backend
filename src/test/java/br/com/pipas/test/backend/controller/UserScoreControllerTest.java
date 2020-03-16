package br.com.pipas.test.backend.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.pipas.test.backend.model.EmptyJsonResponse;
import br.com.pipas.test.backend.model.ScoreResponse;
import br.com.pipas.test.backend.model.UserScore;
import br.com.pipas.test.backend.service.UserScoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserScoreControllerTest {

	@MockBean
	private UserScoreService service;

	@Autowired
	private UserScoreController controller;

	@Test
	public void createScoreTest() {

		try {
			UserScore userScore = createUserScore();
			doNothing().when(service).save(userScore);

			ResponseEntity response = controller.createScore(userScore);

			assertEquals(HttpStatus.OK, response.getStatusCode());

			verify(service, times(1)).save(userScore);

		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	@Test
	public void createScoreWithErrorTest() {

		try {
			UserScore userScore = createUserScore();
			doThrow(new RuntimeException()).when(service).save(userScore);

			ResponseEntity response = controller.createScore(userScore);

			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

			verify(service, times(1)).save(userScore);

		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	private UserScore createUserScore() {
		return new UserScore(1, 20);
	}

	@Test
	public void getPositionTest() {
		try {

			UserScore user = createUserScore();
			ScoreResponse score = ScoreResponse.createFromUserScore(user, 1);
			Mockito.when(service.getPosition(1)).thenReturn(Optional.of(score));
			ResponseEntity responseEntity = controller.getPosition(1);
			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			
			assertEquals(score, responseEntity.getBody());
			
			verify(service, Mockito.atLeastOnce()).getPosition(1);
			
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	@Test
	public void getPositionWithErrorTest() {
		try {

			Mockito.when(service.getPosition(1)).thenThrow(new RuntimeException());
			ResponseEntity responseEntity = controller.getPosition(1);
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
			
			verify(service, Mockito.atLeastOnce()).getPosition(1);
			
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	@Test
	public void getEmptyPositionTest() {
		try {

			Mockito.when(service.getPosition(1)).thenReturn(Optional.ofNullable(null));
			ResponseEntity responseEntity = controller.getPosition(1);
			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			
			assertTrue(responseEntity.getBody() instanceof EmptyJsonResponse);
			
			verify(service, Mockito.atLeastOnce()).getPosition(1);
			
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	
	@Test
	public void getHighScoreListWithEmptyListTest() {
		try {

			Mockito.when(service.getHighScoreList()).thenReturn(new ArrayList<>());
			ResponseEntity<HashMap<String, List<ScoreResponse>>> responseEntity = controller.getHighScoreList();
			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			
			assertTrue(responseEntity.getBody().get("highscores").isEmpty());
			
			verify(service, Mockito.atLeastOnce()).getHighScoreList();
			
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}
	

	@Test
	public void getHighScoreListWithErrorTest() {
		try {

			Mockito.when(service.getHighScoreList()).thenThrow(new RuntimeException());
			ResponseEntity responseEntity = controller.getHighScoreList();
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
			
			assertFalse(responseEntity.hasBody());
			
			verify(service, Mockito.atLeastOnce()).getHighScoreList();
			
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	
	@Test
	public void getHighScoreListTest() {
		try {

			List<ScoreResponse> list = createScoreList();
			Mockito.when(service.getHighScoreList()).thenReturn(list);
			ResponseEntity<HashMap<String, List<ScoreResponse>>> responseEntity = controller.getHighScoreList();
			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			
			assertTrue(responseEntity.hasBody());
			
			assertEquals(list.get(0), responseEntity.getBody().get("highscores").get(0));
			
			verify(service, Mockito.atLeastOnce()).getHighScoreList();
			
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	
	
	
	private List<ScoreResponse> createScoreList() {
		ArrayList<ScoreResponse> list = new ArrayList<>();
		list.add(new ScoreResponse(122, 1004, 1));
		list.add(new ScoreResponse(22, 24, 2));
		list.add(new ScoreResponse(1007, 3, 3));
		
		return list;
	}

	public void reset() {
		Mockito.reset(service);
	}

	@Configuration
	@Import(UserScoreController.class)
	static class TestConfig {
		@Bean
		UserScoreService userScoreService() {
			return Mockito.mock(UserScoreService.class);
		}
	}
}
