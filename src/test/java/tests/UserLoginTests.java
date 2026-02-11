package tests;

import client.UserClient;
import constants.ApiConstants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.LoginRequest;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.TestDataGenerator;

import static org.hamcrest.Matchers.*;

public class UserLoginTests extends BaseTest {
    private final UserClient userClient = new UserClient();
    private User createdUser;
    private String accessToken;

    public UserLoginTests() {
        setupAllure();
    }

    @Before
    public void setUp() {
        createdUser = TestDataGenerator.generateUniqueUser();
        userClient.register(createdUser)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.OK);
    }

    @After
    public void tearDown() {
        createdUser = null;
        accessToken = null;
    }

    //вход под существующим пользователем
    @Test
    @DisplayName("Вход под существующим пользователем")
    @Description("Проверка успешной авторизации с корректными учетными данными")
    public void loginWithExistingUserShouldSucceed() {
        LoginRequest loginRequest = new LoginRequest(
                createdUser.getEmail(),
                createdUser.getPassword()
        );

        userClient.login(loginRequest)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(createdUser.getEmail().toLowerCase()))
                .body("user.name", equalTo(createdUser.getName()))
                .body("accessToken", startsWith(ApiConstants.BEARER_PREFIX))
                .body("refreshToken", notNullValue());
    }

    //вход с неверным логином
    @Test
    @DisplayName("Вход с неверным email")
    @Description("Проверка авторизации с некорректным email")
    public void loginWithWrongEmailShouldFail() {
        String wrongEmail = "wrong_" + System.currentTimeMillis() + "@example.com";
        LoginRequest loginRequest = new LoginRequest(
                wrongEmail,
                createdUser.getPassword()
        );

        userClient.login(loginRequest)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(ApiConstants.ErrorMessages.INVALID_CREDENTIALS));
    }

    //вход с неверным паролем
    @Test
    @DisplayName("Вход с неверным паролем")
    @Description("Проверка авторизации с некорректным паролем")
    public void loginWithWrongPasswordShouldFail() {
        LoginRequest loginRequest = new LoginRequest(
                createdUser.getEmail(),
                "wrong_password_123"
        );

        userClient.login(loginRequest)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(ApiConstants.ErrorMessages.INVALID_CREDENTIALS));
    }

    //дополнительные тесты с пустыми полями
    @Test
    @DisplayName("Вход с пустым email")
    @Description("Проверка авторизации без указания email")
    public void loginWithEmptyEmailShouldFail() {
        LoginRequest loginRequest = new LoginRequest(
                "",
                createdUser.getPassword()
        );

        userClient.login(loginRequest)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(ApiConstants.ErrorMessages.INVALID_CREDENTIALS));
    }

    @Test
    @DisplayName("Вход с пустым паролем")
    @Description("Проверка авторизации без указания пароля")
    public void loginWithEmptyPasswordShouldFail() {
        LoginRequest loginRequest = new LoginRequest(
                createdUser.getEmail(),
                ""
        );

        userClient.login(loginRequest)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(ApiConstants.ErrorMessages.INVALID_CREDENTIALS));

    }
}