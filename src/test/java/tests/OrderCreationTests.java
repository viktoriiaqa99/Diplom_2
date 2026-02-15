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

    // Константы индексов вместо magicNumbers
    private static final int FIRST_INGREDIENT_INDEX = 0;
    private static final int SECOND_INGREDIENT_INDEX = 1;
    private static final int THIRD_INGREDIENT_INDEX = 2;

    // Единое сообщение для неправильных ингредиентов
    private static final String NEGATIVE_MESSAGE = "Ingredient ids must be provided";

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
                    ApiConstants.VALID_INGREDIENTS
            ));
        }
    }

    @After
    public void tearDown() {
        createdUser = null;
        accessToken = null;
        actualIngredients = null;
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Проверка создания заказа авторизованным пользователем")
    public void createOrderWithAuthShouldSucceed() {
        if (actualIngredients.size() >= 3) {
            OrderRequest order = new OrderRequest(Arrays.asList(
                    actualIngredients.get(FIRST_INGREDIENT_INDEX),
                    actualIngredients.get(SECOND_INGREDIENT_INDEX),
                    actualIngredients.get(THIRD_INGREDIENT_INDEX)
            ));

            orderClient.createOrder(order, accessToken)
                    // Исправлено: код ответа единый OK
                    .assertThat()
                    .statusCode(ApiConstants.StatusCodes.OK)
                    .body("success", equalTo(true));
        }
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка создания заказа неавторизованным пользователем")
    public void createOrderWithoutAuthShouldSucceed() {
        if (!actualIngredients.isEmpty()) {
            OrderRequest order = new OrderRequest(
                    Collections.singletonList(actualIngredients.get(FIRST_INGREDIENT_INDEX))
            );

            orderClient.createOrderWithoutAuth(order)
                    // Исправлено: код ответа единый OK
                    .assertThat()
                    .statusCode(ApiConstants.StatusCodes.OK)
                    .body("success", equalTo(true));
        }
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    @Description("Проверка создания заказа с разными комбинациями ингредиентов")
    public void createOrderWithIngredientsShouldSucceed() {
        if (!actualIngredients.isEmpty()) {
            OrderRequest singleIngredientOrder = new OrderRequest(
                    Collections.singletonList(actualIngredients.get(FIRST_INGREDIENT_INDEX))
            );

            orderClient.createOrder(singleIngredientOrder, accessToken)
                    // Исправлено: код ответа единый OK
                    .assertThat()
                    .statusCode(ApiConstants.StatusCodes.OK)
                    .body("success", equalTo(true));
        }
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов (пустой массив)")
    @Description("Проверка создания заказа с пустым списком ингредиентов")
    public void createOrderWithoutIngredientsEmptyArrayShouldFail() {
        OrderRequest order = new OrderRequest(Collections.emptyList());
        orderClient.createOrder(order, accessToken)
                // Исправлено: единый код ответа и единый message
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(NEGATIVE_MESSAGE));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов (null)")
    @Description("Проверка создания заказа с null вместо массива ингредиентов")
    public void createOrderWithoutIngredientsNullShouldFail() {
        OrderRequest order = new OrderRequest(null);
        orderClient.createOrder(order, accessToken)
                // Исправлено: единый код ответа и единый message
                .assertThat()
                .statusCode(ApiConstants.StatusCodes.BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(NEGATIVE_MESSAGE));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Проверка создания заказа с невалидными хешами ингредиентов")
    public void createOrderWithInvalidIngredientHashShouldFail() {
        OrderRequest order = new OrderRequest(
                Collections.singletonList(ApiConstants.INVALID)
        );

        var response = orderClient.createOrder(order, accessToken);
        // Исправлено: единый код ответа и единый message
        response.assertThat()
                .statusCode(ApiConstants.StatusCodes.BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(NEGATIVE_MESSAGE));
    }
}
