package com.lee.af.niuke;

import java.util.Arrays;
import java.util.Scanner;

public class HuYang {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 读取总共的胡杨树数量
        int total = scanner.nextInt();
        // 读取未成活的胡杨树数量
        int deadCount = scanner.nextInt();
        // 创建一个数组来表示每棵树是否成活，0表示成活，1表示未成活
        int[] nums = new int[total];
        Arrays.fill(nums, 0);

        for (int i = 0; i < deadCount; i++) {
            // 读取未成活的胡杨树的位置,标记为1
            int index = scanner.nextInt();
            nums[index - 1] = 1;
        }
        // 读取可以补种的树的数量
        int supplementCount = scanner.nextInt();
        //极端情况，deadCount=total
        if (deadCount == total) {
            System.out.println(supplementCount);
        } else {
            // 初始化滑动窗口的左右边界
            int left = 0;
            int maxLen = 0; // 用于存储最大连续成活区域的长度
            int sumLeft = 0; // 滑动窗口左边界的未成活树数量
            int sumRight = 0; // 滑动窗口右边界的未成活树数量

            // 遍历所有的树，right代表滑动窗口的右边界
            for (int right = 0; right < total; right++) {
                sumRight += nums[right]; // 更新右边界的未成活树数量

                // 如果窗口内的未成活树数量大于可以补种的数量
                while (sumRight - sumLeft > supplementCount) {
                    sumLeft += nums[left]; // 缩小窗口，左边界右移
                    left++;
                }

                // 更新最大成活区域的长度
                maxLen = Math.max(maxLen, right - left + 1);
            }

            // 输出最大连续成活区域的长度
            System.out.println(maxLen);
        }





//        scanner.close(); // 关闭Scanner
    }
}
