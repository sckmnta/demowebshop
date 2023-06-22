package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class TestBase {

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
}
