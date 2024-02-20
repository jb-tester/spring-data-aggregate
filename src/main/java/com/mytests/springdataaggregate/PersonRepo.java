package com.mytests.springdataaggregate;

import com.mytests.springdataaggregate.model.*;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Repository
public class PersonRepo {

    private final EntityManager entityManager;

    public PersonRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void setupDB() {

        Person person1 = new Person("Ivan");
        person1.setSurName("Petrov");
        person1.setMain_address("Street 1, City 1, Country 1");
        person1.setBirthday(LocalDateTime.of(1999, 1, 1, 1, 1, 1));
        person1.getAddresses().put(AddressType.HOME, "Home address 11");
        person1.getAddresses().put(AddressType.OFFICE, "Office address 11");
        person1.getAddresses().put(AddressType.PRIVATE, "Private address 11");
        person1.setNations(new String[]{"country 1", "country 2"});
        entityManager.persist(person1);

        Person person2 = new Person("Maria");
        person2.setSurName("Petrova");
        person2.setMain_address("Street 2, City 2, Country 2");
        person2.setBirthday(LocalDateTime.of(2000, 2, 2, 2, 2, 2));
        person2.getAddresses().put(AddressType.HOME, "Home address 21");
        person2.getAddresses().put(AddressType.OFFICE, "Office address 21");
        person2.getAddresses().put(AddressType.PRIVATE, "Private address 21");
        person1.setNations(new String[]{"country 1", "country 2", "country 3"});
        entityManager.persist(person2);

        Person person3 = new Person("Maria");
        person3.setSurName("Ivanova");
        person3.setMain_address("Street 3, City 3, Country 3");
        person3.setBirthday(LocalDateTime.of(1995, 3, 3, 3, 3, 3));
        person3.getAddresses().put(AddressType.HOME, "Home address 31");
        person3.getAddresses().put(AddressType.OFFICE, "Office address 31");
        person3.getAddresses().put(AddressType.PRIVATE, "Private address 31");
        person1.setNations(new String[]{"country 1"});
        entityManager.persist(person3);

        Phone phone1 = new Phone("123-456-7890");
        phone1.setId(1L);
        phone1.setType(PhoneType.MOBILE);
        phone1.setPrice(25);
        person1.addPhone(phone1);
        phone1.getRepairTimestamps().add(LocalDateTime.of(2005, 1, 1, 12, 0, 0));
        phone1.getRepairTimestamps().add(LocalDateTime.of(2006, 1, 1, 12, 0, 0));

        Call call11 = new Call();
        call11.setDuration(12);
        call11.setTime(LocalDateTime.of(2000, 1, 1, 0, 0, 0));

        Call call12 = new Call();
        call12.setDuration(33);
        call12.setTime(LocalDateTime.of(2000, 1, 1, 1, 0, 0));

        phone1.addCall(call11);
        phone1.addCall(call12);

        Phone phone2 = new Phone("420-600-1234");
        phone2.setId(2L);
        phone2.setType(PhoneType.HOME);
        phone2.setPrice(20);

        Call call21 = new Call();
        call21.setDuration(3);
        call21.setTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        phone2.addCall(call21);

        Phone phone3 = new Phone("420-600-1233");
        phone3.setId(3L);
        phone3.setType(PhoneType.HOME);
        phone3.setPrice(10);

        Phone phone4 = new Phone("123-123-1234");
        phone4.setId(4L);
        phone4.setType(PhoneType.MOBILE);
        phone4.setPrice(15);

        Phone phone5 = new Phone("123-111-0000");
        phone5.setId(5L);
        phone5.setType(PhoneType.WORK);
        phone5.setPrice(30);

        person2.addPhone(phone2);
        person3.addPhone(phone3);
        person3.addPhone(phone4);
        person3.addPhone(phone5);

        for (Person person : entityManager.createQuery("select p from Person p", Person.class).getResultList()) {
            System.out.println(person);
        }
    }

