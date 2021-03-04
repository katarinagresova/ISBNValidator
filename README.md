# Learning unit tests

ISBN validator is toy project for learning how to write unit tests in Java using JUnit and following logic of Test Driven Development (TDD).

## Unit testing tips

1) Use long and descriptive names for your tests. If any point in the future one of your tests fails, it will help you know straight away what it is that is not working.

2) Test are not fot testing methods but for testing business logic. There should not be a 1-1 relationship between tests and methods.

3) Tests must be repeatable and consistent. Given the same data, tests must produce the same results every time.

## Rules of TDD

### 1) Examples and outcomes

Test the expected outcome of an example, do not test the logic, behaviour, the code, way of implementation, the architecture, ...

Test will generally work on basis of "I expect this particular example to result in a following outcome". By using lots of tests with lots of examples we will know we covered all the logic we need.

When writing test, thing about examples and outcomes, not code or how it should work in detail.

### 2) Don't pre-judge design... let your tests drive it

Decision around things like data types, class structures, even whole architecture of code in general will change over time as you come up with tests that determine what the requirements really are.

### 3) Write the minimum code required to get your tests to pass

We are not interested in building the correct and full functionality at this point

### 4) Each test should validate one single piece of logic

You expect to have only one `assert` in a test method. If there are multiple `asserts` in test method, it needs to fit one of these following examples:

1) you need to test more than one value to check that something work correctly (for example, if method is setting multiple class variables, we you can use multiple `asserts` to check if they are set properly)

2) you want to test multiple values that are just different examples of the same thing (for example, multiple valid ISBNs)

### X) Red -> Green -> Refactor

Always start with failing tests.

## Testing code with dependencies

Let's say we have some business logic we are trying to test. Our implementation of this business logic has a dependency on some external service. When running our test, we don't want to use that external service, because we don't want to test its behaviour. We want to create a mockup of what that external service should be and use this version instead.

We want to create a *test stub*. Stub is a replacement for an object of class that our business logic has dependency on. We can inject this replacement into that class from our test to override use of external dependency.

In case of ISBN validator project, we are testing `StockManager`. This `StockManager` class is using method `lookup()` from service, that implements `ExternalISBNDataService` interface. So in our test we implement this interface `ExternalISBNDataService` to have a behavior necessary for current test.

    ExternalISBNDataService testService = new ExternalISBNDataService() {
        @Override
        public Book lookup(String isbn) {
            return new Book(isbn, "Of Mice and Man", "J. Steinbeck");
        }
    };
    
    StockManager stockManager = new StockManager();
    stockManager.setService(testService);
    
    String isbn = "0140177396";
    String locatorCode = stockManager.getLocatorCode(isbn);

However, this solution can be only used if our external dependency implements some interface. If this interface is too big then overriding it in a test would be too much work, and we need to use different solution.