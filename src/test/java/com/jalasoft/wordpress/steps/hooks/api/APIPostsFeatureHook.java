/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.hooks.api;

import api.controller.APIController;
import api.methods.APIPostsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

public class APIPostsFeatureHook {
    private final APIController controller;

    public APIPostsFeatureHook(APIController controller) {
        this.controller = controller;
    }

    @Before("@RetrieveAPost or @UpdateAPost or @DeleteAPost or @DeleteAPostTrash")
    public void createAPost() {
        Response requestResponse = APIPostsMethods.createAPost();
        controller.setResponse(requestResponse);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post was not created");
    }

    @Before("@RetrieveADraftPost or @UpdateADraftPost or @DeleteADraftPost or @DeleteADraftPostTrash")
    public void createADraftPost() {
        Response requestResponse = APIPostsMethods.createADraftPost();
        controller.setResponse(requestResponse);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "draft post was not created");
    }

    @After(("@CreateAPost or @CreateADraftPost or @RetrieveAPost or @RetrieveADraftPost or @UpdateAPost or @UpdateADraftPost or @DeleteAPostTrash")
            + (" or @DeleteADraftPostTrash or @CreateAndUpdateAPost or @CreateAndUpdateADraftPost"))
    public void deleteAPostById() {
        String id;
        if (Status.SUCCESS.matches(controller.getResponse().getStatusCode())){
            id = controller.getResponse().jsonPath().getString("id");
        } else {
            id = controller.getIdAux();
        }
        Response requestResponse = APIPostsMethods.deleteAPostById(id);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post with id -> " + id + " was not deleted");
    }
}
