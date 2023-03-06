/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.hooks.ui.admin;

import api.methods.APITagsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UIController;
import ui.methods.CommonMethods;
import utils.LoggerManager;

import java.util.List;

public class GUITagsFeatureHook {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private final UIController controller;
    private static final int ORDER = 100;

    public GUITagsFeatureHook(UIController controller) {
        this.controller = controller;
    }

    @Before("@UpdateTag or @OpenTag or @DeleteTagFromEditPage or @DeleteTag or @FindValidTag or @FindNotTag or @CreateTagSameName or @CreateTagSameNameAndSlug")
    public void createTag() {
        LOG.info("Creating a tag");
        Response requestResponse = APITagsMethods.createATag();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "tag was not created");
        String id = requestResponse.jsonPath().getString("id");
        String name = requestResponse.jsonPath().getString("name");
        String slug = requestResponse.jsonPath().getString("slug");
        String description = requestResponse.jsonPath().getString("description");
        controller.setId(id);
        controller.setName(name);
        controller.setSlug(slug);
        controller.setDescription(description);
    }

    @After(value = "@Tags", order = ORDER)
    public void afterTags() {
        CommonMethods.logout();
        List<Integer> objects = APITagsMethods.getAllTags().jsonPath().getList("id");
        for (Integer id : objects) {
            APITagsMethods.deleteATagById(id.toString());
        }
    }
}
