/**
 * User Interface for DatenbankenPortfolio.HibernateProject
 * 
 * @Version 1.0
 */
package ui;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import DatenbankenPortfolio.HibernateProjekt.DBManagement;
import DatenbankenPortfolio.HibernateProjekt.PersistenceClasses.*;

public class Ui {
	private static String operations = " -create\n -read\n -readAll\n -update\n -delete";
	private static String tables = " -Mitarbeiter\n -Bueroausstattung\n -Standort\n -Mitarbeiterequipment\n -Raum\n -Adresse\n -Abteilung\n -Land\n -Status\n -Hersteller\n -Typ";

	public static void main(String args[]) {
		DBManagement db = new DBManagement();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		startUi(db, reader);
	}

	/**
	 * Starts the UI
	 * @param db		Connected DB
	 * @param reader	to read Inputs
	 */
	private static void startUi(DBManagement db, BufferedReader reader) {
		System.out.print(
				"Welcome \nHibernate Projekt for DHBW WWI18B4 Datenbanken \nGroup: Grimm, Jonczyk, Tenten, Widmann, Wolf\n");
		System.out.println(
				"######################################################################################################");
		System.out.println("To stop the System type \"exit\" while you are not performing an operation ");
		System.out.println("Possible Operations: \n" + operations + "\n on following tables:" + tables
				+ "\nSyntax: <operation> <table>\nfurther instructions will appear during operations.\n\n");
		try {
			ui(db, reader);
		} catch (IOException e) {
			System.out.println("IO failed");
			e.printStackTrace();
		}
	}

	/**
	 * Actual UI. Handles basic Inputs
	 * @param db			Connected DB
	 * @param reader		to read Inputs
	 * @throws IOException	thrown by BufferedReader
	 */
	private static void ui(DBManagement db, BufferedReader reader) throws IOException {
		boolean running = true;
		while (running) {
			String input = reader.readLine();
			String[] tmp = input.split(" ");
			if(tmp.length > 1) {
				tmp[1] = tmp[1].toLowerCase();
			}
				tmp[0] = tmp[0].toLowerCase();
			switch (tmp[0]) {
			case "exit":
				running = false;
				System.out.println("The program has been stopped");
				break;
			case "create":
				create(db, reader, tmp[1]);
				break;
			case "read":
				read(db, reader, tmp[1]);
				break;
			case "readall":
				readAll(db, tmp[1]);
				break;
			case "update":
				update(db, reader, tmp[1]);
				break;
			case "delete":
				delete(db, reader, tmp[1]);
				break;
			default:
				System.out.println("Unknown operation.\nPossible operations:\n -exit\n" + operations);
				break;
			}
		}
	}

