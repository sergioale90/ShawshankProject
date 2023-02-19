package framework.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import utils.LoggerManager;

public class UIMethods {
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final WebDriver driver = DriverManager.getInstance().getWebDriver();
    private static final Wait<WebDriver> wait = DriverManager.getInstance().getFluentWait();
    private static final JavascriptExecutor js = (JavascriptExecutor) driver;

    public static void moveToWebElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public static void clickWebElementJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public static void clickWebElementJs(String cssLocator) {
        js.executeScript("""
                clickWebElement(arguments[0]);
                function clickWebElement(cssLocator) {
                var element = document.querySelector(cssLocator);
                element.click();
                }""", cssLocator);
    }

    public static void clickWebElementIfPresentJs(String cssLocator) {
        js.executeScript("""
                clickWebElementIfPresent(arguments[0]);
                function clickWebElementIfPresent(cssLocator) {
                var elements = document.querySelectorAll(cssLocator);
                if (elements.length > 0) {
                elements[0].click();
                }
                }""", cssLocator);
    }

    public static boolean isWebElementPresentJs(String cssLocator) {
        return (Boolean) js.executeScript("""
                return isWebElementPresent(arguments[0]);
                function isWebElementPresent(cssLocator) {
                var elements = document.querySelectorAll(cssLocator);
                return elements.length > 0;
                }""", cssLocator);
    }

    public static boolean isWebElementPresentByXpathJs(String xpathLocator) {
        return (Boolean) js.executeScript("""
                return isWebElementPresentByXpath(arguments[0]) == null;
                function isWebElementPresentByXpath(path) {
                return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
                }""", xpathLocator);
    }

    public static boolean isWebElementVisibleJs(WebElement element) {
        return (Boolean) js.executeScript("return arguments[0].checkVisibility();", element);
    }

    public static void removeWebElementFromDOMJs(String cssLocator) {
        js.executeScript("""
                removeWebElement(arguments[0]);
                function removeWebElement(cssLocator) {
                var element = document.querySelector(cssLocator);
                element.remove();
                }""", cssLocator);
    }

    public static void hideWebElementJs(String cssLocator) {
        js.executeScript("""
                hideWebElement(arguments[0]);
                function hideWebElement(cssLocator) {
                var element = document.querySelector(cssLocator);
                element.style.display = 'none';
                }""", cssLocator);
    }
}
