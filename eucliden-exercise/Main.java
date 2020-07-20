package com.company;

public class Main {

    public static void main(String[] args) {
        int N = 10;
        int M = 4;
        System.out.println(solution(N, M));
    }

   static int solution(int N, int M){
       int[] eatenChocolates = new int[N];
       int circleLimit = N-1;
       int chocolateIndex = 0;
       while (eatenChocolates[chocolateIndex] == 0){
           eatenChocolates[chocolateIndex] = 1;
           if(chocolateIndex + M > circleLimit){
               int remaining = N - chocolateIndex;
               chocolateIndex = M - remaining;
           }else{
               chocolateIndex = chocolateIndex + M;
           }
       }
       int count = 0;
       for (int number : eatenChocolates) {
           if(number == 1)
               count++;
       }
        return count;
   }
}
