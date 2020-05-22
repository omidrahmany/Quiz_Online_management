package io.spring.quiz_online.repositories;

import io.spring.quiz_online.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {

}
