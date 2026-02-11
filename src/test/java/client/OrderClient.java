package client;

import constants.ApiConstants;
import io.restassured.response.ValidatableResponse;
import model.OrderRequest;

import java.util.ArrayList;
import java.util.List;

public class OrderClient extends BaseClient {
    public ValidatableResponse createOrder(OrderRequest order, String token) {
        return getSpecWithAuth(token)
                .body(order)
                .when()
                .post(ApiConstants.ORDERS_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse createOrderWithoutAuth(OrderRequest order) {
        return getSpec()
                .body(order)
                .when()
                .post(ApiConstants.ORDERS_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse createOrderWithIngredients(List<String> ingredients, String token) {
        OrderRequest order = new OrderRequest(ingredients);
        return createOrder(order, token);
    }

    public ValidatableResponse getIngredients() {
        return getSpec()
                .when()
                .get(ApiConstants.INGREDIENTS_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse getUserOrders(String token) {
        return getSpecWithAuth(token)
                .when()
                .get(ApiConstants.ORDERS_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse getAllOrders() {
        return getSpec()
                .when()
                .get(ApiConstants.ORDERS_ALL_PATH)
                .then()
                .log().all();
    }

    public Integer extractOrderNumber(ValidatableResponse response) {
        return response.extract().path("order.number");
    }

    /**
     * Получить реальные ингредиенты из API
     */
    public List<String> getActualIngredientIds() {
        ValidatableResponse response = getIngredients();
        List<String> ingredients = new ArrayList<>();

        try {
            ingredients = response.extract().jsonPath().getList("data._id");
            if (ingredients == null) {
                ingredients = new ArrayList<>();
            }
        } catch (Exception e) {
            // Если не удалось получить ингредиенты, используем тестовые
            ingredients = new ArrayList<>();
        }

        return ingredients;
    }

    /**
     * Создать заказ с реальными ингредиентами из API
     */
    public ValidatableResponse createOrderWithActualIngredients(String token) {
        List<String> actualIngredients = getActualIngredientIds();

        if (actualIngredients.isEmpty() || actualIngredients.size() < 2) {
            // Если не получили ингредиенты, используем тестовые
            OrderRequest order = new OrderRequest(List.of(
                    ApiConstants.BUN_1,
                    ApiConstants.SAUCE_1
            ));
            return createOrder(order, token);
        }

        OrderRequest order = new OrderRequest(List.of(
                actualIngredients.get(0),
                actualIngredients.get(1)
        ));
        return createOrder(order, token);
    }
}