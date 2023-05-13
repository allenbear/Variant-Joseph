package practice;

/*
import java.util.*;

public class Demo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 输入双胞胎捆绑后的报名数，录取数，起始号和间隔数
        String string = scanner.nextLine();
        String[] strings = string.split(" ");
        int totalNum = Integer.parseInt(strings[0]);
        int admitedNum = Integer.parseInt(strings[1]);
        String startId = strings[2];
        int interval = Integer.parseInt(strings[3]);

        String stringtwins = scanner.nextLine();
        String[] twins = stringtwins.split(" ");
        // 初始化学生列表并添加学生信息到列表中
        List<Student> students = new ArrayList<>();
        // 如果存在双胞胎，则双胞胎两个人的学生ID是一样的，只需要添加其中一个就行
        if(twins[0].equals("0")){
            for (int i = 0; i < totalNum; i++) {
                students.add(new Student(startId.substring(0 , 3)+String.format("%04d" , i+1)));
            }
        }else{
            for (int i = 0; i < totalNum; i=i+2) {

            }
        }
        // 按照题意进行循环派位
        List<String> admitedStudents = new ArrayList<>();
        int currentIndex = students.indexOf(new Student(startId));
        for (int i = 0; i < admitedNum; i++) {
            admitedStudents.add(students.get(currentIndex).getStudentId());
            currentIndex = (currentIndex + interval - 1) % students.size();
        }

        // 输出结果
        for (String studentId : admitedStudents) {
            System.out.println(studentId);
        }
    }
}

class Student{
    private String studentId;
    public Student(String studentId){
        this.studentId = studentId;
    }
    public String getStudentId(){
        return studentId;
    }
}*/



/*
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String string = sc.nextLine();
        String[] strings = string.split(" ");
        int totalNum = Integer.parseInt(strings[0]); // 总报名人数
        int selectedNum = Integer.parseInt(strings[1]); // 计划录取人数
        String startNoStr = strings[2]; // 录取起始号
        int interval = Integer.parseInt(strings[3]); // 录取间隔数

        List<String> twinNoList = new ArrayList<>(); // 双胞胎报名序号列表
        String twinNoLine = sc.nextLine();
        String[] twinNos = twinNoLine.split(" ");

        if (!twinNos[0].equals("0")) { // 如果有双胞胎，则读取输入的双胞胎序号
            for (String twinNo : twinNos) {
                twinNoList.add(twinNo);
            }
        }

        // 将起始号和双胞胎序号转换为对应的整数类型
        int startNo = Integer.parseInt(startNoStr.substring(3));
        for (int i = 0; i < twinNoList.size(); i++) {
            String twinNo = twinNoList.get(i);
            if (!twinNo.equals("0")) {
                int twinStartNo = Integer.parseInt(twinNo.substring(3));
                if (twinStartNo < startNo) { // 如果双胞胎序号比起始号小，将起始号设置为双胞胎序号
                    startNo = twinStartNo;
                }
            }
        }

        List<String> selectedNoList = new ArrayList<>(); // 记录被录取的报名序号

        // 进行循环随机派位，直到达到计划录取人数为止
        int currentNo = startNo;
        while (selectedNoList.size() < selectedNum) {
            String currentNoStr = startNoStr.substring(0 , 3) + String.format("%04d", currentNo); // 构造当前派位的报名序号
            selectedNoList.add(currentNoStr);

            currentNo += interval; // 往前走interval步
            if (currentNo > totalNum) { // 如果超过总报名人数，则回到起点继续走
                currentNo %= totalNum;
                if (currentNo == 0) {
                    currentNo = totalNum;
                }
            }
            // 检查是否抽中了双胞胎
            if (twinNoList.contains(startNoStr.substring(0 , 3) + String.format("%04d", currentNo))) {
                selectedNoList.add(startNoStr.substring(0 , 3) + String.format("%04d", currentNo)); // 如果抽中了双胞胎，将其一并加入记录列表
                currentNo += interval;
                if (currentNo > totalNum) {
                    currentNo %= totalNum;
                    if (currentNo == 0) {
                        currentNo = totalNum;
                    }
                }
            }
        }

        // 输出所有被录取的报名序号
        for (String selectedNo : selectedNoList) {
            System.out.println(selectedNo);
        }
    }
}*/



import java.util.Scanner;

public class Demo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] strings1 = s.split(" ");
        int enrollment = Integer.parseInt(strings1[0]);
        int admissions = Integer.parseInt(strings1[1]);
        int first = Integer.parseInt(strings1[2].substring(3));
        int interval = Integer.parseInt(strings1[3]);

        String school = strings1[2].substring(0,3);

        String s1 = scanner.nextLine();
        String[] strings2 = s1.split(" ");
        boolean[] isTwins = new boolean[enrollment + 1];
        if(!strings2[0].equals("0")) {
            for (String str : strings2) {
                int i = Integer.parseInt(str.substring(3));
                isTwins[i] = true;
            }
        }


        int[] totalNum = func(enrollment,interval,first,isTwins);
        for (int i = 0; i < admissions; i++) {
            System.out.print(school);
            System.out.println(String.format("%04d", totalNum[i]));
        }

    }
    /**
     *  约瑟夫环问题变形
     * 招生摇号第一个先摇中，即第k人先出圈，并且双胞胎捆绑成一个号码，
     * 但抽中时是两人，故对约瑟夫环问题作变形
     * n个人转成一圈，顺序排号（1,2,3...,n），从第k人先出圈，再从k+1开始不断报数
     *  凡是报到m的人出圈，重复报数，输出出圈次序
     *  由于数组元素与数组下标完全一致，只考虑数组下标
     * @param n 表示n个人围成一个圈
     * @param m 报到m的出圈
     * @param k 从k开始报数
     * @param twins 是否是双胞胎，从1开始，其下标和n人在圈中的序号一致
     * @return 返回出圈次序数组
     */
    static int[] func(int n,int m,int k,boolean[] twins) {
        int a = 0;//twins中true的个数
        for (boolean b : twins) {
            if(b) a++;
        }
        int[] result = new int[n + a];
        int j = 0;//result数组的下标
        boolean[] isSelected = new boolean[n + 1];// 是否已出圈，从下标1开始
        int t = 0;//每次间隔的计数，每次到m后从1开始
        int count = 0;//共出圈多少人
        result[j++] = k;
        isSelected[k] = true;
        count++;
        if(twins[k]) {
            result[j++] = k;
            count++;
        }
        while(count < (n + a)) {
            if (!isSelected[k]) t++;
            if (t == m) {
                result[j++] = k;
                isSelected[k] = true;
                t = 0;
                count++;
                if(twins[k]) {
                    result[j++] = k;
                    count++;
                }
            }
            k++;
            if (k > n) k = 1;
        }
        return result;
    }
}