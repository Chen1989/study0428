package com.chen.security.tree.avl_tree;


import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        question1005();
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

//    private static void question1003() {
//        Scanner cin = new Scanner(System.in);
//        while (cin.hasNext()) {
//            int T = cin.nextInt();
//
//            int No = 1;
//            while (T > 0) {
//                int N = cin.nextInt();
//                int[] num = new int[N];
//                for (int i = 0; i < N; i++) {
//                    num[i] = cin.nextInt();
//                }
//                int maxSum = -10000, thisSum = 0;
//                int start = 0, end = 0;
//                for (int i = 0; i < N; i++) {
//                    thisSum += num[i];
//                    if (thisSum > maxSum) {
//                        maxSum = thisSum;
//                        end = i;
//                    }
//                    if (thisSum < 0) {
//                        thisSum = 0;
//                    }
//                }
//                int sum = 0;
//                for (int i = end; i > -1; i--) {
//                    sum += num[i];
//                    if (sum == maxSum) {
//                        start = i;
//                    }
//                }
//
//                System.out.println("Case " + No + ":");
//                System.out.println(maxSum + " " + (start + 1) + " " + (end + 1));
//                No++;
//                if (T != 1)
//                    System.out.println();
//                T--;
//
//            }
//        }
//    }

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
}
