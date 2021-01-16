package com.csc.service;

import java.util.List;

import java.util.Optional;


import com.csc.model.Student;
import com.csc.repository.EmployeeRepository;
import com.csc.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentsRepository studentsRepository;

    @Override
    public List <Student> getAllStudents() {
        return studentsRepository.findAll();
    }
    @Override
    public void saveStudent(Student student) {
        this.studentsRepository.save(student);
    }
    @Override
    public Student getStudentById(long id) {
        Optional < Student > optional = studentsRepository.findById(id);
        Student student = null;
        if (optional.isPresent()) {
            student = optional.get();
        } else {
            throw new RuntimeException(" Student not found for id :: " + id);
        }
        return student;
    }

    @Override
    public void deleteStudentById(long id) {
        this.studentsRepository.deleteById(id);
    }
    @Override
    public Page<Student> findPaginatedStudent(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.studentsRepository.findAll(pageable);
    }

    @Override
    public Page<Student> findPaginatedStudent(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.studentsRepository.findAll(pageable);
    }

}
