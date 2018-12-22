package com.chen.security.tree.avl_tree;


import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        question1003();
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
}
