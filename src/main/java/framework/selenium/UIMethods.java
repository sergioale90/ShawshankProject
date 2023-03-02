/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package framework.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import utils.LoggerManager;

public class UIMethods {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private static final WebDriver DRIVER = DriverManager.getInstance().getWebDriver();
    private static final Wait<WebDriver> WAIT = DriverManager.getInstance().getFluentWait();
    private static final JavascriptExecutor JS = (JavascriptExecutor) DRIVER;

    public static void moveToWebElement(WebElement element) {
        Actions actions = new Actions(DRIVER);
        actions.moveToElement(element).perform();
    }

    public static void clickWebElementJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DRIVER;
        js.executeScript("arguments[0].click();", element);
    }

    public static void clickWebElementJs(String cssLocator) {
        JS.executeScript("""
                clickWebElement(arguments[0]);
                function clickWebElement(cssLocator) {
                var element = document.querySelector(cssLocator);
                element.click();
                }""", cssLocator);
    }

    public static void clickWebElementIfPresentJs(String cssLocator) {
        JS.executeScript("""
                clickWebElementIfPresent(arguments[0]);
                function clickWebElementIfPresent(cssLocator) {
                var elements = document.querySelectorAll(cssLocator);
                if (elements.length > 0) {
                elements[0].click();
                }
                }""", cssLocator);
    }

    public static boolean isWebElementPresentJs(String cssLocator) {
        return (Boolean) JS.executeScript("""
                return isWebElementPresent(arguments[0]);
                function isWebElementPresent(cssLocator) {
                var elements = document.querySelectorAll(cssLocator);
                return elements.length > 0;
                }""", cssLocator);
    }

    public static boolean isWebElementNotPresentByXpathJs(String xpathLocator) {
        return (Boolean) JS.executeScript("""
                return isWebElementNotPresentByXpath(arguments[0]) == null;
                function isWebElementNotPresentByXpath(path) {
                return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
                }""", xpathLocator);
    }

    public static boolean isWebElementVisibleJs(WebElement element) {
        return (Boolean) JS.executeScript("return arguments[0].checkVisibility();", element);
    }

    public static void removeWebElementFromDOMJs(String cssLocator) {
        JS.executeScript("""
                removeWebElement(arguments[0]);
                function removeWebElement(cssLocator) {
                var element = document.querySelector(cssLocator);
                element.remove();
                }""", cssLocator);
    }

    public static void hideWebElementJs(String cssLocator) {
        JS.executeScript("""
                hideWebElement(arguments[0]);
                function hideWebElement(cssLocator) {
                var element = document.querySelector(cssLocator);
                element.style.display = 'none';
                }""", cssLocator);
    }
}
