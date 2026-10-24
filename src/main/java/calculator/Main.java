package calculator;

public class Main {
    public static void main(String[] args) {
        double leftOperand = 10;
        double rightOperand = 5;
        Calculator calculator = new Calculator(new AddStrategy());

        System.out.println("Addition: " + calculator.calculate(leftOperand, rightOperand));

        calculator.setStrategy(new SubtractStrategy());
        System.out.println("Subtraction: " + calculator.calculate(leftOperand, rightOperand));

        calculator.setStrategy(new MultiplyStrategy());
        System.out.println("Multiplication: " + calculator.calculate(leftOperand, rightOperand));

        calculator.setStrategy(new DivisionStrategy());
        System.out.println("Division: " + calculator.calculate(leftOperand, rightOperand));

        calculator.setStrategy(new PowerStrategy());
        System.out.println("Power: " + calculator.calculate(leftOperand, rightOperand));
    }
}
