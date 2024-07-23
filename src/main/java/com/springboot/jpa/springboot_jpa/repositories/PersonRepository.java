package com.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;
import java.util.Optional;


public interface PersonRepository extends CrudRepository<Person, Long>{

    
    @Query("select p from Person p where p.id not in ?1") // lista todo MENOS lo del parametro
    List<Person> getPersonsNotByIds(List<Long> ids);

    @Query("select p from Person p where p.id in ?1") // lista todo lo del parametro
    List<Person> getPersonsByIds(List<Long> ids);

    @Query("select p.name, length(p.name) from Person p where length(p.name)=(select min(length(p.name)) from Person p)") //subconsultas
    List<Object[]> getShorterName();

    @Query("select p from Person p where p.id=(select max(p.id) from Person p)") //subconsultas
    Optional<Person> getLastRegistration();

    @Query("select min(p.id), max(p.id), sum(p.id), avg(length(p.name)), count(p.id) from Person p")
    public Object getResumeAggregationFunction();

    @Query("select max(length(p.name)) from Person p")
    public Integer getMaxLengthName();

    @Query("select min(length(p.name)) from Person p")
    public Integer getMinLengthName();

    @Query("select p.name, length(p.name) from Person p")
    List<Object[]> getPersonNameLength();

    @Query("select count(p) from Person p") // devuelve cuantas personas hay
    Long totalPerson();

    @Query("select min(p.id) from Person p") //devuelve el que tiene el id mas chico
    Long minId();

    @Query("select max(p.id) from Person p") //devuelve el que tiene el id mas alto
    Long MAXId();

    List<Person> findAllByOrderByNameDescLastnameAsc();  //es lo mismo que el getAllOrder()

    @Query("select p from Person p order by p.name desc, p.lastname asc")
    List<Person> getAllOrder();

    List<Person> findByIdBetween(Long id1, Long id2);

    List<Person> findByNameBetween(String name1, String name2);

    List<Person> findByIdBetweenOrderByIdAsc(Long id1, Long id2);

    //@Query("select p from Person p where p.name between 'J' and 'P'") // desde la j hasta la p, sin incluir la p
    //List<Person> findAllBetweenName();

    @Query("select p from Person p where p.name between ?1 and ?2 order by p.name asc") //ordena de forma ascendente
    List<Person> findAllBetweenNameOrder(String c1, String c2);


    @Query("select p from Person p where p.id between 2 and 5 order by p.name desc, p.lastname asc") //ordena de forma descendente el nombre y ascendente el apellido
    List<Person> findAllBetweenIdOrder();

    @Query("select p.id, upper(p.name), lower(p.lastname), p.programmingLanguage from Person p")
    List<Object[]> findAllPersonDataListCase();

    @Query("select upper(p.name || ' ' || p.lastname) from Person p")   // otra forma de concatenar
    List<String> findAllFullNameConcatUpper();

    @Query("select lower(concat(p.name, ' ', p.lastname)) from Person p")   // otra forma de concatenar
    List<String> findAllFullNameConcatLower();

    //@Query("select concat(p.name, ' ', p.lastname) from Person p")
    @Query("select p.name || ' ' || p.lastname from Person p")   // otra forma de concatenar
    List<String> findAllFullNameConcat();

    @Query("select p.name from Person p")
    List<String> findAllNames();

    @Query("select distinct(p.name) from Person p") //Devuelve todos los nombres pero no los repite
    List<String> findAllNamesDistinct();

    @Query("select count(distinct(p.programmingLanguage)) from Person p") //Devuelve todos los lenguajes pero no los repite
    Long findAllprogrammingLanguageDistinctCount();

    @Query("select distinct(p.programmingLanguage) from Person p") //Devuelve la cantidad de lenguajes
    List<String> findAllprogrammingLanguageDistinct();

    @Query("select new com.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();

    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllObjectPersonPersonalized();

    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);

    @Query("select p.id from Person p where p.id=?1")
    String getIdById(Long id);

    @Query("select concat(p.name, ' ', p.lastname) as fullname from Person p where p.id=?1")
    String getFullNameById(Long id);

    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name); //que contenga esas letras

    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixPerson();

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonDataList();

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id=?1")
    Object obtenerPersonDataById(Long id);

    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
    List<Object[]> obtenerPersonData(String name);

    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);
    
} 
