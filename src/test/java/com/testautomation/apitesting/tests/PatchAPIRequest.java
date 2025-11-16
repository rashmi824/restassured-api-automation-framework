package com.testautomation.apitesting.tests;

import com.jayway.jsonpath.JsonPath;
import com.testautomation.apitesting.utils.FileNameConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class PatchAPIRequest {

    @Test
    public void patchAPIRequest() throws IOException {

        String postAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY),"UTF-8");

        String tokenAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.TOKEN_API_REQUEST_BODY),"UTF-8");

        String putAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PUT_API_REQUEST_BODY),"UTF-8");

        String patchAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PATCH_API_REQUEST_BODY),"UTF-8");


        //post api call
        Response response =
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
                        .response();

        JSONArray jsonArray = JsonPath.read(response.body().asString(),"$.booking..firstname");
        String firstName = (String) jsonArray.get(0);// Get the first 'firstname' from the array and convert it to String

        Assert.assertEquals(firstName,"Ann");//Validate that the extracted firstname equals "Ann"



        int  BookingID = JsonPath.read(response.body().asString(),"$.bookingid");

        //get api call
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri("https://restful-booker.herokuapp.com/booking")
                .when()
                .get("/{bookingId}",BookingID)
                .then()
                .assertThat()
                .statusCode(200);

        //token generationa
        Response tokenAPIResponse =
                RestAssured
                        .given()
                        .contentType(ContentType.JSON)
                        .body(tokenAPIRequestBody)
                        .baseUri("https://restful-booker.herokuapp.com/auth")
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .response();

        String token = JsonPath.read(tokenAPIResponse.body().asString(),"$.token");

        //put api call
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(putAPIRequestBody)
                .header("Cookie", "token=" + token)
                .baseUri("https://restful-booker.herokuapp.com/booking")
                .when()
                .put("/{bookingId}",BookingID)
                .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", Matchers.equalTo("Preeti111"))
                .body("lastname", Matchers.equalTo("Zinta"));

        //patch api call
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(patchAPIRequestBody)
                .header("Cookie","token="+token)
                .baseUri("https://restful-booker.herokuapp.com/booking")
                .when()
                .patch("/{bookingId}",BookingID)
                .then()
                .assertThat()
                .statusCode(200)
                .body("firstname",Matchers.equalTo("Postman"));





    }
}
