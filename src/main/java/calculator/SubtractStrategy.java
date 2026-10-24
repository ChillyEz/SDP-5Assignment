package calculator;

public class SubtractStrategy implements CalculationStrategy {
    @Override
    public double calculate(double leftOperand, double rightOperand) {
        return leftOperand - rightOperand;
    }
}
