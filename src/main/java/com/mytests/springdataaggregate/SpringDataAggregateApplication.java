package com.mytests.springdataaggregate;

import com.mytests.springdataaggregate.services.JpaCallService;
import com.mytests.springdataaggregate.services.JpaPersonService;
import com.mytests.springdataaggregate.services.JpaPhoneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataAggregateApplication implements CommandLineRunner {

    private final PersonRepo personRepo;
    private final JpaPersonService jpaPersonService;
    private final JpaPhoneService jpaPhoneService;
    private final JpaCallService jpaCallService;

    public SpringDataAggregateApplication(PersonRepo personRepo, JpaPersonService jpaPersonService, JpaPhoneService jpaPhoneService, JpaCallService jpaCallService) {
        this.personRepo = personRepo;
        this.jpaPersonService = jpaPersonService;
        this.jpaPhoneService = jpaPhoneService;
        this.jpaCallService = jpaCallService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDataAggregateApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        personRepo.setupDB();
        //jpaPhoneService.showAllPhones();
        //System.out.println("********************  HQL tests: ***************");
        //personRepo.testElements();
        //personRepo.testAggregates();
        //personRepo.testSubqueries();
        System.out.println("********************  JPA QL tests: ***************");
        jpaPersonService.everyQualifierTest();
        jpaPersonService.existsTest();
        //jpaPersonService.everyAnyAggregateFunctionsTest();
        //jpaPersonService.conditionsInSelectClauseTest();
        //jpaPersonService.some();
       // jpaPhoneService.keyValueEntryTest();
       // jpaPhoneService.elementIndexTest();
        //jpaPhoneService.memberOfIsEmptyTest();
        //jpaPhoneService.countTests();
        //jpaPhoneService.inverseDistrFunctionsTests();
        //jpaPhoneService.hypotheticalSetFunctionsTests();
        jpaCallService.displayAllCalls();
    }
}
