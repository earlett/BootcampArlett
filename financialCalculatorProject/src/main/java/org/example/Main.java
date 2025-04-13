package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("What is principal loan amount?");
        //String p = "principal";
        double principal = scanner.nextDouble();
        System.out.println("What is the length of your loan in years?");
        int numOfYears = scanner.nextInt();
        System.out.println("what is your annual interest rate?");
        double rate = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("""
                What would you like to calculate? Possible calculations:\s
                (m) monthly payments amount\s
                (t) total interest paid on loan\s
                Please Select an option""");



        //M = P[r(1+r)^n] / [(1+r)^n-1]
        String calculationType = scanner.nextLine();

      double r = rate/ 12/100;
      int n = (int) (numOfYears*12);
      double i = 1 + r;
      double o = Math.pow(i,n);
      double l = o * r;
      double left = principal * l;
      double right = o - 1;
      double m = left/right;
      double totalInterestPaid = m * n - principal;

        scanner.nextLine();
        if (calculationType.equalsIgnoreCase("m")){
            System.out.printf ("Your monthly payment is " + "%.2f", m);
        }

        else if (calculationType.equalsIgnoreCase("t")){
            System.out.printf("Your total interest on the principal is " + "%.2f" , totalInterestPaid);
          //M = P[r(1+r)^n] / [(1+r)^n-1]
        }




    }
}