package ui.methods;

import ui.PageTransporter;
import ui.admin.components.TopBarMenu;
import utils.LoggerManager;

public class CommonMethods {
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final PageTransporter pageTransporter = PageTransporter.getInstance();
    private static TopBarMenu topBarMenu;

    public static void logout() {
        if (pageTransporter.isOnAdminPage()) {
            topBarMenu = new TopBarMenu();
            topBarMenu.logout();
        }
    }
}
