package com.csc.controller;



import com.csc.model.Student;
import com.csc.model.UserAccount;
import com.csc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller

public class StudentController {


    @Autowired
    private StudentService studentService;
    // display list of students

    @GetMapping("/student_index")
    public String viewHomePage(Model model) {
        return findPaginatedStudent(1, "firstName", "asc", model);
    }

    @GetMapping("/showNewStudentForm")
    public String showNewStudentForm(Model model) {
        // create model attribute to bind form data
        Student student = new Student();
        model.addAttribute("student", student);
        return "new_student";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student) {
        // save Student to database
        studentService.saveStudent(student);
        return "redirect:student_index";
    }

    @GetMapping("/showStudentFormForUpdate/{id}")
    public String showStudentFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get student from the service
        Student student = studentService.getStudentById(id);

        // set Student as a model attribute to pre-populate the form
        model.addAttribute("student", student);
        return "update_student";
    }

    @GetMapping("/deleteStudentForm/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id) {

        // call delete Student method
        this.studentService.deleteStudentById(id);
        return "redirect:/student_index";
    }


    @GetMapping("/studentpage/{pageNo}")
    public String findPaginatedStudent(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page < Student > page = studentService.findPaginatedStudent(pageNo, pageSize, sortField, sortDir);
        List < Student > listStudents = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listStudents", listStudents);
        return "student_index";
    }

   /* @GetMapping("/register")
    public String register(Model model) {
        UserAccount userAccount = new UserAccount();
        model.addAttribute("userAccount", userAccount);

        return "security/register";
    }
*/

}