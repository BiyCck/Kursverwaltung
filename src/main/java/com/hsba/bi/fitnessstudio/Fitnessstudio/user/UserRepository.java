package com.hsba.bi.fitnessstudio.Fitnessstudio.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository für User- und Trainer-Objekt
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Methode um alle Trainer zu erhalten
    @Query("select t from Trainer t")
    List<Trainer> findAllTrainer();

    //Methode um alle Trainer mit entsprechender ID zu erhalten
    @Query("select t from Trainer t where t.id = :id")
    Optional<Trainer> findTrainerById(@Param("id") Long id);

    //Methode um alle Trainer mit entsprechendem Usernamen zu erhalten
    @Query("select t from Trainer t where t.username = :username")
    Optional<Trainer> findTrainerByUsername(@Param("username") String name);

    //Methode um alle Trainer mit entsprechendem Kurs zu erhalten
    @Query("select t from Trainer t join t.courses c where c.id = :id")
    List<Trainer> findTrainersByCourse(@Param("id") Long id);

    User findDistinctByUsername(String username);

    List<User> findByRole(String role);
}
