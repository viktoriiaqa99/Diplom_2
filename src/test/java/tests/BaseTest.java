package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class BaseTest {

    @BeforeClass
    public static void setupAllure() {
        RestAssured.filters(new AllureRestAssured());
    }
}