package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class DemoWebShopTests {
    @AfterAll
    static void afterAll(){
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void openPage(){
        open("https://demowebshop.tricentis.com");
    }
}
