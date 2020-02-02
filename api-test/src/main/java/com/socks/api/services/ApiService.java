package com.socks.api.services;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApiService {

    protected RequestSpecification setup(){
        return RestAssured
                .given().contentType(ContentType.JSON)
                .filters(getFilters());
    }

    private List<Filter> getFilters() {
        Boolean enable = Boolean.getBoolean(System.getProperty("loging", "true"));
        if(enable) {return Arrays.asList(new ResponseLoggingFilter(), new RequestLoggingFilter());}
        return Collections.emptyList();
    }


}
