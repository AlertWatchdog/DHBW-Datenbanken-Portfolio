package DatenbankenPortfolio.HibernateProjekt;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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
}
