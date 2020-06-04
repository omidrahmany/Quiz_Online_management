package io.spring.quiz_online.repositories;

import io.spring.quiz_online.model.Course;
import io.spring.quiz_online.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByAccount_Enabled(boolean account_enabled);
    Optional<Student> findByAccount_Email(String email);
}
