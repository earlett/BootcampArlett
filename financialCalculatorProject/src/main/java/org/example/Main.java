package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("What is principal loan amount?");
        //String p = "principal";
        double principal = scanner.nextFloat();
        System.out.println("What is the length of your loan in months?");
        int months = scanner.nextInt();
        System.out.println("what is your annual interest rate?");
        double rate = scanner.nextFloat();
        scanner.nextLine();
        System.out.println("What would you like to calculate?" + " Possible calculations: \n" +
        "(r) monthly interest rate \n"+
        "(m) monthly payments amount \n" +
        "(n) number of monthly payments \n"+
                        "Please Select an option");

        String calculationType = scanner.nextLine();

        if (calculationType.equalsIgnoreCase("r")){
        double rateResult;
            rateResult = (double) (principal/ months /12);
            System.out.printf ("Your Monthly rate is " + "%.2f%n", rateResult);
        }
        //operationInput = scanner.nextLine();

        //
        //Accept the principal (P), interest rate (r), and loan length (n) from the user as input.
       // step  1.  string "what is principal loan amount"
        // step 2. String = "what is your interest rate?"
        // step 3. String "what is the length of your loan in months?"
        //

        // Float p =
        //Calculate the monthly interest rate (r) by dividing the annual interest rate by 12 and then dividing by 100 to convert it to a decimal.
        //
        //Calculate the number of monthly payments (n) by multiplying the loan length in years by 12.
        //
        //Use this formula to calculate the monthly payment (M).
        //
        //M = P[r(1+r)^n] / [(1+r)^n-1]



        //M is the monthly payment.
        //    P is the principal amount (loan amount). float or double
        // float p = principal


        //    r is the monthly interest rate float int or float

        //    (annual interest rate divided by 12 and then divided by 100 to convert to a decimal). float
        //    n is the number of monthly payments (loan length in years multiplied by 12). int






















    }
}