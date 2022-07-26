package com.jb.CouponSystem3.security;

import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Lazy
@RequiredArgsConstructor
public class LoginManager {

    private final AdminService adminService;

    //    private final ApplicationContext ctx;
    private final TokenManager tokenManager;
    private final CompanyService companyService;
    private final CustomerService customerService;


    public UUID loginUUID(String email, String password, ClientType clientType) throws CouponSecurityException {

        switch (clientType) {
            case ADMINISTRATOR:
                if (adminService.login(email, password)) {//If wrong, exception thrown
                    UUID token = tokenManager.add(email, password, clientType);
                    ClientType type = tokenManager.getType(token);
                    return token;
                }
                break;
            case COMPANY:
                if (companyService.login(email, password)) {//If wrong, exception thrown
                    UUID token = tokenManager.add(email, password, clientType);
                    return token;
                }
            case CUSTOMER:
                if (customerService.login(email, password)) {//If wrong, exception thrown;
                    UUID token = tokenManager.add(email, password, clientType);
                    return token;
                }
        }
        return null;
    }

    //This is required for testing the Services without a HTTP Client
    public ClientService login(String email, String password, ClientType clientType) {
        try {
            switch (clientType.name()) {
                case "ADMINISTRATOR":
                    if (adminService.login(email, password)) {//If wrong, exception thrown
                        return (AdminServiceImpl) adminService;
                    }
                    break;
                case "COMPANY"://
                    System.out.println(companyService.login(email, password));//If wrong, exception thrown; Prints true on right login
                    return (CompanyServiceImpl) companyService;
                case "CUSTOMER"://
                    System.out.println(customerService.login(email, password));//If wrong, exception thrown; Prints true on right login
                    return (CustomerServiceImpl) customerService;
            }
        } catch (CouponSecurityException e) {
            //e.printStackTrace();
            System.out.println(e.toString());
        }
        return null;
    }
}
