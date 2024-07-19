package com.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.jpa.springboot_jpa.dto.PersonDto;
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

		personalizedQueriesBetween();
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesBetween(){
		System.out.println("========================= consulta por rangos de id y ordenado =========================");
		List<Person> persons = repository.findAllBetweenIdOrder();
		persons.forEach(System.out::println);

		System.out.println("========================= consulta por rinicio de letra de nombre y ordenado =========================");
		List<Person> personsName = repository.findAllBetweenNameOrder("j","p ");
		personsName.forEach(System.out::println);

		System.out.println("========================= consulta por rangos de id y ordenado y los ordena SIN QUERY=========================");
		List<Person> persons3 = repository.findByIdBetweenOrderByIdAsc(1L, 5L);
		persons3.forEach(System.out::println);

		System.out.println("========================= consulta por rangos de id sin QUERY=========================");
		List<Person> persons2 = repository.findByIdBetween(2L, 5L);
		persons2.forEach(System.out::println);

		System.out.println("========================= consulta por rinicio de letra de nombre sin QUERY =========================");
		List<Person> personsName2 = repository.findByNameBetween("j","p ");
		personsName2.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesConcatUpperAndLowerCase(){
		System.out.println("========================= consulta nombres y apellidos de personas =========================");	
		List<String> names = repository.findAllFullNameConcat();
		names.forEach(System.out::println);
		
		System.out.println("========================= consulta nombres y apellidos de personas =========================");	
		List<String> namesL = repository.findAllFullNameConcatLower();
		namesL.forEach(System.out::println);

		System.out.println("========================= consulta nombres y apellidos de personas =========================");	
		List<String> namesU = repository.findAllFullNameConcatUpper();
		namesU.forEach(System.out::println);

		System.out.println("========================= consulta nombres y apellidos de personas =========================");	
		List<Object[]> namesC = repository.findAllPersonDataListCase();
		namesC.forEach(reg ->{
			System.out.println(" nombre: " + reg[1] + " apellido: " + reg[2]);
		} );
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct(){
		System.out.println("========================= consulta con nombres de personas =========================");	
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);

		System.out.println("========================= consulta con nombres de personas con distinct =========================");	
		List<String> namesDistinct = repository.findAllNamesDistinct();
		namesDistinct.forEach(System.out::println);

		System.out.println("========================= consulta con nombres de lenguajes con distinct =========================");	
		List<String> lenguagesDistinct = repository.findAllprogrammingLanguageDistinct();
		lenguagesDistinct.forEach(System.out::println);

		System.out.println("========================= consulta cantidad de lenguajes NO repetidos =========================");	
		Long lenguagesDistinctCount = repository.findAllprogrammingLanguageDistinctCount();
		System.out.println(lenguagesDistinctCount);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries2(){
		System.out.println("========================= consulta por objeto persona y lenguaje de programacion =========================");
	List<Object[]> personsRegs = repository.findAllMixPerson();

	personsRegs.forEach(reg -> {
		System.out.println("programmingLanguage: " + reg[1] + " " + reg[0]);
	});

	System.out.println("consulta que puebla y devuelve objeto entity de una instancia personalizada");
	List<Person> persons = repository.findAllObjectPersonPersonalized();

	persons.forEach(person -> {
		System.out.println(person);
	});

	System.out.println("consulta que puebla y devuelve objeto DTO");
	List<PersonDto> personsDto = repository.findAllPersonDto();
	personsDto.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("========================= consula el nombre por id =========================");
		System.out.println("INGRESE EL ID DE LA PERSONA: ");
		Long id = scanner.nextLong();
		scanner.close();

		System.out.println("========================= mostrando nombre =========================");
		String name = repository.getNameById(id);
		System.out.println("Su nombre es " + name);

		System.out.println("========================= mostrando id =========================");
		String idDB = repository.getIdById(id);
		System.out.println("Su id es " + idDB);

		System.out.println("========================= mostrando nombre completo con concat =========================");
		String fullname = repository.getFullNameById(id);
		System.out.println("Su nombre completo es " + fullname);

		System.out.println("========================= consula personalizada por id =========================");
		Object[] personReg = (Object[]) repository.obtenerPersonDataById(id);
		System.out.println("id: " + personReg[0] + " nombre: " + personReg[1] + " apellido: " + personReg[2] + " lenguaje: " + personReg[3]);

		System.out.println("========================= consula campos personalizados list =========================");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(reg ->{
			System.out.println("id: " + reg[0] + " nombre: " + reg[1] + " apellido: " + reg[2] + " lenguaje: " + reg[3]);
		} );
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
