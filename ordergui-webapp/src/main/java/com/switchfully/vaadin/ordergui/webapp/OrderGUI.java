package com.switchfully.vaadin.ordergui.webapp;

import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.views.ItemCreationView;
import com.switchfully.vaadin.ordergui.webapp.views.ItemsView;
import com.switchfully.vaadin.ordergui.webapp.views.MainView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class OrderGUI extends UI {

    private ItemResource itemResource;
    private Navigator navigator;

    @Autowired
    public OrderGUI(ItemResource itemResource) {
        this.itemResource = itemResource;
    }

    @Override
    protected void init(VaadinRequest request) {
        setWidth("100%");
        navigator = new Navigator(this, this);
        navigator.addView("", new MainView());
        navigator.addView("items", new ItemsView(itemResource));
        navigator.addView("items/new", new ItemCreationView(itemResource));
//        VerticalLayout mainLayout = new VerticalLayout();
//        addTitleLabel(mainLayout);
//        renderItems(mainLayout);
//        setContent(mainLayout);
    }

    private void renderItems(VerticalLayout mainLayout) {
        itemResource.getItems()
                .forEach(item ->
                        mainLayout.addComponent(
                                new HorizontalLayout(
                                        new Label("--> " + item.name + " €" + item.price))));
    }

    private void addTitleLabel(VerticalLayout mainLayout) {
        mainLayout.addComponent(
                new HorizontalLayout(
                        new Label("ITEMS:")
                )
        );
    }

}