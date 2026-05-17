package com.orangehrm.test;


public class Test {

	
	public static void main(String abc[]) {

		// triangle
		
		for(int i=1; i<=10; i++) {
			for(int j=1; j<=10+i; j++) {
				System.out.print(" ");
			}for(int k=10; k<=i; k--) {
				System.out.print("* ");
			}
			System.out.println(" ");
		}
		
		   
		/*
		 * find the sum of the numbers 
		 * int num = 12345; int sum = String.valueOf(num) .chars()
		 * .map(Character::getNumericValue) .sum();
		 * System.out.println("Sum of digits = " + sum);
		 */
		

	}
}
