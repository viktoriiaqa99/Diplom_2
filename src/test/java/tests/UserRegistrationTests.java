package tests;

import client.UserClient;
import constants.ApiConstants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.Test;
import utils.TestDataGenerator;
import org.junit.Before;

import static org.hamcrest.Matchers.*;

public class UserRegistrationTests extends BaseTest {
    private final UserClient userClient = new UserClient();

    @Before
    public void setup() {
        setupAllure();
    }

    // Создать уникального пользователя
    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка успешной регистрации нового пользователя с уникальными данными")
    public void createUniqueUserSuccessfully() {
        User uniqueUser = TestDataGenerator.generateUniqueUser();
        var response = userClient.register(uniqueUser)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(uniqueUser.getEmail().toLowerCase()))
                .body("user.name", equalTo(uniqueUser.getName()))
                .body("accessToken", startsWith(ApiConstants.BEARER_PREFIX))
                .body("refreshToken", notNullValue());

        // Удаляем созданного пользователя
        String accessToken = userClient.extractAccessToken(response);
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    // Создать пользователя, который уже зарегистрирован
    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Проверка, что нельзя создать пользователя с уже существующим email")
    public void createExistingUserShouldFail() {
        User existingUser = TestDataGenerator.generateUniqueUser();
        var firstResponse = userClient.register(existingUser)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.OK);

        userClient.register(existingUser)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(ApiConstants.ErrorMessages.USER_ALREADY_EXISTS));

        // Удаляем созданного пользователя
        String firstAccessToken = userClient.extractAccessToken(firstResponse);
        if (firstAccessToken != null) {
            userClient.delete(firstAccessToken);
        }
    }

    // Не заполнить поле Email
    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Проверка обязательности поля email при регистрации")
    public void createUserWithoutEmailShouldFail() {
        User userWithoutEmail = TestDataGenerator.generateUserWithoutEmail();
        userClient.register(userWithoutEmail)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(ApiConstants.ErrorMessages.REQUIRED_FIELDS));
    }

    // Не заполнить поле Password
    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проверка обязательности поля password при регистрации")
    public void createUserWithoutPasswordShouldFail() {
        User userWithoutPassword = TestDataGenerator.generateUserWithoutPassword();
        userClient.register(userWithoutPassword)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(ApiConstants.ErrorMessages.REQUIRED_FIELDS));
    }

    // Не заполнить поле Name
    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Проверка обязательности поля name при регистрации")
    public void createUserWithoutNameShouldFail() {
        User userWithoutName = TestDataGenerator.generateUserWithoutName();
        userClient.register(userWithoutName)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(ApiConstants.ErrorMessages.REQUIRED_FIELDS));
    }
}
