package com.testautomation.apitesting.tests;

import com.jayway.jsonpath.JsonPath;
import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.FileNameConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;



public class PostAPIRequestUsingFile extends BaseTest {

    @Test
    public void postAPIRequest() throws IOException {

        // Reads the entire file into a String using UTF-8 encoding
        String postAPIRequestBody= FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY),"UTF-8");

       //System.out.println(postAPIRequestBody);//print the request body on console

        Response response = //Create a variable called response to store the result of the API call
        RestAssured
                .given()//Start building the API request
                  .contentType(ContentType.JSON)//Tell the API we are sending JSON
                  .body(postAPIRequestBody)//Attach the request body
                  .baseUri("https://restful-booker.herokuapp.com/booking")//Set the API URL
                .when()
                  .post()//Send a POST request
                .then()
                  .assertThat()
                  .statusCode(200)//API returns status code 200
                .extract()
                  .response();//AFTER validation:Extract the entire API response,Return it,Save it inside the variable response



        // Read the API response body as a String, then use JsonPath to search inside the JSON.
        // The JSONPath query "$.booking..firstname" means:
        //   $              → start from the root of the JSON
        //   booking        → go inside the 'booking' object
        //   ..firstname    → find ALL 'firstname' keys inside 'booking', even if nested at any level
        // JsonPath.read(...) returns all matching values as a JSONArray.
        // So jsonArray will contain a list of all 'firstname' values found in the response.


        JSONArray jsonArray = JsonPath.read(response.body().asString(),"$.booking..firstname");
        String firstName = (String) jsonArray.get(0);// Get the first 'firstname' from the array and convert it to String

        Assert.assertEquals(firstName,"Ann");//Validate that the extracted firstname equals "Ann"

        JSONArray jsonArrayLastname = JsonPath.read(response.body().asString(),"$.booking..lastname");
        String lastName = (String) jsonArrayLastname.get(0);// Get the first 'lastname' from the array and convert it to String

        Assert.assertEquals(lastName,"Peter");







        // Extract 'checkin' directly as String (not an array)
        String checkin = JsonPath.read(response.body().asString(), "$.booking.bookingdates.checkin");

        // Validate
        Assert.assertEquals(checkin, "2018-01-01");


        int  BookingID = JsonPath.read(response.body().asString(),"$.bookingid");


        RestAssured
                .given()
                   .contentType(ContentType.JSON)
                   .baseUri("https://restful-booker.herokuapp.com/booking")
                .when()
                   .get("/{bookingId}",BookingID)
                .then()
                   .assertThat()
                   .statusCode(200);




    }


}

