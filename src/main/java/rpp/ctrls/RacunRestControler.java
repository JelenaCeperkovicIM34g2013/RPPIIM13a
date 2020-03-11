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
import rpp.jpa.Racun;
import rpp.reps.RacunRepository;

@RestController
@Api(tags = {"Racun CRUD operacije"})
public class RacunRestControler {
	
	@Autowired
	private RacunRepository racunRepository;
	
	@GetMapping("racun")
	@ApiOperation(value = "Vraća kolekciju svih racuna iz baze podataka")
	public Collection<Racun> getRacuni(){
		return racunRepository.findAll();
	}
	
	@GetMapping("racun/{id}")
	@ApiOperation(value = "Vraća racun iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public Racun getRacun(@PathVariable ("id") Integer id) {
		return racunRepository.getOne(id);
	}
	
	@DeleteMapping("racun/{id}")
	@ApiOperation(value = "Briše racun iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public ResponseEntity <Racun> deleteRacun (@PathVariable ("id") Integer id){
		if(!racunRepository.existsById(id))
			return new ResponseEntity<> (HttpStatus.NO_CONTENT);
		racunRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//insert
	@PostMapping("racun")
	@ApiOperation(value = "Upisuje racun u bazu podataka")
	public ResponseEntity<Racun> insertRacun(@RequestBody Racun racun){
		if(!racunRepository.existsById(racun.getId())) {
			return new ResponseEntity<> (HttpStatus.CONFLICT);
		}
		racunRepository.save(racun);
		return new ResponseEntity<> (HttpStatus.OK);
	}
	
	@PutMapping("racun")
	@ApiOperation(value = "Modifikuje postojeći racun u bazi podataka")
	public ResponseEntity<Racun> updateRacun (@RequestBody Racun racun){
		if(!racunRepository.existsById(racun.getId())) {
			racunRepository.save(racun);
			return new ResponseEntity<> (HttpStatus.OK);
		}
			
		
		return new ResponseEntity<> (HttpStatus.NO_CONTENT);
	}

}