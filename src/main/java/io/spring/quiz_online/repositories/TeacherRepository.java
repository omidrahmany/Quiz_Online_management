package io.spring.quiz_online.repositories;

import io.spring.quiz_online.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    List<Teacher> findAllByAccount_Enabled(boolean account_enabled);
}
