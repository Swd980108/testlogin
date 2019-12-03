package com.test.demo.controller;

import com.test.demo.pojo.User;
import com.test.demo.server.UserServer;
import net.sf.json.JSON;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import static com.test.demo.util.MD5.getMD5;

//@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserServer userServer;

    //查询所有用户
    @RequestMapping("/listUser")
    public List<User> listUser(){
        List<User> users = userServer.listUser();
        for(User u : users){
            System.out.println("用户信息："+u);
        }
        return users;
    }

    //登录的方法
    @RequestMapping("/login")
    public HashMap<String,Object> login(@Param("username") String username , @Param("password") String  password , HttpServletRequest request){
        System.out.println();
        HashMap map = new HashMap();
        //对密码进行MD5加密
        String md5password = getMD5(password);
        System.out.println("username:" + username + "--- password:" + password + ",--- md5password:"+md5password);
        //调用login方法
        User loginuser = userServer.login(username, md5password);
        if(loginuser != null){
            HttpSession session = request.getSession();
            //将登陆成功的用户信息存入session中
            session.setAttribute("loginuser",loginuser);
            User loguser = (User) session.getAttribute("loginuser");
            System.out.println("登录时存的session用户信息："+loguser);

            //将登陆成功的信息存入redis中
            redisTemplate.opsForValue().set("loginUser:" + loginuser.getUserid(), session.getId());
//            redisTemplate.boundHashOps("loginuserid:"+loginuser.getUserid()).put("userid",loginuser.getUserid()+"");
//            redisTemplate.boundHashOps("loginuserid:"+loginuser.getUserid()).put("username",loginuser.getUsername()+"");
//            redisTemplate.boundHashOps("loginuserid:"+loginuser.getUserid()).put("password",loginuser.getPassword()+"");

            map.put("code",1);
            map.put("mes","登录成功！！");
            map.put("data",loginuser);
            return map;
        }else {
            map.put("code",0);
            map.put("mes","登录失败！！");
            map.put("data",null);
            return map;
        }
    }

    //判断注册时，用户名时候存在的情况
    @RequestMapping("/existUserbyName")
    public boolean existUserbyName(String username){
        User user = userServer.existUserbyName(username);
        if (user==null){
            //查找不到username的用户，返回false
            return false;
        }else {
            //表示存在username的用户，返回true
            return true;
        }
    }

    //注册功能
    @RequestMapping("/addUser")
    public boolean addUser(User user){
        //添加用户信息的时候，应该先去判断该用户的用户名存在，如果存在，返回false
        //如果不存在，才会将用户信息添加到user表中
        User exituser = userServer.existUserbyName(user.getUsername());
        if(exituser == null){
            //注册时，首先对密码进行MD5加密
            String md5password = getMD5(user.getPassword());
            user.setPassword(md5password);
            //添加用户信息到user表中
            //一旦添加成功，会返回1；否则，返回0
            int i = userServer.addUser(user);
            if(i==1){

                return true;
            }else {
                return false;
            }
        }else {
            //用户用户名存在，添加失败
            return false;
        }
    }


    //处理文件上传
    @RequestMapping(value = "/Uploadimg", method = RequestMethod.POST)
    public String uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String filePath = "src/main/resources/static/img/";
        if (file.isEmpty()) {
            return "文件为空！";
        }
        try {
            uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
        // TODO: handle exception
        }
        //返回json
        return "上传成功";
    }

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath +"/"+ fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 判断是否登陆了
     * 登陆的信息是否与redis中的信息一致
     * @return
     */
    @RequestMapping(value = "/iflogin" , method = {RequestMethod.GET})
    public String iflogin(int userid , HttpServletRequest request){
        HttpSession session = request.getSession();
        User loginuser = (User) session.getAttribute("loginuser");
        System.out.println("判断用户是否登陆loginuser:"+loginuser);
//        if(loginuser != null){
//            String loginsessionid = redisTemplate.opsForValue().get("loginUser:"+(long)loginuser.getUserid());
//            String sessionID= session.getId();
//            System.out.println("loginsessionid:::"+loginsessionid);
//            System.out.println("sessionID::"+sessionID);
//            return "yes";
//        }else {
//            System.out.println("未登录");
//            return "no";
//        }
        String user = redisTemplate.opsForValue().get("loginUser:"+userid);
        System.out.println(user);
        return "ok";
    }


    /**
     * 处理用户退出的操作
     * @return
     */
    @RequestMapping("/logout")
    public String logout(int userid , HttpServletRequest request){
//        HttpSession session = request.getSession();
//        User loginuser = (User) session.getAttribute("loginuser");
//        System.out.println("logout时用户的session:"+loginuser);
//        if(loginuser!=null){
//            //销毁session
//            session.removeAttribute("loginuser");
////            System.out.println("loginut:redis:"+redisTemplate.opsForValue().get("loginUser:"+(long)loginuser.getUserid()));;
////            redisTemplate.delete("loginUser:"+(long)loginuser.getUserid());
//            return "yes";
//        }else {
//            return "no";
//        }
        redisTemplate.delete("loginUser:"+userid);
        System.out.println("yes");
        return "yes";
    }

}
