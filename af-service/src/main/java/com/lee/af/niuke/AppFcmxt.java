package com.lee.af.niuke;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 题目描述
 * 智能手机方便了我们生活的同时，也侵占了我们不少的时间。“手机App防沉迷系统”能够让我们每天合理地规划手机App使用时间，在正确的时间做正确的事。
 *
 * 它的大概原理是这样的：
 *
 * 在一天24小时内，可以注册每个App的允许使用时段
 * 一个时间段只能使用一个App
 * App有优先级，数值越高，优先级越高。注册使用时段时，如果高优先级的App时间和低优先级的时段有冲突，则系统会自动注销低优先级的时段，如果App的优先级相同，则后添加的App不能注册。
 * 请编程实现，根据输入数据注册App，并根据输入的时间点，返回时间点使用的App名称，如果该时间点没有注册任何App，请返回字符串“NA”。
 *
 * 输入描述
 * 第一行表示注册的App数量 N（N ≤ 100）
 *
 * 第二部分包括 N 行，每行表示一条App注册数据
 *
 * 最后一行输入一个时间点，程序即返回该时间点使用的App
 *
 * 2
 * App1 1 09:00 10:00
 * App2 2 11:00 11:30
 * 09:30
 *
 * 数据说明如下：
 *
 * N行注册数据以空格分隔，四项数依次表示：App名称、优先级、起始时间、结束时间
 * 优先级1~5，数字越大，优先级越高
 * 时间格式 HH:MM，小时和分钟都是两位，不足两位前面补0
 * 起始时间需小于结束时间，否则注册不上
 * 注册信息中的时间段包含起始时间点，不包含结束时间点
 * 输出描述
 * 输出一个字符串，表示App名称，或NA表示空闲时间
 *
 * 示例1
 * 输入
 *
 * 1
 * App1 1 09:00 10:00
 * 09:30
 * 1
 * 2
 * 3
 * 输出
 *
 * App1
 */
public class AppFcmxt {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        int nums = sc.nextInt();
        List<App> appList = new ArrayList<>();
        for (int i=0;i<nums; i++) {
            App app =new App(sc.next(),sc.nextInt(),sc.next(), sc.next());
            appList.add(app);
        }
        String targetTime = sc.next();
        sc.close();
        List<App> finalAppList = new ArrayList<>();
        for (App app : appList) {
            if(app.startTime.compareTo(app.endTime)>0) {
                continue; //无效数据
            }
            boolean add = true;
            //和已有的数据比较 检查是否有时间冲突
            for (int i=0; i< finalAppList.size(); i++) {
                App appf = finalAppList.get(i);
                //注销所有优先级比app低的数据
                if (((app.startTime.compareTo(appf.startTime)>=0 && app.startTime.compareTo(appf.endTime)<0)
                        || (app.endTime.compareTo(appf.startTime)>=0 && app.endTime.compareTo(appf.endTime)<0))
                && app.priority> appf.priority) {
                    finalAppList.remove(i);
                } if (((app.startTime.compareTo(appf.startTime)>=0 && app.startTime.compareTo(appf.endTime)<0)
                        || (app.endTime.compareTo(appf.startTime)>=0 && app.endTime.compareTo(appf.endTime)<0))
                        && app.priority<= appf.priority) {
                    add=false;
                }
            }
            if(add) {
                finalAppList.add(app);
            }
        }
        String result = "NA";
        //查找目标时间对应的app
        for (App app : finalAppList) {
            if (app.startTime.compareTo(targetTime)<=0 && app.endTime.compareTo(targetTime)>=0) {
                result= app.name;
            }
        }
        System.out.println(result);
    }


}
 class App {
     String name;
     String startTime;
     String endTime;
     int priority;

    // App类的构造函数，用于创建App对象
    public App(String name, int priority, String startTime, String endTime) {
        this.name = name;
        this.priority = priority;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

