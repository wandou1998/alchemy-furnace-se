package com.lee.af.niuke;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KaoQin {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        System.out.println("输入考勤记录：");
        int nums = Integer.parseInt(in.nextLine().trim()); //去掉首尾空格
        List<String[]> list = new ArrayList<>();
        for (int i=0; i<nums; i++) {
            String[] records = in.nextLine().trim().split(" ");
            list.add(records);
        }
        for (String[] records : list) {
            int lastLLIndex = -1; //上次迟到/早退的下标
            int absentCount =0 ;  //缺勤记录统计
            boolean result = true;
            for (int i=0;i<records.length; i++) {
                int noPresentCount = 0;  // 7天内非正常上班次数统计
                for (int j=i; j<i+7;j++) {
                    if (j>=records.length) {
                        break;
                    }
                    if("absent".equalsIgnoreCase(records[j])) {
                        absentCount ++;
                        if (absentCount > 1) {
                            result=false; // 缺勤超过一次
                            break;
                        } else {
                            noPresentCount ++;
                        }
                    } else if ("absent".equalsIgnoreCase(records[j]) || "leaveearly".equalsIgnoreCase(records[j])) {
                        if (lastLLIndex != -1 && lastLLIndex == j-1) {
                            result=false; // 连续迟到早退
                            break;
                        } else {
                            lastLLIndex = j;
                            noPresentCount ++;
                        }
                    }
                    if (noPresentCount>3) {
                        result=false; // 任意连续7次考勤，缺勤/迟到/早退不超过3次
                        break;
                    }
                }
                if (!result) {
                    break;
                }
            }
            System.out.println(result);
        }
    }
}
