package DatenbankenPortfolio.HibernateProjekt.PersistenceClasses;

import javax.persistence.*;

@Entity  
@Table(name= "abteilung")
public class Abteilung {
	@Id
	@Column(name="AbteilungsID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="Abteilung")
	private String abteilung;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAbteilung() {
		return abteilung;
	}
	public void setAbteilung(String abteilung) {
		this.abteilung = abteilung;
	}
}
