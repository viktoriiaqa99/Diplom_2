package client;

import constants.ApiConstants;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClient {
    protected RequestSpecification getSpec() {
        return given()
                .baseUri(ApiConstants.BASE_URI)
                .contentType(ContentType.JSON)
                .log().all();
    }

    protected RequestSpecification getSpecWithAuth(String token) {
        return getSpec()
                .header(ApiConstants.AUTHORIZATION_HEADER, token);
    }

    protected RequestSpecification getSpecWithBearerToken(String token) {
        return getSpec()
                .header(ApiConstants.AUTHORIZATION_HEADER,
                        ApiConstants.BEARER_PREFIX + token);
    }
}