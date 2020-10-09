package DatenbankenPortfolio.HibernateProjekt;

import java.sql.Date;

import javax.persistence.*;

@Entity  
@Table(name= "büroausstattung")
public class Bueroausstattung {
	@Id
	@Column(name="Inventarnummer")
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
	
}
