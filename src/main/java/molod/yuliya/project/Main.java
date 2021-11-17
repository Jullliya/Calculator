package molod.yuliya.project;

import molod.yuliya.calculator.Calculator;
import java.lang.*;
import java.util.Objects;
import java.util.Scanner;
import java.lang.String;

/**
 * Main class
 */
public class Main {
    /**
     * Main method
     * @param args main arguments
     */
    public static void main(String[] args) {
        boolean IsCorrect; String text;
        Scanner in = new Scanner(System.in);
        do {
            System.out.print("Enter the expression: ");
            String EnteredExpression = in.nextLine();

            Calculator Expression = new Calculator(EnteredExpression);
            IsCorrect = Expression.Calculate();

            if (!IsCorrect) {
                System.out.print("The expression is incorrect");
            } else {
                System.out.print(EnteredExpression + " = ");
                System.out.println(Expression);
            }
            System.out.println("\nPress 'e' to exit");
            text = in.nextLine();
        }
        while (!Objects.equals(text, "e"));
    }
}
