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

	public DBCRUDManager(Session session) {
		this.session = session;
	}

	// Create Methods
	// create Mitarbeiter
	protected void createMitarbeiter(Date geburtsdatum, char geschlecht, String nachname, String vorname, int abteilungsID,
			int standortID, int adressID) {
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

	// create Bueroausstattung
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

	// create Standort
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

	// create MitarbeiterEquipment
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

	// create Raum
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

	// create Adresse
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

	// create Abteilung
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

	// create Land
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

	// create Status
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

	// create Hersteller
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

	// create Typ
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
	// read Mitarbeiter

	protected Mitarbeiter readMitarbeiter(int id) {
		Transaction t = session.beginTransaction();
		Mitarbeiter tmp = session.get(Mitarbeiter.class, id);
		t.commit();
		return tmp;
	}

	protected List<Mitarbeiter> readAllMitarbeiter() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Mitarbeiter> tmp = session.createQuery("from Mitarbeiter").list();
		t.commit();
		return tmp;
	}

	// read Abteilung

	protected Abteilung readAbteilung(int id) {
		Transaction t = session.beginTransaction();
		Abteilung tmp = session.get(Abteilung.class, id);
		t.commit();
		return tmp;
	}

	protected List<Abteilung> readAllAbteilung() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Abteilung> tmp = session.createQuery("from Abteilung").list();
		t.commit();
		return tmp;
	}

	// read Adresse

	protected Adresse readAdresse(int id) {
		Transaction t = session.beginTransaction();
		Adresse tmp = session.get(Adresse.class, id);
		t.commit();
		return tmp;
	}

	protected List<Adresse> readAllAdresse() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Adresse> tmp = session.createQuery("from Adresse").list();
		t.commit();
		return tmp;
	}

	// read Bueroausstattung

	protected Bueroausstattung readBueroausstattung(int id) {
		Transaction t = session.beginTransaction();
		Bueroausstattung tmp = session.get(Bueroausstattung.class, id);
		t.commit();
		return tmp;
	}

	protected List<Bueroausstattung> readAllBueroausstattung() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Bueroausstattung> tmp = session.createQuery("from Bueroausstattung").list();
		t.commit();
		return tmp;
	}

	// read Hersteller

	protected Hersteller readHersteller(int id) {
		Transaction t = session.beginTransaction();
		Hersteller tmp = session.get(Hersteller.class, id);
		t.commit();
		return tmp;
	}

	protected List<Hersteller> readAllHersteller() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Hersteller> tmp = session.createQuery("from Hersteller").list();
		t.commit();
		return tmp;
	}

	// read Land

	protected Land readLand(String id) {
		Transaction t = session.beginTransaction();
		Land tmp = session.get(Land.class, id);
		t.commit();
		return tmp;
	}

	protected List<Land> readAllLand() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Land> tmp = session.createQuery("from Land").list();
		t.commit();
		return tmp;
	}

	// read Mitarbeiter Equipment

	protected MitarbeiterEquipment readMitarbeiterEquipment(int id) {
		Transaction t = session.beginTransaction();
		MitarbeiterEquipment tmp = session.get(MitarbeiterEquipment.class, id);
		t.commit();
		return tmp;
	}

	protected List<MitarbeiterEquipment> readAllMitarbeiterEquipment() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<MitarbeiterEquipment> tmp = session.createQuery("from MitarbeiterEquipment").list();
		t.commit();
		return tmp;
	}

	// read Raum

	protected Raum readRaum(int id) {
		Transaction t = session.beginTransaction();
		Raum tmp = session.get(Raum.class, id);
		t.commit();
		return tmp;
	}

	protected List<Raum> readAllRaum() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Raum> tmp = session.createQuery("from Raum").list();
		t.commit();
		return tmp;
	}

	// read Standort

	protected Standort readStandort(int id) {
		Transaction t = session.beginTransaction();
		Standort tmp = session.get(Standort.class, id);
		t.commit();
		return tmp;
	}

	protected List<Standort> readAllStandort() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Standort> tmp = session.createQuery("from Standort").list();
		t.commit();
		return tmp;
	}

	// read Status

	protected Status readStatus(int id) {
		Transaction t = session.beginTransaction();
		Status tmp = session.get(Status.class, id);
		t.commit();
		return tmp;
	}

	protected List<Status> readAllStatus() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Status> tmp = session.createQuery("from Status").list();
		t.commit();
		return tmp;
	}

	// read Typ

	protected Typ readTyp(int id) {
		Transaction t = session.beginTransaction();
		Typ tmp = session.get(Typ.class, id);
		t.commit();
		return tmp;
	}

	protected List<Typ> readAllTyp() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Typ> tmp = session.createQuery("from Typ").list();
		t.commit();
		return tmp;
	}

	// End of Read Methods
	
	//Update Methods
		// Update Mitarbeiter
		protected void updateMitarbeiter(int id, Date geburtsdatum, char geschlecht, String nachname, String vorname, int abteilungsID,
				int standortID, int adressID) {
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

		// update Bueroausstattung
		protected void updateBueroausstattung(int id, Date anschaffungsdatum, String bezeichnung, int herstellerID, int raumID,
				int typID) {
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

		// update Standort
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

		// update MitarbeiterEquipment
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

		// update Raum
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

		// update Adresse
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

		// update Abteilung
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

		// update Land
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

		// update Status
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

		// update Hersteller
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

		// update Typ
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
		
	//End of Update Methods
	
	// Delete Methods
	// Delete Mitarbeiter
	protected void deleteMitarbeiter(int id) {
		session.delete(session.get(Mitarbeiter.class, id));
	}

	// Delete Abteilung
	protected void deleteAbteilung(int id) {
		session.delete(session.get(Abteilung.class, id));
	}

	// Delete Adresse
	protected void deleteAdresse(int id) {
		session.delete(session.get(Adresse.class, id));
	}

	// Delete Bueroausstattung
	protected void deleteBueroausstattung(int id) {
		session.delete(session.get(Bueroausstattung.class, id));
	}

	// Delete Hersteller
	protected void deleteHersteller(int id) {
		session.delete(session.get(Hersteller.class, id));
	}

	// Delete Land
	protected void deleteLand(String id) {
		session.delete(session.get(Land.class, id));
	}

	// Delete MitarbeiterEquipment
	protected void deleteMitarbeiterEquipment(int id) {
		session.delete(session.get(MitarbeiterEquipment.class, id));
	}

	// Delete Raum
	protected void deleteRaum(int id) {
		session.delete(session.get(Raum.class, id));
	}

	// Delete Standort
	protected void deleteStandort(int id) {
		session.delete(session.get(Standort.class, id));
	}

	// Delete Status
	protected void deleteStatus(int id) {
		session.delete(session.get(Status.class, id));
	}

	// Delete Typ
	protected void deleteTyp(int id) {
		session.delete(session.get(Typ.class, id));
	}

}
