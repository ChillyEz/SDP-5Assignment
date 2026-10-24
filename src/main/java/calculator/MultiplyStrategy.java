package calculator;

public class MultiplyStrategy implements CalculationStrategy {
    @Override
    public double calculate(double leftOperand, double rightOperand) {
        return leftOperand * rightOperand;
    }
}
