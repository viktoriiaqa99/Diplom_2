package client;

import constants.ApiConstants;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClient {

    @Step("Получение базовой спецификации запроса")
    protected RequestSpecification getSpec() {
        return given()
                .baseUri(ApiConstants.BASE_URI)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @Step("Получение спецификации запроса с токеном: {token}")
    protected RequestSpecification getSpecWithAuth(String token) {
        return getSpec()
                .header(ApiConstants.AUTHORIZATION_HEADER, token);
    }

    @Step("Получение спецификации запроса с Bearer токеном: {token}")
    protected RequestSpecification getSpecWithBearerToken(String token) {
        return getSpec()
                .header(ApiConstants.AUTHORIZATION_HEADER,
                        ApiConstants.BEARER_PREFIX + token);
    }
}