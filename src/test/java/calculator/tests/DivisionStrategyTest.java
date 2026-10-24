package calculator.tests;

import calculator.DivisionStrategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DivisionStrategyTest {
    @ParameterizedTest(name = "Rejects divisor {0}")
    @ValueSource(doubles = {0.0, -0.0})
    void rejectsZeroDivisor(double divisor) {
        DivisionStrategy strategy = new DivisionStrategy();

        assertThrows(ArithmeticException.class, () -> strategy.calculate(9, divisor));
    }
}
