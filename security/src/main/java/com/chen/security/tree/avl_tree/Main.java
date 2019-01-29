package com.chen.security.tree.avl_tree;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
//        question1005();
//        System.out.println('b' - 'a');
        question1181();
    }

//    private static void question1002() {
//        Scanner cin = new Scanner(System.in);
//        int t = cin.nextInt();
//        for (int i = 0; i < t; i++) {
//            BigInteger num1 = cin.nextBigInteger();
//            BigInteger num2 = cin.nextBigInteger();
//            System.out.println("Case " + (i + 1) + ":");
//            System.out.println(num1 + " + " + num2 + " = " + num1.add(num2));
//            if (i + 1 != t) {
//                System.out.println();
//            }
//        }
//    }

    private static void question1003() {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            int T = cin.nextInt();

            int No = 1;
            while (T > 0) {
                int N = cin.nextInt();
                int[] num = new int[N];
                for (int i = 0; i < N; i++) {
                    num[i] = cin.nextInt();
                }
                int maxSum = -10000, thisSum = 0;
                int start = 0, end = 0;
                for (int i = 0; i < N; i++) {
                    thisSum += num[i];
                    if (thisSum > maxSum) {
                        maxSum = thisSum;
                        end = i;
                    }
                    if (thisSum < 0) {
                        thisSum = 0;
                    }
                }
                int sum = 0;
                for (int i = end; i > -1; i--) {
                    sum += num[i];
                    if (sum == maxSum) {
                        start = i;
                    }
                }

                System.out.println("Case " + No + ":");
                System.out.println(maxSum + " " + (start + 1) + " " + (end + 1));
                No++;
                if (T != 1)
                    System.out.println();
                T--;

            }
        }
    }

    private static void question1004() {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            int num = cin.nextInt();
            if (num == 0) {
                return;
            }
            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < num; i++) {
                String str = cin.next();
                int strNum = 0;
                if (map.get(str) != null) {
                    strNum = map.get(str);
                }
                map.put(str, strNum + 1);
            }
            int max = 0;
            String name = "";
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (max < entry.getValue()) {
                    name = entry.getKey();
                    max = entry.getValue();
                }
            }
            System.out.println(name);
        }
    }

    private static void question1005() {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            int n1 = cin.nextInt();
            int n2 = cin.nextInt();
            int num = cin.nextInt();
            if (n1 == 0 && n2 == 0 && num == 0) {
                return;
            }
            int[] result = new int[50];
            result[0] = 1;
            result[1] = 1;
            for (int i = 2; i < 50; i++) {
                result[i] = (n1 * result[i - 1] + n2 * result[i - 2]) % 7;
            }
            num =  (num - 1) % 49;
            System.out.println(result[num]);
        }
    }

    private static void question1181() {

        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            int[][] map = new int[26][26];
            String input;
            while (!(input = cin.next()).equals("0")) {
                int x = input.charAt(0) - 'a';
                int y = input.charAt(input.length() - 1) - 'a';
                map[x][y] = 1;
            }
            DFS(1, map);
            if(Right==1) {
                System.out.println("Yes.");
            } else {
                System.out.println("No.");
            }
            Right = 0;
        }
    }

    private static int Right = 0;
    private static void DFS(int x, int[][] map){
        if(Right == 1) return;
        if(x == 12){
            Right = 1;
            return;
        }
        for(int i = 0;i<26;i++) {
            if(map[x][i]==1){
                map[x][i] = 0;
                DFS(i, map);
                map[x][i] = 1;
            }
        }
    }
}
