package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] a = new int[7];
        a[0] = 9;  a[1] = 3;  a[2] = 9;
        a[3] = 3;  a[4] = 9;  a[5] = 7;
        a[6] = 9;
        System.out.println(solution(a));
    }

   static int solution(int[] a){
       if(a.length % 2 == 0) {
           System.out.println("This list has an even number of elements, it's not possible to determine only one element without a pair");
           return -1;
       }
       if(a.length == 0  || 1000000 < a.length){
           System.out.println("This list has a number of elements out of the stipulated range [1 to 1,000,000");
           return -1;
       }
       int[] listOfCountsForEachNum = new int[a.length];
       int count = 0;
       for(int x = 0;x<a.length;x++){
           if( a[x] < 1  && 1000000 < a[x] ){
               System.out.println("This list contains elements out of the stipulated range [1 to 1,000,000");
               return -1;
           }
           for (int i : a) {
               if (a[x] == i) {
                   count++;
               }
           }
           listOfCountsForEachNum[x] = count;
           count = 0;
       }
       int[] numbersWithoutPair = new int[listOfCountsForEachNum.length];
       for(int y : listOfCountsForEachNum){
           if(y % 2 == 1){
               numbersWithoutPair[count] = a[count];
           }
           count++;
       }
       if(Arrays.stream(numbersWithoutPair).filter(i -> i != 0).toArray().length > 1){
           System.out.println("More than one element is unpaired");
           System.out.println(Arrays.toString(Arrays.stream(numbersWithoutPair).filter(i -> i != 0).toArray()));
           return -1;
       }else{
           return Arrays.stream(numbersWithoutPair).filter(i -> i != 0).toArray()[0];
       }
   }
}
