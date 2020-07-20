package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] a = new int[6];
        a[0] = 2;  a[1] = 1;  a[2] = 1;
        a[3] = 2;  a[4] = 3;  a[5] = 1;
        System.out.println(solution(a));
    }

   static int solution(int[] a){
       if(100000 < a.length){
           System.out.println("This list has a number of elements out of the stipulated range [0 to 100,000");
           return -1;
       }
       int index = 0;
       int[] distinctValues = new int[index];
       for(int x = 0;x<a.length;x++){
           if( a[x] < -1000000  && 1000000 < a[x] ){
               System.out.println("This list contains elements out of the stipulated range [-1,000,000 to 1,000,000");
               return -1;
           }
           int finalX = x;
           if(Arrays.stream(distinctValues).noneMatch(i -> i == a[finalX])){
               int[] temporaryArray = new int[index + 1];
               System.arraycopy(distinctValues, 0, temporaryArray, 0, distinctValues.length);
               temporaryArray[index] = a[x];
               index++;
               distinctValues = temporaryArray;
           }
       }
       return distinctValues.length;
   }
}
