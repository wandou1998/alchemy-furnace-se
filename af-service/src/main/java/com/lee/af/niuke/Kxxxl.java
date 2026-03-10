package com.lee.af.niuke;

import java.util.Scanner;

/**
 * 给定一个N行M列的二维矩阵，矩阵中每个位置的数字取值为0或1。矩阵示例如：
 *
 * 1 1 0 0
 * 0 0 0 1
 * 0 0 1 1
 * 1 1 1 1
 * 1
 * 2
 * 3
 * 4
 * 现需要将矩阵中所有的1进行反转为0，规则如下：
 *
 * 当点击一个1时，该1便被反转为0，同时相邻的上、下、左、右，以及左上、左下、右上、右下8 个方向的1（如果存在1）均会自动反转为0；
 * 进一步地，一个位置上的1被反转为0时，与其相邻的8个方向的1（如果存在1）均会自动反转为0；
 * 按照上述规则示例中的矩阵只最少需要点击2次后，所有值均为0。
 *
 * 请问，给定一个矩阵，最少需要点击几次后，所有数字均为0？
 *
 * 输入描述
 * 第一行为两个整数，分别表示句子的行数 N 和列数 M，取值范围均为 [1, 100]
 *
 * 接下来 N 行表示矩阵的初始值，每行均为 M 个数，取值范围 [0, 1]
 *
 * 输出描述
 * 输出一个整数，表示最少需要点击的次数
 *
 * 示例1
 * 输入
 *
 * 4 4
 * 1 1 0 0
 * 0 0 0 1
 * 0 0 1 1
 * 1 1 1 1
 * 1
 * 2
 * 3
 * 4
 * 5
 * 输出
 *
 * 2
 * 1
 * 示例2
 * 输入
 *
 * 3 3
 * 1 0 1
 * 0 1 0
 * 1 0 1
 * 1
 * 2
 * 3
 * 4
 * 输出
 *
 * 1
 * 1
 * 说明
 *
 * 上述样例中，四个角上的 1 均在中间的 1 的相邻 8 个方向上，因此只需要点击一次即可
 */
public class Kxxxl {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入参数:");
        int n = scanner.nextInt(); // 行
        int m = scanner.nextInt();  // 列
        int[][] arr = new int[n][m];
        //组装数组
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                arr[i][j] = scanner.nextInt();
            }
        }
        int result = 0;
        //遍历数组
        for(int i=0;i<n;i++) {
            for (int j = 0; j < m; j++) {
                if(arr[i][j] == 1) {
                    change(arr, i,j,n,m);
                    result ++;
                }
            }
        }
        System.out.println("输出结果："+result);

    }

    private static void change(int[][] arr, int i, int j, int n, int m) {
        arr[i][j] = 0;
        //周围的8个坐标点的偏移量
        int[][] offset = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,1},{1,-1},{-1,-1}};
        for(int[] dir : offset){
            int x = i+dir[0];
            int y= j+dir[1];
            if (x>=0 && x<n && y>=0 && y<m && arr[x][y]==1){
                change(arr,x,y,n,m);
            }
        }
    }


}
