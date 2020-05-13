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

    public BasicSettingOnDB insertManagerAccountIntoDB()  {
        if (accountService.findAccountsByRoleType(RoleEnum.ROLE_MANAGER).size() != 1) {
            accountService.deleteAccountsByRole(RoleEnum.ROLE_MANAGER);
            RegisteredUserInfo managerInfo = RegisteredUserInfo.getRegisteredUserInfoBuilder()
                    .setFirstName("Omid")
                    .setLastName("Rahmani")
                    .setEmail("omid.rahmani.ie@gmail.com")
                    .setIsEnable(true)
                    .setRoleType("manager")
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
        admin.setRoleType(RoleEnum.ROLE_SUPER_ADMIN);
        manager.setRoleType(RoleEnum.ROLE_MANAGER);
        student.setRoleType(RoleEnum.ROLE_STUDENT);
        teacher.setRoleType(RoleEnum.ROLE_TEACHER);
        roleRepository.save(admin);
        roleRepository.save(student);
        roleRepository.save(teacher);
        roleRepository.save(manager);
    }
}
