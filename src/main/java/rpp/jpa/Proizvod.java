package rpp.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the proizvod database table.
 * 
 */
@Entity
@NamedQuery(name="Proizvod.findAll", query="SELECT p FROM Proizvod p")
@JsonIgnoreProperties({"hibernateLazyInitalizer", "handler"})
public class Proizvod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROIZVOD_ID_GENERATOR", sequenceName="PROIZVOD_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROIZVOD_ID_GENERATOR")
	private Integer id;

	private String naziv;

	@ManyToOne
	@JoinColumn(name="proizvodjac")
	private Proizvodjac proizvodjac;

	//bi-directional many-to-one association to StavkaRacuna
	@OneToMany(mappedBy="proizvod")
	private List<StavkaRacuna> stavkeRacuna;

	public Proizvod() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Proizvodjac getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(Proizvodjac proizvodjac) {
		this.proizvodjac = proizvodjac;
	}

	public List<StavkaRacuna> getStavkeRacuna() {
		return this.stavkeRacuna;
	}

	public void setStavkeRacuna(List<StavkaRacuna> stavkeRacuna) {
		this.stavkeRacuna = stavkeRacuna;
	}

	public StavkaRacuna addStavkaRacuna(StavkaRacuna stavkaRacuna) {
		getStavkeRacuna().add(stavkaRacuna);
		stavkaRacuna.setProizvod(this);

		return stavkaRacuna;
	}

	public StavkaRacuna removeStavkaRacuna(StavkaRacuna stavkaRacuna) {
		getStavkeRacuna().remove(stavkaRacuna);
		stavkaRacuna.setProizvod(null);

		return stavkaRacuna;
	}

}
