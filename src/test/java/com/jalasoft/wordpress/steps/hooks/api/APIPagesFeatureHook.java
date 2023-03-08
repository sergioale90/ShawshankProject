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
import api.methods.APIPagesMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

/**
 * This class is the hook for pages scenarios from API.
 */

public class APIPagesFeatureHook {
    private final APIController controller;
    private static final int ORDER = 100;

    public APIPagesFeatureHook(APIController controller) {
        this.controller = controller;
    }

    @Before("@RetrieveAPage or @DeleteAPage or @MoveAPageToTrash or @DeleteAPage")
    public void createADrafPage() {
        Response requestResponse = APIPagesMethods.createADraftPage();
        controller.setResponse(requestResponse);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page was not created");
    }

    @Before("@UpdateAPagePublished")
    public void createAPublishPage() {
        Response requestResponse = APIPagesMethods.createAPagePublished();
        controller.setResponse(requestResponse);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page was not created");
    }

    @After("@CreateAPagePublish or @CreateAPageDraft or @RetrieveAPage or @UpdateAPagePublished or @MoveAPageToTrash")
    public void deleteAPageById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Response requestResponse = APIPagesMethods.deleteAPageById(id);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()),
                "page with id -> " + id + " was not deleted");
    }

    @After(value = "@APIPages", order = ORDER)
    public void afterPages() {
        List<Integer> objects = APIPagesMethods.getAllPages().jsonPath().getList("id");
        for (Integer id : objects) {
            APIPagesMethods.deleteAPageById(id.toString());
        }
    }
}
