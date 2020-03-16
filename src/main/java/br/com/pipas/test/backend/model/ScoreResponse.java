package br.com.pipas.test.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreResponse {

	private Integer userId;
	
	private Integer score;
	
	private Integer position;

	public static ScoreResponse createFromUserScore(UserScore u, Integer position) {
		ScoreResponse response = new ScoreResponse();
		response.setScore(u.getPoints());
		response.setUserId(u.getUserId());
		response.setPosition(position);
		return response;
	}
	
	
}
