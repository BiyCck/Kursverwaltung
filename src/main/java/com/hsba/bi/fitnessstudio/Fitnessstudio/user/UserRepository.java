package com.hsba.bi.fitnessstudio.Fitnessstudio.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select t from Trainer t")
    List<Trainer> findAllTrainer();

    @Query("select t from Trainer t where t.id = :id")
    Optional<Trainer> findTrainerById(Long id);
}
