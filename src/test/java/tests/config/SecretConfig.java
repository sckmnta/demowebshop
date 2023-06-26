package tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "file:/secret.properties",
        "classpath:secret.properties"
})

public interface SecretConfig extends Config {
    @Key("login")
    String login();

    @Key("password")
    String password();

}
