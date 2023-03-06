/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package api.controller;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

public class APIController {
    private final List<Header> headers = new ArrayList<>();
    private Response response;
    private String idAux;

    public void addHeader(Header header) {
        headers.add(header);
    }

    public Headers getHeaders() {
        return new Headers(headers);
    }

    public Header getHeader(String headerName) {
        return new Headers(headers).get(headerName);
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setIdAux(String idAux) {
        this.idAux = idAux;
    }

    public String getIdAux() {
        return idAux;
    }
}
