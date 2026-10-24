# Clean Code examples from Chapters 9–11

This calculator demonstrates five concrete practices. The examples below describe
observable behavior and responsibilities in this project; they do not claim that
a small calculator needs enterprise architecture.

## 1. Single Responsibility — Chapter 10, Classes

**Evidence:** each strategy implements one arithmetic rule. `Calculator` manages
the active strategy and delegates calculation. `Main` selects operations and
prints their results.

```java
// PowerStrategy: a change to the power algorithm belongs here.
public double calculate(double base, double exponent) {
    return Math.pow(base, exponent);
}
```

Changing console labels affects `Main`, not `PowerStrategy`. Changing the division
rule affects `DivisionStrategy`, not the output code. These are separate reasons
to change. The benefit comes from those boundaries, not merely from short files.

**Demonstration:** locate the zero-divisor guard in `DivisionStrategy`. It belongs
to that operation, so another strategy does not need to know about it.

## 2. Isolating from Change — Chapter 10, Organizing for Change

**Evidence:** the context depends on `CalculationStrategy`, not concrete classes.

```java
// Calculator: every operation follows the same execution path.
public double calculate(double leftOperand, double rightOperand) {
    return strategy.calculate(leftOperand, rightOperand);
}
```

There is no list of known operation types inside `Calculator`. Adding an algorithm
does not require editing its execution logic. The test
`acceptsANewStrategyWithoutChangingTheContext` makes this claim executable:

```java
CalculationStrategy remainder = (dividend, divisor) -> dividend % divisor;
Calculator calculator = new Calculator(remainder);

double result = calculator.calculate(9, 2);

assertEquals(1.0, result);
```

The lambda supplies a new implementation of the same interface. A named
`RemainderStrategy` class could implement it in the same way. The client still
has to select the new operation; the context and existing strategies stay unchanged.

## 3. Encapsulation — Chapter 10, Class Organization

**Evidence:** the active strategy is private and every assignment validates it.

```java
private CalculationStrategy strategy;

public void setStrategy(CalculationStrategy strategy) {
    this.strategy = Objects.requireNonNull(
        strategy, "Calculation strategy cannot be null"
    );
}
```

The constructor uses the same validation. The invariant is specific: every
successfully constructed calculator always has a strategy. A client cannot bypass
validation through a public field. If a replacement is null, validation throws
before assignment and the existing strategy remains usable.

**Demonstration:** `rejectsNullReplacementWithoutLosingCurrentStrategy` checks both
rejection and preservation of the valid state. This is stronger evidence than
pointing to the `private` keyword alone.

## 4. Separate Construction from Use — Chapter 11, Systems

**Evidence:** `Main` constructs concrete strategies and supplies them to the context.

```java
// Main: construction and selection happen at the application entry point.
Calculator calculator = new Calculator(new AddStrategy());
calculator.setStrategy(new MultiplyStrategy());
```

`Calculator.calculate` only uses the supplied dependency. It does not construct a
default algorithm internally, look one up globally, or choose a type from a string.
This is manual dependency injection through the constructor and setter; it needs
no framework. Tests can supply their own strategy because construction is separate.

This complements example 2: the interface isolates the context from algorithm
changes, while `Main` determines where object creation and configuration belong.
There is no need to add factories, a dependency injection container, or services
for five stateless arithmetic operations.

## 5. Single Concept per Test and Clean Tests — Chapter 9, Unit Tests

**Evidence:** algorithm results, runtime replacement, accepting a new algorithm,
invalid replacement, and zero-divisor rejection are separate test methods.
The exception test has no unrelated recovery calculation:

```java
@ParameterizedTest(name = "Rejects divisor {0}")
@ValueSource(doubles = {0.0, -0.0})
void rejectsZeroDivisor(double divisor) {
    DivisionStrategy strategy = new DivisionStrategy();

    assertThrows(ArithmeticException.class, () -> strategy.calculate(9, divisor));
}
```

The strategy-switching test has several assertions because they describe one
transition sequence on the same object. A single concept does not require
mechanically limiting every test to one assertion. Parameterized tests give each
input its own reported result without duplicating the test body.

The suite also demonstrates four properties from **F.I.R.S.T.**:

- **Fast:** the tests perform in-memory arithmetic without external services.
- **Independent:** each invocation constructs its own objects; no test order is required.
- **Repeatable:** inputs are fixed, with no clock, randomness, files, or network calls in tests.
- **Self-validating:** JUnit assertions determine the result without manual output inspection.

Dependency downloads belong to build setup, not test behavior. **Timely** and the
three laws of TDD describe the development process; adding tests after implementation
does not establish them, so this project does not claim them as demonstrated practices.

## Supporting improvements and limits

Names such as `dividend`, `divisor`, `base`, and `exponent` make argument roles clear,
but meaningful naming is not presented as a Chapter 9–11 example. Similarly,
`final Calculator` prevents subclass overrides during constructor initialization;
it is not counted as an additional Clean Code principle.

Small strategy classes are justified by independent algorithms and runtime
interchangeability. Their size alone does not demonstrate cohesion in a complex
stateful class. No proxies, AOP, or other Chapter 11 infrastructure are added just
to increase the number of claimed principles.
