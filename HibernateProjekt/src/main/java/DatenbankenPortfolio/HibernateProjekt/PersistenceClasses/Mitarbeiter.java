package DatenbankenPortfolio.HibernateProjekt.PersistenceClasses;

import java.sql.Date;

import javax.persistence.*;

@Entity  
@Table(name= "mitarbeiter")
public class Mitarbeiter {
	@Id
	@Column(name="MitarbeiterID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="Nachname")
	private String nachname;
	@Column(name="Vorname")
	private String vorname;
	@Column(name="Geschlecht")
	private char geschlecht;
	@Column(name="Geburtsdatum")
	private Date geburtsdatum;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public char getGeschlecht() {
		return geschlecht;
	}
	public void setGeschlecht(char geschlecht) {
		this.geschlecht = geschlecht;
	}
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}
	
	//Fremdschlüssel
	@ManyToOne
	@JoinColumn(name = "AbteilungsID")
	private Abteilung abteilung;
	
	public Abteilung getAbteilung() {
		return abteilung;
	}
	public void setAbteilung(Abteilung abteilung) {
		this.abteilung = abteilung;
	}

	@ManyToOne
	@JoinColumn(name = "StandortID")
	private Standort standort;
	
	public Standort getStandort() {
		return standort;
	}
	public void setStandort(Standort standort) {
		this.standort = standort;
	}
	
	@ManyToOne
	@JoinColumn(name = "AdressID")
	private Adresse adresse;
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
}
