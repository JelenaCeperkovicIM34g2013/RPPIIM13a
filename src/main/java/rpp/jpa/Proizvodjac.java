package rpp.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the proizvodjac database table.
 * 
 */
@Entity
@NamedQuery(name="Proizvodjac.findAll", query="SELECT p FROM Proizvodjac p")
@JsonIgnoreProperties({"hibernateLazyInitalizer", "handler"})
public class Proizvodjac implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROIZVODJAC_ID_GENERATOR", sequenceName="PROIZVODJAC_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROIZVODJAC_ID_GENERATOR")
	private Integer id;

	private String adresa;

	private String kontakt;

	private String naziv;

	//bi-directional many-to-one association to Proizvod
	@OneToMany(mappedBy="proizvodjac")
	@JsonIgnore //fasterxml jackson
	private List<Proizvod> proizvods;

	public Proizvodjac() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getKontakt() {
		return this.kontakt;
	}

	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Proizvod> getProizvods() {
		return this.proizvods;
	}

	public void setProizvods(List<Proizvod> proizvods) {
		this.proizvods = proizvods;
	}

	public Proizvod addProizvod(Proizvod proizvod) {
		getProizvods().add(proizvod);
		proizvod.setProizvodjac(this);

		return proizvod;
	}

	public Proizvod removeProizvod(Proizvod proizvod) {
		getProizvods().remove(proizvod);
		proizvod.setProizvodjac(null);

		return proizvod;
	}

}
