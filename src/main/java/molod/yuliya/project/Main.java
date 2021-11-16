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
        boolean t; String code;
        Scanner in = new Scanner(System.in);
        do {
            System.out.print("Enter the expression: ");
            String str = in.nextLine();

            Calculator exp = new Calculator(str);
            t = exp.calculate();

            if (!t) {
                System.out.print("The expression is incorrect.");
            } else {
                System.out.print(str + " = ");
                System.out.println(exp);
            }
            System.out.println("Press 'e' to exit.");
            code = in.nextLine();
        }
        while (!Objects.equals(code, "e"));
    }
}
