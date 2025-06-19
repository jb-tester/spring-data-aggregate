package com.mytests.springdataaggregate.services;

import com.mytests.springdataaggregate.PersonRepository;
import com.mytests.springdataaggregate.PhoneRepository;
import com.mytests.springdataaggregate.model.Call;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class JpaPhoneService {


    private final PhoneRepository phoneRepository;
    private final PersonRepository personRepository;

    public JpaPhoneService(PhoneRepository phoneRepository, PersonRepository personRepository) {
        this.phoneRepository = phoneRepository;
        this.personRepository = personRepository;
    }

    public void keyValueEntryTest() {
        System.out.println("============= key(), value(), entry() Test: ============= ");
        System.out.println("-- key() in select clause:");
        for (LocalDateTime time : phoneRepository.customQuery1("123-456-7890")) {
            System.out.println(time);
        }
        System.out.println("-- value() in select clause:");
        for (LocalDateTime time : phoneRepository.customQuery2("123-456-7890")) {
            System.out.println(time);
        }
        System.out.println("-- entry() in select clause:");
        phoneRepository.customQuery3("123-456-7890").iterator().forEachRemaining(System.out::println);

        System.out.println("-- value()  and key() in where clause:");

        phoneRepository.customQuery4(1, 1).iterator().forEachRemaining(System.out::println);


    }

    public void elementIndexTest() {
        System.out.println("============= element() and index() Test: ============= ");
        System.out.println("-- index() as key() for map:");
        phoneRepository.customQuery5().iterator().forEachRemaining(System.out::println);
        System.out.println("-- index() for list:");
        phoneRepository.customQuery8().iterator().forEachRemaining(System.out::println);
        System.out.println("-- element() in where clause:");
        phoneRepository.customQuery6(1).iterator().forEachRemaining(System.out::println);
        System.out.println("-- element() in select clause:");
        phoneRepository.customQuery7(1).iterator().forEachRemaining(System.out::println);
    }

    public void memberOfIsEmptyTest() {

        System.out.println("============= is empty and member of Test: ============= ");
        System.out.println("-- in select clause");
        Call call = phoneRepository.findByCallsNotEmpty().get(0).getCalls().get(0);
        phoneRepository.isNotEmptyInSelectClauseTest().iterator().forEachRemaining(System.out::println);
        phoneRepository.memberOfInSelectClauseTest(call).iterator().forEachRemaining(System.out::println);
    }

    public void countTests() {
        System.out.println("============= count tests: ============= ");
        System.out.println(phoneRepository.countWithCase());
        System.out.println(phoneRepository.countInHaving());
       // System.out.println(phoneRepository.arrayAggWithCase());
       // System.out.println(phoneRepository.listaggWithCase());

    }

    public void inverseDistrFunctionsTests() {
        System.out.println("============= Inverse distribution functions tests: ============= ");
        System.out.println(phoneRepository.modeTest());
        System.out.println(phoneRepository.percentileContTest());
        System.out.println(phoneRepository.percentileDiscTest());


    }
public void hypotheticalSetFunctionsTests() {
        System.out.println("============= Hypothetical set functions tests: ============= ");
        System.out.println(phoneRepository.percentRankTest(20));
        System.out.println(phoneRepository.rankTest(20));
        System.out.println(phoneRepository.denseRankTest(20));
        System.out.println(phoneRepository.denseRankOverTest());
        System.out.println(phoneRepository.cumeDistTest(20));



    }

    public void showAllPhones() {
        System.out.println("----------- all phones: ----------");
        phoneRepository.findAll().iterator().forEachRemaining(System.out::println);
    }

    ;
}
