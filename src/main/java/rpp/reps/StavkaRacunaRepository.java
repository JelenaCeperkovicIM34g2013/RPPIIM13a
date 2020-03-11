package rpp.reps;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rpp.jpa.Racun;
import rpp.jpa.StavkaRacuna;

public interface StavkaRacunaRepository extends JpaRepository<StavkaRacuna, Integer> {
	
	Collection<StavkaRacuna> findByRacun(Racun p);
	Collection<StavkaRacuna> findByCenaLessThanOrderById(Integer cena);

	@Query(value = "select coalesce(max(redni_broj)+1, 1) from stavka_racuna where racun = ?1", nativeQuery = true)
	Integer nextRBr (int idRacuna);

}
