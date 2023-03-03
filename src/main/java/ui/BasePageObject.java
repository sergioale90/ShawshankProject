/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui;

import framework.selenium.DriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

import java.util.Objects;

public abstract class BasePageObject {
    protected WebDriver driver;
    protected Wait<WebDriver> wait;

    public BasePageObject() {
        this.driver = DriverManager.getInstance().getWebDriver();
        this.wait = DriverManager.getInstance().getFluentWait();
        PageFactory.initElements(driver, this);
    }

    public abstract void waitUntilPageObjectIsLoaded() throws WebDriverException;

    public Alert getAlert() {
        Alert alert;
        try {
            alert = driver.switchTo().alert();
        } catch (NoAlertPresentException e) {
            alert = null;
        }
        return alert;
    }

    public boolean alertIsPresent() {
        return Objects.nonNull(getAlert());
    }

    public void acceptAlert() {
        Alert alert = getAlert();
        if (Objects.nonNull(alert)) {
            alert.accept();
        }
    }

    public void dismissAlert() {
        Alert alert = getAlert();
        if (Objects.nonNull(alert)) {
            alert.dismiss();
        }
    }

    public String getAlertMessage() {
        String message = "";
        Alert alert = getAlert();
        if (Objects.nonNull(alert)) {
            message = alert.getText();
        }
        return message;
    }

    public String getAlertMessageAndAccept() {
        String message = "";
        Alert alert = getAlert();
        if (Objects.nonNull(alert)) {
            message = alert.getText();
            alert.accept();
        }
        return message;
    }

    public String getAlertMessageAndDismiss() {
        String message = "";
        Alert alert = getAlert();
        if (Objects.nonNull(alert)) {
            message = alert.getText();
            alert.dismiss();
        }
        return message;
    }
}
