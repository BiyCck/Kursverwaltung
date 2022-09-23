package com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.repository;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @abakhtiari Repository für Kurse
 */

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
