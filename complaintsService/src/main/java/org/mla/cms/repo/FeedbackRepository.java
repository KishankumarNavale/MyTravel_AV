package org.mla.cms.repo;


import org.mla.cms.model.Feedbacks;
import org.mla.cms.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedbacks, Integer> {
}
