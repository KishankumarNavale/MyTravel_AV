package org.mla.cms.controller;


import lombok.AllArgsConstructor;
import org.mla.cms.model.Feedbacks;
import org.mla.cms.model.Users;
import org.mla.cms.service.FeedbackService;
import org.mla.cms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/feedbacks")
public class FeedbackController {

    private FeedbackService feedbackService;

    // build create Users REST API
    @PostMapping
    public ResponseEntity<Feedbacks> createUser(@RequestBody Feedbacks feedback){
        Feedbacks feedbacks = feedbackService.createFeedback(feedback);
        return new ResponseEntity<>(feedbacks, HttpStatus.CREATED);
    }

    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<Feedbacks>> getAllFeedbacks(){
        List<Feedbacks> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Build Delete Users REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable("id") Integer feedbackId){
        feedbackService.deleteFeedback(feedbackId);
        return new ResponseEntity<>("Feedback successfully deleted!", HttpStatus.OK);
    }
}