package calculator;

public class PowerStrategy implements CalculationStrategy {
    @Override
    public double calculate(double base, double exponent) {
        return Math.pow(base, exponent);
    }
}