    @Transactional
    public void testElements() {
        System.out.println("============= testElements ===================");
        Call call1 = entityManager.createQuery("select c from Call c", Call.class).getResultList().get(0);
        Phone phone1 = call1.getPhone();
        Call call2 = entityManager.createQuery("select c from Call c", Call.class).getResultList().get(2);
        Phone phone2 = call2.getPhone();

        System.out.println("============= all elements():");
        for (Person person : entityManager.createQuery("select p from Person p where :arg1 = all elements(p.phones)", Person.class).setParameter("arg1", phone2).getResultList()) {
            System.out.println(person);
        }
        System.out.println("============= every elements():");
        // `every` qualifier is not parsed
        for (Person person : entityManager.createQuery("select p from Person p where :arg1 = every elements(p.phones)", Person.class).setParameter("arg1", phone2).getResultList()) {
            System.out.println(person);
        }
        System.out.println("============= any elements():");
        for (Person person : entityManager.createQuery("select p from Person p where :arg1 = any elements(p.phones)", Person.class).setParameter("arg1", phone2).getResultList()) {
            System.out.println(person);
        }
        System.out.println("============= some elements():");
        for (Person person : entityManager.createQuery("select p from Person p where :arg1 = some elements(p.phones)", Person.class).setParameter("arg1", phone1).getResultList()) {
            System.out.println(person);
        }
        System.out.println("============= some elements() in select clause (inside count filter):");
        System.out.println(entityManager.createQuery("select count(p) filter (where :arg1 = some elements(p.phones)) from Person p ", Integer.class).setParameter("arg1", phone1).getSingleResult());

        System.out.println("============= condition in select clause:");
        // not supported
        for (Boolean condition : entityManager.createQuery("select :arg1 = p.name from Person p ", Boolean.class).setParameter("arg1", "").getResultList()) {
            System.out.println(condition);
        }
        System.out.println("============= min(elements()): ");
        for (Person person : entityManager.createQuery("select p from Person p where  :arg2 = min(elements(p.phones))", Person.class).setParameter("arg2", phone2).getResultList()) {
            System.out.println(person);
        }
        System.out.println("============= max(elements()) in select clause: ");
        for (Long phone : entityManager.createQuery("select max(elements(p.phones)) from Person p ", Long.class).getResultList()) {
            System.out.println(phone);
        }
        System.out.println("============= member of: ");
        for (Person person : entityManager.createQuery("select p from Person p where :arg1 not member of p.phones", Person.class).setParameter("arg1", phone1).getResultList()) {
            System.out.println(person);
        }

        System.out.println("============= in elements(), in indices(): ");
        // different font for indices() and elements():
        for (Person person : entityManager.createQuery("select p from Person p where :arg1 in elements(p.phones) and 1 in indices(p.phones)", Person.class).setParameter("arg1", phone1).getResultList()) {
            System.out.println(person);
        }
        System.out.println("============= exists elements():");
        for (Person person : entityManager.createQuery("select p from Person p where exists elements(p.phones)", Person.class).getResultList()) {
            System.out.println(person);
        }
        System.out.println("============= exists elements() in select clause:");
        // not supported in select clause
        for (boolean condition : entityManager.createQuery("select exists elements(p.phones) from Person p", Boolean.class).getResultList()) {
            System.out.println(condition);
        }
    }

    public void testAggregates() {
        System.out.println("============== any(), every() aggregate functions: =====================================");
        // any(), every() are not supported:
        //  these are aggregate functions which accept a logical predicate as an argument
        Call call1 = entityManager.createQuery("select c from Call c", Call.class).getResultList().get(0);
        Phone phone1 = call1.getPhone();
        System.out.println(entityManager.createQuery("select any(:arg member of p.phones) from Person p ", Boolean.class).setParameter("arg", phone1).getResultList());
        System.out.println(entityManager.createQuery("select any(p.name = :arg) from Person p ", Boolean.class).setParameter("arg", "Ivan").getResultList());
        System.out.println(entityManager.createQuery("select any(year(p.birthday) = year(local date)) from Person p ", Boolean.class).getResultList());
        System.out.println(entityManager.createQuery("select every(:arg member of p.phones) from Person p ", Boolean.class).setParameter("arg", phone1).getResultList());
        System.out.println(entityManager.createQuery("select every(p.name in ('Ivan','Maria')) from Person p ", Boolean.class).getResultList());
    }

    public void testSubqueries() {
        System.out.println("====== field in subquery ====");
        // aliases w/o 'as' are not parsed - known issue
        for (String s : entityManager.createQuery(
                "select sub.lastname from (select e.surName lastname, e.name firstname from Person e) sub" +
                " where sub.firstname in ('Ivan','Maria')",
                String.class
        ).getResultList()) {
            System.out.println(s);
        }
        System.out.println("====== tuples in subquery ====");
        for (String s : entityManager.createQuery(
                "select cast(res.firstname as string) from (select person.surName as lastname, person.name as firstname from Person person) res" +
                " where (res.firstname, res.lastname) in (select p.name, p.surName from Person p)",
                String.class
        ).getResultList()) {
            System.out.println(s);
        }
    }


}
