package DatenbankenPortfolio.HibernateProjekt.PersistenceClasses;

import javax.persistence.*;


@Entity  
@Table(name= "land")
public class Land {
	@Id
	@Column(name="LandID")
	private String id;
	private String land;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLand() {
		return land;
	}
	public void setLand(String land) {
		this.land = land;
	}
}