	/**
	 * Method to create new Instances in DB with user input
	 * @param db			connected DB
	 * @param reader		reader for Inputs
	 * @param type			type of Instance the user wants to create
	 * @throws IOException	thrown by buffered reader
	 */
	private static void create(DBManagement db, BufferedReader reader, String type) throws IOException {
		System.out.println(
				"Every variable will be asked for separately. If an entity relies on other objects those need to be created beforehands. Possibilities will be shown.");
		String bezeichnung = "";
		int adressID;
		Date anschaffungsdatum;
		int herstellerID;
		int standortID;
		int typID;
		String landID;
		switch (type) {
		case "mitarbeiter":
			System.out.println("Lastname: ");
			String nachname = reader.readLine();
			System.out.println("Surname: ");
			String vorname = reader.readLine();
			System.out.println("Gender: ");
			char geschlecht = reader.readLine().charAt(0);
			System.out.println("Birthdate (dd.mm.yyyy): ");
			Date geburtsdatum = buildDate(reader.readLine());
			System.out.println("Overview on Abteilung: ");
			readAll(db, "abteilung");
			System.out.println("Abteilungs ID: ");
			int abteilungsID = Integer.parseInt(reader.readLine());
			System.out.println("Overview on Standort: ");
			readAll(db, "standort");
			System.out.println("Standort ID: ");
			standortID = Integer.parseInt(reader.readLine());
			System.out.println("Overview on Adresse: ");
			readAll(db, "adresse");
			System.out.println("Adress ID: ");
			adressID = Integer.parseInt(reader.readLine());

			db.createMitarbeiter(geburtsdatum, geschlecht, nachname, vorname, abteilungsID, standortID, adressID);
			break;
		case "bueroausstattung":
			System.out.println("Bezeichnung: ");
			bezeichnung = reader.readLine();
			System.out.println("Date of purchase (dd.mm.yyyy): ");
			anschaffungsdatum = buildDate(reader.readLine());
			System.out.println("Overview on Hersteller: ");
			readAll(db, "hersteller");
			System.out.println("Hersteller ID: ");
			herstellerID = Integer.parseInt(reader.readLine());
			System.out.println("Overview on Raum: ");
			readAll(db, "raum");
			System.out.println("Raum ID: ");
			int raumID = Integer.parseInt(reader.readLine());
			System.out.println("Overview on Typ: ");
			readAll(db, "typ");
			System.out.println("Typ ID: ");
			typID = Integer.parseInt(reader.readLine());
			db.createBueroausstattung(anschaffungsdatum, bezeichnung, herstellerID, raumID, typID);
			break;
		case "standort":
			System.out.println("Overview on Adresse: ");
			readAll(db, "adresse");
			System.out.println("Adress ID: ");
			adressID = Integer.parseInt(reader.readLine());
			db.createStandort(adressID);
			break;
		case "mitarbeiterequipment":
			System.out.println("Bezeichnung: ");
			bezeichnung = reader.readLine();
			System.out.println("Date of purchase (dd.mm.yyyy): ");
			anschaffungsdatum = buildDate(reader.readLine());
			System.out.println("Overview on Hersteller: ");
			readAll(db, "hersteller");
			System.out.println("Hersteller ID: ");
			herstellerID = Integer.parseInt(reader.readLine());
			System.out.println("Overview on Mitarbeiter: ");
			readAll(db, "mitarbeiter");
			System.out.println("Mitarbeiter ID: ");
			int mitarbeiterID = Integer.parseInt(reader.readLine());
			System.out.println("Overview on Typ: ");
			readAll(db, "typ");
			System.out.println("Typ ID: ");
			typID = Integer.parseInt(reader.readLine());
			db.createMitarbeiterEquipment(anschaffungsdatum, bezeichnung, herstellerID, mitarbeiterID, typID);
			break;
		case "raum":
			System.out.println("Bezeichnung: ");
			bezeichnung = reader.readLine();
			System.out.println("Overview on Standort: ");
			readAll(db, "standort");
			System.out.println("Standort ID: ");
			standortID = Integer.parseInt(reader.readLine());
			System.out.println("Stockwerk: ");
			String stockwerk = reader.readLine();
			System.out.println("Arbeitsplätze: ");
			int arbeitsplaetze = Integer.parseInt(reader.readLine());
			db.createRaum(bezeichnung, standortID, stockwerk, arbeitsplaetze);
			break;
		case "adresse":
			System.out.println("Straße: ");
			String straße = reader.readLine();
			System.out.println("Hausnummer: ");
			int hausnummer = Integer.parseInt(reader.readLine());
			System.out.println("Stadt: ");
			String stadt = reader.readLine();
			System.out.println("Postleitzahl: ");
			int plz = Integer.parseInt(reader.readLine());
			System.out.println("Overview on Land: ");
			readAll(db, "land");
			System.out.println("Land ID: ");
			landID = reader.readLine();
			db.createAdresse(straße, hausnummer, stadt, plz, landID);
			break;
		case "abteilung":
			System.out.println("Abteilung: ");
			String abteilung = reader.readLine();
			db.createAbteilung(abteilung);
			break;
		case "land":
			System.out.println("Land: ");
			String land = reader.readLine();
			System.out.println("Land ID: ");
			landID = reader.readLine();
			db.createLand(land, landID);
			break;
		case "status":
			System.out.println("Status: ");
			String status = reader.readLine();
			db.createStatus(status);
			break;
		case "hersteller":
			System.out.println("Hersteller: ");
			String hersteller = reader.readLine();
			db.createHersteller(hersteller);
			break;
		case "typ":
			System.out.println("Typ: ");
			String typ = reader.readLine();
			db.createTyp(typ);
			break;
		default:
			System.out.println("Type " + type + " does not exist");
			break;
		}

	}

