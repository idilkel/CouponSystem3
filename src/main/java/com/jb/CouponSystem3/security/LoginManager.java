package com.jb.CouponSystem3.security;

import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
@RequiredArgsConstructor
public class LoginManager {

    private final AdminService adminService;

    private final ApplicationContext ctx;


    public ClientService login(String email, String password, ClientType clientType) {
        try {
            switch (clientType.name()) {
                case "ADMINISTRATOR":
                    if (adminService.login(email, password)) {//If wrong, exception thrown
                        return (AdminServiceImpl) adminService;
                    }
                    break;
                case "COMPANY":
                    CompanyService companyService = ctx.getBean(CompanyService.class);
                    System.out.println(companyService.login(email, password));//If wrong, exception thrown; Prints true on right login
                    return (CompanyServiceImpl) companyService;
                case "CUSTOMER":
                    CustomerService customerService = ctx.getBean(CustomerService.class);
                    System.out.println(customerService.login(email, password));//If wrong, exception thrown; Prints true on right login
                    return (CustomerServiceImpl) customerService;
            }
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.toString());
        }
        return null;
    }
}
