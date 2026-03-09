package com.lee.af.niuke;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Square {
/**
 * 程序的主入口方法
 * @param args 命令行参数，未在本程序中使用
 */
    public static void main(String[] args) {
    // 创建Scanner对象，用于读取用户输入
        Scanner scanner = new Scanner(System.in);
    // 读取一个整数n，可能表示点的数量
        int n = scanner.nextInt();
    // 创建ArrayList用于存储Point对象
        ArrayList<Point> points = new ArrayList<>();
        Point point = null;
    // 空的if语句块，可能需要根据实际需求添加条件
        if (n<4) {
            System.out.println(0);
        }
        //n表示节点的数量而非单个输入的数据
        for (int i=0; i<n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new Point(x,y));
        }
        int squareCount = 0;

        // 遍历所有点对，检查是否能构成正方形
        for (int i = 0; i < n; i++) {
            Point p1 = points.get(i);

            for (int j = i + 1; j < n; j++) {
                Point p2 = points.get(j);

                /**
                 * 将向量 （dx，dy）顺时针旋转90度 结果（dy，-dx）
                 * 将向量（dx，dy）逆时针旋转90度 结果（-dy，dx）
                 * 将向量（dx，dy）旋转180度 结果（-dx，-dy）
                 *
                 * 两个坐标点之间的向量结果（p1x-p2x， p1y-p2y）
                 */
                Point p3 = new Point(p1.x - (p1.y - p2.y), p1.y + (p1.x - p2.x));
                Point p4 = new Point(p2.x - (p1.y - p2.y), p2.y + (p1.x - p2.x));

                if (pointExists(points, p3) && pointExists(points, p4)) {
                    squareCount++;
                }

                // 计算另外两个可能的对角点
                Point p5 = new Point(p1.x + (p1.y - p2.y), p1.y - (p1.x - p2.x));
                Point p6 = new Point(p2.x + (p1.y - p2.y), p2.y - (p1.x - p2.x));

                if (pointExists(points, p5) && pointExists(points, p6)) {
                    squareCount++;
                }
            }
        }

        // 每个正方形被计算了4次，因此结果需要除以4
        System.out.println(squareCount / 4);

        scanner.close();
    }

    // 检查点是否存在于点列表中
    static boolean pointExists(ArrayList<Point> points, Point p) {
        for (Point point : points) {
            if (point.equals(p)) {
                return true;
            }
        }
        return false;
    }

}
