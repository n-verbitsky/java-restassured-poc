package com.socks.tests;

import com.github.javafaker.Faker;
import com.socks.api.payloads.UserPayload;
import com.socks.api.responses.UserRegistrationResponse;
import com.socks.api.services.UserApiService;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Locale;

import static com.socks.api.conditions.Conditions.bodyField;
import static com.socks.api.conditions.Conditions.statusCode;
import static org.hamcrest.Matchers.*;

public class UsersTest {

    private final UserApiService userApiService = new UserApiService();
    private final Faker faker = new Faker(new Locale("de"));

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "http://46.101.231.209/";
    }

    @Test
    public void testCanRegisterNewUser() {
        String username = faker.name().username();
        String password = RandomStringUtils.randomAlphanumeric(6);
        UserPayload user = new UserPayload()
                .username(faker.name().username())
                .email("qaverb+"+ username + password + "@gmail.com")
                .password(password);

        UserRegistrationResponse response = userApiService.registerUser(user)
                .shouldHave(statusCode(200))
                .asPojo(UserRegistrationResponse.class);

        response.getId();
    }

    @Test
    public void testCanRegisterSameUserTwice() {
        String username = faker.name().username();
        String password = RandomStringUtils.randomAlphanumeric(6);
        UserPayload user = new UserPayload()
                .username(faker.name().username())
                .email("qaverb+"+ username + password + "@gmail.com")
                .password(password);

        userApiService.registerUser(user)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("id", not(isEmptyOrNullString())));

        userApiService.registerUser(user)
                .shouldHave(statusCode(500));
    }
}