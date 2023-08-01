package com.itheima.test;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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
    public static void login() throws Exception {
        System.out.println("登入");
        List<User> list = getList();

        for (int i = 0; i < 3; i++) {



        //1.键盘录入用户名
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名");
        String username = sc.next();

        //判断用户名是否存在
        boolean flag = contains(list, username);
        if (!flag) {
            //用户名不存在
            System.out.println("用户名未注册");
            return;
        }


        //2.键盘录入密码
        System.out.println("请输入密码");
        String password = sc.next();

        //3.键盘录入验证码
        while (true) {
            String rightcode = getCode();
            System.out.println("验证码为：" + rightcode);
            System.out.println("请输入验证码");
            //接收验证码
            String code = sc.next();

            //比较验证码是否正确
            if (rightcode.equalsIgnoreCase(code)) {
                //验证码正确
                break;
            } else {
                System.out.println("验证码输入错误，请重新输入");
                continue;
            }
        }

        //把用户名和密码封装成用户对象

        //创建用户对象
        User userinfo = new User();
        userinfo.setUsername(username);
        userinfo.setPassword(password);


        //生成一个方法，判断用户是否在集合中存在
        boolean flag1 = checkUserinfo(list, userinfo);
        if (flag1) {
            //登入成功
            System.out.println("登入成功");

        } else {
            //登入失败
            System.out.println("登入失败");
            if (i==2){
                System.out.println("当前用户已被锁定");
                return;
            }else {
                System.out.println("还剩余："+(2-i)+"次机会");
            }
        }
    }




    }

    //判断登入的用户是否存在
    private static boolean checkUserinfo(List<User> list, User userinfo) {
        //遍历集合
        for (int i = 0; i < list.size(); i++) {
            //得到用户对象
            User user = list.get(i);

            if (user.getUsername().equals(userinfo.getUsername())&&user.getPassword().equals(userinfo.getPassword())){
                return true;
            }
        }
        return false;
    }

    //生成一个验证码
    private static String getCode() {
        //生成一个集合，用于管理字母
        List<Character> list = new ArrayList<>();
        //获得26个英文字母
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));

        }

        //生成随机数
        Random r = new Random();
        //创建容器
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            //随机索引
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }

        //生成0~9之间的随机数
        int i = r.nextInt(10);

        sb.append(i);

        char[] arr = sb.toString().toCharArray();

        //随机调换位置

        //随机索引
        int i1 = r.nextInt(arr.length);

        //最大索引上的字符放在temp中
        char temp = arr[arr.length-1];

        //随机索引上的字符放到最大索引位置
        arr[arr.length-1] = arr[i1];

        //temp中的字符放到随机索引的位置
        arr[i1] = temp;

        return (new String(arr));

    }

    //2.注册功能
    public static void register() throws Exception {
        System.out.println("注册");
        //获取当前数据库的集合
        List<User> list = getList();

        //键盘输入用户名
        Scanner sc = new Scanner(System.in);

        //验证用户名
        String username;
        while (true) {
            //接收用户名
            System.out.println("请输入需要注册的用户名");
            username = sc.next();
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

        //2.验证密码

        String password;
        while (true) {
            //输入密码
            System.out.println("请输入密码");
           password = sc.next();

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


        //3.验证身份证号码
        String personID;
        while (true) {

            System.out.println("请输入身份证号码");
            personID = sc.next();

            boolean flag = checkPersonID(personID);

            if (flag){
                //身份证号码正确
                break;
            }else {
                System.out.println("身份证号码不符合要求");
                continue;
            }
        }

        //4.验证手机号码
        String phoneNumber;
        while (true) {
            System.out.println("请输入手机号码");
           phoneNumber = sc.next();

            //生成一个方法验证手机号码是否满足要求
            boolean flag = checkPhoneNumber(phoneNumber);

            if (flag){
                //手机号码满足要求
                break;

            }else {
                System.out.println("手机号码不符合要求，请重新输入");
                continue;
            }
        }

        //向数据库中存储注册的信息

        //1.建立数据库连接
        Properties prop = new Properties();
        prop.load(new FileInputStream("C:\\Users\\admin\\IdeaProjects\\student_test01\\student-system\\Druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        Connection conn = dataSource.getConnection();

        //2.sql语句
        String sql = "insert into user (username, password, personID, phoneNumber) value (?,?,?,?)";

        //3.创建执行sql语句的对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //4.设置参数
        pstmt.setString(1,username);
        pstmt.setString(2,password);
        pstmt.setString(3,personID);
        pstmt.setString(4,phoneNumber);

        //5.执行sql
        int count = pstmt.executeUpdate();

        //6.处理结果
        if (count>0){
            System.out.println("注册成功");
        }else {
            System.out.println("注册失败");
        }

        //7.关闭资源
        pstmt.close();
        conn.close();


    }

    //验证手机号码是否满足要求
    private static boolean checkPhoneNumber(String phoneNumber) {
        //使用正则表达式验证
        String regex = "[1-9]\\d{10}";

        boolean flag = phoneNumber.matches(regex);
        return flag;
    }

    //验证身份证号是否满足要求
    private static boolean checkPersonID(String personID) {
        //1.身份证长度为18位
        int len = personID.length();
        if (len!=18){
            return false;
        }

        //2.不能以0开头
      if (personID.startsWith("0")){
          return false;
      }

      //3.前17位必须是数字
        for (int i = 0; i < personID.length()-1; i++) {
            //得到每个字符
            char c = personID.charAt(i);

            if (!(c>='0'&&c<='9')){
                return false;
            }
        }

        //4.最后一位可以是数字，也可以是大写X或者x

        //最后一位字符
        char c = personID.charAt(personID.length() - 1);
        if ((c>='0'&&c<='9')||(c=='x')||(c=='X')){
            return true;
        }else {
            return false;
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
