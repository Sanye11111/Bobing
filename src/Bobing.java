import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;

public static class Bobing {
    //// 现在谁是状元、他的权值多大
    static Player curZhuangyuanPlayer = null;  // 当前状元玩家
    static int cur_zhuangyuanscore;

    public static class Player {            //定义玩家类 包含姓名 状元权值 获得奖项
        String name;
        int zhuangYuanScore = 0;//状元权值，判断最大状元
        int iszhuangyuan = 0, duitang = 0, sanhong = 0, sijin = 0, erju = 0, yixiu = 0;

        Player(String name) {
            this.name = name;
        }

        /// /给予奖项
        public void winPrize(String prizeType) {
            switch (prizeType) {
                case "duitang":
                    this.duitang++;
                    System.out.println(name + "获得对堂");
                    break;
                case "sanhong":
                    this.sanhong++;
                    System.out.println(name + "获得三红");
                    break;
                case "sijin":
                    this.sijin++;
                    System.out.println(name + "获得四进");
                    break;
                case "erju":
                    this.erju++;
                    System.out.println(name + "获得二举");
                    break;
                case "yixiu":
                    this.yixiu++;
                    System.out.println(name + "获得一秀");
                    break;
                case "no prize":
                    System.out.println(name + "未中奖");
                    break;
                case "yixiu and sijin":
                    System.out.println(name + "好事成双，获得一秀和四进");
                    break;
                case "erju and sijin":
                    System.out.println(name + "好事成双，获得二举和四进");
                    break;
            }
        }
    }

    //// 标记是否为自动模式（输入1后设为true）
    static boolean isAutoMode = false;

    static class Prize {
        private int total_zhuangyuan = 1;
        private int total_duitang = 2;
        private int total_sanhong = 4;
        private int total_sijin = 8;
        private int total_erju = 16;
        private int total_yixiu = 32;

        ////分发奖项
        public void giveDuitang(Player player) {
            if (total_duitang > 0) {
                total_duitang--;
                player.winPrize("duitang");
            } else {
                System.out.println("对堂已被发完");
            }
        }

        public void giveSanhong(Player player) {
            if (total_sanhong > 0) {
                total_sanhong--;
                player.winPrize("sanhong");
            } else {
                System.out.println("三红已被发完");
            }
        }

        public boolean giveSijin(Player player) {
            if (total_sijin > 0) {
                total_sijin--;
                player.winPrize("sijin");
                return true;
            } else {
                System.out.println("四进已被发完");
                return false;
            }
        }

        public boolean giveErju(Player player) {
            if (total_erju > 0) {
                total_erju--;
                player.winPrize("erju");
                return true;
            } else {
                System.out.println("二举已被发完");
                return false;
            }
        }

        public boolean giveYixiu(Player player) {
            if (total_yixiu > 0) {
                total_yixiu--;
                player.winPrize("yixiu");
                return true;
            } else {
                System.out.println("一秀已被发完");
                return false;
            }
        }

        ////双奖判断
        public void giveErjuAndSijin(Player player) {
            boolean erjuOk = giveErju(player);
            boolean sijinOk = giveSijin(player);
            if (erjuOk && sijinOk) {
                player.winPrize("erju and sijin");
            } else if (erjuOk) {
                System.out.println("四进已发完，仅获得二举");
            } else if (sijinOk) {
                System.out.println("二举已发完，仅获得四进");
            } else {
                System.out.println("二举和四进都已发完，未获奖");
            }
        }

        public void giveYixiuAndSijin(Player player) {
            boolean yixiuOk = giveYixiu(player);
            boolean sijinOk = giveSijin(player);
            if (yixiuOk && sijinOk) {
                player.winPrize("yixiu and sijin");
            } else if (yixiuOk) {
                System.out.println("四进已发完，仅获得一秀");
            } else if (sijinOk) {
                System.out.println("一秀已发完，仅获得四进");
            } else {
                System.out.println("一秀和四进都已发完，未获奖");
            }
        }


