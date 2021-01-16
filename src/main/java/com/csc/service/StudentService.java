package com.csc.service;


import com.csc.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;



public interface StudentService {
    List<Student> getAllStudents();
    void saveStudent(Student student);
    Student getStudentById(long id);
    void deleteStudentById(long id);
    Page<Student> findPaginatedStudent(int pageNo, int pageSize);
    Page<Student> findPaginatedStudent(int pageNo, int pageSize, String sortField, String sortDirection);
}