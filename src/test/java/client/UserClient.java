package client;

import constants.ApiConstants;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.LoginRequest;
import model.User;

import java.util.Map;

public class UserClient extends BaseClient {

    @Step("Регистрация пользователя: {user}")
    public ValidatableResponse register(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(ApiConstants.REGISTER_PATH)
                .then()
                .log().all();
    }

    @Step("Логин пользователя с email: {loginRequest.email}")
    public ValidatableResponse login(LoginRequest loginRequest) {
        return getSpec()
                .body(loginRequest)
                .when()
                .post(ApiConstants.LOGIN_PATH)
                .then()
                .log().all();
    }

    @Step("Логин пользователя: {user.email}")
    public ValidatableResponse login(User user) {
        LoginRequest loginRequest = new LoginRequest(user.getEmail(), user.getPassword());
        return login(loginRequest);
    }

    @Step("Получение информации о пользователе")
    public ValidatableResponse getUserInfo(String token) {
        return getSpecWithAuth(token)
                .when()
                .get(ApiConstants.USER_PATH)
                .then()
                .log().all();
    }

    @Step("Обновить информацию о пользователе")
    public ValidatableResponse updateUserInfo(User user, String token) {
        return getSpecWithAuth(token)
                .body(user)
                .when()
                .patch(ApiConstants.USER_PATH)
                .then()
                .log().all();
    }

    @Step("Выйти из системы")
    public ValidatableResponse logout(String refreshToken) {
        // Изменено: передаю тело через сериализацию, а не через стрингу
        Map<String, String> body = Map.of("token", refreshToken);
        return getSpec()
                .body(body)
                .when()
                .post(ApiConstants.LOGOUT_PATH)
                .then()
                .log().all();
    }

    @Step("Удалить пользователя")
    public ValidatableResponse delete(String accessToken) {
        return getSpecWithAuth(accessToken)
                .when()
                .delete(ApiConstants.USER_PATH)
                .then()
                .log().all();
    }

    @Step("Извлечение accessToken из ответа")
    public String extractAccessToken(ValidatableResponse response) {
        return response.extract().path("accessToken");
    }

    @Step("Извлечение refreshToken из ответа")
    public String extractRefreshToken(ValidatableResponse response) {
        return response.extract().path("refreshToken");
    }
}