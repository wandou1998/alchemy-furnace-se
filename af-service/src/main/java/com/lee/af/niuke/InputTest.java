
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入内容：");
        String input = sc.nextLine(); // 读取一行输入
        System.out.println("你输入的是：" + input);
        sc.close(); // 关闭Scanner
    }


//        public static void main(String[] args) {
//            Scanner sc = new Scanner(System.in);
//            while (sc.hasNextInt()) { // 注意 while 处理多个 case
//                int a = sc.nextInt();
//                int b = sc.nextInt();
//                System.out.println(a+"-"+b);
//            }
//
//            Integer[] arr =
//                    Arrays.stream(sc.nextLine().split("[\\[\\]\\,\\s]"))
//                            .filter(str -> !"".equals(str))
//                            .map(Integer::parseInt)
//                            .toArray(Integer[]::new);
//
//            String num = sc.next();
//
//            System.out.println(getResult(arr, num));
        }

        public static String getResult(Integer[] arr, String num) {
            ArrayList<Integer> link1 = new ArrayList<>();
            ArrayList<Integer> link2 = new ArrayList<>();

            Arrays.sort(arr, (a, b) -> a - b);
            for (Integer e : arr) {
                if (e < 4) {
                    link1.add(e);
                } else {
                    link2.add(e);
                }
            }

            ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
            int len1 = link1.size();
            int len2 = link2.size();

            switch (num) {
                case "1":
                    if (len1 == 1 || len2 == 1) {
                        if (len1 == 1) dfs(link1, 0, 1, new ArrayList<>(), ans);
                        if (len2 == 1) dfs(link2, 0, 1, new ArrayList<>(), ans);
                    } else if (len1 == 3 || len2 == 3) {
                        if (len1 == 3) dfs(link1, 0, 1, new ArrayList<>(), ans);
                        if (len2 == 3) dfs(link2, 0, 1, new ArrayList<>(), ans);
                    } else if (len1 == 2 || len2 == 2) {
                        if (len1 == 2) dfs(link1, 0, 1, new ArrayList<>(), ans);
                        if (len2 == 2) dfs(link2, 0, 1, new ArrayList<>(), ans);
                    } else if (len1 == 4 || len2 == 4) {
                        if (len1 == 4) dfs(link1, 0, 1, new ArrayList<>(), ans);
                        if (len2 == 4) dfs(link2, 0, 1, new ArrayList<>(), ans);
                    }
                    break;
                case "2":
                    if (len1 == 2 || len2 == 2) {
                        if (len1 == 2) dfs(link1, 0, 2, new ArrayList<>(), ans);
                        if (len2 == 2) dfs(link2, 0, 2, new ArrayList<>(), ans);
                    } else if (len1 == 4 || len2 == 4) {
                        if (len1 == 4) dfs(link1, 0, 2, new ArrayList<>(), ans);
                        if (len2 == 4) dfs(link2, 0, 2, new ArrayList<>(), ans);
                    } else if (len1 == 3 || len2 == 3) {
                        if (len1 == 3) dfs(link1, 0, 2, new ArrayList<>(), ans);
                        if (len2 == 3) dfs(link2, 0, 2, new ArrayList<>(), ans);
                    }
                    break;
                case "4":
                    if (len1 == 4 || len2 == 4) {
                        if (len1 == 4) ans.add(link1);
                        if (len2 == 4) ans.add(link2);
                    }
                    break;
                case "8":
                    if (len1 == 4 && len2 == 4) {
                        ans.add(
                                Stream.concat(link1.stream(), link2.stream())
                                        .collect(Collectors.toCollection(ArrayList<Integer>::new)));
                    }
                    break;
            }

            return ans.toString();
        }

        public static void dfs(
                ArrayList<Integer> arr,
                int index,
                int level,
                ArrayList<Integer> path,
                ArrayList<ArrayList<Integer>> res) {
            if (path.size() == level) {
                res.add(new ArrayList<>(path));
                return;
            }

            for (int i = index; i < arr.size(); i++) {
                path.add(arr.get(i));
                dfs(arr, i + 1, level, path, res);
                path.remove(path.size() - 1);
            }
        }

void main() {
}
