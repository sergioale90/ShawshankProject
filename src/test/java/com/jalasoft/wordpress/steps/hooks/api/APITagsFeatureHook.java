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
import api.methods.APITagsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class APITagsFeatureHook {
    private final APIController controller;
    private static final int ORDER = 100;

    public APITagsFeatureHook(APIController controller) {
        this.controller = controller;
    }

    @Before("@RetrieveATag or @UnableToGetAllTags or @UpdateATag or @DeleteATag or @CreateATagWithSameName or @DeleteWithoutForceParam")
    public void createATag() {
        Response requestResponse = APITagsMethods.createATag();
        controller.setResponse(requestResponse);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "tag was not created");
    }

    @After("@CreateATag or @RetrieveATag or @UpdateATag or @UnableToGetAllTags or @CreateATagWithSameName")
    public void deleteATagById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Response requestResponse = APITagsMethods.deleteATagById(id);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "tag with id -> " + id + " was not deleted");
    }

    @After(value = "@APITags", order = ORDER)
    public void afterTags() {
        List<Integer> objects = APITagsMethods.getAllTags().jsonPath().getList("id");
        for (Integer id : objects) {
            APITagsMethods.deleteATagById(id.toString());
        }
    }
}
