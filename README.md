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

Let's say we have some business logic we are trying to test. Our implementation of this business logic has a dependency on some external webService. When running our test, we don't want to use that external webService, because we don't want to test its behaviour. We want to create a mockup of what that external webService should be and use this version instead.

We want to create a *test stub*. Stub is a replacement for an object of class that our business logic has dependency on. We can inject this replacement into that class from our test to override use of external dependency.

In case of ISBN validator project, we are testing `StockManager`. This `StockManager` class is using method `lookup()` from webService, that implements `ExternalISBNDataService` interface. So in our test we implement this interface `ExternalISBNDataService` to have a behavior necessary for current test.

```java
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
```

However, this solution can be only used if our external dependency implements some interface. If this interface is too big then overriding it in a test would be too much work, and we need to use different solution.

## Testing behaviour

### Mock

Mock is similar to stub, but with a mock we get additional features. We are able to find if method was called or not. There are several libraries that provide stubs and mocks and we are using Mockito here.

To create a mocked object using Mockito library we use the `mock()` method passing it the class (or interface) that we want to mock.

```java
MyClass myClass = mock(MyClass.class);
```

What does this line of code? 
It creates for as a dummy implementation of passed class. 
Methods in this dummy class do essentially no functionality and in case of return value it returns null or object containing null values.
This is great because we don't have to worry about implementing all methods even tho we might not use them.
We can focus on providing just parts of implementation we really need.

To create and actual implementation of the methods, what we tell Mockito to do is: "when particular method is called with particular set of parameters, this is what you should return".

```java
when(myClass.myMethod(params)).thenReturn(return_value);
```

The equivalent of the assert "was this method called" uses `verify` method of Mockito.

```java
verify(myClass, times(?)).myMethod(params);
```

We say that we want to verify that in my class, following number of times, this method was called with these parameters.
We are saying that we don't care what the return value was, we just care how many times was this method called.

Mockito provides also more flexible way of setting expected parameters. 
If we don't care about value, as long as it is a String, we can use `anyString()` method as parameter. 
These `any...()` methods are available for multiple datatypes.
If we want to set our own class, we can use `any(MyClass.class)`.

Mockito also provide more flexible way of verifying number of execution of method. 
Next to `times()` method, there are also `atLeast()` and `atMost()` methods.
There is also alternative syntax to `times(0)` which is `never()`.