        ////状元判断(包含抢壮元)
        public void giveZhuangyuan(Player p) {
            if (total_zhuangyuan == 1) {
                total_zhuangyuan--;
                System.out.println("玩家首次摇出状元，目前的状元是" + p.name);
                curZhuangyuanPlayer = p;
                cur_zhuangyuanscore = p.zhuangYuanScore;
                curZhuangyuanPlayer.iszhuangyuan = 1;
            } else {
                if (p.zhuangYuanScore <= cur_zhuangyuanscore) {
                    System.out.println("没有摇出更大的状元，状元不变");
                } else {
                    curZhuangyuanPlayer.iszhuangyuan = 0;
                    System.out.println(p.name + "摇出更大的状元，目前的状元是" + p.name);
                    curZhuangyuanPlayer = p;
                    curZhuangyuanPlayer.iszhuangyuan = 1;
                }

            }
        }

        ////判断奖项是否全部分发
        public boolean gameOver() {
            int[] prizeValues = new int[]{total_zhuangyuan, total_duitang, total_sanhong, total_sijin, total_erju, total_yixiu};
            for (int value : prizeValues) {
                if (value != 0) { // 只要有一个非0，就没清空
                    return false;
                }
            }
            return true; // 全部为0，已清空
        }

        ////打印出剩余奖项
        public void printRemainPrize() {
            System.out.println("剩余奖项：");
            System.out.println("状元：" + total_zhuangyuan + " | 对堂：" + total_duitang + " | 三红：" + total_sanhong);
            System.out.println("四进：" + total_sijin + " | 二举：" + total_erju + " | 一秀：" + total_yixiu);

        }

    }

    ////模拟摇骰子生成一次结果
    public static int[] generateSixRandom() {
        Random random = new Random();
        int[] nums = new int[6];
        for (int i = 0; i < 6; i++) {
            nums[i] = random.nextInt(6) + 1;
            System.out.print(nums[i] + " ");
        }
        System.out.print("\n");
        return nums;
    }

    //// 判断获得什么奖项
    public static String JudgePrize(int[] nums, Player player) {
        int[] count = new int[7];   //去掉下标0，对应1-6
        for (int num : nums) {
            count[num]++;
        }
        switch (count[4]) {
            case 6:
                player.zhuangYuanScore = 7;
                return "zhaungyuan";        //444444  状元;权值为7(最大)

            case 5:
                player.zhuangYuanScore = 3;
                return "zhaungyuan";         //44444    状元;权值为3

            case 4:
                if (count[1] == 2) {
                    player.zhuangYuanScore = 4;
                    return "zhaungyuan";      //444411 状元;权值为4
                } else {
                    player.zhuangYuanScore = 1;
                    return "zhaungyuan";    //4444 状元;权值为1
                }

            case 3:
                return "sanhong";     //444 三红

            case 2:
                if (count[1] == 4 || count[2] == 4 || count[3] == 4 || count[5] == 4 || count[6] == 4) {
                    return "erju and sijin";   //44+4n 双奖
                } else {
                    return "erju";      //44 二举
                }

            case 1:
                if (count[1] == 1 && count[2] == 1 && count[3] == 1 && count[5] == 1 && count[6] == 1) {
                    return "duitang";      //123456 对堂
                } else if (count[1] == 5 || count[2] == 5 || count[3] == 5 || count[5] == 5 || count[6] == 5) {
                    player.zhuangYuanScore = 2;
                    return "zhaungyuan";        // 11111/22222/33333/55555/66666 状元;权值为2
                } else if (count[1] == 4 || count[2] == 4 || count[3] == 4 || count[5] == 4 || count[6] == 4) {
                    return "yixiu and sijin";   //44+4n 双奖 二举加四进
                } else {
                    return "yixiu";      //4 一秀
                }

            case 0:
                if (count[1] == 6) {
                    player.zhuangYuanScore = 6;
                    return "zhaungyuan";         //111111 状元;权值为6
                } else if (count[2] == 6 || count[3] == 6 || count[5] == 6 || count[6] == 6) {
                    player.zhuangYuanScore = 5;
                    return "zhaungyuan";         // 222222/333333/555555/666666 状元;权值为5
                } else if (count[1] == 4 || count[2] == 4 || count[3] == 4 || count[5] == 4 || count[6] == 4) {
                    return "sijin";         // 1111/2222/3333/5555/6666 四进
                } else if (count[1] == 5 || count[2] == 5 || count[3] == 5 || count[5] == 5 || count[6] == 5) {
                    player.zhuangYuanScore = 2;
                    return "zhaungyuan";         // 11111/22222/33333/55555/66666 状元;权值为2
                } else {
                    return "no prize";
                }
            default:
                return "no prize";

        }


    }
}


