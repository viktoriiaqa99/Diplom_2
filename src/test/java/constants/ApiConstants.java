package constants;

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
    public static final String BUN_1 = "643d69a5c3f7b9001cfa093c";
    public static final String BUN_2 = "643d69a5c3f7b9001cfa093d";
    public static final String SAUCE_1 = "643d69a5c3f7b9001cfa0943";
    public static final String SAUCE_2 = "643d69a5c3f7b9001cfa0944";
    public static final String MAIN_1 = "643d69a5c3f7b9001cfa093e";
    public static final String MAIN_2 = "643d69a5c3f7b9001cfa0941";
    public static final String MAIN_3 = "643d69a5c3f7b9001cfa0945";
    public static final String MAIN_4 = "643d69a5c3f7b9001cfa0946";

    public static final String INVALID = "invalid_hash_12345";
    public static final String[] VALID_INGREDIENTS = {BUN_1, BUN_2, SAUCE_1, SAUCE_2, MAIN_1, MAIN_2, MAIN_3, MAIN_4};

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

    public static final class StatusCodes {
        public static final int OK = 200;
        public static final int BAD_REQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int ACCEPTED = 202;
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