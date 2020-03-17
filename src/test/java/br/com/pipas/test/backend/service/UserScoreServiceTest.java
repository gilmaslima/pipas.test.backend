package br.com.pipas.test.backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
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
import org.springframework.test.context.junit4.SpringRunner;

import br.com.pipas.test.backend.model.ScoreResponse;
import br.com.pipas.test.backend.model.UserScore;
import br.com.pipas.test.backend.repository.UserScoreRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserScoreServiceTest {

	@Autowired
	private UserScoreService service;

	@MockBean
	private UserScoreRepository repository;

	@Test
	public void saveTest() {
		try {
			UserScore userScore = createUserScore();
			Mockito.when(repository.getUnsorted(userScore)).thenReturn(Optional.of(userScore));

			Mockito.when(repository.save(userScore)).thenReturn(true);

			service.save(userScore);

			verify(repository, times(1)).getUnsorted(userScore);
			verify(repository, times(1)).save(userScore);

		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}
	
	@Test
	public void saveWithPointNullTest() {
		try {
			UserScore userScore = new UserScore(1, null);
			Mockito.when(repository.getUnsorted(userScore)).thenReturn(Optional.ofNullable(null));

			Mockito.when(repository.save(userScore)).thenReturn(true);

			service.save(userScore);

			verify(repository, times(1)).getUnsorted(userScore);
			verify(repository, times(1)).save(userScore);

		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}
	
	
	
	@Test
	public void getPositionTest() {
		
		try {
			
			UserScore user = createUserScore();
			ScoreResponse score = ScoreResponse.createFromUserScore(user, 1);
			Mockito.when(repository.getUserScoreById(1)).thenReturn(Optional.of(score));
			
			Optional<ScoreResponse> optional = service.getPosition(1);
			
			assertEquals(score, optional.get());
			
			verify(repository, times(1)).getUserScoreById(1);
			
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}
		
	}
	

	@Test
	public void getHighScoreListtest() {
		try {
			
			UserScore user = createUserScore();
			List<UserScore> list = new ArrayList<>();
			list.add(user);
			Mockito.when(repository.getHighScoreList()).thenReturn(list);
			
			List<ScoreResponse> scores = service.getHighScoreList();
			
			assertEquals(user.getUserId(), scores.get(0).getUserId());
			
			verify(repository, times(1)).getHighScoreList();
			
			
		}catch (Exception e) {
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
	
	
	private UserScore createUserScore() {
		return new UserScore(1, 20);
	}

	public void reset() {
		Mockito.reset(repository);
	}

	@Configuration
	@Import(UserScoreService.class)
	static class TestConfig {
		@Bean
		UserScoreRepository userScoreRepository() {
			return Mockito.mock(UserScoreRepository.class);
		}
	}
}
