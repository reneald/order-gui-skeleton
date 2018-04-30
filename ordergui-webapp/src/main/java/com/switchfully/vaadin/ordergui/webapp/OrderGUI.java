package com.switchfully.vaadin.ordergui.webapp;

import com.switchfully.vaadin.ordergui.interfaces.customers.CustomerResource;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.views.*;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class OrderGUI extends UI {

    private ItemResource itemResource;
    private CustomerResource customerResource;
    private Navigator navigator;

    @Autowired
    public OrderGUI(ItemResource itemResource, CustomerResource customerResource) {
        this.itemResource = itemResource;
        this.customerResource = customerResource;
    }

    @Override
    protected void init(VaadinRequest request) {
        setWidth("100%");

        VerticalLayout rootLayout = new VerticalLayout();
        TopMenu menu = new TopMenu();
        VerticalLayout contentLayout = new VerticalLayout();
        rootLayout.addComponents(menu, contentLayout);
        rootLayout.setMargin(true);
        navigator = new Navigator(this, contentLayout);
        navigator.addView("", new MainView());
        navigator.addView("items", new ItemsView(itemResource));
        navigator.addView("itemsnew", new ItemCreationView(itemResource));
        navigator.addView("itemsedit", new ItemCreationView(itemResource));
        navigator.addView("customers", new CustomersView(customerResource));
        setContent(rootLayout);
    }

}