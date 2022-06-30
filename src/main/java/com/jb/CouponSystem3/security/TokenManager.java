package com.jb.CouponSystem3.security;

import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.SecMsg;
import com.jb.CouponSystem3.repos.CompanyRepository;
import com.jb.CouponSystem3.repos.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenManager {
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final Map<UUID, Information> map;

    public UUID add(String email, String password, ClientType type) throws CouponSecurityException {

        int userId = 0;

        if (type == ClientType.COMPANY) {
            if (!companyRepository.existsByEmailAndPassword(email, password)) {
                throw new CouponSecurityException(SecMsg.EMAIL_OR_PASSWORD_INCORRECT);
            }
            userId = companyRepository.getIdByEmail(email);
        } else if (type == ClientType.CUSTOMER) {
            if (!customerRepository.existsByEmailAndPassword(email, password)) {
                throw new CouponSecurityException(SecMsg.EMAIL_OR_PASSWORD_INCORRECT);
            }
            userId = customerRepository.getIdByEmail(email);
        }

        removePreviousInstances(userId);

        Information information = new Information();
        information.setId(userId);
        information.setType(type);
        information.setEmail(email);
        information.setTime(LocalDateTime.now());

        UUID token = UUID.randomUUID();
        map.put(token, information);
        // TODO: 30/06/2022 Delete this print
        //for test
        System.out.println(map);
        System.out.println("----------------------");
        return token;
    }

    public void removePreviousInstances(int userId) {
        map.entrySet().removeIf(ins -> ins.getValue().getId() == userId);
    }

    public int getUserId(UUID token) throws CouponSecurityException {
        Information information = map.get(token);
        if (information == null) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return information.getId();
    }

//    //the same id can be to a company or a customer
//    public ClientType getType(UUID token) throws CouponSecurityException {
//        Information information = map.get(token);
//        if (information == null) {
//            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
//        }
//        return information.getType();
//    }

    @Scheduled(fixedRate = 1000 * 60)
    public void deleteExpiredTokenOver30Minutes() {
        map.entrySet().removeIf(ins -> ins.getValue().getTime().isBefore(LocalDateTime.now().minusMinutes(30)));
    }
}

