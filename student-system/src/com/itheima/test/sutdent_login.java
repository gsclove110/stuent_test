package com.itheima.test;

import java.util.Scanner;

public class sutdent_login {
    public static void main(String[] args) {
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
    public static void register(){
        System.out.println("注册");
    }

    //3.忘记密码
    public static void forgetPassword(){
        System.out.println("忘记密码");
    }


}
