package com.jpmc.AccountValidator.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
class AccountControllerIT {

    WireMockServer wireMockServer;

    @BeforeEach
    public void setup (TestInfo info) {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        if(!info.getDisplayName().equals("PROVIDER_NOT_FOUND")){
            setupProvider1();
            setupProvider2();
        }
    }
    @AfterEach
    public void teardown () {
        wireMockServer.stop();
    }

    public void setupProvider1() {
        String provider1Response = "{ \n" +
                "\"isValid\": \"true\"\n" +
                "}\n";
        wireMockServer.stubFor(post(urlEqualTo("/v1/api/account/validate"))
                .withRequestBody(equalToJson("{\"accountNumber\": \"12345678\"}"))
                .willReturn(aResponse()
                       .withStatus(200)
                        .withBody(provider1Response)));
    }

    public void setupProvider2() {
        String provider2Response = "{ \n" +
                "\"isValid\": \"true\"\n" +
                "}\n";
        wireMockServer.stubFor(post(urlEqualTo("/v2/api/account/validate"))
                .withRequestBody(equalToJson("{\"accountNumber\": \"12345678\"}"))
                .willReturn(aResponse()
                       .withStatus(200)
                        .withBody(provider2Response)));
    }

    @Test
    @DisplayName("ACCOUNT_PROVIDER_1")
    public void testProvider1() {
     String payload = "{\n" +
             "  \"accountNumber\": \"12345678\",\n" +
             "  \"providers\": [\n" +
             "    \"provider1\"\n" +
             "  ]\n" +
             "}";
        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("http://localhost:8080/accountValidator/validate")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    @DisplayName("ACCOUNT_PROVIDER_2")
    public void testProvider2() {
        String payload = "{\n" +
                "  \"accountNumber\": \"12345678\",\n" +
                "  \"providers\": [\n" +
                "    \"provider2\"\n" +
                "  ]\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("http://localhost:8080/accountValidator/validate")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    @DisplayName("ACCOUNT_PROVIDER_1_2")
    public void testProvider12() {
        String payload = "{\n" +
                "  \"accountNumber\": \"12345678\",\n" +
                "  \"providers\": [\n" +
                "    \"provider1\",\n" +
                "    \"provider2\"\n" +
                "  ]\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("http://localhost:8080/accountValidator/validate")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    @DisplayName("NO_ACCOUNT_PROVIDER")
    public void testNoProvider() {
        String payload = "{\n" +
                "  \"accountNumber\": \"12345678\"\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("http://localhost:8080/accountValidator/validate")
                .then()
                .log().all()
                .statusCode(460)
                .extract()
                .response();
    }

    @Test
    @DisplayName("EMPTY_ACCOUNT_PROVIDER")
    public void testEmptyProviders() {
        String payload = "{\n" +
                "  \"accountNumber\": \"12345678\",\n" +
                "  \"providers\": [\n" +
                "  ]\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("http://localhost:8080/accountValidator/validate")
                .then()
                .log().all()
                .statusCode(460)
                .extract()
                .response();
    }

    @Test
    @DisplayName("NO_ACCOUNT_NUMBER")
    public void testNoAccountNumber() {
        String payload = "{\n" +
                "  \"providers\": [\n" +
                "    \"provider1\",\n" +
                "    \"provider2\"\n" +
                "  ]\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("http://localhost:8080/accountValidator/validate")
                .then()
                .log().all()
                .statusCode(461)
                .extract()
                .response();
    }

    @Test
    @DisplayName("EMPTY_ACCOUNT_NUMBER")
    public void testEmptyAccountNumber() {
        String payload = "{\n" +
                "  \"accountNumber\": \"\",\n" +
                "  \"providers\": [\n" +
                "    \"provider1\",\n" +
                "    \"provider2\"\n" +
                "  ]\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("http://localhost:8080/accountValidator/validate")
                .then()
                .log().all()
                .statusCode(461)
                .extract()
                .response();
    }

    @Test
    @DisplayName("PROVIDER_NOT_FOUND")
    public void testProviderNotFoundException() {
        String payload = "{\n" +
                "  \"accountNumber\": \"12345678\",\n" +
                "  \"providers\": [\n" +
                "    \"provider1\",\n" +
                "    \"provider2\"\n" +
                "  ]\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("http://localhost:8080/accountValidator/validate")
                .then()
                .log().all()
                .statusCode(599)
                .extract()
                .response();
    }

}