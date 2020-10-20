package DatenbankenPortfolio.HibernateProjekt;

public class Test {
	public static void main(String[] args) {
		DBManagement db = new DBManagement();
		db.deleteLand("FR");
		System.out.println(db.readLand("FR").getLand());
	}
}
