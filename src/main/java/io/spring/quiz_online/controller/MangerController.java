package io.spring.quiz_online.controller;

import io.spring.quiz_online.dto.AccountDto;
import io.spring.quiz_online.dto.TeacherDto;
import io.spring.quiz_online.model.Course;
import io.spring.quiz_online.model.ResultMsg;
import io.spring.quiz_online.service.AccountService;
import io.spring.quiz_online.service.AuthenticationFacade;
import io.spring.quiz_online.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/manager")
public class MangerController {

    private AuthenticationFacade authenticationFacade;
    private AccountService accountService;
    private CourseService courseService;

    public MangerController(CourseService courseService,AuthenticationFacade authenticationFacade, AccountService accountService) {
        this.authenticationFacade = authenticationFacade;
        this.accountService = accountService;
        this.courseService = courseService;
    }

    @GetMapping("/get-manager-info")
    public AccountDto getMangerInfo() {
        String username = authenticationFacade.getAuthentication().getName();
        return accountService.findByUsername(username);
    }


    @GetMapping("/get-non-active-accounts")
    public List<AccountDto> getAccountsInactivated() {
        return accountService.findAccountsNotEnabled();
    }

    @DeleteMapping("/delete-account/{accountId}")
    public void deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
    }

    @GetMapping("/get-account/{accountId}")
    public AccountDto getStudentById(@PathVariable Long accountId){
        return accountService.findById(accountId);
    }

    @PutMapping("/update-account")
    public ResultMsg updateAccount(@RequestBody AccountDto accountDto){
        accountService.updateAccount(accountDto);
        return new ResultMsg( " اعمال تغییرات با موفقیت انجام شد. " , true);
    }


    // return all accounts but manager account. all teachers and students accounts (enabled and disabled accounts)
    @GetMapping("/get-all-accounts")
    public List<AccountDto> getAllAccounts(){
        return accountService.findAllTeachersAndStudentsRole();
    }



    // return enabled teacher accounts to assign to courses.
    @GetMapping("/get-all-active-teachers")
    public List<TeacherDto> getAllTeachers(){
        return courseService.findAllTeachersByAccountEnabled();
    }

    @GetMapping("/get-all-courses")
    public List<Course> getAllCourses(){
        return courseService.findAll();
    }


}
