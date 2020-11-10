package DatenbankenPortfolio.HibernateProjekt;

import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.Abteilung;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.Adresse;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.Bueroausstattung;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.Hersteller;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.Land;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.Mitarbeiter;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.MitarbeiterEquipment;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.Raum;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.Standort;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.Status;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.Typ;

class ExampleQueryManager {
	private Session session;
	private EntityManagerFactory emf;
	private EntityManager entityManager;
	private static final String PERSISTENCE_UNIT_NAME = "my-pu";
	
	public ExampleQueryManager(Session session) {
		this.session = session;
		this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.entityManager = emf.createEntityManager();

	}
	
	@SuppressWarnings("unchecked")
	protected int getNumberofStandorteInLand(String landID){
		String queryString = "SELECT COUNT(Adresse.LandID) FROM Adresse INNER JOIN Standort ON Adresse.AdressID = Standort.AdressID WHERE Adresse.LandID = '" + landID + "'";
		Query query = entityManager.createQuery(queryString);
		List<Standort> standorte = query.getResultList();
		return standorte.size();
	}
	
}
