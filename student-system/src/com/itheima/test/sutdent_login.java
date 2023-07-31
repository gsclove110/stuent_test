package com.itheima.test;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class sutdent_login {
    public static void main(String[] args) throws Exception {
        while (true) {
            //登入界面初始化设置
            System.out.println("-----------------------欢迎来到学生管理系统-----------------------");
            System.out.println("请选择操作 1：登入 2：注册 3：忘记密码 4:退出");

            //键盘输入用户的选择
            Scanner sc = new Scanner(System.in);
            //接收用户的选择
            String choose = sc.next();

            //判断用户的选择
            switch (choose){
                case "1" :
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    forgetPassword();
                    break;
                case "4":
                    System.out.println("退出");
                    System.exit(0);
                default:
                    System.out.println("没有这个选项");
                    break;
            }
        }
    }


    //完善学生管理系统的3个功能

    //1.登入功能
    public static void login(){
        System.out.println("登入");
    }

    //2.注册功能
    public static void register() throws Exception {
        System.out.println("注册");
        //获取当前数据库的集合
        List<User> list = getList();

        //键盘输入用户名
        Scanner sc = new Scanner(System.in);


        while (true) {
            //接收用户名
            System.out.println("请输入需要注册的用户名");
            String username = sc.next();
            //验证用户名的格式是否满足条件,生成一个方法
            boolean flag = checkUsername(username);

            if (!flag){
                //用户名格式不正确
                System.out.println("用户名格式不正确，请重新输入");
                continue;
            }

            //到此用户名格式正确

            //判断用户名是否已经存在

            boolean flag1 = contains(list,username);

            if (flag1){
                System.out.println("用户名已经存在");
            }else {
                //用户名不存在
                break;
            }
        }

        //用户名可以正常使用

        while (true) {
            //输入密码
            System.out.println("请输入密码");
            String password = sc.next();

            System.out.println("请再次输入密码");
            String againpassword = sc.next();

            //判断两次密码是否一致
            if (password.equals(againpassword)){
                //两次密码输入一致
                break;
            }else {
                //两次密码输入不一致
                System.out.println("密码输入不一致，请重新输入");
                continue;
            }
        }


    }

    //判断用户名在集合中是否存在
    private static boolean contains(List<User> list, String username) {
        //遍历集合
        for (int i = 0; i < list.size(); i++) {
            //获得集合中的用户对象
            User user = list.get(i);

            //判断用户名是否在集合中存在
            if (username.equals(user.getUsername())){
                return true;
            }

        }
        return false;
    }

    //验证用户名的格式是否正确
    private static boolean checkUsername(String username) {
        //用户名长度
        int len = username.length();

        //用户名长度必须在3~15之间
        if (len<3||len>15){
            return false;
        }

        //用户名只能是字母加数字的组合
        //遍历用户名字符串
        for (int i = 0; i < username.length(); i++) {
            //得到用户名的每个字符
            char c = username.charAt(i);
            //字母范围a~z A~Z 数字范围0~9
            if (!((c>='a'&&c<='z')||(c>='A'&&c<='Z')||(c>='0'&&c<='9'))){
                return false;
            }
        }

        //不能是纯数字 字母的数量大于1就可以

        //定义统计变量
        int count = 0 ;
        for (int i = 0; i < username.length(); i++) {
            //得到每个字符
            char c = username.charAt(i);
            //统计字符串中有多少个字符是字母
            if ((c>='a'&&c<='z')||(c>='A'&&c<='Z')){
                count++;
                break;
            }


        }
        return count>0;



    }

    //3.忘记密码
    public static void forgetPassword(){
        System.out.println("忘记密码");
    }

    //4.生成一个获取当前数据库集合的方法
    public static List<User> getList() throws Exception {
        //1.建立数据库连接
        Properties prop = new Properties();
        prop.load(new FileInputStream("C:\\Users\\admin\\IdeaProjects\\student_test01\\student-system\\Druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        Connection conn = dataSource.getConnection();

        //2.sql语句
        String sql = "select * from user";

        //3.创建执行sql语句的对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //4.设置参数

        //5.执行sql语句
        ResultSet rs = pstmt.executeQuery();

        //6.处理结果
        //创建集合，存储用户对象
        List<User> list = new ArrayList<>();
        list.clear();

        while (rs.next()){
            String username = rs.getString("username");
            String password = rs.getString("password");
            String personID = rs.getString("personID");
            String phoneNumber = rs.getString("phoneNumber");

            //创建用户对象，封装用户数据
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setPersonID(personID);
            user.setPhoneNumber(phoneNumber);
            list.add(user);

        }

        //7.关闭资源
        rs.close();
        pstmt.close();
        conn.close();


        return list;

    }


}
