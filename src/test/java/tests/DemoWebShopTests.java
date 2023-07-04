package tests;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import tests.config.AuthConfig;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;

import static io.restassured.RestAssured.given;

public class DemoWebShopTests extends TestBase {




    @Test
    void loginWithUiTest() {

        AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());

        step("Open login page", () -> {
            open("/login");
        });
        step("Fill login form and press enter", () -> {
            $("#Email").setValue(config.login());
            $("#Password").setValue(config.password()).pressEnter();
        });
        step("Verify successful authorisation", () -> {
            $(".account").shouldHave(text("qa@qa.guru"));
        });
    }

    @Test
    void loginWithApiTest() {

        AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());

        step("Get auth cookies by api and set them to browser", () -> {
            String authCookieKey = "NOPCOMMERCE.AUTH";
            String authCookieVal =
                    given()
                            .contentType("application/x-www-form-urlencoded")
                            .formParam("Email", config.login())
                            .formParam("Password", config.password())
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
            $(".account").shouldHave(text(config.login()));
        });
    }

}
