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
import rpp.jpa.StavkaRacuna;
import rpp.reps.RacunRepository;
import rpp.reps.StavkaRacunaRepository;


@RestController
@Api(tags = {"Stavka racuna CRUD operacije"})
public class StavkaRacunaRestControler {
	
	@Autowired
	private StavkaRacunaRepository stavkaRacunaRepository;
	
	@Autowired
	private RacunRepository racunRepository;
	
	
	@GetMapping("stavkaRacuna")
	@ApiOperation(value = "Vraća kolekciju svih stavki racuna iz baze podataka")
	public Collection<StavkaRacuna> getStavkeRacuna() {
		return stavkaRacunaRepository.findAll();
	}
	
	@GetMapping(value = "stavkaRacuna/{id}")
	@ApiOperation(value = "Vraća stavku racuna iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public ResponseEntity<StavkaRacuna> getStavkaRacuna(@PathVariable ("id") Integer id) {
		StavkaRacuna stavkaRacuna = stavkaRacunaRepository.getOne(id);
		return new ResponseEntity<StavkaRacuna> (stavkaRacuna, HttpStatus.OK);
	}
	
	@GetMapping(value = "stavkeZaRacuneId/{id}")
	@ApiOperation(value = "Vraća sve stavke racuna iz baze podataka vezane za racun čiji je id vrednost prosleđena kao path varijabla")
	public Collection<StavkaRacuna> stavkaPoRacunuId(@PathVariable("id") Integer id){
		Racun r = racunRepository.getOne(id);
		return stavkaRacunaRepository.findByRacun(r);
	}
	
	@GetMapping (value="stavkaRacunaCena/{cena}")
	public Collection<StavkaRacuna> getStavkaRacunaCena(@PathVariable("cena") Integer cena){
		return stavkaRacunaRepository.findByCenaLessThanOrderById(cena);
	}
	
	@DeleteMapping (value="stavkaRacuna/{id}")
	@ApiOperation(value = "Briše stavku racuna iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public ResponseEntity<StavkaRacuna> deleteStavkaRacuna (@PathVariable("id") Integer id){
		if(!stavkaRacunaRepository.existsById(id))
			return new ResponseEntity<StavkaRacuna> (HttpStatus.NO_CONTENT);
		stavkaRacunaRepository.deleteById(id);
		return new ResponseEntity<StavkaRacuna> (HttpStatus.OK);
	}
	
	// insert
	@PostMapping (value="stavkaRacuna")
	@ApiOperation(value = "Upisuje stavku racuna u bazu podataka")
	public ResponseEntity<Void> insertStavkaRacuna (@RequestBody StavkaRacuna stavkaRacuna){
		if(stavkaRacunaRepository.existsById(stavkaRacuna.getId()))
			return new ResponseEntity<Void> (HttpStatus.CONFLICT);
		stavkaRacuna.setRedniBroj(stavkaRacunaRepository.nextRBr(stavkaRacuna.getRacun().getId()));
		stavkaRacunaRepository.save(stavkaRacuna);
		return new ResponseEntity<Void> (HttpStatus.OK);
	}
	
	// update
	@PutMapping (value = "stavkaRacuna")
	@ApiOperation(value = "Modifikuje postojeću stavku racuna u bazi podataka")
	public ResponseEntity<Void> updateStavkaRacuna (@RequestBody StavkaRacuna stavkaRacuna){
		if(!stavkaRacunaRepository.existsById(stavkaRacuna.getId()))
			return new ResponseEntity<Void> (HttpStatus.NO_CONTENT);
		stavkaRacunaRepository.save(stavkaRacuna);
		return new ResponseEntity<Void> (HttpStatus.OK);
	}
	
}


