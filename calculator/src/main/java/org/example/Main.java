package org.example;


import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Enter a value .");
        Scanner scanner = new Scanner(System.in);
        int userInput = scanner.nextInt();
        System.out.println("Please select onf of the following type A for, S for Subtract, M, for Multiply , D for Division");
        int secondInput = scanner.nextInt();
        System.out.println("What would you like to do with the two number? ");
        int numbersSum = Math.multiplyExact(userInput, secondInput);
        System.out.printf("The multiplied sum of both numbers is " + numbersSum);

        System.out.println(userInput * userInput);
        System.out.println("Enter a value");
        int secondInput = scanner.nextInt();
        System.out.println("What would you like to do with the two number? ");
        int numbersSum = Math.multiplyExact(userInput, secondInput);
        System.out.printf("The multiplied sum of both numbers is " + numbersSum);

       // System.out.println("What would you like to do with the two number?" =  + 'Arrays' .toString(args));
       // System.out.println("Main.main");
      //  System.out.println("secondInput = " + secondInput);

       // int numbersSum = Math.multiplyExact(userInput, secondInput);
       // System.out.printf("The multiplied sum of both numbers is " + numbersSum);

    }
}