package com.lee.af.niuke;

import org.apache.dubbo.common.json.GsonUtils;

import java.util.*;

public class heightsRank {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入身高：");
        String input = scanner.nextLine();
        if (!input.matches("[0-9\\s]+")) {
            System.out.println("[]");
            return;
        }
//        List<Integer> heightList = new ArrayList<>();
//        while (scanner.hasNextInt()) { // 注意 while 处理多个 case
//            heightList.add(scanner.nextInt());
//        }
//        for (int i=0; i<heightList.size()-1;i++){
//            height[i] = heightList.get(i);
//        }
        int[] height = Arrays.stream(input.split(" ")).mapToInt(Integer::parseInt).toArray();
        int temp = 0;
        for (int i = 0; i < height.length-1; i++) {
           if (i%2==0 && height[i]<height[i+1]) {
               temp = height[i];
               height[i] = height[i+1];
               height[i+1]=temp;
           }
        }
        System.out.println("输出结果：");
        StringJoiner sj = new StringJoiner(" ");
        for (int h : height) {  // 遍历heights数组中的每一个元素
            sj.add(String.valueOf(h));  // 将元素转换为字符串并添加到StringJoiner中
        }
        System.out.println(sj.toString());
    }


}
