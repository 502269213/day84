package com.qf.service;

import com.qf.entity.Student;

import java.util.List;

public interface IStudentService {
    List<Student> getPage();

    int addStudent(Student student);

    Student getStudentById(int id);

    int deleteStudent(int id);

    int updateStudent(Student student);

    Student getUserByUsername(String username);

    int resetPassword(String username, String password);

    Student login(String username, String password);
}
