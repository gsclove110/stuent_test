package com.itheima;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.itheima.polo.student;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class login {
    public static void main(String[] args) throws Exception {
        while (true) {
            //构建登入界面
            System.out.println("----------------------欢迎来到黑马学生管理系统---------------------------");
            System.out.println("1：添加学生");
            System.out.println("2：删除学生");
            System.out.println("3：修改学生");
            System.out.println("4：查询学生");
            System.out.println("5：退出");
            System.out.println("请输入您的选择");

            //使用scanner用户输入选择
            Scanner sc = new Scanner(System.in);
            //接收用户的选择
            String choose = sc.next();

            //判断用户的选择使用switch
            switch (choose){
                case "1" :
                    addStudent();
                    break;
                case "2" :
                    deleteStudent();
                    break;
                case "3" :
                   updateStudent();
                    break;
                case "4" :
                    queryStudent();
                    break;
                case "5" :
                    System.out.println("退出");
                    System.exit(0);
                default:
                    System.out.println("没有这个选项");
                    break;
            }
        }

    }


    //完成四个功能

    //添加学生
    public static void addStudent() throws Exception {
        System.out.println("添加学生");
        List<student> list = getList();
        System.out.println(list);


        //键盘录入学生的id


    }


    //删除学生
    public static void deleteStudent(){
        System.out.println("删除学生");
    }



    //修改学生
    public static void updateStudent(){
        System.out.println("修改学生");
    }



    //查询学生
    public static void queryStudent(){
        System.out.println("查询学生");
    }


    //生成一个与mysql数据库连接的方法
    public static List<student> getList() throws Exception {

        //建立数据库连接
        Properties prop = new Properties();
        prop.load(new FileInputStream("C:\\Users\\admin\\IdeaProjects\\student_test01\\student-system\\Druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        Connection conn = dataSource.getConnection();

        //2.sql语句 ：查询数据库
        String sql = "select * from student";
        //3.创建执行sql语句的对象
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //4.设置参数
        //5.执行sql

        ResultSet rs = pstmt.executeQuery();

        //6.处理结果

        //创建集合，用于存储学生数据
        List<student> list = new ArrayList<>();
        //清空list集合
        list.clear();

        while (rs.next()){
            //创建学生对象
            student stu = new student();
            String number = rs.getString("number");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            String address = rs.getString("address");

            //将数据库中的数据封装到学生对象当中去
            stu.setId(number);
            stu.setName(name);
            stu.setAge(age);
            stu.setAddress(address);

            //将学生数据添加到集合当中去
            list.add(stu);

        }

        //7.释放资源
        rs.close();
        pstmt.close();
        conn.close();

        return list;



    }
}