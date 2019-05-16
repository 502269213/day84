package com.qf.controller;

import com.qf.entity.Student;
import com.qf.service.IStudentService;
import com.qf.util.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping(value = "/studentController")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @RequestMapping(value = "getPage")
    public String getPage(Model model){
        List<Student> list = studentService.getPage();
        model.addAttribute("list",list);
        return "studentList";
    }
    @RequestMapping(value = "/toAddStudent")
    public String toAddStudent(){
        return "studentAdd";
    }
/*    @RequestMapping(value = "/addStudent")
    public String addStudent(Student student){
        student.setBirthday(new Date());
        studentService.addStudent(student);
        return "redirect:/studentController/getPage";
    }*/
    @RequestMapping(value = "toUpdateStudent")
    public String toUpdateStudent(int id,Model model){
        Student student = studentService.getStudentById(id);
        model.addAttribute("student",student);
        return "studentUpdate";
    }
    @RequestMapping(value = "/updateStudent")
    public String updateStudent(Student student){
        student.setBirthday(new Date());
        studentService.updateStudent(student);
        return "redirect:/studentController/getPage";
    }
    @RequestMapping(value = "/deleteStudent")
    public String deleteStudent(int id){
        studentService.deleteStudent(id);
        return "redirect:/studentController/getPage";
    }

    /*整合邮箱认证和找回密码*/
    @RequestMapping(value="/addStudent")
    public String addStudent(Student student,String code,HttpServletRequest request){
        student.setBirthday(new Date());
        String server_code = (String) request.getSession().getAttribute("server_code");
        if(checkCode(code,server_code)){//调用判定验证码是否正确的方法
            studentService.addStudent(student);
            return "login";
        }
        return "error";
    }

    private boolean checkCode(String code,String server_code) {
        if(code.equals(server_code)){
            return true;
        }
        return false;
    }

    /**
     * 发送验证码邮件的方法
     * @throws Exception
     */
    @RequestMapping(value="/sendCodeEmail")
    @ResponseBody
    public String sendCodeEmail(String email, HttpServletRequest request){
        String code = getCode();
        String receiverAddress = email;
        String title = "注册验证码通知";
        String content = "您正在进行xxx网站的注册的操作,您此次操作的验证码为:"+code;
        try {
            SendEmail.SendEmailInfoUser(receiverAddress, title, content, "502269213@qq.com");
        } catch (Exception e) {
            return "0";
        }
        request.getSession().setAttribute("server_code",code);
        return "1";
    }
    /**
     * 获取验证码的方法
     */
    private String getCode() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 找回密码,发送改密链接给用户
     */
    @RequestMapping(value = "toFindPassword")
    @ResponseBody
    public String toFindPassword(String username){
        Student user = studentService.getUserByUsername(username);
        if (user!=null){//判断用户是否存在
            String email = user.getEmail();
            String title = "您正在进行用户名为:"+username+"的密码找回操作";
            String content = "您正在进行用户名为:"+username+"的密码找回操作,请点击以下链接进行密码重置:" +
                    "<h2><a href='http://localhost:8080/studentController/toResetPassword?username="+username+"'>[点击此处]</a></h2>";
            try {
                SendEmail.SendEmailInfoUser(email,title,content,email);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "1";
        }
        return "0";
    }
    /**
     * 到达重置密码的页面
     */
    @RequestMapping(value = "toResetPassword")
    public String toResetPassword(String username, Model model){
        model.addAttribute("username",username);
        return "resetPassword";
    }
    /**
     * 找回密码的重置密码
     */
    @RequestMapping(value = "/resetPassword")
    @ResponseBody
    public String resetPassword(String username,String password){
        int result = studentService.resetPassword(username,password);
        if (result > 0){
            return "1";
        }
        return "0";
    }
    /**
     * 跳转到找回密码页面
     */
    @RequestMapping(value = "/toFindPage")
    public String toFindPage(){
        return "findPassword";
    }

    @RequestMapping(value = "toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login(String username,String password,HttpServletRequest request){
        Student student = studentService.login(username,password);
        if(student != null){
            request.getSession().setAttribute("student",student);
            return "index";
        }
        return "error";
    }
}
