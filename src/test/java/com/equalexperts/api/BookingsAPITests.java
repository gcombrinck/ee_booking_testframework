package com.equalexperts.api;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingsAPITests {
    private int bookingID;
    private String payload = "{\n" +
            "  \"firstname\": \"Garth\",\n" +
            "  \"lastname\": \"Combrinck\",\n" +
            "  \"totalprice\": \"1000\",\n" +
            "  \"depositpaid\": \"true\",\n" +
            "  \"bookingdates\": {\n" +
            "    \"checkin\": \"2020-05-01\",\n" +
            "    \"checkout\": \"2020-05-30\"\n" +
            "  }\n" +
            "}";


    @Test
    public void makeBooking() {
         bookingID = given().
                when().
                contentType(ContentType.JSON).
                body(payload).
                post("http://hotel-test.equalexperts.io/booking").
                then().
                 body("booking.firstname", equalTo("Garth")).
                 body("booking.lastname", equalTo("Combrinck")).
                 body("booking.totalprice", equalTo(1000)).
                 body("booking.depositpaid", equalTo(true)).
                 body("booking.bookingdates.checkin", equalTo("2020-05-01")).
                 body("booking.bookingdates.checkout", equalTo("2020-05-30")).
                log().body()
                .assertThat().
                        statusCode(200)
         .extract().path("bookingid");
        System.out.println(bookingID);
    }

    @Test(dependsOnMethods = {"makeBooking"})
    public void getBookingByID() {
        JsonPath expectedJson = new JsonPath(new File("test.json"));
        given().
                when().
                get("http://hotel-test.equalexperts.io/booking/"+bookingID).
                then().
                log().body().
                body("firstname", equalTo("Garth")).
                body("lastname", equalTo("Combrinck")).
                body("totalprice", equalTo(1000)).
                body("depositpaid", equalTo(true)).
                body("bookingdates.checkin", equalTo("2020-05-01")).
                body("bookingdates.checkout", equalTo("2020-05-30"))
                .assertThat().
                statusCode(200);
    }

    @Test(dependsOnMethods = "getBookingByID")
    public void deleteBookingByID(){
        given().
                when().
                header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=").
                delete("http://hotel-test.equalexperts.io/booking/"+bookingID).
                then().
                log().body()
                .assertThat().
                statusCode(201);
    }
}
