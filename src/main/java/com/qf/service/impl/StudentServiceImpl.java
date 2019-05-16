package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.StudentMapper;
import com.qf.entity.Student;
import com.qf.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> getPage() {
        return studentMapper.selectList(null);
    }

    @Override
    public int addStudent(Student student) {
        return studentMapper.insert(student);
    }

    @Override
    public Student getStudentById(int id) {
        return studentMapper.selectById(id);
    }

    @Override
    public int deleteStudent(int id) {
        return studentMapper.deleteById(id);
    }

    @Override
    public int updateStudent(Student student) {
        return studentMapper.updateById(student);
    }

    @Override
    public Student getUserByUsername(String username) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return studentMapper.selectOne(queryWrapper);
    }

    @Override
    public int resetPassword(String username, String password) {
        Student student = getUserByUsername(username);
        student.setPassword(password);
        student.setBirthday(new Date());
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return studentMapper.update(student,queryWrapper);
    }

    @Override
    public Student login(String username, String password) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        queryWrapper.allEq(map);
        return studentMapper.selectOne(queryWrapper);
    }
}
