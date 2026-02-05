package com.sirius.tree;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class App3 {

    private static List<Integer> listA = new ArrayList<>();
    private static List<Integer> listB = new ArrayList<>();
    private static List<Integer> result = new ArrayList<>();
    private static int seatchNum;

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i = i + 2) {
            listA.add(i);
        }
        for (int i = 0; i < 10000; i = i + 3) {
            listB.add(i);
        }

        // 常规查询 最慢无需排序
        LocalDateTime localDate1 = LocalDateTime.now();
        Loop:
        for (Integer a : listA) {
            for (Integer b : listB) {
                seatchNum++;
                if (a.equals(b)) {
                    result.add(a);
                    continue Loop;
                }
            }
        }
        System.out.println("耗时:" + localDate1.until(LocalDateTime.now(), ChronoUnit.MILLIS) + "毫秒");
        System.out.println("循环:" + seatchNum + "次");
        System.out.println("结果:" + result);
        System.out.println("------------------------------------------------");
        seatchNum = 0;
        result = new ArrayList<>();
        localDate1 = LocalDateTime.now();
        // 归并算法 最快需要两个数组都排序
        int i = 0;
        int j = 0;
        while (i < listA.size() || j < listB.size()) {
            seatchNum++;
            int A = i < listA.size() ? listA.get(i) : listA.get(listA.size() - 1);
            int B = j < listB.size() ? listB.get(j) : listB.get(listB.size() - 1);
            if (A > B) {
                j++;
                if (j == listB.size()) {
                    break;
                }
            } else if (A < B) {
                i++;
                if (i == listA.size()) {
                    break;
                }
            } else {
                result.add(listA.get(i));
                i++;
                j++;
                if (j == listB.size() && i == listA.size()) {
                    break;
                }
            }
        }
        System.out.println("耗时:" + localDate1.until(LocalDateTime.now(), ChronoUnit.MILLIS) + "毫秒");
        System.out.println("循环:" + seatchNum + "次");
        System.out.println("结果:" + result);
        System.out.println("------------------------------------------------");
        seatchNum = 0;
        result = new ArrayList<>();
        localDate1 = LocalDateTime.now();
        // 二分查找 需要被查找的数组排序
        for (Integer a : listA) {
            int min = 0;
            int max = listB.size() - 1;
            while (min <= max) {
                seatchNum++;
                int mid = (max - min) / 2 + min;
                if (a < listB.get(mid)) {
                    max = mid - 1;
                } else if (a > listB.get(mid)) {
                    min = mid + 1;
                } else {
                    result.add(a);
                    break;
                }
            }
        }
        System.out.println("耗时:" + localDate1.until(LocalDateTime.now(), ChronoUnit.MILLIS) + "毫秒");
        System.out.println("循环:" + seatchNum + "次");
        System.out.println("结果:" + result);
        System.out.println("------------------------------------------------");
        seatchNum = 0;
        result = new ArrayList<>();
        localDate1 = LocalDateTime.now();
    }

}
