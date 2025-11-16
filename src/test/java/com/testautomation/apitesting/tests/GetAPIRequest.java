package com.testautomation.apitesting.tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;

public class GetAPIRequest {

    @Test
    public void getAllBooking(){

        Response response =

        RestAssured
                .given()// Start the RestAssured request setup
                   .contentType(ContentType.JSON)// Define request specifications
                   .baseUri("https://restful-booker.herokuapp.com/booking")// Set the base URI for the API endpoint
                .when() // Specify the action to perform (send the request)
                   .get() // Send a GET request to the given URI
                .then()
                   .assertThat()  // Start verifying the response
                   .statusCode(200)// Check that the response status code is 200 (OK)
                   .statusLine("HTTP/1.1 200 OK") // Verify that the response status line matches "HTTP/1.1 200 OK"
                   .header("Content-Type", "application/json; charset=utf-8")
                .extract() // Extract the response object after validation
                    .response();
        Assert.assertTrue(response.getBody().asString().contains("bookingid"));// Verify that the response body contains the text "bookingid"



    }
}
