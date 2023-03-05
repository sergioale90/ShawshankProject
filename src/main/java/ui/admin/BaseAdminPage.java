/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin;

import ui.BasePageObject;
import ui.admin.components.LeftSideBarMenu;
import ui.admin.components.TopBarMenu;

public abstract class BaseAdminPage extends BasePageObject {
    private TopBarMenu topBarMenu;

    public TopBarMenu getTopBarMenu() {
        return topBarMenu;
    }

    public LeftSideBarMenu getLeftSideBarMenu() {
        return leftSideBarMenu;
    }

    private LeftSideBarMenu leftSideBarMenu;

    public BaseAdminPage() {
        topBarMenu = new TopBarMenu();
        leftSideBarMenu = new LeftSideBarMenu();
    }

}
