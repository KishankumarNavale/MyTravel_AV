package org.mla.cms.repo;


import org.mla.cms.model.Complaints;
import org.mla.cms.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintsRepository extends JpaRepository<Complaints, Integer> {
}
