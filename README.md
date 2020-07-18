# Refactoring:

#### 1. The application classes are not structured in any way. Split them into logical packages.
#### 2. The register functionality is not finished. Finish it. You will have to do several things:
* #### The user must have a user role (ROLE_USER).
* #### Check if the two passwords (the password and the repeated password) match. Throw an exception if they don’t.
* #### The user’s password must be encrypted.

# Bugs:

#### 1. When trying to access the login page /login, the application throws an exception. Please check what’s happening.
#### 2. For some reason when you click on the “Purchase” button in the cart, none of the items get purchased but the cart is cleared. Investigate what’s happening.

# New functionality:

#### 1. Create a new page that lists all of the currently logged user’s purchases. The page should be accessible only for logged users.
#### 2. Implement a global exception handler. It should have a case for all custom exceptions in the application, as well as one general exception handler that catches everything else. All exceptions should lead to some informational page(or pages if you want) of your choice.
