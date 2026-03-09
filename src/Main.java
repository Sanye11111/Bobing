import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
       int[] nums= generateSixRandom();
       JudgePrize(nums);
    }

    ////模拟摇骰子生成一次结果
    public static int[] generateSixRandom(){
        Random random = new Random();
        int[] nums=new int[6];
        for (int i=0;i<6;i++){
            nums[i] = random.nextInt(6)+1;
            System.out.print(nums[i]+" ");
        }
        System.out.print("\n");
        return nums;
    }

    //// 判断获得什么奖项
    public static void JudgePrize(int[] nums){
        int[] count=new int[7];   //去掉下标0，对应1-6
        for(int num:nums){
            count[num]++;
        }
        switch(count[4]){
            case 6:
                System.out.print("中状元：六勃红");    //444444  状元;权值为7(最大)
                break;
            case 5:
                System.out.print("中状元：五王");     //44444    状元;权值为3
                break;
            case 4:
                if(count[1] == 2){
                    System.out.print("中状元：状元插金花");      //444411 状元;权值为4
                }
                else{
                    System.out.print("中状元：四红");     //4444 状元;权值为1
                }
                break;
            case 3:
                System.out.print("中奖：三红");      //444 三红
                break;
            case 2:
                if(count[1] == 4 || count[2] == 4 || count[3] == 4 || count[5] == 4 || count[6] == 4 ) {
                    System.out.print("好事成双：二举+四进");   //44+4n 双奖
                }
                else{
                    System.out.print("中奖：二举");      //44 二举
                }
                break;
            case 1:
                if(count[1] == 1 && count[2] == 1 && count[3] == 1 && count[5] == 1 && count[6] == 1 ){
                    System.out.print("中奖：对堂");      //123456 对堂
                }
                else if(count[1] == 4 || count[2] == 4 || count[3] == 4 || count[5] == 4 || count[6] == 4 ) {
                    System.out.print("好事成双：一举+四进");   //44+4n 双奖
                }
                else{
                    System.out.print("中奖：一秀");      //4 一秀
                }
                break;
            case 0:
                if(count[1] == 6){
                    System.out.print("中状元：遍地锦");        //111111 状元;权值为6
                }
                else if (count[2] == 6 || count[3] == 6 || count[5] == 6 || count[6] == 6 ) {
                    System.out.print("中状元：六勃黑");        // 222222/333333/555555/666666 状元;权值为5
                }
                else if (count[1] == 5 || count[2] == 5 || count[3] == 5 || count[5] == 5 || count[6] == 5 ) {
                    System.out.print("中状元：五子");        // 11111/22222/33333/55555/66666 状元;权值为2
                }
                else if (count[1] == 4 || count[2] == 4 || count[3] == 4 || count[5] == 4 || count[6] == 4 ) {
                    System.out.print("中奖：四进");        // 1111/2222/3333/5555/6666
                }
                else {
                    System.out.print("很遗憾未中奖");
                }

        }

    }
}
