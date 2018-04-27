package com.switchfully.vaadin.ordergui.webapp.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class MainView extends CustomComponent implements View {

    private VerticalLayout mainLayout = new VerticalLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        TopMenu topMenu = new TopMenu();
        mainLayout.setMargin(true);
        mainLayout.addComponent(topMenu);
        setCompositionRoot(mainLayout);
    }
}
