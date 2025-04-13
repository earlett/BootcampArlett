package org.example;


import java.util.Scanner;

public class financialCalculator2 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the initial deposit amount?" );
        double deposit = scanner.nextDouble();
        System.out.println("What is the annual interest rate?");
        double rate = scanner.nextDouble();
        System.out.println("What are the years occurred on deposit?");
        double years = scanner.nextDouble();
        double r = (rate/365);
        double l = (years * 12);
        double f = 365 * years;
        double j = 1+r/365;
        double k = Math.pow(j,f);
        double fv =  deposit*k;
        double totalInterest = fv - deposit;
        scanner.nextLine();
        System.out.printf("Your current is " + "%.2f " + "and the  total is " + "%.2f ", fv, totalInterest);
    }
}
