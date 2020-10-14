package DatenbankenPortfolio.HibernateProjekt;

import javax.persistence.*;

@Entity  
@Table(name= "raum")
public class Raum {
	@Id
	@Column(name="RaumID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="Anzahl Arbeitsplätze")
	private int anzahlArbeitsplaetze;
	@Column(name="Bezeichnung")
	private String bezeichnung;
	@Column(name="Stockwerk")
	private String stockwerk;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAnzahlArbeitsplaetze() {
		return anzahlArbeitsplaetze;
	}
	public void setAnzahlArbeitsplaetze(int anzahlArbeitsplaetze) {
		this.anzahlArbeitsplaetze = anzahlArbeitsplaetze;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public String getStockwerk() {
		return stockwerk;
	}
	public void setStockwerk(String stockwerk) {
		this.stockwerk = stockwerk;
	}
	
	//Fremdschlüssel
		@ManyToOne
		@JoinColumn(name = "StandortID")
		private Standort standort;
}
