package com.socks.api.conditions;

import io.restassured.response.Response;
import org.hamcrest.Matcher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BodyFieldCondition implements Condition {

    private final String jsonPath;
    private final Matcher matcher;

    @Override
    public void check(Response response) {
        response.then().assertThat().body(jsonPath, matcher);
    }

    @Override
    public String toString() {
        return "Body field [" + jsonPath + "] " +  matcher;
    }
}
