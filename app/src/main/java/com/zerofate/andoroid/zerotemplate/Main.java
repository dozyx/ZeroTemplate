package com.zerofate.andoroid.zerotemplate;

import java.util.Scanner;

public class Main {

    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int day = scanner.nextInt();
            int flag = 2;
            int money = 1;
            for (int i = 0, cycle = 0; i < day; i++) {
                if (i > 0){
                    cycle ++;
                }
                if (cycle == flag) {
                    money--;
                    flag++;
                    cycle =0;
                } else if (i > 0) {
                    money++;
                }
            }
            System.out.println(money);
        }
    }*/
    /*public static void main(String[] args) {
        int max = 0;
        int current = 0;
        int stationNums = 0;
        int up;
        int down;
        Scanner scanner = new Scanner(System.in);
        stationNums = Integer.valueOf(scanner.nextLine());
        while (scanner.hasNext()){
            down = Integer.valueOf(scanner.next());
            up = Integer.valueOf(scanner.next());
            current += up - down;
            max = Math.max(max,current);
        }
        System.out.print(max);

    }
    */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        String target = scanner.nextLine();
        char[] splitSource = string.toCharArray();
        char[] splitTarget = target.toCharArray();
        int matchNum = 0;
        int count = target.length();
        for (int i = 0; i < splitSource.length; i++) {
            if ((splitSource[i] >= 'a' && splitSource[i] <= 'z') || (splitSource[i] >= 'A' && splitSource[i] <= 'Z')
                    || (splitSource[i] >= '0' && splitSource[i] <= '9')) {
                if (splitTarget[i] == '1') {
                    matchNum ++;
                }
            } else {
                if (splitTarget[i] == '0') {
                    matchNum ++;
                }
            }
        }
        System.out.printf("%.2f%%", matchNum / (float)count * 100);

    }
}
