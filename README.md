# County Suggestion API

This API will get suggested counties based on provided query string. Responds with an array, limited to 5 results, of counties where the county name or state matches the provided query string.

## Prerequisites

Java 20
Maven

## How to run this API?

Navigate to the root directory and run the following command

mvn spring-boot:run

## How to test this API?

Navigate to the root directory and run the following command

mvn verify

## How it works?

There are 2 endpoints in this system

1. Checks for state code first, if there are no suffeciuent results then checks for county name.

http://localhost:3000/getcountyp?q=YOUR_QUERY

2. Check for both state and county name in single query without any priorities 

http://localhost:3000/getcounty?q=YOUR_QUERY
