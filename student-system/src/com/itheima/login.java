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
        //获得当前数据库的集合
        List<student> list = getList();

        //键盘输入学生的id
        Scanner sc = new Scanner(System.in);

        String number =null;
        while (true) {
            //接收学生的id
            System.out.println("请输入需要添加的学生id");
             number = sc.next();

            //判断学生的id是否在集合中是否存在

            int index = getIndex(list,number);

            if (index>=0){
                //id在集合中存在
                System.out.println("输入的id已存在，请重新输入");
                continue;
            }else {
                //id不存在
                break;
            }
        }

        System.out.println("请输入学生的姓名");
        String name = sc.next();

        System.out.println("请输入学生的年龄");
        int age = sc.nextInt();

        System.out.println("请输入学生的家庭住址");
        String address = sc.next();

        //把新输入的学生信息添加到数据库当中

        //1.建立数据库连接
        Properties prop = new Properties();
        prop.load(new FileInputStream("C:\\Users\\admin\\IdeaProjects\\student_test01\\student-system\\Druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        Connection conn = dataSource.getConnection();

        //2.sql语句
        String sql = "insert into student (number, name, age, address) VALUES (?,?,?,?)";

        //3.创建执行sql语句的对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //4.设置参数
        pstmt.setString(1,number);
        pstmt.setString(2,name);
        pstmt.setInt(3,age);
        pstmt.setString(4,address);

        //5.执行sql
        int count = pstmt.executeUpdate();

        //6.处理结果
        if (count>0){
            System.out.println("添加成功");
        }else {
            System.out.println("添加失败");
        }

        //7.关闭资源
        pstmt.close();
        conn.close();

    }


    //判断学生id是否存在集合当中
    private static int getIndex(List<student> list, String number) {
        //判断number是否在list集合中存在，存在返回索引，不存在返回-1
        for (int i = 0; i < list.size(); i++) {
            //得到集合中的学生对象
            student stu = list.get(i);
            //得到学生的id
            String id = stu.getId();

            if (number.equals(id)){
                return i;
            }

        }
        return -1;
    }


    //删除学生
    public static void deleteStudent() throws Exception {
        System.out.println("删除学生");
        //获取当前数据库集合
        List<student> list = getList();

        //键盘输入需要删除的学生id
        System.out.println("请输入需要删除的学生id");

        //scanner接收键盘输入的数据
        Scanner sc = new Scanner(System.in);
        //接收输入的id
        String id = sc.next();
        //获取学生id在集合中的索引
        int index = getIndex(list, id);

        //判断索引是否存在
        if (index>=0){
            //表示索引存在,进行删除操作

            //1.建立数据库连接
            Properties prop = new Properties();
            prop.load(new FileInputStream("C:\\Users\\admin\\IdeaProjects\\student_test01\\student-system\\Druid.properties"));
            DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
            Connection conn = dataSource.getConnection();

            //2.sql语句
            String sql = "delete from student where number=?";

            //3.创建执行sql语句的对象
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //4.设置参数
            pstmt.setString(1,id);

            //5.执行sql语句
            int count = pstmt.executeUpdate();

            //6.处理结果
            System.out.println("删除成功");

            //7.关闭资源
            pstmt.close();
            conn.close();


        }else {
            System.out.println("id不存在，删除失败");
            return;
        }



    }



    //修改学生
    public static void updateStudent() throws Exception {
        System.out.println("修改学生");

        //键盘输入需要修改的学生id
        Scanner sc = new Scanner(System.in);
        //接收输入的学生id
        System.out.println("请输入需要修改的学生id");
        String id = sc.next();

        //获取当前数据库集合
        List<student> list = getList();

        //判断当前集合中是否存在学生id
        int index = getIndex(list, id);

        //判断索引是否存在
        if (index>=0){
            //索引存在

            //输入需要修改的学生名字
            System.out.println("请输入需要修改的名字");
            String name = sc.next();

            //输入需要修改的年龄
            System.out.println("请输入需要修改的年龄");
            int age = sc.nextInt();

            //请输入需要修改的家庭住址
            System.out.println("请输入需要修改的家庭住址");
            String address = sc.next();



            //1.建立数据库连接
            Properties prop = new Properties();
            prop.load(new FileInputStream("C:\\Users\\admin\\IdeaProjects\\student_test01\\student-system\\Druid.properties"));
            DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
            Connection conn = dataSource.getConnection();

            //2.sql语句
            String sql = "update student set name = ?,age = ?,address = ? where number = ?";

            //3.创建执行sql语句对象
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //4.设置参数
            pstmt.setString(1,name);
            pstmt.setInt(2,age);
            pstmt.setString(3,address);
            pstmt.setString(4,id);

            //5.执行sql
            int i = pstmt.executeUpdate();

            //6.处理结果
            if (i>0){
                System.out.println("修改成功");
            }else {
                System.out.println("修改失败");
            }

            //7.关闭资源
            pstmt.close();
            conn.close();


        }else {
            System.out.println("需要修改的学生id不存在");
            return;
        }

    }



    //查询学生
    public static void queryStudent() throws Exception {
        System.out.println("查询学生");
        //获取当前数据库集合
        List<student> list = getList();
        if (list.size()==0){
            System.out.println("当前无学生信息，请添加后在查询");
            return;
        }
        System.out.println("学号"+"\t"+"姓名"+"\t"+"\t"+"年龄"+"\t"+"家庭住址");
        for (int i = 0; i < list.size(); i++) {
            //获取学生对象
            student stu = list.get(i);
            System.out.println(stu.getId()+"\t\t"+stu.getName()+"\t\t"+stu.getAge()+"\t\t"+stu.getAddress());
        }

    }


    //连接数据库，获取当前数据库的集合
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