package com.jalasoft.wordpress.steps.ui.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import ui.admin.pages.CategoriesPage;
import ui.admin.pages.HomeAdminPage;
import ui.controller.UIController;

public class CategoriesSteps {
    private final UIController controller;
    private final HomeAdminPage homeAdminPage;
    private CategoriesPage categoriesPage;
    public CategoriesSteps(UIController controller, HomeAdminPage homeAdminPage) {
        this.controller = controller;
        this.homeAdminPage = homeAdminPage;
    }


    @Given("^the user hover over of Pages menu in the left side bar menu and click on the categories button")
    public void enterToCategoriesPage() {
        categoriesPage = homeAdminPage.getLeftSideBarMenu().goToCategoriesPage();
    }
    @Then("^the user can access to the categories page")
    public void isTheUserInTheCategoriesPage() {
        Assert.assertTrue(categoriesPage.isOnTheCategoryPage());
    }
}
