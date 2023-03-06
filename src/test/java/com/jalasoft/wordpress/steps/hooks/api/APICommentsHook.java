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
import api.methods.APICommentsMethods;
import api.methods.APIPostsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

public class APICommentsHook {
    private final APIController controller;

    public APICommentsHook(APIController controller) {
        this.controller = controller;
    }

    @Before("@RetrieveAComment or @UpdateAComment or @DeleteAComment or @SimplyDeleteAComment or @GetAllCommentsOfAPost")
    public void publishAComment() {
        Response postResponse = APIPostsMethods.createAPost();
        controller.setIdAux(postResponse.jsonPath().getString("id"));
        Assert.assertTrue(Status.SUCCESS.matches(postResponse.getStatusCode()), "post was not created");

        Response requestResponse = APICommentsMethods.publishAComment(controller.getIdAux());
        controller.setResponse(requestResponse);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "comment was not published");
    }

    @After("@PublishAComment or @RetrieveAComment or @UpdateAComment or @GetAllCommentsOfAPost or @SimplyDeleteAComment")
    public void deleteACommentById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Response requestResponse = APICommentsMethods.deleteACommentById(id);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()),
                "comment with id -> " + id + " was not deleted");

        String idPost = controller.getIdAux();
        Response postResponse = APIPostsMethods.deleteAPostById(idPost);
        Assert.assertTrue(Status.SUCCESS.matches(postResponse.getStatusCode()),
                "post with id -> " + idPost + " was not deleted");
    }

}
