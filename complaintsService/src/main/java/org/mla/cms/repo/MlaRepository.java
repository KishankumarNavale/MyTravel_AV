package org.mla.cms.repo;


import org.mla.cms.model.Mla;
import org.mla.cms.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MlaRepository extends JpaRepository<Mla, Integer> {
}
