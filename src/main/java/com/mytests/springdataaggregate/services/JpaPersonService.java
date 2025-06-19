package com.mytests.springdataaggregate.services;

import com.mytests.springdataaggregate.PersonRepository;
import com.mytests.springdataaggregate.PhoneRepository;
import com.mytests.springdataaggregate.model.Person;
import com.mytests.springdataaggregate.model.Phone;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JpaPersonService {

    private final PersonRepository personRepository;
    private final PhoneRepository phoneRepository;

    public JpaPersonService(PersonRepository personRepository, PhoneRepository phoneRepository) {
        this.personRepository = personRepository;
        this.phoneRepository = phoneRepository;
    }

    public void everyQualifierTest() {
        System.out.println("============= qualifiers test: ==================");
        Phone phone = phoneRepository.findAll().iterator().next();
        System.out.println("phone: " + phone.getNumber());
        System.out.println("---- every: ----");
        for (Person person : personRepository.everyQualifierTest(phone)) {
            System.out.println(person);
        }
        System.out.println("---- all: ----");
        for (Person person : personRepository.allQualifierTest(phone)) {
            System.out.println(person);
        }
        System.out.println("---- any: ----");
        for (Person person : personRepository.anyQualifierTest(phone)) {
            System.out.println(person);
        }
        System.out.println("---- some: ----");
        for (Person person : personRepository.someQualifierTest(phone)) {
            System.out.println(person);
        }
        System.out.println("---- some in select clause: ----");
        System.out.println(personRepository.someQualifierInSelectClauseTest(phone));
    }
    public void existsTest() {
        System.out.println("============= exists test: ==================");

        System.out.println("---- exists elements(): ----");
        for (Person person : personRepository.existsElementsTest()) {
            System.out.println(person);
        }
        System.out.println("---- exists elements() in select clause: ----");
        System.out.println(personRepository.existsInSelectClauseTest());
        System.out.println("---- exists subquery: ----");
        System.out.println(personRepository.existsSubqueryTest("Petrov"));
    }

    public void everyAnyAggregateFunctionsTest() {
        System.out.println("============= every/any aggregate functions test: ==================");
        Phone phone = phoneRepository.findAll().iterator().next();
        System.out.println("phone: " + phone.getNumber());
        System.out.println("---- any() tests: ----");
        System.out.println("-- 1:");
        System.out.println(personRepository.anyFunctionTest1(phone));
        System.out.println("-- 2:");
        System.out.println(personRepository.anyFunctionTest2("Ivan"));
        System.out.println("-- 3:");
        System.out.println(personRepository.anyFunctionTest3());
        System.out.println("---- every() tests: ----");
        System.out.println("-- 1:");
        System.out.println(personRepository.everyFunctionTest1(phone));
        System.out.println("-- 2:");
        System.out.println(personRepository.everyFunctionTest2());

    }

    public void conditionsInSelectClauseTest() {
        System.out.println("============= every/any aggregate functions test : ==================");

        System.out.println("-- 1:");
        System.out.println(personRepository.conditionInSelectClause1("Ivan"));
        System.out.println("-- 2:");
        System.out.println(personRepository.conditionInSelectClause2(0));
        System.out.println("-- 3:");
        System.out.println(personRepository.conditionInSelectClause3(0));
        System.out.println("-- 4:");
        System.out.println(personRepository.conditionInSelectClause4(0));

    }

    public void some() {
        System.out.println("--------------------------------------");
        System.out.println(personRepository.customQuery1());
        System.out.println(personRepository.customQuery2());


    }



    ;
}