	/**
	 * Method to output specific entities from the DB
	 * @param db			connected DB
	 * @param reader		reader for Inputs
	 * @param type			type of Instance the user wants to read
	 * @throws IOException	thrown by buffered reader
	 */
	private static void read(DBManagement db, BufferedReader reader, String type) throws IOException {
		System.out.println("Enter the ID of the " + type + " you want to read:");
		String input = reader.readLine();
		int id = 0;
		if (type != "land") {
			id = Integer.parseInt(input);
		}

		List<String> header = new ArrayList<>();
		List<String> values = new ArrayList<>();

		switch (type) {
		case "mitarbeiter":
			Mitarbeiter ma = db.readMitarbeiter(id);

			header.clear();
			header.add("MitarbeiterID");
			header.add("Nachname");
			header.add("Vorname");
			header.add("Geschlecht");
			header.add("Geburtsdatum");

			values.clear();
			values.add("" + ma.getId());
			values.add(ma.getNachname());
			values.add(ma.getVorname());
			values.add("" + ma.getGeschlecht());
			values.add(ma.getGeburtsdatum().toString());

			show(header);
			show(values);
			break;
		case "bueroausstattung":
			Bueroausstattung ba = db.readBueroausstattung(id);

			header.clear();
			header.add("Inventarnummer");
			header.add("Bezeichnung");
			header.add("Anschaffungsdatum");

			values.clear();
			values.add("" + ba.getId());
			values.add(ba.getBezeichnung());
			values.add(ba.getAnschaffungsdatum().toString());

			show(header);
			show(values);
			break;
		case "standort":
			Standort so = db.readStandort(id);

			header.clear();
			header.add("StandortID");
			header.add("AdressID");

			values.clear();
			values.add("" + so.getId());
			values.add("" + so.getAdresse().getId());

			show(header);
			show(values);
			break;
		case "mitarbeiterequipment":
			MitarbeiterEquipment mae = db.readMitarbeiterEquipment(id);

			header.clear();
			header.add("Seriennummer");
			header.add("Bezeichnung");
			header.add("Anschaffungsdatum");

			values.clear();
			values.add("" + mae.getId());
			values.add(mae.getBezeichnung());
			values.add(mae.getAnschaffungsdatum().toString());

			show(header);
			show(values);
			break;
		case "raum":
			Raum ro = db.readRaum(id);
			header.clear();
			header.add("RaumID");
			header.add("Anzal Arbeitsplätze");
			header.add("Bezeichnung");
			header.add("Stockwerk");
			header.add("StandortID");

			values.clear();
			values.add("" + ro.getId());
			values.add("" + ro.getAnzahlArbeitsplaetze());
			values.add(ro.getBezeichnung());
			values.add(ro.getStockwerk());
			values.add("" + ro.getStandort().getId());

			show(header);
			show(values);
			break;
		case "adresse":
			Adresse ad = db.readAdresse(id);

			header.clear();
			header.add("AdressID");
			header.add("Postleitzahl");
			header.add("Stadt");
			header.add("Straße");
			header.add("Hausnummer");
			header.add("LandID");

			values.clear();
			values.add("" + ad.getId());
			values.add("" + ad.getPlz());
			values.add(ad.getStadt());
			values.add(ad.getStraße());
			values.add("" + ad.getHausnummer());
			values.add("" + ad.getLand().getId());

			show(header);
			show(values);
			break;
		case "abteilung":
			Abteilung ab = db.readAbteilung(id);

			header.clear();
			header.add("AbteilungsID");
			header.add("Abteilung");

			values.clear();
			values.add("" + ab.getId());
			values.add(ab.getAbteilung());

			show(header);
			show(values);
			break;
		case "land":
			Land la = db.readLand(input);

			header.clear();
			header.add("LandID");
			header.add("Land");

			values.clear();
			values.add("" + la.getId());
			values.add("" + la.getLand());

			show(header);
			show(values);
			break;
		case "status":
			Status st = db.readStatus(id);

			header.clear();
			header.add("StatusID");
			header.add("Status");

			values.clear();
			values.add("" + st.getId());
			values.add(st.getStatus());

			show(header);
			show(values);
			break;
		case "hersteller":
			Hersteller he = db.readHersteller(id);
			header.clear();
			header.add("HerstellerID");
			header.add("Hersteller");

			values.clear();
			values.add("" + he.getId());
			values.add(he.getHersteller());

			show(header);
			show(values);
			break;
		case "typ":
			Typ tp = db.readTyp(id);

			header.clear();
			header.add("TypID");
			header.add("Typ");

			values.clear();
			values.add("" + tp.getId());
			values.add(tp.getTyp());

			show(header);
			show(values);
			break;
		default:
			System.out.println("Type " + type + " does not exist");
			break;
		}
	}

