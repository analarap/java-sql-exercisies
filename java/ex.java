import java.util.Scanner;
import java.util.Stack;

public class ExpressionSolver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite uma expressão matemática (ou 'sair' para encerrar):");

        while (true) {
            System.out.print("> ");
            String expression = scanner.nextLine().replaceAll(" ", "");

            if (expression.equalsIgnoreCase("sair")) {
                break;
            }

            try {
                solveExpression(expression);
            } catch (Exception e) {
                System.out.println("Erro ao processar a expressão: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void solveExpression(String expression) {
        System.out.println(expression);
        while (containsParenthesesOrExponent(expression)) {
            expression = simplifyStep(expression);
        }

        if (!expression.equals(evaluateExpression(expression))) {
            System.out.println(evaluateExpression(expression));
        }
    }

    private static boolean containsParenthesesOrExponent(String expression) {
        return expression.contains("(") || expression.contains("^");
    }

    private static String simplifyStep(String expression) {
        while (expression.contains("(")) {
            int closeIndex = expression.indexOf(")");
            int openIndex = expression.lastIndexOf("(", closeIndex);
            String subExpression = expression.substring(openIndex + 1, closeIndex);
            String evaluated = evaluateExpression(subExpression);
            expression = expression.substring(0, openIndex) + evaluated + expression.substring(closeIndex + 1);
            System.out.println(expression);
        }

        while (expression.contains("^")) {
            expression = evaluateExponentiation(expression);
            System.out.println(expression);
        }

        return evaluateExpression(expression);
    }

    private static String evaluateExponentiation(String expression) {
        Stack<Integer> indexStack = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '^') {
                indexStack.push(i);
            }
        }

        while (!indexStack.isEmpty()) {
            int index = indexStack.pop();
            int baseStart = index - 1;
            int expEnd = index + 1;

            while (baseStart >= 0 && Character.isDigit(expression.charAt(baseStart))) {
                baseStart--;
            }
            baseStart++;

            while (expEnd < expression.length() && Character.isDigit(expression.charAt(expEnd))) {
                expEnd++;
            }

            double base = Double.parseDouble(expression.substring(baseStart, index));
            double exponent = Double.parseDouble(expression.substring(index + 1, expEnd));
            double result = Math.pow(base, exponent);

            expression = expression.substring(0, baseStart) + (int) result + expression.substring(expEnd);
        }
        return expression;
    }

    private static String evaluateExpression(String expression) {
        return Integer.toString(evaluate(expression));
    }

    private static int evaluate(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            int parse() {
                nextChar();
                int x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Caractere inesperado: " + (char) ch);
                return x;
            }

            int parseExpression() {
                int x = parseTerm();
                while (true) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            int parseTerm() {
                int x = parseFactor();
                while (true) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            int parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                int x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else {
                    while (ch >= '0' && ch <= '9') nextChar();
                    x = Integer.parseInt(expression.substring(startPos, this.pos));
                }
                return x;
            }
        }.parse();
    }
}
