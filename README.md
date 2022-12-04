# Sprint_3

Description: You need to test the Yandex.Samokat training service API. Its documentation: qa-scooter.praktikum-services.ru/docs/.

Create a Courier:

Courier can be created; 

You cannot create two identical couriers; 

To create a courier you must pass all required fields; 

Request returns correct response code: successful request returns ok: true; if one of the fields is missing, request returns error; if you create a user with a login that already exists, error is returned.

Login a Courier:

Courier can log in; 

All required fields must be passed for authorization; 

The system will return an error if the username or password is incorrect; if any field is missing, the request returns an error; 

If you authorize as a non-existent user, the request returns an error; a successful request returns id.

Create an order:

To test that when you create an order: you can specify one of the colors - BLACK or GREY; you can specify both colors; you can specify no color at all; 

The response body contains track. 

To test the creation of an order, you must use parameterization.

Order List:

Verify that the order list is returned in the response body.

Generate an Allure report.

