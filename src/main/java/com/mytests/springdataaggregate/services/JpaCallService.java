package com.mytests.springdataaggregate.services;

import com.mytests.springdataaggregate.CallRepository;
import org.springframework.stereotype.Service;

/**
 * *
 * <p>Created by irina on 6/19/2025.</p>
 * *
 */
@Service
public class JpaCallService {

    private final CallRepository callRepository;

    public JpaCallService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public void displayAllCalls() {
        System.out.println("====== AllCalls =====");
        callRepository.findAll().iterator().forEachRemaining(System.out::println);
        System.out.println("===================");
    }

    ;
}
