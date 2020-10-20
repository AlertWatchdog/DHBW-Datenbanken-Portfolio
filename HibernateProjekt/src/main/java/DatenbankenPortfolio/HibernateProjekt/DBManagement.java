package DatenbankenPortfolio.HibernateProjekt;

import java.sql.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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

public class DBManagement {
	private StandardServiceRegistry ssr;
	private Metadata meta;
	private SessionFactory factory;
	private Session session;
	private DBCRUDManager crudManager;

	public DBManagement() {
		this.ssr = new StandardServiceRegistryBuilder().configure("resources/hibernate.cfg.xml").build();
		this.meta = new MetadataSources(ssr).getMetadataBuilder().build();

		this.factory = meta.getSessionFactoryBuilder().build();
		this.session = factory.openSession();

		crudManager = new DBCRUDManager(session);
	}

	// CRUD Methods
	// Create Methods
	// create Mitarbeiter
	public void createMitarbeiter(Date geburtsdatum, char geschlecht, String nachname, String vorname, int abteilungsID,
			int standortID, int adressID) {
		this.crudManager.createMitarbeiter(geburtsdatum, geschlecht, nachname, vorname, abteilungsID, standortID,
				adressID);
	}

	// create Bueroausstattung
	public void createBueroausstattung(Date anschaffungsdatum, String bezeichnung, int herstellerID, int raumID,
			int typID) {
		this.crudManager.createBueroausstattung(anschaffungsdatum, bezeichnung, herstellerID, raumID, typID);
	}

	// create Standort
	public void createStandort(int adressID) {
		this.crudManager.createStandort(adressID);
	}

	// create MitarbeiterEquipment
	public void createMitarbeiterEquipment(Date anschaffungsdatum, String bezeichnung, int herstellerID,
			int mitarbeiterID, int typID) {
		this.crudManager.createMitarbeiterEquipment(anschaffungsdatum, bezeichnung, herstellerID, mitarbeiterID, typID);
	}

	// create Raum
	public void createRaum(String bezeichnung, int standortID, String stockwerk, int arbeitsplaetze) {
		this.crudManager.createRaum(bezeichnung, standortID, stockwerk, arbeitsplaetze);
	}

	// create Adresse
	public void createAdresse(String straße, int hausnummer, String stadt, int plz, String landID) {
		this.crudManager.createAdresse(straße, hausnummer, stadt, plz, landID);
	}

	// create Abteilung
	public void createAbteilung(String abteilung) {
		this.crudManager.createAbteilung(abteilung);
	}

	// create Land
	public void createLand(String land, String landID) {
		this.crudManager.createLand(land, landID);
	}

	// create Status
	public void createStatus(String status) {
		this.crudManager.createStatus(status);
	}

	// create Hersteller
	public void createHersteller(String hersteller) {
		this.crudManager.createHersteller(hersteller);
	}

	// create Typ
	public void createTyp(String typ) {
		this.crudManager.createTyp(typ);
	}
	// End of Create Methods

	// read Methods start here
	// read Mitarbeiter

	public Mitarbeiter readMitarbeiter(int id) {
		return this.crudManager.readMitarbeiter(id);
	}

	public List<Mitarbeiter> readAllMitarbeiter() {
		return this.crudManager.readAllMitarbeiter();
	}

	// read Abteilung

	public Abteilung readAbteilung(int id) {
		return this.crudManager.readAbteilung(id);
	}

	public List<Abteilung> readAllAbteilung() {
		return this.crudManager.readAllAbteilung();
	}

	// read Adresse

	public Adresse readAdresse(int id) {
		return this.crudManager.readAdresse(id);
	}

	public List<Adresse> readAllAdresse() {
		return this.crudManager.readAllAdresse();
	}

	// read Bueroausstattung

	public Bueroausstattung readBueroausstattung(int id) {
		return this.crudManager.readBueroausstattung(id);
	}

	public List<Bueroausstattung> readAllBueroausstattung() {
		return this.crudManager.readAllBueroausstattung();
	}

	// read Hersteller

	public Hersteller readHersteller(int id) {
		return this.crudManager.readHersteller(id);
	}

	public List<Hersteller> readAllHersteller() {
		return this.crudManager.readAllHersteller();
	}

	// read Land

	public Land readLand(String id) {
		return this.crudManager.readLand(id);
	}

	public List<Land> readAllLand() {
		return this.crudManager.readAllLand();
	}

	// read Mitarbeiter Equipment

	public MitarbeiterEquipment readMitarbeiterEquipment(int id) {
		return this.crudManager.readMitarbeiterEquipment(id);
	}

	public List<MitarbeiterEquipment> readAllMitarbeiterEquipment() {
		return this.crudManager.readAllMitarbeiterEquipment();
	}

	// read Raum

	public Raum readRaum(int id) {
		return this.crudManager.readRaum(id);
	}

	public List<Raum> readAllRaum() {
		return this.crudManager.readAllRaum();
	}

	// read Standort

	public Standort readStandort(int id) {
		return this.crudManager.readStandort(id);
	}

	public List<Standort> readAllStandort() {
		return this.crudManager.readAllStandort();
	}

	// read Status

	public Status readStatus(int id) {
		return this.crudManager.readStatus(id);
	}

	public List<Status> readAllStatus() {
		return this.crudManager.readAllStatus();
	}

	// read Typ

	public Typ readTyp(int id) {
		return this.crudManager.readTyp(id);
	}

	public List<Typ> readAllTyp() {
		return this.crudManager.readAllTyp();
	}

	// End of Read Methods

	// Update Methods

	// End of Update Methods

	// Delete Methods
	// Delete Mitarbeiter
	public void deleteMitarbeiter(int id) {
		this.crudManager.deleteMitarbeiter(id);
	}

	// Delete Abteilung
	public void deleteAbteilung(int id) {
		this.crudManager.deleteAbteilung(id);
	}

	// Delete Adresse
	public void deleteAdresse(int id) {
		this.crudManager.deleteAbteilung(id);
	}

	// Delete Bueroausstattung
	public void deleteBueroausstattung(int id) {
		this.crudManager.deleteBueroausstattung(id);
	}

	// Delete Hersteller
	public void deleteHersteller(int id) {
		this.crudManager.deleteHersteller(id);
	}

	// Delete Land
	public void deleteLand(String id) {
		this.crudManager.deleteLand(id);
	}

	// Delete MitarbeiterEquipment
	public void deleteMitarbeiterEquipment(int id) {
		this.crudManager.deleteMitarbeiterEquipment(id);
	}

	// Delete Raum
	public void deleteRaum(int id) {
		this.crudManager.deleteRaum(id);
	}

	// Delete Standort
	public void deleteStandort(int id) {
		this.crudManager.deleteStandort(id);
	}

	// Delete Status
	public void deleteStatus(int id) {
		this.crudManager.deleteStatus(id);
	}

	// Delete Typ
	public void deleteTyp(int id) {
		this.crudManager.deleteTyp(id);
	}
}
