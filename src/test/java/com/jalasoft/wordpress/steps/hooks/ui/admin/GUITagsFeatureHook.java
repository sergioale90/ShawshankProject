/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.hooks.ui.admin;

import api.methods.APIPagesMethods;
import io.cucumber.java.After;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UIController;
import ui.methods.CommonMethods;
import utils.LoggerManager;

public class GUITagsFeatureHook {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private final UIController controller;

    public GUITagsFeatureHook(UIController controller) {
        this.controller = controller;
    }

    @After("@CreateTag")
    public void afterTags() {
        CommonMethods.logout();
        String title = controller.getName();
        // Response requestResponse = APIPagesMethods.deleteAPageByTitle(title);

        // Assert.assertNotNull(requestResponse, "page with title -> " + title + " was not found");
        // Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page with title -> " + title + " was not deleted");
    }


}
