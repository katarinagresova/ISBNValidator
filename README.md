# Learning unit tests

ISBN validator is toy project for learning how to write unit tests in Java using JUnit and following logic of Test Driven Development (TDD).

## Rules of TDD

### 1) Examples and outcomes

Test the expected outcome of an example, do not test the logic, behaviour, the code, way of implementation, the architecture, ...

Test will generally work on basis of "I expect this particular example to result in a following outcome". By using lots of tests with lots of examples we will know we covered all the logic we need.

When writing test, thing about examples and outcomes, not code or how it should work in detail.

### 3) Don't pre-judge design... let your tests drive it

Decision around things like data types, class structures, even whole architecture of code in general will change over time as you come up with tests that determine what the requirements really are.



### X) Red -> Green -> Refactor

Always start with failing tests.