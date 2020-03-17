Java back-end developer test
============================

## Overview
Backend application to save and retrieve user's score.
[more info](https://github.com/gilmaslima/pipas.test.backend/blob/master/Java%20back-end%20developer%20test.pdf)


## Technology chosen

Program language:
* Java 8 

Frameworks:
* Spring Boot - used to dependency injection and inversion of control, rest layer, and application logs.
* Lombok - Get/Set declarations, constructors and builders for Java classes.
* Swaggwer - rest endpoint documentations.
* Mockito - used with Spring-boot-test for Mock and Verify unit tests.


Build:
* Maven - was used in project structure and packaging artifact a fat JAR to deploy.

Code coverage:
* Jacoco - integrated into the Maven's build to provides a unit test coverage report.


## Application layers
The application use the follow package division:
* ``br.com.pipas.test.backend`` - root package, classes:
[Application](https://github.com/gilmaslima/pipas.test.backend/blob/master/src/main/java/br/com/pipas/test/backend/Application.java) - Used to start application and initialize Spring context.

* ``br.com.pipas.test.backend.controller`` - in this package has the rest controller's classes:
[UserScoreController](https://github.com/gilmaslima/pipas.test.backend/blob/master/src/main/java/br/com/pipas/test/backend/controller/UserScoreController.java)
- createScore method 
  > makes available the rest entry POST /score
  > example request: 
  > {
  >  "userId": 2,
  >  "points": 50
  >	}
  >
  > responses:
  > HTTP 200 saved 
  > HTTP 400 invalid request
  > HTTP 500 generic error

- getPosition method
  > makes available GET /{userId}/position
  > is used the userId in path variable
  > response example:
  > {
  >  "userId": 2,
  >  "score": 200,
  >  "position": 2
  >	}
  >
  > responses:
  > HTTP 200 on return payload
  > HTTP 500 generic error

- getHighScoreList method
  > makes available GET /highscorelist
  > return the top 20 highest score
  > response example:
  > {
  >  "highscores": [
  >      {
  >          "userId": 45,
  >          "score": 400,
  >          "position": 1
  >      },
  >      {
  >          "userId": 2,
  >          "score": 200,
  >          "position": 2
  >      },
  >      {
  >          "userId": 3,
  >          "score": 2,
  >          "position": 3
  >      }
  >  ]
  >	}
  >
  > responses:
  > HTTP 200 on return payload
  > HTTP 500 generic error


* ``br.com.pipas.test.backend.model`` - in this package there are objects that represent request and response data:
[EmptyJsonResponse](https://github.com/gilmaslima/pipas.test.backend/blob/master/src/main/java/br/com/pipas/test/backend/model/EmptyJsonResponse.java) - Used to return a empty response.

[ScoreResponse](https://github.com/gilmaslima/pipas.test.backend/blob/master/src/main/java/br/com/pipas/test/backend/model/ScoreResponse.java) - Used to return Json object for "/{userId}/position" and  "/highscorelist".

[UserScore](https://github.com/gilmaslima/pipas.test.backend/blob/master/src/main/java/br/com/pipas/test/backend/model/UserScore.java) - Used in "/score" request to save data.


* ``br.com.pipas.test.backend.repository`` - has the in memory repository classes:
[UserScoreRepository](https://github.com/gilmaslima/pipas.test.backend/blob/master/src/main/java/br/com/pipas/test/backend/repository/UserScoreRepository.java) - Java class to manipulate in memory data.


* ``br.com.pipas.test.backend.service`` - Service application layer.
[UserScoreService](https://github.com/gilmaslima/pipas.test.backend/blob/master/src/main/java/br/com/pipas/test/backend/service/UserScoreService.java) - In this class has the aplication's business logic.



## Configuração da aplicação
``src/main/resources``<br>
[application.properties](https://github.com/gilmaslima/pipas.test.backend/blob/master/src/main/resources/application.properties) - Application configuration:
    
	logging.level.root=INFO
	logging.level.org.springframework.web=INFO
	server.port=8000
