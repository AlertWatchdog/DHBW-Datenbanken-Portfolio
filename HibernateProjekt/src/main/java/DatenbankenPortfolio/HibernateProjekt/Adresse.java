package DatenbankenPortfolio.HibernateProjekt;

import javax.persistence.*;

@Entity  
@Table(name= "adresse")
public class Adresse {
	@Id
	@Column(name="AdressID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="Postleitzahl")
	private int plz;
	@Column(name="Stadt")
	private String stadt;
	@Column(name="Straße")
	private String straße;
	@Column(name="Hausnummer")
	private int hausnummer;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPlz() {
		return plz;
	}
	public void setPlz(int plz) {
		this.plz = plz;
	}
	public String getStadt() {
		return stadt;
	}
	public void setStadt(String stadt) {
		this.stadt = stadt;
	}
	public String getStraße() {
		return straße;
	}
	public void setStraße(String straße) {
		this.straße = straße;
	}
	public int getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}
	
	//Fremdschlüssel
			@ManyToOne
			@JoinColumn(name = "LandID")
			private Land land;

			public Land getLand() {
				return land;
			}
			public void setLand(Land land) {
				this.land = land;
			}
			
}
