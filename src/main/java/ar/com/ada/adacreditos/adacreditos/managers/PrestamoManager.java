package ar.com.ada.creditos.managers;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.creditos.entities.*;

public class PrestamoManager {

    //Importante: prestamo Manager crea, lee, modifica, elimina(AMB/CRUD) y lista todos los prestamos de la base de datos

    protected SessionFactory sessionFactory;

    public void setup() {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
                                                                                                  // from
                                                                                                  // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw ex;
        }

    }

    public void exit() {
        sessionFactory.close();
    }

    public void create(Prestamo prestamo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(prestamo);


        session.getTransaction().commit();
        session.close();
    }

    public Prestamo read(int prestamoId) {
        Session session = sessionFactory.openSession();

        Prestamo prestamo = session.get(Prestamo.class, prestamoId);

        session.close();

        return prestamo;
    }

  /*  public Cliente readByDNI(int dni) {
        Session session = sessionFactory.openSession();
        Cliente cliente = session.byNaturalId(Cliente.class).using("dni", dni).load();
        session.close();
        return cliente;
    } no existe este metodo en prestamo porque no es un atributo en Prestamo*/

    public void update(Prestamo prestamo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(prestamo);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Prestamo prestamo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(prestamo);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Este metodo en la vida real no debe existir ya qeu puede haber miles de
     * usuarios
     * 
     * @return
     */
    public List<Prestamo> buscarTodosLosPrestamos() {

        Session session = sessionFactory.openSession();

        /// NUNCA HARCODEAR SQLs nativos en la aplicacion.
        // ESTO es solo para nivel educativo
        Query query = session.createNativeQuery("SELECT * FROM prestamo", Prestamo.class);
        //query = session.createQuery("From Obse")
        List<Prestamo> todos = query.getResultList();

        return todos;

    }

    /**
     * Busca una lista de clientes por el nombre completo Esta armado para que se
     * pueda generar un SQL Injection y mostrar commo NO debe programarse.
     * 
     * @param nombre
     * @return
     */
    public List<Prestamo> buscarPrestamoPor(int prestamoId) {

        Session session = sessionFactory.openSession();

        // SQL Injection vulnerability exposed.
        // Deberia traer solo aquella del nombre y con esto demostrarmos que trae todas
        // si pasamos
        // como nombre: "' or '1'='1"
        Query query = session.createNativeQuery("SELECT * FROM prestamo where nombre = '" + prestamoId + "'", Prestamo.class);

        List<Prestamo> prestamo = query.getResultList();

        return prestamo;

    }


}