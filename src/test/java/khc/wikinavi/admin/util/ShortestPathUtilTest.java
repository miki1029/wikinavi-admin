package khc.wikinavi.admin.util;

import khc.wikinavi.admin.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miki on 15. 11. 7..
 */
public class ShortestPathUtilTest {

    private IndoorMap indoorMap;
    private List<Vertex> vertexes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
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

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testShortestPath() throws Exception {
        List<Vertex> shortestPath = ShortestPathUtil.shortestPath(indoorMap, vertexes.get(3), vertexes.get(2));
        for (Vertex vertex : shortestPath) {
            System.out.println(vertex);
        }
    }
}