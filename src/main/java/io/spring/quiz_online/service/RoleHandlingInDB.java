package io.spring.quiz_online.service;


import io.spring.quiz_online.model.Role;
import io.spring.quiz_online.model.RoleEnum;
import io.spring.quiz_online.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleHandlingInDB {


    private RoleRepository roleRepository;

    @Autowired
    public RoleHandlingInDB(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void createRolesInDB() {
        List<Role> roles = roleRepository.findAll();
        if (roles.size() != 4) {
            roleRepository.deleteAll();
            createRole();
        }
    }


    private void createRole() {
        Role admin = new Role();
        Role student = new Role();
        Role teacher = new Role();
        Role manager = new Role();
        admin.setRole(RoleEnum.SUPER_ADMIN);
        manager.setRole(RoleEnum.MANAGER);
        student.setRole(RoleEnum.STUDENT);
        teacher.setRole(RoleEnum.TEACHER);
        roleRepository.save(admin);
        roleRepository.save(student);
        roleRepository.save(teacher);
        roleRepository.save(manager);
    }
}