	/**
	 * Method to show all instances of type
	 * @param db			connected DB
	 * @param type			type of Instance the user wants to read
	 */
	private static void readAll(DBManagement db, String type) {
		List<String> header = new ArrayList<>();
		List<String> values = new ArrayList<>();

		switch (type) {
		case "mitarbeiter":
			List<Mitarbeiter> ma = db.readAllMitarbeiter();

			header.clear();
			header.add("MitarbeiterID");
			header.add("Nachname");
			header.add("Vorname");
			header.add("Geschlecht");
			header.add("Geburtsdatum");
			show(header);

			ma.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add(x.getNachname());
				values.add(x.getVorname());
				values.add("" + x.getGeschlecht());
				values.add(x.getGeburtsdatum().toString());

				show(values);
			});
			break;
		case "bueroausstattung":
			List<Bueroausstattung> ba = db.readAllBueroausstattung();

			header.clear();
			header.add("Inventarnummer");
			header.add("Bezeichnung");
			header.add("Anschaffungsdatum");
			show(header);

			ba.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add(x.getBezeichnung());
				values.add(x.getAnschaffungsdatum().toString());

				show(values);
			});
			break;
		case "standort":
			List<Standort> so = db.readAllStandort();

			header.clear();
			header.add("StandortID");
			header.add("AdressID");
			show(header);

			so.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add("" + x.getAdresse().getId());

				show(values);
			});
			break;
		case "mitarbeiterequipment":
			List<MitarbeiterEquipment> mae = db.readAllMitarbeiterEquipment();

			header.clear();
			header.add("Seriennummer");
			header.add("Bezeichnung");
			header.add("Anschaffungsdatum");
			show(header);

			mae.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add(x.getBezeichnung());
				values.add(x.getAnschaffungsdatum().toString());

				show(values);
			});
			break;
		case "raum":
			List<Raum> ro = db.readAllRaum();
			header.clear();
			header.add("RaumID");
			header.add("Anzal Arbeitsplätze");
			header.add("Bezeichnung");
			header.add("Stockwerk");
			header.add("StandortID");
			show(header);

			ro.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add("" + x.getAnzahlArbeitsplaetze());
				values.add(x.getBezeichnung());
				values.add(x.getStockwerk());
				values.add("" + x.getStandort().getId());

				show(values);
			});
			break;
		case "adresse":
			List<Adresse> ad = db.readAllAdresse();

			header.clear();
			header.add("AdressID");
			header.add("Postleitzahl");
			header.add("Stadt");
			header.add("Straße");
			header.add("Hausnummer");
			header.add("LandID");
			show(header);

			ad.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add("" + x.getPlz());
				values.add(x.getStadt());
				values.add(x.getStraße());
				values.add("" + x.getHausnummer());
				values.add("" + x.getLand().getId());

				show(values);
			});
			break;
		case "abteilung":
			List<Abteilung> ab = db.readAllAbteilung();

			header.clear();
			header.add("AbteilungsID");
			header.add("Abteilung");
			show(header);

			ab.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add(x.getAbteilung());

				show(values);
			});
			break;
		case "land":
			List<Land> la = db.readAllLand();

			header.clear();
			header.add("LandID");
			header.add("Land");
			show(header);

			la.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add("" + x.getLand());

				show(values);
			});
			break;
		case "status":
			List<Status> st = db.readAllStatus();

			header.clear();
			header.add("StatusID");
			header.add("Status");
			show(header);

			st.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add(x.getStatus());

				show(values);
			});
			break;
		case "hersteller":
			List<Hersteller> he = db.readAllHersteller();
			header.clear();
			header.add("HerstellerID");
			header.add("Hersteller");
			show(header);

			he.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add(x.getHersteller());

				show(values);
			});
			break;
		case "typ":
			List<Typ> tp = db.readAllTyp();

			header.clear();
			header.add("TypID");
			header.add("Typ");
			show(header);

			tp.forEach(x -> {
				values.clear();
				values.add("" + x.getId());
				values.add(x.getTyp());

				show(values);
			});
			break;
		default:
			System.out.println("Type " + type + " does not exist");
			break;
		}
	}

	/**
	 * Method to update instance of type
	 * @param db			connected DB
	 * @param reader		bufferedReader for inputs
	 * @param type			type of instance to update
	 * @throws IOException	thrown by buffered reader
	 */
	private static void update(DBManagement db, BufferedReader reader, String type) throws IOException {
		System.out.println(
				"Every variable will be asked for separately. If an entity relies on other objects those need to be created beforehands. Possibilities will be shown.");
		System.out.println("Show possible " + type + " to pick? (y/n)");
		char input = reader.readLine().charAt(0);
		boolean showEntities = false;
		if (input == 'y')
			showEntities = true;

		String bezeichnung, landID, nachname, vorname, stockwerk, straße, stadt;
		int adressID, herstellerID, standortID, id, abteilungsID, raumID, mitarbeiterID, arbeitsplaetze, hausnummer, plz, typID;
		Date anschaffungsdatum, geburtsdatum;
		char geschlecht;
		switch (type) {
		case "mitarbeiter":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Mitarbeiter you want to update: ");
			Mitarbeiter ma = db.readMitarbeiter(Integer.parseInt(reader.readLine()));
			id = ma.getId();
			if (updateColumn(reader, "Lastname")) {
				System.out.println("Lastname: ");
				nachname = reader.readLine();
			} else {
				nachname = ma.getNachname();
			}
			if (updateColumn(reader, "Surname")) {
				System.out.println("Surname: ");
				vorname = reader.readLine();
			} else {
				vorname = ma.getVorname();
			}
			if (updateColumn(reader, "Gender")) {
				System.out.println("Gender: ");
				geschlecht = reader.readLine().charAt(0);
			} else {
				geschlecht = ma.getGeschlecht();
			}
			if (updateColumn(reader, "Birthdate")) {
				System.out.println("Birthdate (dd.mm.yyyy): ");
				geburtsdatum = buildDate(reader.readLine());
			} else {
				geburtsdatum = ma.getGeburtsdatum();
			}
			if (updateColumn(reader, "Abteilung")) {
				System.out.println("Overview on Abteilung: ");
				readAll(db, "abteilung");
				System.out.println("Abteilungs ID: ");
				abteilungsID = Integer.parseInt(reader.readLine());
			} else {
				abteilungsID = ma.getAbteilung().getId();
			}
			if (updateColumn(reader, "Standort")) {
				System.out.println("Overview on Standort: ");
				readAll(db, "standort");
				System.out.println("Standort ID: ");
				standortID = Integer.parseInt(reader.readLine());
			} else {
				standortID = ma.getStandort().getId();
			}
			if (updateColumn(reader, "Adresse")) {
				System.out.println("Overview on Adresse: ");
				readAll(db, "adresse");
				System.out.println("Adress ID: ");
				adressID = Integer.parseInt(reader.readLine());
			} else {
				adressID = ma.getAdresse().getId();
			}

			db.updateMitarbeiter(id, geburtsdatum, geschlecht, nachname, vorname, abteilungsID, standortID, adressID);
			break;
		case "bueroausstattung":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Büroausstattung you want to update: ");
			Bueroausstattung ba = db.readBueroausstattung(Integer.parseInt(reader.readLine()));
			id = ba.getId();
			if (updateColumn(reader, "Bezeichnung")) {
				System.out.println("Bezeichnung: ");
				bezeichnung = reader.readLine();
			} else {
				bezeichnung = ba.getBezeichnung();
			}
			if (updateColumn(reader, "Date of purchase")) {
				System.out.println("Date of purchase (dd.mm.yyyy): ");
				anschaffungsdatum = buildDate(reader.readLine());
			} else {
				anschaffungsdatum = ba.getAnschaffungsdatum();
				;
			}
			if (updateColumn(reader, "Hersteller")) {
				System.out.println("Overview on Hersteller: ");
				readAll(db, "hersteller");
				System.out.println("Hersteller ID: ");
				herstellerID = Integer.parseInt(reader.readLine());
			} else {
				herstellerID = ba.getHersteller().getId();
			}
			if (updateColumn(reader, "Raum")) {
				System.out.println("Overview on Raum: ");
				readAll(db, "raum");
				System.out.println("Raum ID: ");
				raumID = Integer.parseInt(reader.readLine());
			} else {
				raumID = ba.getRaum().getId();
			}
			if (updateColumn(reader, "Typ")) {
				System.out.println("Overview on Typ: ");
				readAll(db, "typ");
				System.out.println("Typ ID: ");
				typID = Integer.parseInt(reader.readLine());
			} else {
				typID = ba.getTyp().getId();
			}
			db.updateBueroausstattung(id, anschaffungsdatum, bezeichnung, herstellerID, raumID, typID);
			break;
		case "standort":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Standort you want to update: ");
			id = Integer.parseInt(reader.readLine());
			System.out.println("Overview on Adresse: ");
			readAll(db, "adresse");
			System.out.println("Adress ID: ");
			adressID = Integer.parseInt(reader.readLine());
			db.updateStandort(id, adressID);
			break;
		case "mitarbeiterequipment":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Mitarbeiterequipment you want to update: ");
			MitarbeiterEquipment me = db.readMitarbeiterEquipment(Integer.parseInt(reader.readLine()));
			id = me.getId();

			if (updateColumn(reader, "Bezeichnung")) {
				System.out.println("Bezeichnung: ");
				bezeichnung = reader.readLine();
			} else {
				bezeichnung = me.getBezeichnung();
			}
			if (updateColumn(reader, "Date of purchase")) {
				System.out.println("Date of purchase (dd.mm.yyyy): ");
				anschaffungsdatum = buildDate(reader.readLine());
			} else {
				anschaffungsdatum = me.getAnschaffungsdatum();
			}
			if (updateColumn(reader, "Hersteller")) {
				System.out.println("Overview on Hersteller: ");
				readAll(db, "hersteller");
				System.out.println("Hersteller ID: ");
				herstellerID = Integer.parseInt(reader.readLine());
			} else {
				herstellerID = me.getHersteller().getId();
			}
			if (updateColumn(reader, "Mitarbeiter")) {
				System.out.println("Overview on Mitarbeiter: ");
				readAll(db, "mitarbeiter");
				System.out.println("Mitarbeiter ID: ");
				mitarbeiterID = Integer.parseInt(reader.readLine());
			} else {
				mitarbeiterID = me.getMitarbeiter().getId();
			}
			if (updateColumn(reader, "Typ")) {
				System.out.println("Overview on Typ: ");
				readAll(db, "typ");
				System.out.println("Typ ID: ");
				typID = Integer.parseInt(reader.readLine());
			} else {
				typID = me.getTyp().getId();
			}
			db.updateMitarbeiterEquipment(id, anschaffungsdatum, bezeichnung, herstellerID, mitarbeiterID, typID);
			break;
		case "raum":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Raum you want to update: ");
			Raum ra = db.readRaum(Integer.parseInt(reader.readLine()));
			id = ra.getId();
			if (updateColumn(reader, "Bezeichnung")) {
				System.out.println("Bezeichnung: ");
				bezeichnung = reader.readLine();
			} else {
				bezeichnung = ra.getBezeichnung();
				;
			}
			if (updateColumn(reader, "Standort")) {
				System.out.println("Overview on Standort: ");
				readAll(db, "standort");
				System.out.println("Standort ID: ");
				standortID = Integer.parseInt(reader.readLine());
			} else {
				standortID = ra.getStandort().getId();
			}
			if (updateColumn(reader, "Stockwerk")) {
				System.out.println("Stockwerk: ");
				stockwerk = reader.readLine();
			} else {
				stockwerk = ra.getStockwerk();
			}
			if (updateColumn(reader, "Arbeitsplätze")) {
				System.out.println("Arbeitsplätze: ");
				arbeitsplaetze = Integer.parseInt(reader.readLine());
			} else {
				arbeitsplaetze = ra.getAnzahlArbeitsplaetze();
			}
			db.updateRaum(id, bezeichnung, standortID, stockwerk, arbeitsplaetze);
			break;
		case "adresse":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Adresse you want to update: ");
			Adresse ad = db.readAdresse(Integer.parseInt(reader.readLine()));
			id = ad.getId();

			if (updateColumn(reader, "Straße")) {
				System.out.println("Straße: ");
				straße = reader.readLine();
			} else {
				straße = ad.getStraße();
			}
			if (updateColumn(reader, "Hausnummer")) {
				System.out.println("Hausnummer: ");
				hausnummer = Integer.parseInt(reader.readLine());
			} else {
				hausnummer = ad.getHausnummer();
			}
			if (updateColumn(reader, "Stadt")) {
				System.out.println("Stadt: ");
				stadt = reader.readLine();
			} else {
				stadt = ad.getStadt();
			}
			if (updateColumn(reader, "Postleitzahl")) {
				System.out.println("Postleitzahl: ");
				plz = Integer.parseInt(reader.readLine());
			} else {
				plz = ad.getPlz();
			}
			if (updateColumn(reader, "Land")) {
				System.out.println("Overview on Land: ");
				readAll(db, "land");
				System.out.println("Land ID: ");
				landID = reader.readLine();
			} else {
				landID = ad.getLand().getId();
			}
			db.updateAdresse(id, straße, hausnummer, stadt, plz, landID);
			break;
		case "abteilung":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Abteilung you want to update: ");
			id = Integer.parseInt(reader.readLine());
			System.out.println("Abteilung: ");
			String abteilung = reader.readLine();
			db.updateAbteilung(id, abteilung);
			break;
		case "land":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Land you want to update: ");
			landID = reader.readLine();
			System.out.println("Land: ");
			String land = reader.readLine();
			System.out.println("Land ID: ");
			landID = reader.readLine();
			db.updateLand(landID, land, landID);
			break;
		case "status":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Adresse you want to update: ");
			id = Integer.parseInt(reader.readLine());
			System.out.println("Status: ");
			String status = reader.readLine();
			db.updateStatus(id, status);
			break;
		case "hersteller":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Hersteller you want to update: ");
			id = Integer.parseInt(reader.readLine());
			System.out.println("Hersteller: ");
			String hersteller = reader.readLine();
			db.updateHersteller(id, hersteller);
			break;
		case "typ":
			if (showEntities)
				readAll(db, type);
			System.out.println("Enter ID of the Typ you want to update: ");
			id = Integer.parseInt(reader.readLine());
			System.out.println("Typ: ");
			String typ = reader.readLine();
			db.updateTyp(id, typ);
			break;
		default:
			System.out.println("Type " + type + " does not exist");
			break;
		}
	}

	/**
	 * Deletes specific instance of type from DB
	 * @param db			connected DB
	 * @param reader		bufferedReader for input
	 * @param type			type of instance to delete
	 * @throws IOException	thrown by BufferedReader
	 */
	private static void delete(DBManagement db, BufferedReader reader, String type) throws IOException {
		System.out.println("Enter the ID of the " + type + " you want to delete:");
		String input = reader.readLine();
		int id = 0;
		if (type != "land") {
			id = Integer.parseInt(input);
		}
		switch (type) {
		case "mitarbeiter":
			db.deleteMitarbeiter(id);
			break;
		case "bueroausstattung":
			db.deleteBueroausstattung(id);
			break;
		case "standort":
			db.deleteStandort(id);
			break;
		case "mitarbeiterequipment":
			db.deleteMitarbeiterEquipment(id);
			break;
		case "raum":
			db.deleteRaum(id);
			break;
		case "adresse":
			db.deleteAdresse(id);
			break;
		case "abteilung":

			db.deleteAbteilung(id);
			break;
		case "land":
			db.deleteLand(input);
			break;
		case "status":
			db.deleteStatus(id);
			break;
		case "hersteller":
			db.deleteHersteller(id);
			break;
		case "typ":
			db.deleteTyp(id);
			break;
		default:
			System.out.println("Type " + type + " does not exist");
			break;
		}

	}

	/**
	 * Outputs rows of table to console
	 * @param values	List of DB rows
	 */
	private static void show(List<String> values) {
		values.forEach((x) -> {
			System.out.print("| " + x + " |");
		});
		System.out.println("");
	}

	/**
	 * builds a java.sql.Date from String
	 * @param input	Date String (dd.MM.yyyy)
	 * @return	java.sql.Date
	 */
	private static Date buildDate(String input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		try {
			return new Date(dateFormat.parse(input).getTime());
		} catch (ParseException e) {
			System.out.println("Pasing Date failed");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks if the user wants to update a specific column from an element
	 * @param reader		BufferedReader for Input
	 * @param column		name of column checked
	 * @return				true, if the user wants to update column; false if not
	 * @throws IOException	thrown by BufferedReader
	 */
	private static boolean updateColumn(BufferedReader reader, String column) throws IOException {
		System.out.println("Do you want to change " + column + "? (y/n)");
		char input = reader.readLine().charAt(0);
		if (input == 'y')
			return true;
		return false;
	}
}
