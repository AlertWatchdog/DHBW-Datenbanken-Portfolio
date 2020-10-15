package DatenbankenPortfolio.HibernateProjekt;

import javax.persistence.*;

@Entity  
@Table(name= "standort")
public class Standort {
	@Id
	@Column(name="StandortID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	//Fremdschlüssel
		@ManyToOne
		@JoinColumn(name = "AdressID")
		private Adresse adresse;

		public Adresse getAdresse() {
			return adresse;
		}
		public void setAdresse(Adresse adresse) {
			this.adresse = adresse;
		}
}
