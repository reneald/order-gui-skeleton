package com.switchfully.vaadin.ordergui.webapp.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class MainView extends VerticalLayout implements View {



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Button menuItemsButton = new Button("Items");
        menuItemsButton.addClickListener(event1 -> getUI().getNavigator().navigateTo("items"));
        HorizontalLayout menuLayout = new HorizontalLayout(menuItemsButton);
        addComponent(menuLayout);
    }
}
