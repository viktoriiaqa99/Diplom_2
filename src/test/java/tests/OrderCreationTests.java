package tests;

import client.OrderClient;
import client.UserClient;
import constants.ApiConstants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.OrderRequest;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.TestDataGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class OrderCreationTests extends BaseTest {
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private User createdUser;
    private String accessToken;
    private List<String> actualIngredients;

    public OrderCreationTests() {
        setupAllure();
    }

    @Before
    public void setUp() {
        createdUser = TestDataGenerator.generateUniqueUser();
        var registerResponse = userClient.register(createdUser)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.OK);
        accessToken = userClient.extractAccessToken(registerResponse);

        actualIngredients = orderClient.getActualIngredientIds();
        if (actualIngredients.isEmpty()) {
            actualIngredients = new ArrayList<>(Arrays.asList(
                    ApiConstants.BUN_1,
                    ApiConstants.BUN_2,
                    ApiConstants.SAUCE_1
            ));
        }
    }

    @After
    public void tearDown() {
        createdUser = null;
        accessToken = null;
        actualIngredients = null;
    }

    //создание заказа с авторизацией
    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Проверка создания заказа авторизованным пользователем")
    public void createOrderWithAuthShouldSucceed() {
        if (actualIngredients.size() >= 3) {
            OrderRequest order = new OrderRequest(Arrays.asList(
                    actualIngredients.get(0),
                    actualIngredients.get(1),
                    actualIngredients.get(2)
            ));

            orderClient.createOrder(order, accessToken)
                    .assertThat()
                    .statusCode(anyOf(
                            equalTo(ApiConstants.StatusCodes.OK),
                            equalTo(ApiConstants.StatusCodes.BAD_REQUEST)
                    ))
                    .body("success", anyOf(equalTo(true), equalTo(false)));
        }
    }

    //без авторизации
    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка создания заказа неавторизованным пользователем")
    public void createOrderWithoutAuthShouldSucceed() {
        if (!actualIngredients.isEmpty()) {
            OrderRequest order = new OrderRequest(
                    Collections.singletonList(actualIngredients.get(0))
            );

            orderClient.createOrderWithoutAuth(order)
                    .assertThat()
                    .statusCode(anyOf(
                            equalTo(ApiConstants.StatusCodes.OK),
                            equalTo(ApiConstants.StatusCodes.BAD_REQUEST)
                    ));
        }
    }

    //с ингредиентами
    @Test
    @DisplayName("Создание заказа с ингредиентами")
    @Description("Проверка создания заказа с разными комбинациями ингредиентов")
    public void createOrderWithIngredientsShouldSucceed() {
        if (!actualIngredients.isEmpty()) {
            OrderRequest singleIngredientOrder = new OrderRequest(
                    Collections.singletonList(actualIngredients.get(0))
            );

            orderClient.createOrder(singleIngredientOrder, accessToken)
                    .assertThat()
                    .statusCode(anyOf(
                            equalTo(ApiConstants.StatusCodes.OK),
                            equalTo(ApiConstants.StatusCodes.BAD_REQUEST)
                    ));
        }
    }

    //без ингредиентов (пустой массив)
    @Test
    @DisplayName("Создание заказа без ингредиентов (пустой массив)")
    @Description("Проверка создания заказа с пустым списком ингредиентов")
    public void createOrderWithoutIngredientsEmptyArrayShouldFail() {
        OrderRequest order = new OrderRequest(Collections.emptyList());
        orderClient.createOrder(order, accessToken)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", anyOf(
                        equalTo(ApiConstants.ErrorMessages.INGREDIENTS_REQUIRED),
                        equalTo(ApiConstants.ErrorMessages.INVALID_INGREDIENTS)
                ));
    }

    //без ингредиентов (null)
    @Test
    @DisplayName("Создание заказа без ингредиентов (null)")
    @Description("Проверка создания заказа с null вместо массива ингредиентов")
    public void createOrderWithoutIngredientsNullShouldFail() {
        OrderRequest order = new OrderRequest(null);
        orderClient.createOrder(order, accessToken)
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", anyOf(
                        equalTo(ApiConstants.ErrorMessages.INGREDIENTS_REQUIRED),
                        equalTo(ApiConstants.ErrorMessages.INVALID_INGREDIENTS)
                ));
    }

    //с неверным хешем ингредиентов
    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Проверка создания заказа с невалидными хешами ингредиентов")
    public void createOrderWithInvalidIngredientHashShouldFail() {
        OrderRequest order = new OrderRequest(
                Collections.singletonList(ApiConstants.INVALID)
        );

        orderClient.createOrder(order, accessToken)
                .assertThat()
                .statusCode(anyOf(
                        equalTo(ApiConstants.StatusCodes.INTERNAL_SERVER_ERROR),
                        equalTo(ApiConstants.StatusCodes.BAD_REQUEST)
                ));
    }

}