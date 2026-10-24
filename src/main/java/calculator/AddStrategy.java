package calculator;

public class AddStrategy implements CalculationStrategy {
    @Override
    public double calculate(double leftOperand, double rightOperand) {
        return leftOperand + rightOperand;
    }
}
