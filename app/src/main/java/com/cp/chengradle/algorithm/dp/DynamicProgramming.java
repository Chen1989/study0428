package com.cp.chengradle.algorithm.dp;

import android.util.Log;

import java.math.BigInteger;

/**
 * Created by PengChen on 2018/9/14.
 *
 * 动态规划
 *
 */

public class DynamicProgramming {
//    opt[i] = min{opt[i-1] + a[1] + a[i] , opt[i-2] + a[1] + a[i] + 2*a[2] }

    private static int[] results;
    public static int dynamiRriverCrossing(int[] resource) {
        results = new int[resource.length];
//        return crossing(resource, resource.length - 1);
        return crossing2(resource);
    }

    private static int crossing(int[] resouce, int m) {
        if (m < 2) {
            return resouce[m];
        }

        int temp1 = crossing(resouce, m - 1) + resouce[0] + resouce[m];
        int temp2 = crossing(resouce, m - 2) + resouce[0] + resouce[m] + 2*resouce[1];
        int result = temp1 > temp2 ? temp2 : temp1;
        return result;
    }

    private static int crossing2(int[] resouce) {
        if (resouce.length < 2) {
            return resouce[resouce.length - 1];
        }
        results[0] = resouce[0];
        results[1] = resouce[1];
        for (int i = 2; i < resouce.length; i++) {
            int temp1 = results[i - 1] + resouce[0] + resouce[i];
            int temp2 = results[i - 2] + resouce[0] + resouce[i] + 2*resouce[1];
            results[i] = temp1 > temp2 ? temp2 : temp1;
            Log.i("ChenSdk", "min time = " + results[i]);
        }
        return results[resouce.length - 1];
    }

    public static int Bagger01() {
        int[] weight = new int[]{0,4,6,2,2,5,1};
        int[] value = new int[]{0,8,10,6,3,7,2};
        int[][] result = new int[7][13];
        for(int i=1;i<=6;i++)
        {
            for(int j=1;j<=12;j++)
            {
                if(j>=weight[i])
                    result[i][j]=max(result[i-1][j],result[i-1][j-weight[i]]+value[i]);

                else
                    result[i][j]=result[i-1][j];
            }
        }

        for(int i=1;i<=6;i++)
        {
            for(int j=1;j<=12;j++)
            {
                Log.i("ChenSdk", "result[" + i + "][" + j + "] = " + result[i][j]);
            }
        }

        return 0;
    }

    private static int max(int v1, int v2) {
        return v1 > v2 ? v1 : v2;
    }

    public static void fa(int num) {
        BigInteger integer1 = BigInteger.valueOf(1);
        BigInteger integer2 = BigInteger.valueOf(1);
        BigInteger[] va = new BigInteger[num];
        va[0] = integer1;
        va[1] = integer2;
        for (int i = 2; i < num; i++) {
            va[i] = va[i - 1].add(va[i - 2]);
        }
        Log.i("ChenSdk", "va = " + va[num - 1].toString());
        Log.i("ChenSdk", "va = " + va[num - 1].toString().length());
    }

    public static void fa2() {
        BigInteger[] integers = new BigInteger[1000];
        Log.i("ChenSdk", "va = " + faa(50, integers).toString());
    }

    private static BigInteger faa(int num, BigInteger[] integers) {
        if (num < 3) {
            return BigInteger.valueOf(1);
        }
        if (integers[num - 1] == null) {
            integers[num - 1] = faa(num - 1, integers);
        }
        if (integers[num - 2] == null) {
            integers[num - 2] = faa(num - 2, integers);
        }
        return integers[num - 1].add(integers[num - 2]);
    }

    public static void maxSubStr() {
        int result = 0;
        String str1 = "asnjdhjdssasasaff";
        String str2 = "sasasasadjnjdf";
        for (int i = 0; i < str1.length() - 1; i++) {
            for (int j = 0; j < str2.length() - 1; j++) {
                if (i == 0 || j == 0) {
                    result = 0;
                }
                else if (str1.substring(i, i + 1).equals(str2.substring(j, j+1))) {
                    result = result + 1;
                    Log.i("ChenSdk", "result = " + result);
                    if (i + 1 < str1.length()) {
                        i++;
                    } else {
                        break;
                    }

                } else {
                    result = 0;
                }
            }
        }
    }


}