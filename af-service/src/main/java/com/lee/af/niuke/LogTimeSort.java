package com.lee.af.niuke;

import java.util.*;

/**
 * [运维工程师]采集到某产品线网运行一天产生的日志n条，现需根据日志时间先后顺序对日志进行排序，日志时间格式为H:M:S.N。
 *
 * H表示小时(0~23)
 * M表示分钟(0~59)
 * S表示秒(0~59)
 * N表示毫秒(0~999)
 * 时间可能并没有补全，也就是说，01:01:01.001也可能表示为1:1:1.1。
 *
 * 输入描述
 * 第一行输入一个整数n表示日志条数，1<=n<=100000，接下来n行输入n个时间。
 *
 * 输出描述
 * 按时间升序排序之后的时间，如果有两个时间表示的时间相同，则保持输入顺序。
 *
 * 示例1
 * 输入
 *
 * 2
 * 01:41:8.9
 * 1:1:09.211
 * 1
 * 2
 * 3
 * 输出
 *
 * 1:1:09.211
 * 01:41:8.9
 */
public class LogTimeSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nums = sc.nextInt();
        List<String> logList = new ArrayList<>();

        for (int i=0;i<nums;i++) {
            logList.add(sc.next());
        }
        sc.close();
        //比较排序并输出
        Collections.sort(logList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (fillLogTime(o1).compareTo(fillLogTime(o2))>0) {
                    return 1;
                }else if (fillLogTime(o1).compareTo(fillLogTime(o2))<0) {
                    return -1;
                }
                return 0;
            }
        });
        for (String log : logList) {
            System.out.println(log);
        }
    }
    static String fillLogTime(String o1) {
        String hms = o1.substring(0, o1.indexOf("."));
        //🙆‍♀️转换格式
        StringBuilder sb = new StringBuilder();
        List<String> list= Arrays.asList(hms.split(":"));
        for (int i=0; i<list.size(); i++) {
            String e = list.get(i);
            if (list.get(i).length()<2) {
                sb.append("0").append(e);
            }  else {
                sb.append(e);
            }
            if (i<list.size()-1){
                sb.append(":");
            }
        }
        String lastSplitR = o1.split("\\.")[1];
        if (lastSplitR.length()==1) {
            sb.append(".00").append(lastSplitR);
        }else if (lastSplitR.length()==2) {
            sb.append(".0").append(lastSplitR);
        }else {
            sb.append(".").append(lastSplitR);
        }
        System.out.println("填充后的结果");
        System.out.println(sb.toString());
        return sb.toString();
    }
}
