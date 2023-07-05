package com.exterro.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exterro.entity.Question;
import com.exterro.entity.UserAnswer;
import com.exterro.entity.UserEntity;
import com.exterro.repository.QuestionRepository;
import com.exterro.repository.UserAnswerRepository;
import com.exterro.request.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class FeedbackController {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserAnswerRepository userAnswerRepository;

	@GetMapping("/data/api/questions/{batch}")
	@ResponseBody
	public ResponseEntity<List<Question>> getQuestionsApi(@PathVariable String batch) {
		return ResponseEntity.ok(questionRepository.findByBatch(batch));
	}

//	@PostMapping("/question")
//
//	public ResponseEntity<String> submitQuestion(@RequestBody String qst)
//			throws JsonMappingException, JsonProcessingException {
//		ObjectMapper obj = new ObjectMapper();
//		Question question = obj.readValue(qst, Question.class);
//		questionRepository.save(question);
//		return ResponseEntity.ok("feedbackConfirmation.html");
//	}
	
	
	
	
	@PostMapping("/question")

	public ResponseEntity<String> submitQuestion(@RequestBody String qst)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper obj = new ObjectMapper();
		Question detail = obj.readValue(qst, Question.class);
		Question feedback = new Question(1, detail.getText1(), detail.getBatch());
		questionRepository.save(feedback);
		return ResponseEntity.ok("feedbackConfirmation.html");
	}
	
	
	
	
	
	
	@PostMapping("/data/api/submit")
	@ResponseBody
	public ResponseEntity<String> submitDetails(@RequestParam String uu)
	       throws JsonMappingException, JsonProcessingException {
	    ObjectMapper obj = new ObjectMapper();
	    UserRequest detail = obj.readValue(uu, UserRequest.class);
	    List<UserAnswer> userAnswers = new ArrayList<>();
	    List<String> questions = detail.getQuestion();
	    List<String> answers = detail.getAnswer();

	    if (questions.size() != answers.size()) {
	        return ResponseEntity.badRequest().body("Number of questions and answers do not match.");
	    }
	    for (int i = 0; i < questions.size(); i++) {
	        String question = questions.get(i);
	        String answer = answers.get(i);
	        System.out.println(answers.get(i));
	        if (question != null && !question.isEmpty() && answer != null && !answer.isEmpty()) {
	        	UserAnswer feedback = new UserAnswer(
	        		    new UserEntity(detail.getName(), detail.getPhoneNumber(), detail.getEmail()),
	        		    question, answer, detail.getBatch());

	            userAnswers.add(feedback);
	        }
	        else {
	        	System.out.println("Hello");
	        }
	    }
	    if (!userAnswers.isEmpty()) {
	        userAnswerRepository.saveAll(userAnswers);
	    }

	    return ResponseEntity.ok("feedbackConfirmation.html");
	}

	
	

	
	@GetMapping("/data/api/results/{batch}")
	@ResponseBody
	public ResponseEntity<List<UserAnswer>> getJavaDataApi(@PathVariable String batch) {
		if (batch.equals("allData")) {
			return ResponseEntity.ok(userAnswerRepository.findAll());
		}
		return ResponseEntity.ok(userAnswerRepository.getFeedbackBatchwise(batch));
	}
	
	
	
	
	
	

}
