package com.mytests.springdataaggregate;

import com.mytests.springdataaggregate.model.Person;
import com.mytests.springdataaggregate.model.Phone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface PersonRepository extends CrudRepository<Person, Integer> {



   // https://youtrack.jetbrains.com/issue/IDEA-345367/JPA-QL-HQL-every-qualifier-is-not-parsed
    @Query("select p from Person p where :arg = every elements(p.phones)")
    List<Person> everyQualifierTest(@Param("arg") Phone arg);
    @Query("select p from Person p where :arg = all elements(p.phones)")
    List<Person> allQualifierTest(@Param("arg") Phone arg);
    @Query("select p from Person p where :arg = some elements(p.phones)")
    List<Person> someQualifierTest(@Param("arg") Phone arg);
    @Query("select p from Person p where :arg = any elements(p.phones)")
    List<Person> anyQualifierTest(@Param("arg") Phone arg);
    @Query("select count(p) filter (where :arg = some elements(p.phones)) from Person p")
    int someQualifierInSelectClauseTest(@Param("arg") Phone arg);

    // https://youtrack.jetbrains.com/issue/IDEA-345387/JPA-QL-HQL-exists-operator-is-not-parsed-in-select-clause
    @Query("select p from Person p where exists elements(p.phones)")
    List<Person> existsElementsTest();
    @Query("select exists elements(p.phones) from Person p")
    List<Boolean> existsInSelectClauseTest();
    @Query("select 1 from Person e where exists (select 1 from Person e where e.surName = :arg)")
    List<Integer> existsSubqueryTest(@Param("arg") String arg);

    // https://youtrack.jetbrains.com/issue/IDEA-345394/JPA-QL-HQL-logical-predicates-as-any-every-functions-arguments-are-not-parsed
    @Query("select any(:arg member of p.phones) from Person p")
    List<Boolean> anyFunctionTest1(@Param("arg") Phone arg);
    @Query("select any(p.name = :arg) from Person p ")
    List<Boolean> anyFunctionTest2(@Param("arg") String arg);
    @Query("select any(year(p.birthday) = year(local date)) from Person p ")
    List<Boolean> anyFunctionTest3();
    @Query("select every(:arg member of p.phones) from Person p ")
    List<Boolean> everyFunctionTest1(@Param("arg") Phone arg);
    @Query("select every(p.name in ('Ivan','Maria')) from Person p ")
    List<Boolean> everyFunctionTest2();

    // comparison operation is the select clause
    // https://youtrack.jetbrains.com/issue/IDEA-345422/JPA-QL-HQL-logical-expression-in-select-clause-is-not-parsed
    @Query("select :arg = p.name from Person p")
    List<Boolean> conditionInSelectClause1(@Param("arg") String arg);
     @Query("select p.version > :arg from Person p")
    List<Boolean> conditionInSelectClause2(@Param("arg") int arg);
    @Query("select max(indices(p.phones)) = :arg from Person p")
    List<Boolean> conditionInSelectClause3(@Param("arg") int arg);
    @Query("select :arg = min(indices(p.phones)) from Person p")
    List<Boolean> conditionInSelectClause4(@Param("arg") int arg);


    @Query("select p.phones[0] from Person p where p.name not like 'M|_%' escape '|'")
    List<Phone> customQuery2();
    @Query("select p from Person p where p.phones[max(indices(p.phones))].type = 'MOBILE'")
    List<Person> customQuery1();

    // different fonts for functions elements() and indices():
    // https://youtrack.jetbrains.com/issue/IDEA-345426/JPA-QL-HQL-elements-and-indices-functions-are-shown-in-the-different-way
    @Query("select p from Person p where :arg1 in elements(p.phones) and 1 in indices(p.phones)")
    List<Person> fonts();


    @Query("select count(*) from Person e")
    List<Person> countSubquery();

@Query("select p from Person p where p.phones[max(indices(p.phones))].type = 'MOBILE' and null = some elements(p.phones)")
List<Person> sss();

 @Query("select str(mod(e.version,2)) from Person e")
 List<String> testModAndStr();


}
