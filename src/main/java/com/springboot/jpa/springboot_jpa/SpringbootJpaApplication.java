package com.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.jpa.springboot_jpa.entities.Person;
import com.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		delete2();
	}

	@Transactional
	public void delete(){
		repository.findAll().forEach(System.out::println);

		Scanner scanner = new Scanner(System.in);
		System.out.println("INGRESE EL ID DE LA PERSONA A ELIMINAR: ");
		Long id = scanner.nextLong();
		repository.deleteById(id);

		repository.findAll().forEach(System.out::println);
		scanner.close();
	}

	@Transactional
	public void delete2(){
		repository.findAll().forEach(System.out::println);

		Scanner scanner = new Scanner(System.in);
		System.out.println("INGRESE EL ID DE LA PERSONA A ELIMINAR: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(repository::delete, () -> System.out.println("NO EXISTE"));

		repository.findAll().forEach(System.out::println);
		scanner.close();
	}

	@Transactional
	public void update(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("INGRESE EL ID DE LA PERSONA A EDITAR: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		if(optionalPerson.isPresent()){
			Person person = optionalPerson.orElseThrow();
			System.out.println(person);
			System.out.println("ingrese el lenguaje de programacion: ");
			String programmingLanguage = scanner.next();
			person.setProgrammingLanguage(programmingLanguage);
			Person persondb = repository.save(person); // al ya existir hace el updtae y no el insert
			System.out.println(persondb);
		}else{
			System.out.println("La persona no EXISTE");
		}
		scanner.close();
	}

	@Transactional // cuando modificas de alguna forma la base de datos
	public void create(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("INGRESE EL NOMBRE: ");
		String name = scanner.next();
		System.out.println("INGRESE EL APELLIDO: ");
		String lastname = scanner.next();
		System.out.println("INGRESE EL LENGUAJE: ");
		String programmingLanguage = scanner.next();
		scanner.close();
		Person person = new Person(null, name, lastname, programmingLanguage);

		Person personNew = repository.save(person);

		repository.findById(personNew.getId()).ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)  // cuando solo pedis informacion a la base de datos
	public void findOne(){
		/*Person person = null;
		Optional<Person> optionalPerson = repository.findById(1L);
		if(optionalPerson.isPresent()){
			person = optionalPerson.get();
		}
		System.out.println(person);*/

		//repository.findById(1L).ifPresent(person -> System.out.println(person));
		repository.findOne(1L).ifPresent(System.out::println); //es lo mismo que la linea anterior
		repository.findByNameContaining("se").ifPresent(System.out::println); //es lo mismo que la linea anterior
	}

	public void list(){
		//List<Person> persons = (List<Person>)repository.findAll();
		//List<Person> persons = (List<Person>)repository.buscarByProgrammingLanguage("java", "andres");
		List<Person> persons = (List<Person>)repository.findByProgrammingLanguageAndName("java", "andres");
		persons.stream().forEach(person -> System.out.println(person));
		List<Object[]> personvAalues = repository.obtenerPersonData();
		personvAalues.stream().forEach(data -> System.out.println(data[0] + " es experto en " + data[1]));
	}
}
