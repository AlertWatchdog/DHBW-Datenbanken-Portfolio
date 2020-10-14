package DatenbankenPortfolio.HibernateProjekt;
import java.sql.Date;

import javax.persistence.*;

@Entity  
@Table(name= "mitarbeiter-equipment")
public class MitarbeiterEquipment {
	@Id
	@Column(name="Seriennummer") 
	@GeneratedValue(strategy = GenerationType.AUTO)
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
		@JoinColumn(name = "MitarbeiterID")
		private Mitarbeiter mitarbeiter;
		
		@ManyToOne
		@JoinColumn(name = "TypID")
		private Typ typ;
		
		@ManyToOne
		@JoinColumn(name = "HerstellerID")
		private Hersteller Hersteller;
}