void main() {
    Bobing.Prize prize = new Bobing.Prize();       //构建初始奖项数组
    Scanner sc = new Scanner(System.in);
    System.out.print("请输入玩家数量:");
    int playerCount = sc.nextInt();        //得到玩家数量


    List<Bobing.Player> players = new ArrayList<>();
    sc.nextLine(); // 清除换行//
    for (int i = 1; i <= playerCount; i++) {
        System.out.print("请输入第" + i + "位玩家的名字：");
        String name = sc.nextLine();
        players.add(new Bobing.Player(name));
    }

    System.out.println("是否开始自动模式：   1：是  ;  0： 否");
    String auto = sc.nextLine();
    if (auto.equals("1")) {
        Bobing.isAutoMode = true;
        System.out.println("开启自动模式");
    } else if (auto.equals("0")) {
        Bobing.isAutoMode = false;
        System.out.println("开启手动模式");
    } else {
        System.out.println("输入错误，仅支持1或0");
    }
    while (!prize.gameOver()) {
        boolean isGameEnd = false;
        ////   遍历所有玩家
        for (Bobing.Player p : players) {
            System.out.println("\n====================================");
            System.out.println(p.name + " 准备摇骰子，按回车键开始...");
            if (Bobing.isAutoMode) {
                System.out.println("【自动模式】已模拟按回车，继续游戏...");
            } else {
                sc.nextLine();// 等待玩家按回车触发摇骰子
            }
            //// 生成骰子结果
            System.out.print(p.name + " 摇出的骰子：");
            int[] dice = Bobing.generateSixRandom();

            ////判断奖项类型
            String prizeType = Bobing.JudgePrize(dice, p);

            ////根据奖项类型发放对应奖励
            switch (prizeType) {
                case "zhaungyuan":
                    prize.giveZhuangyuan(p);
                    break;
                case "duitang":
                    prize.giveDuitang(p);
                    break;
                case "sanhong":
                    prize.giveSanhong(p);
                    break;
                case "sijin":
                    prize.giveSijin(p);
                    break;
                case "erju":
                    prize.giveErju(p);
                    break;
                case "yixiu":
                    prize.giveYixiu(p);
                    break;
                case "erju and sijin":
                    prize.giveErjuAndSijin(p);
                    break;
                case "yixiu and sijin":
                    prize.giveYixiuAndSijin(p);
                    break;
                case "no prize":
                    p.winPrize("no prize");
                    break;
            }

            //// 检查是否所有奖项都发完，发完则结束游戏
            if (prize.gameOver()) {
                isGameEnd = true;
                break;
            }
        }
        if (isGameEnd) break;
    }
    System.out.println("\n====================================");
    System.out.println("游戏结束！");
    prize.printRemainPrize();

    System.out.println("\n====================================");
    System.out.println("最终获奖情况：");
    for (Bobing.Player p : players) {
        System.out.println("\n【" + p.name + "】：");
        System.out.println("是否为最终状元：" + (p.iszhuangyuan == 1 ? "是（权值：" + p.zhuangYuanScore + "）" : "否"));
        System.out.println("对堂：" + p.duitang + " | 三红：" + p.sanhong + " | 四进：" + p.sijin);
        System.out.println("二举：" + p.erju + " | 一秀：" + p.yixiu);
    }
    sc.close();
}