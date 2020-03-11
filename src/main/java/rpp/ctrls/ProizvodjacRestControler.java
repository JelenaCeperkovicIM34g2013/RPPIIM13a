package rpp.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rpp.jpa.Proizvodjac;
import rpp.reps.ProizvodjacRepository;

@RestController
@Api(tags = {"Proizvodjac CRUD operacije"})
public class ProizvodjacRestControler {
	
	@Autowired
	private ProizvodjacRepository proizvodjacRepository;

	@GetMapping("proizvodjac")
	@ApiOperation(value = "Vraća kolekciju svih proizvodjaca iz baze podataka")
	public Collection<Proizvodjac> getProizvodjaci() {
		return proizvodjacRepository.findAll();
	}
	
	@GetMapping("proizvodjac/{id}")
	@ApiOperation(value = "Vraća proizvodjaca iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public Proizvodjac getProizvodjac(@PathVariable("id") Integer id) {
		return proizvodjacRepository.getOne(id);
	}
	
	@GetMapping("proizvodjacNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju proizvodjaca iz baze podataka koji u nazivu sadrže string prosleđen kao path varijabla")
	public Collection<Proizvodjac> getProizvodjacByNaziv(@PathVariable("naziv") String naziv){
		return proizvodjacRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@DeleteMapping("proizvodjac/{id}")
	@ApiOperation(value = "Briše proizvodjaca iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public ResponseEntity<Proizvodjac> deleteProizvodjac(@PathVariable ("id") Integer id){
		if(!proizvodjacRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		proizvodjacRepository.deleteById(id);
		return new ResponseEntity<> (HttpStatus.OK);
	}
	
	// insert
	@PostMapping("proizvodjac")
	@ApiOperation(value = "Upisuje proizvodjaca u bazu podataka")
	public ResponseEntity<Proizvodjac> insertProizvodjac(@RequestBody Proizvodjac proizvodjac){
		if(proizvodjacRepository.existsById(proizvodjac.getId())) {
			return new ResponseEntity<> (HttpStatus.CONFLICT);
		}
		proizvodjacRepository.save(proizvodjac);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// update
	@PutMapping("proizvodjac")
	@ApiOperation(value = "Modifikuje postojećeg proizvodjaca u bazi podataka")
	public ResponseEntity<Proizvodjac> updateProizvodjac(@RequestBody Proizvodjac proizvodjac){
		if(proizvodjacRepository.existsById(proizvodjac.getId())) {
			proizvodjacRepository.save(proizvodjac);
			return new ResponseEntity<> (HttpStatus.OK);
		}
		return new ResponseEntity<> (HttpStatus.NO_CONTENT);
	}
	
	
}
