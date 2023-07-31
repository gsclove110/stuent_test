import java.util.Scanner;

public class login {
    public static void main(String[] args) {
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
                    System.out.println("添加学生");
                    break;
                case "2" :
                    System.out.println("删除学生");
                    break;
                case "3" :
                    System.out.println("修改学生");
                    break;
                case "4" :
                    System.out.println("查询学生");
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
}