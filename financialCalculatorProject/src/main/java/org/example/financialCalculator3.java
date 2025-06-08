package org.example;

import java.util.Scanner;

public class financialCalculator3 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the monthly payment amount in dollars? ");
        double payment = scanner.nextDouble();
        System.out.println("What is the interest rate?");
        double rate = scanner.nextDouble();
        System.out.println("Number of years since initial deposit?");
        int years = scanner.nextInt();
        int n = (12 * years);
        double r = rate/12/100;
        double pv  = payment * ( Math.pow(1 - (1 + r),(-n)) / r);
        System.out.printf("Present value of annuity is " + " %.2f",pv);







































    }
}
