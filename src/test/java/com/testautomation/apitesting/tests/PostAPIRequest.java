package com.testautomation.apitesting.tests;
import com.testautomation.apitesting.utils.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class PostAPIRequest extends BaseTest {

    @Test
    public void createBooking(){

       // RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
       //Enables logging only when a test fails, so you can debug the request and response.

        //Prepare request body

        JSONObject booking = new JSONObject();  // Creates two JSON objects:booking → main request body
        JSONObject bookingDates = new JSONObject();//bookingDates → nested object for check-in and check-out dates

        booking.put("firstname","Ann");//Adds key-value pairs (fields) to the main booking JSON.
        booking.put("lastname","Peter");
        booking.put("totalprice",1000);
        booking.put("depositpaid",true);
        booking.put("additionalneeds","breakfast");
        booking.put("bookingdates",bookingDates);

        bookingDates.put("checkin","2025-03-25");//Adds check-in and check-out dates inside the nested JSON object.
        bookingDates.put("checkout","2025-03-30");

        Response response =

        RestAssured
                .given()//Builds the request
                    .contentType(ContentType.JSON)//Defines it’s JSON format
                    .body(booking.toString())//Attaches the booking data as the body
                    .baseUri("https://restful-booker.herokuapp.com/booking")//Sets the base URI for the API endpoint
                   // .log().body()
                   // .log().all()

                .when()
                    .post()//Sends a POST request to create a new booking record.
                .then()
                    .assertThat()//Validates the response
                    //.log().body()
                    //.log().headers()
                    //.log().all()
                    //.log().ifValidationFails()
                    .statusCode(200)//Checks HTTP status code = 200 (success)
                    .body("booking.firstname", Matchers.equalTo("Ann"))//Verifies the response JSON contains expected values (name, price, check-in date)
                    .body("booking.totalprice",Matchers.equalTo(1000))
                    .body("booking.bookingdates.checkin",Matchers.equalTo("2025-03-25"))
                .extract()//Extracts the response object for later use (stored in variable response)
                    .response();

        int bookingId = response.path("bookingid");//Extracts the booking ID from the response JSON to use in the next GET request.

        RestAssured//Builds a new GET request
                .given()
                    .contentType(ContentType.JSON)//Sets JSON format
                    .pathParam("bookingID",bookingId)//Passes the booking ID as a path parameter
                    .baseUri("https://restful-booker.herokuapp.com/booking")//Uses the same API base URI
                .when()
                    .get("{bookingID}")//Sends a GET request to retrieve the booking details using the booking ID.
                .then()
                    .assertThat()
                    .statusCode(200)//Booking exists (status code 200)
                    .body("firstname", Matchers.equalTo("Ann"))//The name and last name match what was created earlier.
                    .body("lastname", Matchers.equalTo("Peter"));










    }

}


//Creates a new booking (POST request).
//
//Checks if the creation was successful.
//
//Extracts the booking ID.
//
//Retrieves the booking using that ID (GET request).
//
//Validates that the data matches what was originally sent.
