package com.switchfully.vaadin.ordergui.webapp.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class TopMenu extends CssLayout {
    Button menuItemsButton = new Button("Items");
    Button menuCustomersButton = new Button("Customers");
    Label menuLabel = new Label("MENU:");


    public TopMenu() {
        menuItemsButton.addClickListener(event1 -> getUI().getNavigator().navigateTo("items"));
        menuCustomersButton.addClickListener(event1 -> getUI().getNavigator().navigateTo("customers"));
        addComponents(menuLabel, menuItemsButton, menuCustomersButton);
        setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    }
}
