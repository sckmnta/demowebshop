package tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DemoWebShopTests {

    String login = "qa@qa.guru",
            password = "qa@qa.guru1";

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demowebshop.tricentis.com";
        baseURI = "https://demowebshop.tricentis.com";
    }

    @AfterAll
    static void afterAll() {
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void loginWithUiTest() {

        step("Open login page", () -> {
            open("/login");
        });
        step("Fill login form and press enter", () -> {
            $("#Email").setValue(login);
            $("#Password").setValue(password).pressEnter();
        });
        step("Verify successful authorisation", () -> {
            $(".account").shouldHave(text(login));
        });
    }

    @Test
    void loginWithApiTest() {

        step("Get auth cookies by api and set them to browser", () -> {
            String authCookieKey = "NOPCOMMERCE.AUTH";
            String authCookieVal =
                    given()
                            .contentType("application/x-www-form-urlencoded")
                            .formParam("Email", login)
                            .formParam("Password", password)
                            .when()
                            .post("/login")
                            .then()
                            .log().all()
                            .statusCode(302)
                            .extract().cookie(authCookieKey);

            open("/Themes/DefaultClean/Content/images/logo.png");
            Cookie authCookie = new Cookie(authCookieKey, authCookieVal);
            getWebDriver().manage().addCookie(authCookie);
        });
        step("Open main page", () -> {
            open("/");
        });
        step("Verify successful authorisation", () -> {
            $(".account").shouldHave(text(login));
        });
    }
}
