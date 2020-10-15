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

public class DBManagement {
	private StandardServiceRegistry ssr;
	private Metadata meta;
	private SessionFactory factory;
	private Session session;

	public DBManagement() {
		this.ssr = new StandardServiceRegistryBuilder().configure("resources/hibernate.cfg.xml").build();
		this.meta = new MetadataSources(ssr).getMetadataBuilder().build();

		this.factory = meta.getSessionFactoryBuilder().build();
		this.session = factory.openSession();

	}


// read Methods start here
	// read Mitarbeiter

	public Mitarbeiter readMitarbeiter(int id) {
		Transaction t = session.beginTransaction();
		Mitarbeiter tmp = session.get(Mitarbeiter.class, id);
		t.commit();
		return tmp;
	}

	public List<Mitarbeiter> readAllMitarbeiter() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Mitarbeiter> tmp = session.createQuery("from mitarbeiter").list();
		t.commit();
		return tmp;
	}

	// read Abteilung

	public Abteilung readAbteilung(int id) {
		Transaction t = session.beginTransaction();
		Abteilung tmp = session.get(Abteilung.class, id);
		t.commit();
		return tmp;
	}

	public List<Abteilung> readAllAbteilung() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Abteilung> tmp = session.createQuery("from abteilung").list();
		t.commit();
		return tmp;
	}

	// read Adresse

	public Adresse readAdresse(int id) {
		Transaction t = session.beginTransaction();
		Adresse tmp = session.get(Adresse.class, id);
		t.commit();
		return tmp;
	}

	public List<Adresse> readAllAdresse() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Adresse> tmp = session.createQuery("from adresse").list();
		t.commit();
		return tmp;
	}

	// read Bueroausstattung

	public Bueroausstattung readBueroausstattung(int id) {
		Transaction t = session.beginTransaction();
		Bueroausstattung tmp = session.get(Bueroausstattung.class, id);
		t.commit();
		return tmp;
	}

	public List<Bueroausstattung> readAllBueroausstattung() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Bueroausstattung> tmp = session.createQuery("from büroausstattung").list();
		t.commit();
		return tmp;
	}

	// read Hersteller

	public Hersteller readHersteller(int id) {
		Transaction t = session.beginTransaction();
		Hersteller tmp = session.get(Hersteller.class, id);
		t.commit();
		return tmp;
	}

	public List<Hersteller> readAllHersteller() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Hersteller> tmp = session.createQuery("from hersteller").list();
		t.commit();
		return tmp;
	}

	// read Land

	public Land readLand(String id) {
		Transaction t = session.beginTransaction();
		Land tmp = session.get(Land.class, id);
		t.commit();
		return tmp;
	}

	public List<Land> readAllLand() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Land> tmp = session.createQuery("from land").list();
		t.commit();
		return tmp;
	}

	// read Mitarbeiter Equipment

	public MitarbeiterEquipment readMitarbeiterEquipment(int id) {
		Transaction t = session.beginTransaction();
		MitarbeiterEquipment tmp = session.get(MitarbeiterEquipment.class, id);
		t.commit();
		return tmp;
	}

	public List<MitarbeiterEquipment> readAllMitarbeiterEquipment() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<MitarbeiterEquipment> tmp = session.createQuery("from mitarbeiter-equipment").list();
		t.commit();
		return tmp;
	}

	// read Raum

	public Raum readRaum(int id) {
		Transaction t = session.beginTransaction();
		Raum tmp = session.get(Raum.class, id);
		t.commit();
		return tmp;
	}

	public List<Raum> readAllRaum() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Raum> tmp = session.createQuery("from raum").list();
		t.commit();
		return tmp;
	}

	// read Standort

	public Standort readStandort(int id) {
		Transaction t = session.beginTransaction();
		Standort tmp = session.get(Standort.class, id);
		t.commit();
		return tmp;
	}

	public List<Standort> readAllStandort() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Standort> tmp = session.createQuery("from standort").list();
		t.commit();
		return tmp;
	}

	// read Status

	public Status readStatus(int id) {
		Transaction t = session.beginTransaction();
		Status tmp = session.get(Status.class, id);
		t.commit();
		return tmp;
	}

	public List<Status> readAllStatus() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Status> tmp = session.createQuery("from status").list();
		t.commit();
		return tmp;
	}

	// read Typ

	public Typ readTyp(int id) {
		Transaction t = session.beginTransaction();
		Typ tmp = session.get(Typ.class, id);
		t.commit();
		return tmp;
	}

	public List<Typ> readAllTyp() {
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Typ> tmp = session.createQuery("from typ").list();
		t.commit();
		return tmp;
	}

//End of Read Methods
//Delete Methods
	// Delete Mitarbeiter
	public void deleteMitarbeiter(int id) {
		Mitarbeiter tmp = new Mitarbeiter();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// Delete Abteilung
	public void deleteAbteilung(int id) {
		Abteilung tmp = new Abteilung();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// Delete Adresse
	public void deleteAdresse(int id) {
		Adresse tmp = new Adresse();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// Delete Bueroausstattung
	public void deleteBueroausstattung(int id) {
		Bueroausstattung tmp = new Bueroausstattung();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// Delete Hersteller
	public void deleteHersteller(int id) {
		Hersteller tmp = new Hersteller();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// Delete Land
	public void deleteLand(String id) {
		Land tmp = new Land();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// Delete MitarbeiterEquipment
	public void deleteMitarbeiterEquipment(int id) {
		MitarbeiterEquipment tmp = new MitarbeiterEquipment();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// Delete Raum
	public void deleteRaum(int id) {
		Raum tmp = new Raum();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// Delete Standort
	public void deleteStandort(int id) {
		Standort tmp = new Standort();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// Delete Status
	public void deleteStatus(int id) {
		Status tmp = new Status();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

	// Delete Typ
	public void deleteTyp(int id) {
		Typ tmp = new Typ();
		tmp.setId(id);

		Transaction t = session.beginTransaction();
		try {
			session.delete(tmp);
			t.commit();
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
	}

}
