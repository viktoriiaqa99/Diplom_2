package client;

import constants.ApiConstants;
import io.restassured.response.ValidatableResponse;
import model.LoginRequest;
import model.User;

public class UserClient extends BaseClient {
    public ValidatableResponse register(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(ApiConstants.REGISTER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse login(LoginRequest loginRequest) {
        return getSpec()
                .body(loginRequest)
                .when()
                .post(ApiConstants.LOGIN_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse login(User user) {
        LoginRequest loginRequest = new LoginRequest(user.getEmail(), user.getPassword());
        return login(loginRequest);
    }

    public ValidatableResponse getUserInfo(String token) {
        return getSpecWithAuth(token)
                .when()
                .get(ApiConstants.USER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse updateUserInfo(User user, String token) {
        return getSpecWithAuth(token)
                .body(user)
                .when()
                .patch(ApiConstants.USER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse logout(String refreshToken) {
        String requestBody = String.format("{\"token\": \"%s\"}", refreshToken);
        return getSpec()
                .body(requestBody)
                .when()
                .post(ApiConstants.LOGOUT_PATH)
                .then()
                .log().all();
    }

    public String extractAccessToken(ValidatableResponse response) {
        return response.extract().path("accessToken");
    }

    public String extractRefreshToken(ValidatableResponse response) {
        return response.extract().path("refreshToken");
    }
}