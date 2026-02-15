package constants;

import static org.apache.http.HttpStatus.*;

public class ApiConstants {
    public static final String BASE_URI = "https://stellarburgers.education-services.ru";
    public static final String API_PREFIX = "/api";

    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String AUTH_PATH = "/auth";
    public static final String REGISTER_PATH = API_PREFIX + AUTH_PATH + "/register";
    public static final String LOGIN_PATH = API_PREFIX + AUTH_PATH + "/login";
    public static final String LOGOUT_PATH = API_PREFIX + AUTH_PATH + "/logout";
    public static final String USER_PATH = API_PREFIX + AUTH_PATH + "/user";
    public static final String TOKEN_PATH = API_PREFIX + AUTH_PATH + "/token";

    public static final String PASSWORD_RESET_PATH = API_PREFIX + "/password-reset";
    public static final String PASSWORD_RESET_RESET_PATH = PASSWORD_RESET_PATH + "/reset";

    public static final String ORDERS_PATH = API_PREFIX + "/orders";
    public static final String ORDERS_ALL_PATH = ORDERS_PATH + "/all";
    public static final String INGREDIENTS_PATH = API_PREFIX + "/ingredients";

    // Ингредиенты
    // Булки
    public static final String BUN_FLUORESCENT = "643d69a5c3f7b9001cfa093c";
    public static final String BUN_CRATER = "643d69a5c3f7b9001cfa093d";

    // Соусы
    public static final String SAUCE_SPICY = "643d69a5c3f7b9001cfa0943";
    public static final String SAUCE_GALACTIC = "643d69a5c3f7b9001cfa0944";
    public static final String SAUCE_TRADITIONAL_GALACTIC = "61c0c5a71d1f82001bdaaa74";
    public static final String SAUCE_ANTARIAN_SPIKES = "61c0c5a71d1f82001bdaaa75";

    // Начинки
    public static final String FILLING_PROTOSOMIA = "643d69a5c3f7b9001cfa093e";
    public static final String FILLING_METEORITE = "643d69a5c3f7b9001cfa0941";
    public static final String FILLING_MARS = "643d69a5c3f7b9001cfa0945";
    public static final String FILLING_LUMINESCENT_FISH = "643d69a5c3f7b9001cfa0946";
    public static final String FILLING_CRISPY_MINERAL_RINGS = "61c0c5a71d1f82001bdaaa76";
    public static final String FILLING_FALLENIAN_TREE_FRUITS = "61c0c5a71d1f82001bdaaa77";
    public static final String FILLING_MARTIAN_ALPHA_SUGARS = "61c0c5a71d1f82001bdaaa78";
    public static final String FILLING_EXO_PLANTAGO_SALAD = "61c0c5a71d1f82001bdaaa79";
    public static final String FILLING_ASTEROID_MOLD_CHEESE = "61c0c5a71d1f82001bdaaa7a";


    public static final String INVALID = "invalid643d69a5c3f7b9001cfa0946";
    // Все валидные ингредиенты
    public static final String[] VALID_INGREDIENTS = {
            BUN_FLUORESCENT,
            BUN_CRATER,
            SAUCE_SPICY,
            SAUCE_GALACTIC,
            SAUCE_TRADITIONAL_GALACTIC,
            SAUCE_ANTARIAN_SPIKES,
            FILLING_PROTOSOMIA,
            FILLING_METEORITE,
            FILLING_MARS,
            FILLING_LUMINESCENT_FISH,
            FILLING_CRISPY_MINERAL_RINGS,
            FILLING_FALLENIAN_TREE_FRUITS,
            FILLING_MARTIAN_ALPHA_SUGARS,
            FILLING_EXO_PLANTAGO_SALAD,
            FILLING_ASTEROID_MOLD_CHEESE
    };

    // вложенные классы
    public static final class ErrorMessages {
        public static final String USER_ALREADY_EXISTS = "User already exists";
        public static final String REQUIRED_FIELDS = "Email, password and name are required fields";
        public static final String INVALID_CREDENTIALS = "email or password are incorrect";
        public static final String INGREDIENTS_REQUIRED = "Ingredient ids must be provided";
        public static final String INVALID_INGREDIENTS = "One or more ids provided are incorrect";
        public static final String UNAUTHORIZED = "You should be authorised";
        public static final String RESET_EMAIL_SENT = "Reset email sent";
        public static final String PASSWORD_RESET_SUCCESS = "Password successfully reset";
        public static final String LOGOUT_SUCCESS = "Successful logout";
    }

    // Исправлено: вместо числовых кодов ответов - коды из библиотеки static org.apache.http.HttpStatus
    public static final class StatusCodes {
        public static final int OK = SC_OK;
        public static final int BAD_REQUEST = SC_BAD_REQUEST;
        public static final int UNAUTHORIZED = SC_UNAUTHORIZED;
        public static final int FORBIDDEN = SC_FORBIDDEN;
        public static final int INTERNAL_SERVER_ERROR = SC_INTERNAL_SERVER_ERROR;
        public static final int ACCEPTED = SC_ACCEPTED;
    }

    public static final class OrderStatus {
        public static final String DONE = "done";
        public static final String PENDING = "pending";
        public static final String CREATED = "created";
        public static final String CANCELLED = "cancelled";
    }

    public static final class TestData {
        public static final String EXISTING_USER_EMAIL = "test-data@yandex.ru";
        public static final String EXISTING_USER_PASSWORD = "password";
        public static final String EXISTING_USER_NAME = "Username";
    }
}