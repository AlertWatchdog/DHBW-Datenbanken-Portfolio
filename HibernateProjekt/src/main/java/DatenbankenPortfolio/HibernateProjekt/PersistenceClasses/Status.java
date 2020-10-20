package DatenbankenPortfolio.HibernateProjekt.PersistenceClasses;

import javax.persistence.*;

@Entity  
@Table(name= "status")
public class Status {
	@Id
	@Column(name="StatusID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="Status")
	private String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
