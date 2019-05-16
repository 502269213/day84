package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_student")
public class Student {
    @TableId(type=IdType.AUTO)
    private int id;
    private String username;
    private String password;
    private int sex;
    private String email;
    private Date birthday;
}
