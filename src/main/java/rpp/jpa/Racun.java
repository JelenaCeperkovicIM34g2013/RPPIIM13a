package rpp.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the racun database table.
 * 
 */
@Entity
@NamedQuery(name="Racun.findAll", query="SELECT r FROM Racun r")
@JsonIgnoreProperties({"hibernateLazyInitalizer", "handler"})
public class Racun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RACUN_ID_GENERATOR", sequenceName="RACUN_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RACUN_ID_GENERATOR")
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date datum;

	@Column(name="nacin_placanja")
	private String nacinPlacanja;

	//bi-directional many-to-one association to StavkaRacuna
	@OneToMany(mappedBy="racun")
	@JsonIgnore // fasterxml.jackson
	private List<StavkaRacuna> stavkaRacunas;

	public Racun() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getNacinPlacanja() {
		return this.nacinPlacanja;
	}

	public void setNacinPlacanja(String nacinPlacanja) {
		this.nacinPlacanja = nacinPlacanja;
	}

	public List<StavkaRacuna> getStavkaRacunas() {
		return this.stavkaRacunas;
	}

	public void setStavkaRacunas(List<StavkaRacuna> stavkaRacunas) {
		this.stavkaRacunas = stavkaRacunas;
	}

	public StavkaRacuna addStavkaRacuna(StavkaRacuna stavkaRacuna) {
		getStavkaRacunas().add(stavkaRacuna);
		stavkaRacuna.setRacun(this);

		return stavkaRacuna;
	}

	public StavkaRacuna removeStavkaRacuna(StavkaRacuna stavkaRacuna) {
		getStavkaRacunas().remove(stavkaRacuna);
		stavkaRacuna.setRacun(null);

		return stavkaRacuna;
	}

}