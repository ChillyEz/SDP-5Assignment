package calculator;

public class DivisionStrategy implements CalculationStrategy {
    @Override
    public double calculate(double dividend, double divisor) {
        if (divisor == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        return dividend / divisor;
    }
}
