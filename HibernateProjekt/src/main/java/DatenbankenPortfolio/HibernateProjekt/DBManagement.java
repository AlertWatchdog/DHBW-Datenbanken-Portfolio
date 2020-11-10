/**
 * This class manages the Hibernate DBAccess to Portfolio DB
 * 
 * @version 1.0
 */
package DatenbankenPortfolio.HibernateProjekt;

import java.sql.Date;
import java.util.ArrayList;
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
	
	//Example Queries
	/**
	 * Returns number of Standort-entities for a certain Land
	 * @param landID	ID of the Land entity
	 * @return
	 */
	public int getNumberofStandorteInLand(String landID) {
		List<Standort> standorte = readAllStandort();
		int result = 0;
		for(Standort standort : standorte) {
			if(standort.getAdresse().getLand().getId().contains(landID)) 
				result++;
		}
		return result;
	}
	
	/**
	 * Returns number of Arbeitsplatz-entities for a certain Land
	 * @param landID
	 * @return
	 */
	public int getArbeitsplaetzeInLand(String landID) {
		List<Raum> raeume = readAllRaum();
		int result = 0;
		for(Raum raum : raeume) {
			if(raum.getStandort().getAdresse().getLand().getId().contains(landID))
				result += raum.getAnzahlArbeitsplaetze();
		}
		return result;
	}
	
	/**
	 * Returns all Standort-entities for a certain Land
	 * @param landID
	 * @return
	 */
	public List<Standort> getStandortInLand(String landID){
		List<Standort> standorte = readAllStandort();
		List<Standort> result = new ArrayList<Standort>();
		for(Standort standort : standorte) {
			if(standort.getAdresse().getLand().getId().contains(landID)) 
				result.add(standort);
		}
		return result;
	}
	
	//Ohne Adresse
	/**
	 * Returns all Mitarbeiter-entities where sex is male for a certain Land
	 * @param landID
	 * @return
	 */
	public List<Mitarbeiter> getMaleMitarbeiterInLand(String landID){
		List<Mitarbeiter> mitarbeiter = readAllMitarbeiter();
		List<Mitarbeiter> result = new ArrayList<Mitarbeiter>();
		for(Mitarbeiter ma : mitarbeiter) {
			if(ma.getAdresse().getLand().getId().contains(landID) && ma.getGeschlecht() == 'm') 
				result.add(ma);
		}
		return result;
	}
	
	/**
	 * Returns the number of Bueroausstattung-Entities with the Status "im Einsatz"
	 * @return
	 */
	public int getNumberOfUsedEquipment() {
		List<Bueroausstattung> bueroausstattung = readAllBueroausstattung();
		int result = 0;
		for(Bueroausstattung x : bueroausstattung) {
			if(x.getStatus().getStatus().contains("im Einsatz")) 
				result++;
		}
		return result;
	}
	
	/**
	 * Returns the entities of MitarbeiterEquipment related to one Mitarbeiter
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public List<MitarbeiterEquipment> getMitarbeiterEquipmentOfMitarbeiter(String firstName, String lastName){
		List<Mitarbeiter> mitarbeiterL = readAllMitarbeiter();
		Mitarbeiter mitarbeiter = null;
		for(Mitarbeiter ma : mitarbeiterL) {
			if(ma.getVorname().contains(firstName) && ma.getNachname().contains(lastName))
				mitarbeiter = ma;
		}
		List<MitarbeiterEquipment> mitarbeiterequipment = readAllMitarbeiterEquipment();
		List<MitarbeiterEquipment> result = new ArrayList<MitarbeiterEquipment>();
		for(MitarbeiterEquipment mae : mitarbeiterequipment) {
			if(mae.getMitarbeiter() == mitarbeiter) 
				result.add(mae);
		}
		return result;
	}
	
	/**
	 * Returns the number of Mitarbeiter-entities for one Abteilung
	 * @param abteilungsID
	 * @return
	 */
	public List<Mitarbeiter> getMitarbeiterInAbteilung(int abteilungsID){
		List<Mitarbeiter> mitarbeiter = readAllMitarbeiter();
		List<Mitarbeiter> result = new ArrayList<Mitarbeiter>();
		for(Mitarbeiter ma : mitarbeiter) {
			if(ma.getAbteilung().getId() == abteilungsID)
				result.add(ma);
		}
		return result;
	}
	
	
	

	// CRUD Methods
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
	public void createMitarbeiter(Date geburtsdatum, char geschlecht, String nachname, String vorname, int abteilungsID,
			int standortID, int adressID) {
		this.crudManager.createMitarbeiter(geburtsdatum, geschlecht, nachname, vorname, abteilungsID, standortID,
				adressID);
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
	public void createBueroausstattung(Date anschaffungsdatum, String bezeichnung, int herstellerID, int raumID,
			int typID, int statusID) {
		this.crudManager.createBueroausstattung(anschaffungsdatum, bezeichnung, herstellerID, raumID, typID, statusID);
	}

	/**
	 * Creates a new Standort in DB
	 * 
	 * @param adressID Column in Table
	 */
	public void createStandort(int adressID) {
		this.crudManager.createStandort(adressID);
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
	public void createMitarbeiterEquipment(Date anschaffungsdatum, String bezeichnung, int herstellerID,
			int mitarbeiterID, int typID) {
		this.crudManager.createMitarbeiterEquipment(anschaffungsdatum, bezeichnung, herstellerID, mitarbeiterID, typID);
	}

	/**
	 * Creates a new Raum in DB
	 * 
	 * @param bezeichnung    Column in Table
	 * @param standortID     ID of existing Standort
	 * @param stockwerk      Column in Table
	 * @param arbeitsplaetze Column in Table
	 */
	public void createRaum(String bezeichnung, int standortID, String stockwerk, int arbeitsplaetze) {
		this.crudManager.createRaum(bezeichnung, standortID, stockwerk, arbeitsplaetze);
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
	public void createAdresse(String straße, int hausnummer, String stadt, int plz, String landID) {
		this.crudManager.createAdresse(straße, hausnummer, stadt, plz, landID);
	}

	/**
	 * Creates a new Abteilung in DB
	 * 
	 * @param abteilung Column in Table
	 */
	public void createAbteilung(String abteilung) {
		this.crudManager.createAbteilung(abteilung);
	}

	/**
	 * Creates a new Land in DB
	 * 
	 * @param land   Column in Table
	 * @param landID PrimaryKey
	 */
	public void createLand(String land, String landID) {
		this.crudManager.createLand(land, landID);
	}

	/**
	 * Creates a new Status in DB
	 * 
	 * @param status Column in Table
	 */
	public void createStatus(String status) {
		this.crudManager.createStatus(status);
	}

	/**
	 * Creates a new Hersteller in DB
	 * 
	 * @param hersteller Column in Table
	 */
	public void createHersteller(String hersteller) {
		this.crudManager.createHersteller(hersteller);
	}

	/**
	 * Creates a new Typ in DB
	 * 
	 * @param typ Column in Table
	 */
	public void createTyp(String typ) {
		this.crudManager.createTyp(typ);
	}
	// End of Create Methods

	// read Methods start here
	/**
	 * Reads specific Mitarbeiter from DB
	 * 
	 * @param id Primary key of the Mitarbeiter to read
	 * @return Instance of Mitarbeiter
	 */
	public Mitarbeiter readMitarbeiter(int id) {
		return this.crudManager.readMitarbeiter(id);
	}

	/**
	 * Reads the Table Mitarbeiter from DB
	 * 
	 * @return List of Mitarbeiter Instances
	 */
	public List<Mitarbeiter> readAllMitarbeiter() {
		return this.crudManager.readAllMitarbeiter();
	}

	/**
	 * Reads specific Abteilung from DB
	 * 
	 * @param id Primary key of the Abteilung to read
	 * @return Instance of Abteilung
	 */
	public Abteilung readAbteilung(int id) {
		return this.crudManager.readAbteilung(id);
	}

	/**
	 * Reads Table Abteilung from DB
	 * 
	 * @return List of Abteilung Instances
	 */
	public List<Abteilung> readAllAbteilung() {
		return this.crudManager.readAllAbteilung();
	}

	/**
	 * Reads specific Adresse from DB
	 * 
	 * @param id Primary key of the Adresse to read
	 * @return Instance of Adresse
	 */
	public Adresse readAdresse(int id) {
		return this.crudManager.readAdresse(id);
	}

	/**
	 * Reads the Table Adresse from DB
	 * 
	 * @return List of Adresse Instances
	 */
	public List<Adresse> readAllAdresse() {
		return this.crudManager.readAllAdresse();
	}

	/**
	 * Reads specific Bueroausstattung from DB
	 * 
	 * @param id Primary key of the Bueroausstattung to read
	 * @return Instance of Bueroausstattung
	 */
	public Bueroausstattung readBueroausstattung(int id) {
		return this.crudManager.readBueroausstattung(id);
	}

	/**
	 * Reads table Bueroausstattung from DB
	 * 
	 * @return List of Bueroaustattung instances
	 */
	public List<Bueroausstattung> readAllBueroausstattung() {
		return this.crudManager.readAllBueroausstattung();
	}

	/**
	 * Reads specific Hersteller from DB
	 * 
	 * @param id Primary key of the Hersteller to read
	 * @return Instance of Hersteller
	 */
	public Hersteller readHersteller(int id) {
		return this.crudManager.readHersteller(id);
	}

	/**
	 * Reads table Hersteller from DB
	 * 
	 * @return List of Hersteller instances
	 */
	public List<Hersteller> readAllHersteller() {
		return this.crudManager.readAllHersteller();
	}

	/**
	 * Reads specific Land from DB
	 * 
	 * @param id Primary key of the Land to read
	 * @return Instance of Land
	 */
	public Land readLand(String id) {
		return this.crudManager.readLand(id);
	}

	/**
	 * Reads table Land from DB
	 * 
	 * @return List of Land instances
	 */
	public List<Land> readAllLand() {
		return this.crudManager.readAllLand();
	}

	/**
	 * Reads specific MitarbeiterEquipment from DB
	 * 
	 * @param id Primary key of the MitarbeiterEquipment to read
	 * @return Instance of MitarbeiterEquipment
	 */
	public MitarbeiterEquipment readMitarbeiterEquipment(int id) {
		return this.crudManager.readMitarbeiterEquipment(id);
	}

	/**
	 * Reads table MitarbeiterEquipment from DB
	 * 
	 * @return List of MitarbeiterEquipment instances
	 */
	public List<MitarbeiterEquipment> readAllMitarbeiterEquipment() {
		return this.crudManager.readAllMitarbeiterEquipment();
	}

	/**
	 * Reads specific Raum from DB
	 * 
	 * @param id Primary key of the Raum to read
	 * @return Instance of Raum
	 */
	public Raum readRaum(int id) {
		return this.crudManager.readRaum(id);
	}

	/**
	 * Reads table Raum from DB
	 * 
	 * @return List of Raum instances
	 */
	public List<Raum> readAllRaum() {
		return this.crudManager.readAllRaum();
	}

	/**
	 * Reads specific Standort from DB
	 * 
	 * @param id Primary key of the Standort to read
	 * @return Instance of Standort
	 */
	public Standort readStandort(int id) {
		return this.crudManager.readStandort(id);
	}
	
	/**
	 * Reads table Standort from DB
	 * 
	 * @return List of Standort instances
	 */
	public List<Standort> readAllStandort() {
		return this.crudManager.readAllStandort();
	}

	/**
	 * Reads specific Status from DB
	 * 
	 * @param id Primary key of the Status to read
	 * @return Instance of Status
	 */
	public Status readStatus(int id) {
		return this.crudManager.readStatus(id);
	}

	/**
	 * Reads table Status from DB
	 * 
	 * @return List of Status instances
	 */
	public List<Status> readAllStatus() {
		return this.crudManager.readAllStatus();
	}

	/**
	 * Reads specific Typ from DB
	 * 
	 * @param id Primary key of the Typ to read
	 * @return Instance of Typ
	 */
	public Typ readTyp(int id) {
		return this.crudManager.readTyp(id);
	}

	/**
	 * Reads table Typ from DB
	 * 
	 * @return List of Typ instances
	 */
	public List<Typ> readAllTyp() {
		return this.crudManager.readAllTyp();
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
	public void updateMitarbeiter(int id, Date geburtsdatum, char geschlecht, String nachname, String vorname,
			int abteilungsID, int standortID, int adressID) {
		this.crudManager.updateMitarbeiter(id, geburtsdatum, geschlecht, nachname, vorname, abteilungsID, standortID,
				adressID);
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
	public void updateBueroausstattung(int id, Date anschaffungsdatum, String bezeichnung, int herstellerID, int raumID,
			int typID, int statusID) {
		this.crudManager.updateBueroausstattung(id, anschaffungsdatum, bezeichnung, herstellerID, raumID, typID, statusID);
	}

	/**
	 * Updates an entity of Standort in DB
	 * 
	 * @param id       Primary Key of Bueroausstattung to update
	 * @param adressID ID of existing Adresse
	 */
	public void updateStandort(int id, int adressID) {
		this.crudManager.updateStandort(id, adressID);
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
	public void updateMitarbeiterEquipment(int id, Date anschaffungsdatum, String bezeichnung, int herstellerID,
			int mitarbeiterID, int typID) {
		this.crudManager.updateMitarbeiterEquipment(id, anschaffungsdatum, bezeichnung, herstellerID, mitarbeiterID,
				typID);
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
	public void updateRaum(int id, String bezeichnung, int standortID, String stockwerk, int arbeitsplaetze) {
		this.crudManager.updateRaum(id, bezeichnung, standortID, stockwerk, arbeitsplaetze);
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
	public void updateAdresse(int id, String straße, int hausnummer, String stadt, int plz, String landID) {
		this.crudManager.updateAdresse(id, straße, hausnummer, stadt, plz, landID);
	}

	/**
	 * Updates an entity of Abteilung in DB
	 * 
	 * @param id        Primary Key of Abteilung to update
	 * @param abteilung Column in Table
	 */
	public void updateAbteilung(int id, String abteilung) {
		this.crudManager.updateAbteilung(id, abteilung);
	}

	/**
	 * Updates an entity of Land in DB
	 * 
	 * @param id     Primary Key of Land to update
	 * @param land   new Name of Land
	 * @param landID new Primary Key of Land to update
	 */
	public void updateLand(String id, String land, String landID) {
		this.crudManager.updateLand(id, land, landID);
	}

	/**
	 * Updates an entity of Status in DB
	 * 
	 * @param id     Primary Key of Status to update
	 * @param status new name of Status
	 */
	public void updateStatus(int id, String status) {
		this.crudManager.updateStatus(id, status);
	}

	/**
	 * Updates an entity of Hersteller in DB
	 * 
	 * @param id         Primary Key of Hersteller to update
	 * @param hersteller new name of Hersteller
	 */
	public void updateHersteller(int id, String hersteller) {
		this.crudManager.updateHersteller(id, hersteller);
	}

	/**
	 * Updates an entity of Typ in DB
	 * 
	 * @param id  Primary Key of Typ to update
	 * @param typ new name of Typ
	 */
	public void updateTyp(int id, String typ) {
		this.crudManager.updateTyp(id, typ);
	}
	// End of Update Methods

	// Delete Methods
	/**
	 * Deletes Mitarbeiter from DB
	 * 
	 * @param id Primary key of the Mitarbeiter to delete
	 */
	public void deleteMitarbeiter(int id) {
		this.crudManager.deleteMitarbeiter(id);
	}

	/**
	 * Deletes Abteilung from DB
	 * 
	 * @param id Primary key of the Abteilung to delete
	 */
	public void deleteAbteilung(int id) {
		this.crudManager.deleteAbteilung(id);
	}

	/**
	 * Deletes Adresse from DB
	 * 
	 * @param id Primary key of the Adresse to delete
	 */
	public void deleteAdresse(int id) {
		this.crudManager.deleteAbteilung(id);
	}

	/**
	 * Deletes Bueroausstattung from DB
	 * 
	 * @param id Primary key of the Bueroausstattung to delete
	 */
	public void deleteBueroausstattung(int id) {
		this.crudManager.deleteBueroausstattung(id);
	}

	/**
	 * Deletes Hersteller from DB
	 * 
	 * @param id Primary key of the Hersteller to delete
	 */
	public void deleteHersteller(int id) {
		this.crudManager.deleteHersteller(id);
	}

	/**
	 * Deletes Land from DB
	 * 
	 * @param id Primary key of the Land to delete
	 */
	public void deleteLand(String id) {
		this.crudManager.deleteLand(id);
	}

	/**
	 * Deletes MitarbeiterEquipment from DB
	 * 
	 * @param id Primary key of the MitarbeiterEquipment to delete
	 */
	public void deleteMitarbeiterEquipment(int id) {
		this.crudManager.deleteMitarbeiterEquipment(id);
	}

	/**
	 * Deletes Raum from DB
	 * 
	 * @param id Primary key of the Raum to delete
	 */
	public void deleteRaum(int id) {
		this.crudManager.deleteRaum(id);
	}

	/**
	 * Deletes Standort from DB
	 * 
	 * @param id Primary key of the Standort to delete
	 */
	public void deleteStandort(int id) {
		this.crudManager.deleteStandort(id);
	}

	/**
	 * Deletes Status from DB
	 * 
	 * @param id Primary key of the Status to delete
	 */
	public void deleteStatus(int id) {
		this.crudManager.deleteStatus(id);
	}

	/**
	 * Deletes Typ from DB
	 * 
	 * @param id Primary key of the Typ to delete
	 */
	public void deleteTyp(int id) {
		this.crudManager.deleteTyp(id);
	}
}
