package tests;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Test;
import tests.config.AuthConfig;
import static org.hamcrest.Matchers.is;

import static io.restassured.RestAssured.given;

public class AddToCardDemoWebshop extends TestBase{

    @Test
    void addToCardTest(){

        AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());

        String data = "product_attribute_16_5_4=14" +
                "&product_attribute_16_6_5=17&product_attribute_16_3_6=19" +
                "&product_attribute_16_4_7=21" +
                "&product_attribute_16_8_8=22" +
                "&product_attribute_16_8_8=23" +
                "&product_attribute_16_8_8=24" +
                "&addtocart_16.EnteredQuantity=3";

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

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(authCookieKey, authCookieVal)
                .body(data)
                .when()
                .post("/addproducttocart/details/16/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
        //todo check card size


    }
}
