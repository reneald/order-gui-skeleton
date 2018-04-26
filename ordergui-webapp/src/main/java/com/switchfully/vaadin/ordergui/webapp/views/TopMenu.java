package com.switchfully.vaadin.ordergui.webapp.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TopMenu extends CssLayout {
    Button menuItemsButton = new Button("Items");
    Button menuCustomersButton = new Button("Customers");

    public TopMenu() {
        menuItemsButton.addClickListener(event1 -> getUI().getNavigator().navigateTo("items"));
        menuCustomersButton.addClickListener(event1 -> getUI().getNavigator().navigateTo("items"));
        addComponents(menuItemsButton, menuCustomersButton);
        setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    }
}
