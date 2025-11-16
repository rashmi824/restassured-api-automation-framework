package com.testautomation.apitesting.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.FileNameConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class PostAPIRequestUsingPojos {


    @Test
    public void postAPIRequest() throws IOException {

        //regarding the validate json schema *********
        String jsonSchema = FileUtils.readFileToString(new File(FileNameConstants.JSON_SCHEMA),"UTF-8");

        BookingDates bookingDates = new BookingDates("2025-03-25","2025-03-30");
        Booking booking = new Booking("Ann","Peter","breakfast",1000,true,bookingDates);

        //serialization
        ObjectMapper objectMapper =new ObjectMapper();
        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);

        System.out.println(requestBody);

        //de-serialization

        Booking bookingdetails = objectMapper.readValue(requestBody,Booking.class);
        System.out.println(bookingdetails.getFirstname());
        System.out.println(bookingdetails.getTotalprice());

        System.out.println(bookingdetails.getBookingdates().getCheckin());
        System.out.println(bookingdetails.getBookingdates().getCheckout());


        //post API call

        Response response =
        RestAssured
                .given()
                   .contentType(ContentType.JSON)
                   .body(requestBody)
                   .baseUri("https://restful-booker.herokuapp.com/booking")
                .when()
                   .post()
                .then()
                   .assertThat()
                   .statusCode(200)
                .extract()
                .response();

        int bookingId = response.path("bookingid");//fetch the booking Id


        //regarding the validate json schema ********* check the json schema values printing
        System.out.println(jsonSchema);

        //pass that booking Id to GET API call and validate application details on the resource server

        RestAssured
                .given()
                   .contentType(ContentType.JSON)
                   .baseUri("https://restful-booker.herokuapp.com/booking")
                .when()
                   .get("/{bookingId}",bookingId)
                .then()
                   .assertThat()
                   .statusCode(200)
                   .body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));//regarding the validate json schema ********* validate the json schema




    }




}
