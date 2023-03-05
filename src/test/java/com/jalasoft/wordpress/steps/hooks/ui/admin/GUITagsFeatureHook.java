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

    @After(value = "@Tags", order = ORDER)
    public void afterTags() {
        CommonMethods.logout();
        List<Integer> objects = APITagsMethods.getAllTags().jsonPath().getList("id");
        for (Integer id : objects) {
            APITagsMethods.deleteATagById(id.toString());
        }
    }
}
