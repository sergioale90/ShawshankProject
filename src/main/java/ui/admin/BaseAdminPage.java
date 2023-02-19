package ui.admin;

import ui.BasePageObject;
import ui.admin.components.LeftSideBarMenu;
import ui.admin.components.TopBarMenu;

public abstract class BaseAdminPage extends BasePageObject {
    public TopBarMenu topBarMenu;
    public LeftSideBarMenu leftSideBarMenu;

    public BaseAdminPage() {
        topBarMenu = new TopBarMenu();
        leftSideBarMenu = new LeftSideBarMenu();
    }
}
