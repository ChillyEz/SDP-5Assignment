package calculator.tests;

import calculator.*;
import calculator.CalculationStrategy;
import calculator.Calculator;
import calculator.DivisionStrategy;
import calculator.MultiplyStrategy;
import calculator.PowerStrategy;
import calculator.SubtractStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    @ParameterizedTest(name = "{0}: 9 and 2 produce {2}")
    @MethodSource("operations")
    void calculatesUsingSelectedStrategy(String operation, CalculationStrategy strategy, double expected) {
        Calculator calculator = new Calculator(strategy);

        double result = calculator.calculate(9, 2);

        assertEquals(expected, result);
    }

    static Stream<Arguments> operations() {
        return Stream.of(
            Arguments.of("Addition", new AddStrategy(), 11.0),
            Arguments.of("Subtraction", new SubtractStrategy(), 7.0),
            Arguments.of("Multiplication", new MultiplyStrategy(), 18.0),
            Arguments.of("Division", new DivisionStrategy(), 4.5),
            Arguments.of("Power", new PowerStrategy(), 81.0)
        );
    }

    @Test
    void switchesStrategyOnTheSameCalculator() {
        Calculator calculator = new Calculator(new AddStrategy());
        assertEquals(11.0, calculator.calculate(9, 2));

        calculator.setStrategy(new MultiplyStrategy());
        assertEquals(18.0, calculator.calculate(9, 2));

        calculator.setStrategy(new AddStrategy());
        assertEquals(11.0, calculator.calculate(9, 2));
    }

    @Test
    void acceptsANewStrategyWithoutChangingTheContext() {
        CalculationStrategy remainder = (dividend, divisor) -> dividend % divisor;
        Calculator calculator = new Calculator(remainder);

        double result = calculator.calculate(9, 2);

        assertEquals(1.0, result);
    }

    @Test
    void rejectsNullReplacementWithoutLosingCurrentStrategy() {
        Calculator calculator = new Calculator(new AddStrategy());

        assertThrows(NullPointerException.class, () -> calculator.setStrategy(null));

        assertEquals(11.0, calculator.calculate(9, 2));
    }
}
