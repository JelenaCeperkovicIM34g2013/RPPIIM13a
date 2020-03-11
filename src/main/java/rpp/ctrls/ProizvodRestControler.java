package rpp.ctrls;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import rpp.jpa.Proizvod;
import rpp.reps.ProizvodRepository;

@RestController
@Api(tags = {"Proizvod CRUD operacije"})
public class ProizvodRestControler {
	
	@Autowired
	private ProizvodRepository proizvodRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("proizvod")
	@ApiOperation(value = "Vraća kolekciju svih proizvoda iz baze podataka")
	public Collection<Proizvod> getProizvodi(){
		return proizvodRepository.findAll();
	}
	
	@GetMapping("proizvod/{id}")
	@ApiOperation(value = "Vrаća proizvod iz baze podataka ciji je ID vrednost prosleđena kao path varijabla")
	public Proizvod getProizvod(@PathVariable("id") Integer id) {
		return proizvodRepository.getOne(id);
	}
	
	@GetMapping("proizvodNaziv/{naziv}")
	@ApiOperation(value = "Vrаća proizvod iz baze podataka koji u naziv sadrzi string prosleđen kao path varijabla")
	public Collection<Proizvod> getProizvodByNaziv(@PathVariable("naziv") String naziv){
		return proizvodRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@Transactional
	@DeleteMapping("proizvod/{id}")
	@ApiOperation(value = "Briše proizvod iz baze podataka ciji je ID vrednost prosleđena kao path varijabla")
	public ResponseEntity<Proizvod> deleteProizvod(@PathVariable ("id") Integer id){
		if(!proizvodRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		jdbcTemplate.execute("delete from stavka_racuna where porudzbina = "+id);
		proizvodRepository.deleteById(id);
		return new ResponseEntity<> (HttpStatus.OK);
	}
	
	// insert
	@PostMapping("proizvod")
	@ApiOperation(value = "Insertuje proizvod u bazu podataka")
	public ResponseEntity<Proizvod> insertProizvod(@RequestBody Proizvod proizvod){
		if(proizvodRepository.existsById(proizvod.getId())) {
			return new ResponseEntity<> (HttpStatus.CONFLICT);
		}
		proizvodRepository.save(proizvod);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// update
	@PutMapping("artikl")
	@ApiOperation(value = "Modifikuje proizvod iz baze podataka")
	public ResponseEntity<Proizvod> updateProizvod(@RequestBody Proizvod proizvod){
		if(proizvodRepository.existsById(proizvod.getId())) {
			proizvodRepository.save(proizvod);
			return new ResponseEntity<> (HttpStatus.OK);
		}
		return new ResponseEntity<> (HttpStatus.NO_CONTENT);
	}
	

}