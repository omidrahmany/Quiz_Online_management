package io.spring.quiz_online.config;


import io.spring.quiz_online.model.*;
import io.spring.quiz_online.repositories.RoleRepository;
import io.spring.quiz_online.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BasicSettingOnDB {


    private RoleRepository roleRepository;
    private AccountService accountService;


    @Autowired
    public BasicSettingOnDB(RoleRepository roleRepository, AccountService accountService) {
        this.roleRepository = roleRepository;
        this.accountService = accountService;
    }


    public BasicSettingOnDB insertRolesIntoDB() {
        List<Role> roles = roleRepository.findAll();
        if (roles.size() != 4) {
            roleRepository.deleteAll();
            createRole();
        }
        return this;
    }

    public BasicSettingOnDB insertManagerAccountIntoDB() {
        if (accountService.findAccountsByRoleType(RoleEnum.MANAGER).size() != 1) {
            accountService.deleteAccountsByRole(RoleEnum.MANAGER);
            RegisteredUserInfo managerInfo = RegisteredUserInfo.getRegisteredUserInfoBuilder()
                    .setFirstName("Omid")
                    .setLastName("Rahmani")
                    .setEmail("omid.rahmani.ie@gmail.com")
                    .setIsEnable(true)
                    .setRole("MANAGER")
                    .setUsername("manager")
                    .setPassword("manager")
                    .createRegisteredUserInfo();
            accountService.register(managerInfo);
        }
        return this;
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
