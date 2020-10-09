package DatenbankenPortfolio.HibernateProjekt;

import javax.persistence.*;

@Entity  
@Table(name= "typ")
public class Typ {
	@Id
	@Column(name="TypID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="Typ")
	private String typ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
}
