/**
 * This class manages simple DB operations on Portfolio DB
 * 
 * @version 1.0
 */
package DatenbankenPortfolio.HibernateProjekt;

import java.sql.Date;
import java.util.List;

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

public class DBCRUDManager {
	private Session session;

	/**
	 * Contructor of DBCRUDManager
	 * 
	 * @param session Hibernate session referring to "Portfolio"-Database
	 */
	public DBCRUDManager(Session session) {
		this.session = session;
	}

	// Create Methods
	/**
	 * Creates a new Mitarbeiter in DB
	 * 
	 * @param geburtsdatum Column in Table
	 * @param geschlecht   Column in Table
	 * @param nachname     Column in Table
	 * @param vorname      Column in Table
	 * @param abteilungsID ID of existing Abteilung
	 * @param standortID   ID of existing Standort
	 * @param adressID     ID of existing Adresse
	 */
	protected void createMitarbeiter(Date geburtsdatum, char geschlecht, String nachname, String vorname,
			int abteilungsID, int standortID, int adressID) {
		Mitarbeiter tmp = new Mitarbeiter();

		tmp.setGeburtsdatum(geburtsdatum);
		tmp.setGeschlecht(geschlecht);
		tmp.setNachname(nachname);
		tmp.setVorname(vorname);
		tmp.setAbteilung(readAbteilung(abteilungsID));
		tmp.setStandort(readStandort(standortID));
		tmp.setAdresse(readAdresse(adressID));

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Bueroaustattung in DB
	 * 
	 * @param anschaffungsdatum Column in Table
	 * @param bezeichnung       Column in Table
	 * @param herstellerID      ID of existing Hersteller
	 * @param raumID            ID of existing Raum
	 * @param typID             ID of existing Typ
	 */
	protected void createBueroausstattung(Date anschaffungsdatum, String bezeichnung, int herstellerID, int raumID,
			int typID) {
		Bueroausstattung tmp = new Bueroausstattung();

		tmp.setAnschaffungsdatum(anschaffungsdatum);
		tmp.setBezeichnung(bezeichnung);
		tmp.setHersteller(readHersteller(herstellerID));
		tmp.setRaum(readRaum(raumID));
		tmp.setTyp(readTyp(typID));

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Standort in DB
	 * 
	 * @param adressID Column in Table
	 */
	protected void createStandort(int adressID) {
		Standort tmp = new Standort();

		tmp.setAdresse(readAdresse(adressID));

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new MitarbeiterEquipment in DB
	 * 
	 * @param anschaffungsdatum Column in Table
	 * @param bezeichnung       Column in Table
	 * @param herstellerID      ID of existing Hersteller
	 * @param mitarbeiterID     ID of existing Mitarbeiter
	 * @param typID             ID of existing Typ
	 */
	protected void createMitarbeiterEquipment(Date anschaffungsdatum, String bezeichnung, int herstellerID,
			int mitarbeiterID, int typID) {
		MitarbeiterEquipment tmp = new MitarbeiterEquipment();

		tmp.setAnschaffungsdatum(anschaffungsdatum);
		tmp.setBezeichnung(bezeichnung);
		tmp.setHersteller(readHersteller(herstellerID));
		tmp.setTyp(readTyp(typID));
		tmp.setMitarbeiter(readMitarbeiter(mitarbeiterID));

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Raum in DB
	 * 
	 * @param bezeichnung    Column in Table
	 * @param standortID     ID of existing Standort
	 * @param stockwerk      Column in Table
	 * @param arbeitsplaetze Column in Table
	 */
	protected void createRaum(String bezeichnung, int standortID, String stockwerk, int arbeitsplaetze) {
		Raum tmp = new Raum();

		tmp.setBezeichnung(bezeichnung);
		tmp.setAnzahlArbeitsplaetze(arbeitsplaetze);
		tmp.setStandort(readStandort(standortID));
		tmp.setStockwerk(stockwerk);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Adresse in DB
	 * 
	 * @param straße     Column in Table
	 * @param hausnummer Column in Table
	 * @param stadt      Column in Table
	 * @param plz        Column in Table
	 * @param landID     ID of existing Land
	 */
	protected void createAdresse(String straße, int hausnummer, String stadt, int plz, String landID) {
		Adresse tmp = new Adresse();

		tmp.setHausnummer(hausnummer);
		tmp.setLand(readLand(landID));
		tmp.setPlz(plz);
		tmp.setStadt(stadt);
		tmp.setStraße(straße);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Abteilung in DB
	 * 
	 * @param abteilung Column in Table
	 */
	protected void createAbteilung(String abteilung) {
		Abteilung tmp = new Abteilung();

		tmp.setAbteilung(abteilung);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Land in DB
	 * 
	 * @param land   Column in Table
	 * @param landID PrimaryKey
	 */
	protected void createLand(String land, String landID) {
		Land tmp = new Land();

		tmp.setLand(land);
		tmp.setId(landID);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Status in DB
	 * 
	 * @param status Column in Table
	 */
	protected void createStatus(String status) {
		Status tmp = new Status();

		tmp.setStatus(status);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Hersteller in DB
	 * 
	 * @param hersteller Column in Table
	 */
	protected void createHersteller(String hersteller) {
		Hersteller tmp = new Hersteller();

		tmp.setHersteller(hersteller);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Typ in DB
	 * 
	 * @param typ Column in Table
	 */
	protected void createTyp(String typ) {
		Typ tmp = new Typ();

		tmp.setTyp(typ);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}
	// End of Create Methods

	// read Methods start here
	/**
	 * Reads specific Mitarbeiter from DB
	 * 
	 * @param id Primary key of the Mitarbeiter to read
	 * @return Instance of Mitarbeiter
	 */
	protected Mitarbeiter readMitarbeiter(int id) {
		Transaction t = session.beginTransaction();
		Mitarbeiter tmp = session.get(Mitarbeiter.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads the Table Mitarbeiter from DB
	 * 
	 * @return List of Mitarbeiter Instances
	 */
	protected List<Mitarbeiter> readAllMitarbeiter() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Mitarbeiter> tmp = session.createQuery("from Mitarbeiter").list();
		t.commit();
		return tmp;
	}

	/**
	 * Reads specific Abteilung from DB
	 * 
	 * @param id Primary key of the Abteilung to read
	 * @return Instance of Abteilung
	 */
	protected Abteilung readAbteilung(int id) {
		Transaction t = session.beginTransaction();
		Abteilung tmp = session.get(Abteilung.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads Table Abteilung from DB
	 * 
	 * @return List of Abteilung Instances
	 */
	protected List<Abteilung> readAllAbteilung() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Abteilung> tmp = session.createQuery("from Abteilung").list();
		t.commit();
		return tmp;
	}

	/**
	 * Reads specific Adresse from DB
	 * 
	 * @param id Primary key of the Adresse to read
	 * @return Instance of Adresse
	 */
	protected Adresse readAdresse(int id) {
		Transaction t = session.beginTransaction();
		Adresse tmp = session.get(Adresse.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads the Table Adresse from DB
	 * 
	 * @return List of Adresse Instances
	 */
	protected List<Adresse> readAllAdresse() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Adresse> tmp = session.createQuery("from Adresse").list();
		t.commit();
		return tmp;
	}

	/**
	 * Reads specific Bueroausstattung from DB
	 * 
	 * @param id Primary key of the Bueroausstattung to read
	 * @return Instance of Bueroausstattung
	 */
	protected Bueroausstattung readBueroausstattung(int id) {
		Transaction t = session.beginTransaction();
		Bueroausstattung tmp = session.get(Bueroausstattung.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads table Bueroausstattung from DB
	 * 
	 * @return List of Bueroaustattung instances
	 */
	protected List<Bueroausstattung> readAllBueroausstattung() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Bueroausstattung> tmp = session.createQuery("from Bueroausstattung").list();
		t.commit();
		return tmp;
	}

	/**
	 * Reads specific Hersteller from DB
	 * 
	 * @param id Primary key of the Hersteller to read
	 * @return Instance of Hersteller
	 */
	protected Hersteller readHersteller(int id) {
		Transaction t = session.beginTransaction();
		Hersteller tmp = session.get(Hersteller.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads table Hersteller from DB
	 * 
	 * @return List of Hersteller instances
	 */
	protected List<Hersteller> readAllHersteller() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Hersteller> tmp = session.createQuery("from Hersteller").list();
		t.commit();
		return tmp;
	}

	/**
	 * Reads specific Land from DB
	 * 
	 * @param id Primary key of the Land to read
	 * @return Instance of Land
	 */
	protected Land readLand(String id) {
		Transaction t = session.beginTransaction();
		Land tmp = session.get(Land.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads table Land from DB
	 * 
	 * @return List of Land instances
	 */
	protected List<Land> readAllLand() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Land> tmp = session.createQuery("from Land").list();
		t.commit();
		return tmp;
	}

	/**
	 * Reads specific MitarbeiterEquipment from DB
	 * 
	 * @param id Primary key of the MitarbeiterEquipment to read
	 * @return Instance of MitarbeiterEquipment
	 */
	protected MitarbeiterEquipment readMitarbeiterEquipment(int id) {
		Transaction t = session.beginTransaction();
		MitarbeiterEquipment tmp = session.get(MitarbeiterEquipment.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads table MitarbeiterEquipment from DB
	 * 
	 * @return List of MitarbeiterEquipment instances
	 */
	protected List<MitarbeiterEquipment> readAllMitarbeiterEquipment() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<MitarbeiterEquipment> tmp = session.createQuery("from MitarbeiterEquipment").list();
		t.commit();
		return tmp;
	}

	/**
	 * Reads specific Raum from DB
	 * 
	 * @param id Primary key of the Raum to read
	 * @return Instance of Raum
	 */
	protected Raum readRaum(int id) {
		Transaction t = session.beginTransaction();
		Raum tmp = session.get(Raum.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads table Raum from DB
	 * 
	 * @return List of Raum instances
	 */
	protected List<Raum> readAllRaum() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Raum> tmp = session.createQuery("from Raum").list();
		t.commit();
		return tmp;
	}

	/**
	 * Reads specific Standort from DB
	 * 
	 * @param id Primary key of the Standort to read
	 * @return Instance of Standort
	 */
	protected Standort readStandort(int id) {
		Transaction t = session.beginTransaction();
		Standort tmp = session.get(Standort.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads table Standort from DB
	 * 
	 * @return List of Standort instances
	 */
	protected List<Standort> readAllStandort() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Standort> tmp = session.createQuery("from Standort").list();
		t.commit();
		return tmp;
	}

	/**
	 * Reads specific Status from DB
	 * 
	 * @param id Primary key of the Status to read
	 * @return Instance of Status
	 */
	protected Status readStatus(int id) {
		Transaction t = session.beginTransaction();
		Status tmp = session.get(Status.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads table Status from DB
	 * 
	 * @return List of Status instances
	 */
	protected List<Status> readAllStatus() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Status> tmp = session.createQuery("from Status").list();
		t.commit();
		return tmp;
	}

	/**
	 * Reads specific Typ from DB
	 * 
	 * @param id Primary key of the Typ to read
	 * @return Instance of Typ
	 */
	protected Typ readTyp(int id) {
		Transaction t = session.beginTransaction();
		Typ tmp = session.get(Typ.class, id);
		t.commit();
		return tmp;
	}

	/**
	 * Reads table Typ from DB
	 * 
	 * @return List of Typ instances
	 */
	protected List<Typ> readAllTyp() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Typ> tmp = session.createQuery("from Typ").list();
		t.commit();
		return tmp;
	}

	// End of Read Methods

	// Update Methods
	/**
	 * Updates an entity of Mitarbeiter in DB
	 * 
	 * @param id           Primary Key of Mitarbeiter to update
	 * @param geburtsdatum Column in Table
	 * @param geschlecht   Column in Table
	 * @param nachname     Column in Table
	 * @param vorname      Column in Table
	 * @param abteilungsID ID of existing Abteilung
	 * @param standortID   ID of existing Standort
	 * @param adressID     ID of existing Adresse
	 */
	protected void updateMitarbeiter(int id, Date geburtsdatum, char geschlecht, String nachname, String vorname,
			int abteilungsID, int standortID, int adressID) {
		Mitarbeiter tmp = new Mitarbeiter();

		tmp.setId(id);
		tmp.setGeburtsdatum(geburtsdatum);
		tmp.setGeschlecht(geschlecht);
		tmp.setNachname(nachname);
		tmp.setVorname(vorname);
		tmp.setAbteilung(readAbteilung(abteilungsID));
		tmp.setStandort(readStandort(standortID));
		tmp.setAdresse(readAdresse(adressID));

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Updates an entity of Buerorausstattung in DB
	 * 
	 * @param id                Primary Key of Bueroausstattung to update
	 * @param anschaffungsdatum Column in Table
	 * @param bezeichnung       Column in Table
	 * @param herstellerID      ID of existing Hersteller
	 * @param raumID            ID of existing Raum
	 * @param typID             ID of existing Typ
	 */
	protected void updateBueroausstattung(int id, Date anschaffungsdatum, String bezeichnung, int herstellerID,
			int raumID, int typID) {
		Bueroausstattung tmp = new Bueroausstattung();

		tmp.setId(id);
		tmp.setAnschaffungsdatum(anschaffungsdatum);
		tmp.setBezeichnung(bezeichnung);
		tmp.setHersteller(readHersteller(herstellerID));
		tmp.setRaum(readRaum(raumID));
		tmp.setTyp(readTyp(typID));

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Updates an entity of Standort in DB
	 * 
	 * @param id       Primary Key of Bueroausstattung to update
	 * @param adressID ID of existing Adresse
	 */
	protected void updateStandort(int id, int adressID) {
		Standort tmp = new Standort();

		tmp.setId(id);
		tmp.setAdresse(readAdresse(adressID));

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Updates an entity of MitarbeiterEquipment in DB
	 * 
	 * @param id                Primary Key of MitarbeiterEquipment to update
	 * @param anschaffungsdatum Column in Table
	 * @param bezeichnung       Column in Table
	 * @param herstellerID      ID of existing Hersteller
	 * @param mitarbeiterID     ID of existing Mitarbeiter
	 * @param typID             ID of existing Typ
	 */
	protected void updateMitarbeiterEquipment(int id, Date anschaffungsdatum, String bezeichnung, int herstellerID,
			int mitarbeiterID, int typID) {
		MitarbeiterEquipment tmp = new MitarbeiterEquipment();

		tmp.setId(id);
		tmp.setAnschaffungsdatum(anschaffungsdatum);
		tmp.setBezeichnung(bezeichnung);
		tmp.setHersteller(readHersteller(herstellerID));
		tmp.setTyp(readTyp(typID));
		tmp.setMitarbeiter(readMitarbeiter(mitarbeiterID));

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Updates an entity of Raum in DB
	 * 
	 * @param id             Primary Key of Raum to update
	 * @param bezeichnung    Column in Table
	 * @param standortID     ID of existing Standort
	 * @param stockwerk      Column in Table
	 * @param arbeitsplaetze Column in Table
	 */
	protected void updateRaum(int id, String bezeichnung, int standortID, String stockwerk, int arbeitsplaetze) {
		Raum tmp = new Raum();

		tmp.setId(id);
		tmp.setBezeichnung(bezeichnung);
		tmp.setAnzahlArbeitsplaetze(arbeitsplaetze);
		tmp.setStandort(readStandort(standortID));
		tmp.setStockwerk(stockwerk);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Updates an entity of Adresse in DB
	 * 
	 * @param id         Primary Key of Raum to update
	 * @param straße     Column in Table
	 * @param hausnummer Column in Table
	 * @param stadt      Column in Table
	 * @param plz        Column in Table
	 * @param landID     ID of existing Land
	 */
	protected void updateAdresse(int id, String straße, int hausnummer, String stadt, int plz, String landID) {
		Adresse tmp = new Adresse();

		tmp.setId(id);
		tmp.setHausnummer(hausnummer);
		tmp.setLand(readLand(landID));
		tmp.setPlz(plz);
		tmp.setStadt(stadt);
		tmp.setStraße(straße);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Updates an entity of Abteilung in DB
	 * 
	 * @param id        Primary Key of Abteilung to update
	 * @param abteilung Column in Table
	 */
	protected void updateAbteilung(int id, String abteilung) {
		Abteilung tmp = new Abteilung();

		tmp.setId(id);
		tmp.setAbteilung(abteilung);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Updates an entity of Land in DB
	 * 
	 * @param id     Primary Key of Land to update
	 * @param land   new Name of Land
	 * @param landID new Primary Key of Land to update
	 */
	protected void updateLand(String id, String land, String landID) {
		Land tmp = new Land();

		tmp.setId(id);
		tmp.setLand(land);
		tmp.setId(landID);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Updates an entity of Status in DB
	 * 
	 * @param id     Primary Key of Status to update
	 * @param status new name of Status
	 */
	protected void updateStatus(int id, String status) {
		Status tmp = new Status();

		tmp.setId(id);
		tmp.setStatus(status);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Updates an entity of Hersteller in DB
	 * 
	 * @param id         Primary Key of Hersteller to update
	 * @param hersteller new name of Hersteller
	 */
	protected void updateHersteller(int id, String hersteller) {
		Hersteller tmp = new Hersteller();

		tmp.setId(id);
		tmp.setHersteller(hersteller);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Updates an entity of Typ in DB
	 * 
	 * @param id  Primary Key of Typ to update
	 * @param typ new name of Typ
	 */
	protected void updateTyp(int id, String typ) {
		Typ tmp = new Typ();

		tmp.setId(id);
		tmp.setTyp(typ);

		Transaction t = session.beginTransaction();
		try {
			session.save(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// End of Update Methods

	// Delete Methods
	/**
	 * Deletes Mitarbeiter from DB
	 * 
	 * @param id Primary key of the Mitarbeiter to delete
	 */
	protected void deleteMitarbeiter(int id) {
		session.delete(session.get(Mitarbeiter.class, id));
	}

	/**
	 * Deletes Abteilung from DB
	 * 
	 * @param id Primary key of the Abteilung to delete
	 */
	protected void deleteAbteilung(int id) {
		session.delete(session.get(Abteilung.class, id));
	}

	/**
	 * Deletes Adresse from DB
	 * 
	 * @param id Primary key of the Adresse to delete
	 */
	protected void deleteAdresse(int id) {
		session.delete(session.get(Adresse.class, id));
	}

	/**
	 * Deletes Bueroausstattung from DB
	 * 
	 * @param id Primary key of the Bueroausstattung to delete
	 */
	protected void deleteBueroausstattung(int id) {
		session.delete(session.get(Bueroausstattung.class, id));
	}

	/**
	 * Deletes Hersteller from DB
	 * 
	 * @param id Primary key of the Hersteller to delete
	 */
	protected void deleteHersteller(int id) {
		session.delete(session.get(Hersteller.class, id));
	}

	/**
	 * Deletes Land from DB
	 * 
	 * @param id Primary key of the Land to delete
	 */
	protected void deleteLand(String id) {
		session.delete(session.get(Land.class, id));
	}

	/**
	 * Deletes MitarbeiterEquipment from DB
	 * 
	 * @param id Primary key of the MitarbeiterEquipment to delete
	 */
	protected void deleteMitarbeiterEquipment(int id) {
		session.delete(session.get(MitarbeiterEquipment.class, id));
	}

	/**
	 * Deletes Raum from DB
	 * 
	 * @param id Primary key of the Raum to delete
	 */
	protected void deleteRaum(int id) {
		session.delete(session.get(Raum.class, id));
	}

	/**
	 * Deletes Standort from DB
	 * 
	 * @param id Primary key of the Standort to delete
	 */
	protected void deleteStandort(int id) {
		session.delete(session.get(Standort.class, id));
	}

	/**
	 * Deletes Status from DB
	 * 
	 * @param id Primary key of the Status to delete
	 */
	protected void deleteStatus(int id) {
		session.delete(session.get(Status.class, id));
	}

	/**
	 * Deletes Typ from DB
	 * 
	 * @param id Primary key of the Typ to delete
	 */
	protected void deleteTyp(int id) {
		session.delete(session.get(Typ.class, id));
	}

}
