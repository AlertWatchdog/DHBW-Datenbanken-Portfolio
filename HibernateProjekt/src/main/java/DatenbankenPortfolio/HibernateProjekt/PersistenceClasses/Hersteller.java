package DatenbankenPortfolio.HibernateProjekt.PersistenceClasses;

import javax.persistence.*;

@Entity  
@Table(name= "hersteller")
public class Hersteller {
	@Id
	@Column(name="HerstellerID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="Hersteller")
	private String hersteller;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHersteller() {
		return hersteller;
	}
	public void setHersteller(String hersteller) {
		this.hersteller = hersteller;
	}
	
	
}
