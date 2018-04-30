package com.switchfully.vaadin.ordergui.interfaces.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomerResource {

    private RestTemplate restTemplate;

    @Autowired
    public CustomerResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Customer> getCustomers() {
        Customer[] customers = restTemplate.getForObject("http://localhost:9000/customers/", Customer[].class);
        return Arrays.asList(customers);
    }

    public void save(Customer customer) {
        restTemplate.postForObject("http://localhost:9000/customers/", customer, Customer.class);
    }

    public Customer getItemById(String id) {
        Customer customer = restTemplate.getForObject("http://localhost:9000/customers/" + id, Customer.class);
        return customer;
    }

    public void update(Customer customer) {
        restTemplate.put("http://localhost:9000/customers/" + customer.getId(), customer);
    }
}
