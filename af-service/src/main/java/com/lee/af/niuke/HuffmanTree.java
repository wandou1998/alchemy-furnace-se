package com.lee.af.niuke;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 定长度为 n nn 的无序的数字数组，每个数字代表二叉树的叶子节点的权值，数字数组的值均大于等于 1 11 。请完成一个函数，根据输入的数字数组，生成哈夫曼树，并将哈夫曼树按照中序遍历输出。
 *
 * 为了保证输出的二叉树中序遍历结果统一，增加以下限制:又树节点中，左节点权值小于等于右节点权值，根节点权值为左右节点权值之和。当左右节点权值相同时，左子树高度高度小于等于右子树。
 *
 * 注意: 所有用例保证有效，并能生成哈夫曼树提醒:哈夫曼树又称最优二叉树，是一种带权路径长度最短的一叉树。
 *
 * 所谓树的带权路径长度，就是树中所有的叶结点的权值乘上其到根结点的路径长度(若根结点为 0 00 层，叶结点到根结点的路径长度为叶结点的层数)
 *
 * 输入描述
 * 例如：由叶子节点 5 15 40 30 10 生成的最优二叉树如下图所示，该树的最短带权路径长度为 40 * 1 + 30 * 2 +5 * 4 + 10 * 4 = 205 。
 * 输出描述
 * 输出一个哈夫曼的中序遍历数组，数值间以空格分隔
 *
 * 示例1
 * 输入
 *
 * 5
 * 5 15 40 30 10
 * 1
 * 2
 * 输出
 *
 * 40 100 30 60 15 30 5 15 10
 */
public class HuffmanTree {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();  // 读取节点数量
        ArrayList<Integer> values = new ArrayList<>();  // 存储所有节点的权值
        for (int i = 0; i < n; i++) {
            values.add(scanner.nextInt());  // 读取权值
        }
        scanner.close();
        Node root = buildHuffmanTree(values);  // 构建哈夫曼树
        //开始遍历打印结果树（递归方法）
       StringBuilder result = new StringBuilder();
       inorderTraversal(root, result);
       System.out.println("结果："+ result.toString());
    }

    // 构建哈夫曼树的函数
    public static Node buildHuffmanTree(ArrayList<Integer> values) {
        // PriorityQueue 并不是完全有序的集合，但它保证每次出队的元素是当前优先级最高的（或最低的）元素。
        //自然顺序就是按升序poll
        PriorityQueue<Node> pq = new PriorityQueue<>(new Compare());  // 使用优先队列存储节点
        // 为每个权值创建一个节点并添加到优先队列中
        for (int value : values) {
            pq.add(new Node(value));
        }
        //如果只剩有一个根节点，则没必要循环
        while (pq.size()>1) {
            Node left = pq.poll();  // 弹出最小的节点作为左子节点(返回+移除)
            Node right = pq.poll(); // 弹出次小的节点作为右子节点
            Node parent = new Node(left.value+right.value);
            if (left.value>right.value || (left.value==right.value && left.height>right.height)) {
                Node temp = left;
                left=right;
                right=temp;
            }
            parent.left = left;
            parent.right= right;
            parent.height = Math.max(left.height,right.height)+1;
            pq.add(parent);
        }
        return pq.peek();   //返回队列头部元素，但是不会移除数据！！！！*************** 注意和poll方法的区别***************
    }

    public static void inorderTraversal(Node root, StringBuilder result) {
        if (root!= null) {
            inorderTraversal(root.left,result);
            result.append(root.value).append(" ");
            inorderTraversal(root.right,result);
        }
    }

}

// 定义一个节点类来表示哈夫曼树中的节点
class Node {
    int value;       // 节点存储的权值
    Node left;       // 指向左子节点的引用
    Node right;      // 指向右子节点的引用
    int height;      // 节点的高度，用于处理相等权值的情况

    // 构造函数
    public Node(int v) {
        value = v;
        left = null;
        right = null;
        height = 0;
    }
}

// 实现比较器，用于优先队列的比较逻辑
class Compare implements Comparator<Node> {
    @Override
    public int compare(Node a, Node b) {
        // 首先比较节点的权值，若权值相等则比较高度
        if (a.value > b.value) return 1;
        if (a.value < b.value) return -1;
        if (a.height > b.height) return 1;
        if (a.height < b.height) return -1;
        return 0;
    }
}
