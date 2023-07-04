package tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "file:/auth.properties",
        "classpath:auth.properties"
})

public interface AuthConfig extends Config {
    @Key("login")
    String login();

    @Key("password")
    String password();

}
