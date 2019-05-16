package com.qf.dao;

import com.qf.day84.Day84Application;
import com.qf.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Day84Application.class)
public class StudentMapperTest {
    @Autowired
    StudentMapper studentMapper;

    @Test
    public void test(){
        System.out.println(studentMapper);
    }

    @Test
    public void testAdd(){
        Student student = new Student();
        student.setUsername("zs");
        int insert = studentMapper.insert(student);
        System.out.println(insert);
    }
}
