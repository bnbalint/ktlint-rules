# Ktlint

This project creates a jar of custom rules to applied with ktlint. <br>

## Rules

- NoPrint
    - disallow the usage of the `print ` function
- NoPrintln
    - disallow the usage of the `println` function
- NoNotNullAssertionOperator
    - disallow the usage of the `!!` operator
- EndpointTimedAnnotation
    - when `@RequestMapping` is used, it is required that `@Timed` is also used

