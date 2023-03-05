package com.jalasoft.wordpress.steps.ui.admin;

import api.methods.APICategoriesMethods;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import ui.admin.pages.CategoriesPage;
import ui.admin.pages.EditCategoryPage;
import ui.admin.pages.HomeAdminPage;
import ui.controller.UIController;

public class CategoriesSteps {
    private final UIController controller;
    private final HomeAdminPage homeAdminPage;
    private CategoriesPage categoriesPage;
    private EditCategoryPage editCategoryPage;
    public CategoriesSteps(UIController controller, HomeAdminPage homeAdminPage) {
        this.controller = controller;
        this.homeAdminPage = homeAdminPage;
    }


    @Given("^the user hover over of Posts menu in the left side bar menu and click on the categories button$")
    public void enterToCategoriesPage() {
        categoriesPage = homeAdminPage.getLeftSideBarMenu().goToCategoriesPage();
    }
    @Given("^the user can create a new category only with a category name$")
    public void createANewCategoryOnlyWithName() {
        String name = categoriesPage.fillCategoryNameField();
        controller.setName(name);
        categoriesPage.clickAddNewCategoryButton();

        String categoryId = APICategoriesMethods.getTheIdByName(name);
        controller.setId(categoryId);
    }
    @Given("^the user can create a new category with all params$")
    public void createANewCategoryWithAllParams() {
        String categoryName = categoriesPage.fillCategoryNameField();
        String categorySlug = categoriesPage.fillCategorySlugField();
        String categoryDescription = categoriesPage.fillCategoryDescriptionField();

        controller.setName(categoryName);
        controller.setSlug(categorySlug);
        controller.setDescription(categoryDescription);

        categoriesPage.clickAddNewCategoryButton();

        String categoryId = APICategoriesMethods.getTheIdByName(categoryName);
        controller.setId(categoryId);
    }
    @Given("^the user can create a new category without name$")
    public void createANewCategoryWithoutName() {
        String name = "";
        categoriesPage.blankCategoryNameField(name);
        categoriesPage.clickAddNewCategoryButton();
    }
    @Given("^the user hover over of one category created previously and enter to edit category page using the edit label$")
    public void enterToEditCategoryPage() {
        String categoryId = controller.getId();
        categoriesPage.hoverOneCategoryCreated(categoryId);
        editCategoryPage = categoriesPage.clickOnEditLabel(categoryId);
    }
    @Given("^the user hover over of one category created previously edit category page using the quick label")
    public void enterToQuickEditOption() {
        String categoryId = controller.getId();
        categoriesPage.hoverOneCategoryCreated(categoryId);
        categoriesPage.clickQuickEditButton(categoryId);
    }
    @Given ("^the user edit the fields of the category and update the category information$")
    public void editACategory() {
        String categoryName = editCategoryPage.editNameTextBox();
        String categorySlug = editCategoryPage.editSlugTextBox();
        String categoryDescription = editCategoryPage.editDescriptionTextBox();

        controller.setName(categoryName);
        controller.setSlug(categorySlug);
        controller.setDescription(categoryDescription);

        editCategoryPage.clickOnUpdateButton();
    }
    @Given("the user return to the categories page$")
    public void returnCategoriesPage() {
        categoriesPage = editCategoryPage.clickGoToCategoriesPageButton();
    }
    @Given("^the user erase the information of the slug field and update the category$")
    public void tryToUpdateACategoryWithoutName() {
        editCategoryPage.eraseTheCategorySlug();
        editCategoryPage.clickOnUpdateButton();
    }
    @Given("the user edit the name and slug information of the category$")
    public void quickEditCategory() {
        String categoryId = controller.getId();
        categoriesPage.quickEditCategoryName(categoryId);
        categoriesPage.quickEditCategoryDescription(categoryId);
        categoriesPage.clickUpdateCategoryButton();
    }
    @Then("^the user can access to the categories page$")
    public void isTheUserInTheCategoriesPage() {
        Assert.assertTrue(categoriesPage.isOnTheCategoryPage(), "The user is not logged property");
    }
    @Then("^the user hover over of Posts menu in the left side bar menu can't see the categories button$")
    public void categoriesButtonIsNotAvailable() {
        Assert.assertFalse(homeAdminPage.getLeftSideBarMenu().categoriesButtonIsNotAvailable(), "The user shouldn't see categories button");
    }
    @Then("^the user doesn't have the option of Post Menu in the left side bar$")
    public void postMenuIsNotAvailable() {
        Assert.assertFalse(homeAdminPage.getLeftSideBarMenu().postMenuIsNotAvailable(), "The user shouldn't see post menu");
    }
    @Then("^the page displays a successful \"(.*?)\"$")
    public void verifyIfCategoryIsCreated(String message) {
        Assert.assertTrue(categoriesPage.isSuccessCreatedMessageDisplayed());
        Assert.assertTrue(categoriesPage.createdMessageDisplayed().contains(message), "Message displayed is not successful");

    }
    @Then("^the category was created successfully with a name and the slug value is set by default$")
    public void verifyIfSlugIsEqualToName() {
        String expectedName = controller.getName();
        String actualName = categoriesPage.categoryNameCreated();
        String expectedSlug = controller.getName().toLowerCase();
        String actualSlug = categoriesPage.categorySlugCreated().toLowerCase();

        Assert.assertEquals(actualName, expectedName, "The name of the category is not set correctly");
        Assert.assertEquals(actualSlug, expectedSlug, "The slug of the category is not set correctly");
    }
    @Then("^the page is created with all values correctly$")
    public void verifyIfCategoryWasCreatedProperlyWithAllValues() {
        String expectedName = controller.getName();
        String actualName = categoriesPage.categoryNameCreated();
        String expectedSlug = controller.getSlug().toLowerCase();
        String actualSlug = categoriesPage.categorySlugCreated().toLowerCase();
        String expectedDescription = controller.getDescription();
        String actualDescription = categoriesPage.categoryDescriptionCreated();

        Assert.assertEquals(actualName, expectedName, "The name of the category is not set correctly");
        Assert.assertEquals(actualSlug, expectedSlug.toLowerCase(), "The slug of the category is not set correctly");
        Assert.assertEquals(actualDescription, expectedDescription, "The description of the category is not set correctly");
    }
    @Then("^the page displays a error \"(.*?)\"")
    public void verifyErrorMessage(String errorMessage) {
        String actualErrorMessage = categoriesPage.errorMessageDisplayed();

        Assert.assertTrue(categoriesPage.isErrorCreatedMessageDisplayed());
        Assert.assertEquals(actualErrorMessage, errorMessage);
    }
    @Then("^the user redirects to the Edit Category Page$")
    public void verifyIfUserIsOnEditCategoryPage() {
        Assert.assertTrue(editCategoryPage.isOnEditCategoryPage());
    }
    @Then("^a message confirm that the category was updated with the new information$")
    public void verifyIfTheUpdateMessageIsDisplayed() {
        Assert.assertTrue(editCategoryPage.isCategoryUpdatedMessageDisplayed());
    }
    @Then("^the information of the category was updated correctly$")
    public void verifyIfTheCategoryWasUpdated() {
        String id = controller.getId();
        String expectedCategoryName = controller.getName();
        String expectedCategorySlug = controller.getSlug().toLowerCase();
        String expectedCategoryDescription = controller.getDescription();

        String actualCategoryName = categoriesPage.categoryNameUpdated(id);
        String actualCategorySlug = categoriesPage.categorySlugUpdated(id).toLowerCase();
        String actualCategoryDescription = categoriesPage.categoryDescriptionUpdated(id);

        Assert.assertEquals(actualCategoryName, expectedCategoryName, "The category name was not updated");
        Assert.assertEquals(actualCategorySlug, expectedCategorySlug, "The category slug was not updated");
        Assert.assertEquals(actualCategoryDescription, expectedCategoryDescription, "The category description was not updated");
    }
    @Then("^the page displays a error and the category is not update$")
    public void verifyIfTheCategoryWasNotUpdated() {
        Assert.assertFalse(editCategoryPage.isCategoryUpdatedMessageDisplayed(), "The Slug category can't be in blank");
    }
    @Then("^the page displays the form to edit the category$")
    public void verifyIfQuickEditFormIsDisplayed() {
        String categoryId = controller.getId();
        Assert.assertTrue(categoriesPage.quickEditFormIsVisible(categoryId));
    }
}
