package molod.yuliya.calculator;

import java.util.Objects;
import java.util.Stack;

/**
 * A class that evaluates the value of an expression.
 */
public class Calculator {

    /**
     * Expression containing numbers, operators, brackets, spaces, other characters.
     */
    private String Expression;

    /**
     * Constructor of the Calculator class
     * @param EnteredExpression Expression
     */
    public Calculator(String EnteredExpression) {
        this.Expression = EnteredExpression;
    }

    /**
     * Method for removing spaces from an expression
     */
    private void DeleteSpace()
    {
        StringBuilder NewExpression = new StringBuilder();
        for (int index = 0; index < Expression.length(); index++)
            if (Expression.charAt(index) != ' ')
                NewExpression.append(Expression.charAt(index));
        Expression = NewExpression.toString();
    }

    /**
     * The method of checking the expression for correctness
     * @return True if the expression is true, otherwise false
     */
    private boolean Correctness()
    {
        if (Expression.isEmpty()) return false;
        else {
            DeleteSpace();
            int bracket = 0;

            for (int index = 0; index < Expression.length(); index++) {

                if (bracket >= 0) {

                    switch (Expression.charAt(index)) {

                        case '+': case '-': case '*': case '/': {
                            if(index == 0 || index == Expression.length() - 1) return false;
                        else
                            if (Expression.charAt(index+1) == '+' || Expression.charAt(index+1) == '-' || Expression.charAt(index+1) == '*' || Expression.charAt(index+1) == '/' || Expression.charAt(index+1) == ')')
                            return false;
                            break;
                        }

                        case '(': {
                            bracket++;
                            if (Expression.charAt(index+1) == '+' || Expression.charAt(index+1) == '-' || Expression.charAt(index+1) == '*' || Expression.charAt(index+1) == '/' ||  Expression.charAt(index+1) == ')')
                                return false;
                            else if (index == Expression.length() - 1) return false;
                            break;
                        }

                        case ')': {
                            bracket--;
                            if (index == 0) return false;
                            else if (Expression.charAt(index-1) == '+' || Expression.charAt(index-1) == '-' || Expression.charAt(index-1) == '*' || Expression.charAt(index-1) == '/' || Expression.charAt(index-1) == '(' )
                                return false;
                            break;
                        }

                        case 's': {
                            if(index == Expression.length()-1) return false;
                            else if (Expression.charAt(index+1) != 'i' && Expression.charAt(index+2) != 'n' && Expression.charAt(index+3) != '(')
                                return false;
                            else if (Expression.charAt(index+1) != 'q' && Expression.charAt(index+2) != 'r' && Expression.charAt(index+3) != 't' && Expression.charAt(index+4) != '(')
                                return false;
                            break;
                        }

                        case 'c': {
                            if(index == Expression.length()-1) return false;
                            else if (Expression.charAt(index+1) != 'o' && Expression.charAt(index+2) != 's' && Expression.charAt(index+3) != '(')
                                return false;
                            else if (Expression.charAt(index+2) != 't' && Expression.charAt(index+3) != '(')
                                return false;
                            break;
                        }

                        case 't': {
                            if(index == Expression.length()-1) return false;
                            else if (Expression.charAt(index+1) != 'a' && Expression.charAt(index+2) != 'n' && Expression.charAt(index+2) != '(')
                                return false;
                            break;
                        }

                        default:
                            if (Expression.charAt(index) >= '0' && Expression.charAt(index) <= '9') {
                                if (index != 0)
                                    if (Expression.charAt(index-1) == ')' )
                                        return false;
                                if (index != Expression.length()-1)
                                    if (Expression.charAt(index+1) == '(' )
                                        return false;
                            }
                            else return false;
                    }
                }
                else return false;
            }
            return bracket == 0;
        }
    }

    /**
     * A method that determines the operator's priority.
     * @param Operation Symbol (operator, bracket)
     * @return Symbol priority
     */
    private int Priority(char Operation) {
        if (Operation == '*' || Operation == '/')
            return 3;
        else if (Operation == '+' || Operation == '-')
            return 2;
        else if (Operation == '(')
            return 1;
        else if (Operation == ')')
            return -1;
        return 0;
    }

    /**
     * A method that overwrites an expression into a postfix form.
     * @return True if it was possible to write, otherwise false.
     */
    private boolean PostfixNotation() {

        if (!Correctness() || Expression.isEmpty())
            return false;

        else {
            Stack<Character> SymbolStack = new Stack<Character>();
            StringBuilder NewExprassion = new StringBuilder();

            for (int index = 0; index < Expression.length(); index++) {
                int typeoper = Priority(Expression.charAt(index));

                if (typeoper == 0) NewExprassion.append(Expression.charAt(index));

                else if (typeoper == 1) SymbolStack.push(Expression.charAt(index));

                else if (typeoper > 1) {
                    NewExprassion.append(' ');

                    while (!SymbolStack.empty()) {
                        if (Priority(SymbolStack.peek()) >= typeoper)
                            NewExprassion.append(SymbolStack.pop());
                        else break;
                    }

                    SymbolStack.push(Expression.charAt(index));
                }

                else if (typeoper == -1) {
                    NewExprassion.append(' ');

                    while (Priority(SymbolStack.peek()) != 1)
                        NewExprassion.append(SymbolStack.pop());

                    SymbolStack.pop();
                }
            }

            while (!SymbolStack.empty()) NewExprassion.append(SymbolStack.pop());
            Expression = NewExprassion.toString();
            return true;
        }
    }

    /**
     * A method that reads the value of an expression written in postfix form and writes the result in the field str.
     * @return True if it was calculated, otherwise false.
     */
    public boolean Calculate() {

        boolean t = PostfixNotation();
        if (!t) return false;
        else {

            StringBuilder res = new StringBuilder();
            Stack<Double> st = new Stack<Double>();

            for (int index = 0; index < Expression.length(); index++) {

                if (Expression.charAt(index) == ' ') continue;

                if (Priority(Expression.charAt(index)) == 0){

                    while (Expression.charAt(index) != ' ' && Priority(Expression.charAt(index)) == 0) {
                        res.append(Expression.charAt(index++));
                        if (index == Expression.length()) break;
                    }

                    st.push(Double.parseDouble(res.toString()));
                    res = new StringBuilder();
                }

                if (Priority(Expression.charAt(index)) > 1) {

                    double num1 = st.pop();
                    double num2 = st.pop();

                    if (Expression.charAt(index) == '+')
                        st.push(num2 + num1);

                    if (Expression.charAt(index) == '-')
                        st.push(num2 - num1);

                    if (Expression.charAt(index) == '*')
                        st.push(num2 * num1);

                    if (Expression.charAt(index) == '/')
                        st.push(num2 / num1);
                }
            }
            Expression = Double.toString(st.pop());
            return true;
        }
    }

    /**
     * The method that displays the value of the field str on the screen.
     */
    @Override
    public String toString() {
        return Expression;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Calculator that = (Calculator) obj;
        return Objects.equals(Expression, that.Expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Expression);
    }

}
