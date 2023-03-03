/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.methods;

import ui.PageTransporter;
import ui.admin.components.TopBarMenu;
import utils.LoggerManager;

public class CommonMethods {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private static final PageTransporter PAGE_TRANSPORTER = PageTransporter.getInstance();
    private static TopBarMenu topBarMenu;

    public static void logout() {
        if (PAGE_TRANSPORTER.isOnAdminPage()) {
            topBarMenu = new TopBarMenu();
            topBarMenu.logout();
        }
    }
}
