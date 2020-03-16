package br.com.pipas.test.backend.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserScoreRepositoryTest {

	@MockBean
	private Set<UserScore> data;

	@Autowired
	private UserScoreRepository repository;

	@Test
	public void save() {
		try {
			UserScore userScore = new UserScore(1, 20);
			Mockito.when(data.add(userScore)).thenReturn(true);

			repository.save(userScore);

			verify(data, atLeastOnce()).add(userScore);
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	@Test
	public void getUserScoreByIdTest() {

		try {
			List<UserScore> list = new ArrayList();
			UserScore userScore = new UserScore(1, 20);
			list.add(userScore);

			Optional<UserScore> optional = Optional.of(userScore);

			Stream<UserScore> stream = Mockito.mock(Stream.class);

			Mockito.when(stream.sorted(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.collect(Mockito.any())).thenReturn(list);
			Mockito.when(stream.filter(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.findFirst()).thenReturn(optional);

			Mockito.when(data.stream()).thenReturn(stream);

			Optional<ScoreResponse> result = repository.getUserScoreById(1);

			assertTrue(result.isPresent());

			assertEquals(userScore.getUserId(), result.get().getUserId());
			assertEquals(userScore.getPoints(), result.get().getScore());

			verify(data, atLeastOnce()).stream();

		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	@Test
	public void getUserScoreByIdNotFoundTest() {

		try {
			List<UserScore> list = new ArrayList();

			Stream<UserScore> stream = Mockito.mock(Stream.class);

			Mockito.when(stream.sorted(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.collect(Mockito.any())).thenReturn(list);
			Mockito.when(stream.filter(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.findFirst()).thenReturn(Optional.ofNullable(null));

			Mockito.when(data.stream()).thenReturn(stream);

			Optional<ScoreResponse> result = repository.getUserScoreById(1);

			assertTrue(result.isEmpty());

			verify(data, atLeastOnce()).stream();

		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	@Test
	public void getUnsortedTest() {
		try {

			List<UserScore> list = new ArrayList();
			UserScore userScore = new UserScore(1, 20);
			list.add(userScore);

			Optional<UserScore> optional = Optional.of(userScore);

			Stream<UserScore> stream = Mockito.mock(Stream.class);

			Mockito.when(stream.sorted(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.collect(Mockito.any())).thenReturn(list);
			Mockito.when(stream.filter(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.findFirst()).thenReturn(optional);

			Mockito.when(data.stream()).thenReturn(stream);

			Optional<UserScore> unsorted = repository.getUnsorted(userScore);

			assertTrue(unsorted.isPresent());

			assertEquals(userScore.getUserId(), unsorted.get().getUserId());
			assertEquals(userScore.getPoints(), unsorted.get().getPoints());

			verify(data, atLeastOnce()).stream();

		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}
	}

	@Test
	public void removeTest() {

		try {

			UserScore userScore = new UserScore(1, 20);

			Mockito.when(data.remove(userScore)).thenReturn(true);

			repository.remove(userScore);

			verify(data, atLeastOnce()).remove(userScore);

		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}

	}

	@Test
	public void getHighScoreListTest() {
		
		try {
		
			List<UserScore> list = new ArrayList();
			UserScore userScore = new UserScore(1, 20);
			list.add(userScore);

			Optional<UserScore> optional = Optional.of(userScore);

			Stream<UserScore> stream = Mockito.mock(Stream.class);

			Mockito.when(stream.sorted(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.collect(Mockito.any())).thenReturn(list);
			Mockito.when(stream.filter(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.findFirst()).thenReturn(optional);

			Mockito.when(data.stream()).thenReturn(stream);
			
			List<UserScore> highScoreList = repository.getHighScoreList();
			
			assertEquals(list.get(0), highScoreList.get(0));
			
			verify(data, atLeastOnce()).stream();
			
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}
		
	}
	
	@Test
	public void getHighScoreListWhithSubListTest() {
		
		try {
		
			List<UserScore> list = new ArrayList();
			UserScore userScore = new UserScore(1, 500);
			list.add(userScore);

			for (int i = 2; i < 35; i++) {
				
				list.add(new UserScore(i, i));
			}
			
			Optional<UserScore> optional = Optional.of(userScore);

			Stream<UserScore> stream = Mockito.mock(Stream.class);

			Mockito.when(stream.sorted(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.collect(Mockito.any())).thenReturn(list);
			Mockito.when(stream.filter(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.findFirst()).thenReturn(optional);

			Mockito.when(data.stream()).thenReturn(stream);
			
			List<UserScore> highScoreList = repository.getHighScoreList();
			
			assertEquals(list.get(0), highScoreList.get(0));
			
			verify(data, atLeastOnce()).stream();
			
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}
		
	}
	
	@Test
	public void getHighScoreListWhithEmptyListTest() {
		
		try {
		
			List<UserScore> list = new ArrayList();
						
			Optional<UserScore> optional = Optional.ofNullable(null);

			Stream<UserScore> stream = Mockito.mock(Stream.class);

			Mockito.when(stream.sorted(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.collect(Mockito.any())).thenReturn(list);
			Mockito.when(stream.filter(Mockito.any())).thenReturn(stream);

			Mockito.when(stream.findFirst()).thenReturn(optional);

			Mockito.when(data.stream()).thenReturn(stream);
			
			List<UserScore> highScoreList = repository.getHighScoreList();
			
			assertTrue(highScoreList.isEmpty());
			
			verify(data, atLeastOnce()).stream();
			
		} catch (Exception e) {
			fail();
		} finally {
			reset();
		}
		
	}

	
	public void reset() {
		Mockito.reset(data);
	}

	@Configuration
	@Import(UserScoreRepository.class)
	static class TestConfig {
		@Bean
		Set<UserScore> getData() {
			return Mockito.mock(HashSet.class);
		}
	}

}
