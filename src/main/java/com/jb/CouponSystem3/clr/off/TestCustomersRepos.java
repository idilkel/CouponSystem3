package com.jb.CouponSystem3.clr.off;

import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.repos.CouponRepository;
import com.jb.CouponSystem3.repos.CustomerRepository;
import com.jb.CouponSystem3.utils.ArtUtils;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class TestCustomersRepos implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ArtUtils.customersTitle2);
        System.out.println("---------Test if customer exists by email and password-------");
        System.out.println("By customer 2 details: should be true");
        System.out.println(customerRepository.existsByEmailAndPassword("moshe@gmail.com", "moshe1234"));
        System.out.println("By customer 2 details: wrong email, should be false");
        System.out.println(customerRepository.existsByEmailAndPassword("wrong@gmail.com", "moshe1234"));
        System.out.println("By customer 2 details: wrong password, should be false");
        System.out.println(customerRepository.existsByEmailAndPassword("moshe@gmail.com", "Wrong"));
        System.out.println("-------Get Id of customer 2 by email-------");
        System.out.println(customerRepository.getIdByEmail("moshe@gmail.com"));

        System.out.println("---Add customer already tested-----");
        System.out.println("------------Test update customer#4 and get one customer by Id----------");
        System.out.println("Before update:");
        Customer customer = customerRepository.getById(4);
        TableUtils.drawOneCustomerBuffer(customer);

        System.out.println("After update:");
        customer.setFirstName("Suzika");
        customer.setLastName("Mor Natan");
        customer.setEmail("suzi@ibm.com");
        customer.setPassword("smn123");
        customerRepository.saveAndFlush(customer);
        TableUtils.drawOneCustomerBuffer(customerRepository.getById(4));

        System.out.println("----------Test delete customer #1----------");
        System.out.println("Before delete:");
        TableUtils.drawCustomersBuffer(customerRepository.findAll());
        customerRepository.deleteById(1);
        System.out.println("After delete:");
        TableUtils.drawCustomersBuffer(customerRepository.findAll());

    }
}

