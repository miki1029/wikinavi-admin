package khc.wikinavi.admin;

import khc.wikinavi.admin.domain.*;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miki on 15. 10. 22..
 */
public class DBConnectionTest {
    private static final String DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    private static final String URL = "jdbc:hsqldb:file:/tmp/wikinavi";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private IndoorMap indoorMap;
    private List<Vertex> vertexes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    @Before
    public void setUp() {
        indoorMap = new IndoorMap("신공학관 5층", "서울시 중구 필동", "20150904_170026.jpg", 50, 60);
        Room room1 = new Room(indoorMap, 24, 47, "5145 강의실");
        Room room2 = new Room(indoorMap, 15, 47, "5147 강의실");
        Room room3 = new Room(indoorMap, 21, 40, "5134 연구실");
        Room room4 = new Room(indoorMap, 11, 41, "5103 연구실");
        Beacon beacon1 = new Beacon(indoorMap, 21, 47, "5145 앞", "aa-bb-cc-dd-ee-ff");
        Beacon beacon2 = new Beacon(indoorMap, 11, 47, "5147 앞", "11-22-33-44-55-66");
        vertexes.add(room1);
        vertexes.add(room2);
        vertexes.add(room3);
        vertexes.add(room4);
        vertexes.add(beacon1);
        vertexes.add(beacon2);
        edges.add(new Edge(room1, beacon1));
        edges.add(new Edge(beacon1, room3));
        edges.add(new Edge(beacon1, beacon2));
        edges.add(new Edge(beacon2, room2));
        edges.add(new Edge(beacon2, room4));
    }

    @Test
    public void testJdbcConnection() throws Exception {
        Class.forName(DRIVER);

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJpaConnection() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("wikinavi");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.persist(indoorMap);

            for (Vertex vertex : vertexes) {
                em.persist(vertex);
            }

            for (Edge edge : edges) {
                em.persist(edge);
            }

            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        finally {
            em.close();
        }
        emf.close();
    }
}
