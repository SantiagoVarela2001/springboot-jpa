package com.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;
import java.util.Optional;


public interface PersonRepository extends CrudRepository<Person, Long>{

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
