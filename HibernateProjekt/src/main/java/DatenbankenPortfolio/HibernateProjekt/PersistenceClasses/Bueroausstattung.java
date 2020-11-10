package DatenbankenPortfolio.HibernateProjekt.PersistenceClasses;

import java.sql.Date;

import javax.persistence.*;

@Entity  
@Table(name= "\"büroausstattung\"")
public class Bueroausstattung {
	@Id
	@Column(name="Inventarnummer")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="Bezeichnung")
	private String bezeichnung;
	@Column(name="Anschaffungsdatum")
	private Date anschaffungsdatum;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public Date getAnschaffungsdatum() {
		return anschaffungsdatum;
	}
	public void setAnschaffungsdatum(Date anschaffungsdatum) {
		this.anschaffungsdatum = anschaffungsdatum;
	}
	
	//Fremdschlüssel
			@ManyToOne
			@JoinColumn(name = "RaumID")
			private Raum raum;
			
			public Raum getRaum() {
				return raum;
			}
			public void setRaum(Raum raum) {
				this.raum = raum;
			}

			@ManyToOne
			@JoinColumn(name = "TypID")
			private Typ typ;
			
			public Typ getTyp() {
				return typ;
			}
			public void setTyp(Typ typ) {
				this.typ = typ;
			}
			
			@ManyToOne
			@JoinColumn(name = "HerstellerID")
			private Hersteller Hersteller;

			public Hersteller getHersteller() {
				return Hersteller;
			}
			public void setHersteller(Hersteller hersteller) {
				Hersteller = hersteller;
			}
}
