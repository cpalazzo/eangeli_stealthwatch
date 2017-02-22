# Stealthwatch Automation Testing Challenge
Summary
The purpose of these questions is for the test automation team to gain insight into how you write and test code. As such, include all source code with your answers and be sure to follow the best practices, patterns, and maintainability aspects of software engineering.  Be as thorough as you can.
Be aware that the questions may or may not be complete in their requirements. Feel free to add additional requirements as needed to solve the problem.

##Question 1
Write a method that answers the following problem:
Accept as input:
·       list: a singly-linked list
Remove the middle element of the list without iterating the list more than once.  Assume the list size cannot be known until traversed.
Support your answer with tests.
###Please see modules/problem1
assumptions, solution, and tests are in the code located in src/main and src/test

##Question 2
Write a method that answers the following problem:
Accept as input:
·       list: an unordered list of arrays each with 4 elements.
·       target: an integer
Find and display the complete array that contains the provided target number.
###Please see modules/problem2
assumptions, solution, and tests are in the code located in src/main and src/test

##Question 3
Assume a database with the following structure:
Members

|ID |NAME|ADDRESS|PHONE|NUMBER|AGE|

|---|----|-------|-----|------|---|

Organization

|ID |MEMBER_ID|LOCATION|DUES|

|---|---------|--------|----|

1.     Write a query that lists each member name, address, dues and location.
2.     Write a SQL Query to pull all members that are over 45
3.     Write a SQL Query to pull all members that have a dues value of 0.

###Please see modules/problem3
assumptions, solution, and tests are in the code located in src/main and src/test
the actual queries can be found either in the test code or in modules/problem3/src/main/resources and includes
a list of assumptions.

##How to run:
from a command line run : gradlew clean test
The output of the tests will be in the modules/<modulename>/build/reports
It includes an html formatted test report and code coverage report.
###SQL tests
There are tests that initialize a postgresql db and execute the queries and validate the responses.
They will not be executed by the above command. In order to run them you either need to
have a postgresql db running with the expected user/pw and db created (please see the code),
the user must also own the schema other wise the drop/create table will fail. Alternatively,
you can update the db settings to use a username/pw/db. It expects the db to be running on localhost at port 6543
the port and host can be updated with additional minor code changes.
