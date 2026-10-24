package calculator;

import java.util.Objects;

public final class Calculator {
    private CalculationStrategy strategy;

    public Calculator(CalculationStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(CalculationStrategy strategy) {
        this.strategy = Objects.requireNonNull(
            strategy, "Calculation strategy cannot be null"
        );
    }

    public double calculate(double leftOperand, double rightOperand) {
        return strategy.calculate(leftOperand, rightOperand);
    }
}
