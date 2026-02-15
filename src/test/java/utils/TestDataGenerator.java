package utils;

import constants.ApiConstants;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestDataGenerator {
    public static User generateUniqueUser() {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return new User(
                "user_" + uuid + "@yandex.ru",
                "password_" + uuid,
                "User_" + uuid
        );
    }

    public static User generateUserWithoutEmail() {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return new User(
                null,
                "password_" + uuid,
                "User_" + uuid
        );
    }

    public static User generateUserWithoutPassword() {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return new User(
                "user_" + uuid + "@yandex.ru",
                null,
                "User_" + uuid
        );
    }

    public static User generateUserWithoutName() {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return new User(
                "user_" + uuid + "@yandex.ru",
                "password_" + uuid,
                null
        );
    }

    public static User getExistingTestUser() {
        return new User(
                ApiConstants.TestData.EXISTING_USER_EMAIL,
                ApiConstants.TestData.EXISTING_USER_PASSWORD,
                ApiConstants.TestData.EXISTING_USER_NAME
        );
    }

    public static String getRandomIngredient() {
        String[] ingredients = ApiConstants.VALID_INGREDIENTS;
        if (ingredients.length == 0) {
            return ingredients[0];
        }
        int randomIndex = (int) (Math.random() * ingredients.length);
        return ingredients[randomIndex];
    }

    public static List<String> getRandomIngredients(int count) {
        List<String> ingredients = new ArrayList<>();
        String[] validIngredients = ApiConstants.VALID_INGREDIENTS;

        if (validIngredients.length == 0) {
            ingredients.add(validIngredients[0]);
            return ingredients;
        }

        int maxCount = Math.min(count, validIngredients.length);
        for (int i = 0; i < maxCount; i++) {
            ingredients.add(getRandomIngredient());
        }
        return ingredients;
    }
}