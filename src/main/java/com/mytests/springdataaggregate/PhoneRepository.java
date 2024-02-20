package com.mytests.springdataaggregate;

import com.mytests.springdataaggregate.model.Call;
import com.mytests.springdataaggregate.model.Person;
import com.mytests.springdataaggregate.model.Phone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface PhoneRepository extends CrudRepository<Phone, Integer> {

    // IsEmpty / IsNotEmpty can only be used on collection properties
    //List<Phone> findByCallLogEmpty();

    List<Phone> findByCallsNotEmpty();

    // map functions: key(), value(), entry(), index()
    @Query("select key(c) from Phone p join p.callLog c where p.number = :num ")
    List<LocalDateTime> customQuery1(@Param("num") String num);

    @Query("select value(c).time from Phone p join p.callLog c where p.number = :num ")
    List<LocalDateTime> customQuery2(@Param("num") String num);

    @Query("select entry(p.callLog) from Phone p where p.number = :num ")
    List<Map.Entry<Date, Call>> customQuery3(@Param("num") String num);

    @Query("select e from Phone e where value(e.callLog).duration > :duration and day(key(e.callLog)) = :day ")
    List<Phone> customQuery4(@Param("duration") int duration, @Param("day") int day);

    @Query("select index(e.callLog) from Phone e")
    List<LocalDateTime> customQuery5();

    // list functions: element(), index()
    @Query("select e from Person e join e.phones p where index(p) = 2")
    List<Person> customQuery8();

    // element() not suggested; there is no completion for elements columns
    @Query("select phone from Phone phone where element(phone.calls).duration > :duration")
    List<Phone> customQuery6(@Param("duration") int duration);

    @Query("select element(phone.calls).time from Phone phone ")
    List<LocalDateTime> customQuery7(@Param("duration") int duration);


    // https://youtrack.jetbrains.com/issue/IDEA-345895/JPA-QL-HQL-is-empty-member-of-operators-are-not-parsed-when-appear-in-the-SELECT-clause
    @Query("select p.calls is not empty from Phone p ")
    List<Boolean> isNotEmptyInSelectClauseTest();

    @Query("select phone from Phone phone where phone.calls is not empty ")
    List<Phone> isNotEmptyTest();

    // https://youtrack.jetbrains.com/issue/IDEA-345895/JPA-QL-HQL-is-empty-member-of-operators-are-not-parsed-when-appear-in-the-SELECT-clause
    @Query("select :arg member of phone.calls from Phone phone ")
    List<Boolean> memberOfInSelectClauseTest(@Param("arg") Call arg);

    @Query("select phone from Phone phone where :arg member of phone.calls  ")
    List<Phone> memberOfTest(@Param("arg") Call arg);


    @Query("select count(e.price) from Phone e where e.type = 'MOBILE' group by e.type having count(e.price)>1")
    int countInHaving();

    /// 'case' as function argument tests: ///

    // https://youtrack.jetbrains.com/issue/IDEA-346590/JPA-QL-HQL-case-expression-inside-count-is-not-parsed
    @Query("select count(case when e.type = 'MOBILE' then 1 else 0 end) from Phone e")
    int countWithCase();

    @Query("select sum(case when e.type = 'MOBILE' then 1 else 0 end) from Phone e")
    int sumWithCase();

    @Query("select listagg(case when e.type = 'MOBILE' then e.number else ' ' end, ', ') within group (order by e.id nulls last ) from Phone e")
    List<String> listaggWithCase();

    @Query("select array_agg(case when e.type = 'MOBILE' then e.number else '' end) within group (order by e.id nulls last ) from Phone e")
    List<String> arrayAggWithCase();

    /// Hypothetical set functions tests: ///
    //https://youtrack.jetbrains.com/issue/IDEA-346668/JPA-QL-HQL-Hypothetical-set-functions-with-within-group-clause-are-not-parsed

    //  parameters are not checked: 0 parameters - OVER expected, 1 parameter - WITHIN GROUP expected
    // https://youtrack.jetbrains.com/issue/IDEA-346672/JPA-QL-HQL-check-the-Hypothetical-set-functions-syntax

    @Query("select 100 * percent_rank(:arg) within group (order by e.price) from Phone e")
    int percentRankTest(@Param("arg") int arg);

    @Query("select 100 * rank(:arg) within group (order by e.price) from Phone e")
    int rankTest(@Param("arg") int arg);

    @Query("select 100 * cume_dist(:arg) within group (order by e.price) from Phone e")
    int cumeDistTest(@Param("arg") int arg);


    @Query("SELECT 100 * DENSE_RANK(:arg) WITHIN GROUP (ORDER BY e.price) FROM Phone e")
    int denseRankTest(@Param("arg") int arg);

    @Query("SELECT DENSE_RANK() OVER (PARTITION BY e.type ORDER BY e.price DESC) from Phone e")
    List<Integer> denseRankOverTest();

    /// Inverse distribution functions tests: ///
    // https://youtrack.jetbrains.com/issue/IDEA-346671/JPA-QL-HQL-inverse-distribution-functions-with-within-group-clause-are-reported-as-errors

    // parameters and the required clause are not checked
    // https://youtrack.jetbrains.com/issue/IDEA-346673/JPA-QL-HQL-check-parameters-and-clause-for-inverse-distribution-functions
    @Query("select percentile_cont(0.5) within group (order by e.price) from Phone e")
    int percentileContTest();

    @Query("select percentile_disc(0.5) within group (order by e.price) from Phone e")
    int percentileDiscTest();


    // mode() not completed, parameters are not checked
    // https://youtrack.jetbrains.com/issue/IDEA-346674/JPA-QL-HQL-mode-function-is-not-completed

    @Query("select mode() within group (order by e.price) from Phone e")
    int modeTest();

}