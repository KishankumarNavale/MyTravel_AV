package org.mla.cms.service;

import org.mla.cms.model.Feedbacks;
import org.mla.cms.model.Users;
import org.mla.cms.repo.FeedbackRepository;
import org.mla.cms.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedbacks createFeedback(Feedbacks feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedbacks> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public void deleteFeedback(Integer feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
