package khc.wikinavi.admin;

import khc.wikinavi.admin.domain.*;
import khc.wikinavi.admin.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RepositoryTest {

    @Autowired
    private IndoorMapRepository indoorMapRepository;
    @Autowired
    private VertexRepository vertexRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BeaconRepository beaconRepository;
    @Autowired
    private EdgeRepository edgeRepository;

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

//        edgeRepository.deleteAll();
//        vertexRepository.deleteAll();
        indoorMapRepository.deleteAll();
    }

    @Test
    public void testSaveAndCount() throws Exception {
        indoorMapRepository.save(indoorMap);
        assertThat(indoorMapRepository.count(), is(1L));

//        vertexes.forEach(vertexRepository::save);
        assertThat(vertexRepository.count(), is(6L));
        assertThat(roomRepository.count(), is(4L));
        assertThat(beaconRepository.count(), is(2L));

//        edges.forEach(edgeRepository::save);
        assertThat(edgeRepository.count(), is(5L));
    }

    @Test
    public void testDeleteAll() throws Exception {
        indoorMapRepository.save(indoorMap);
//        vertexes.forEach(vertexRepository::save);
//        edges.forEach(edgeRepository::save);

//        edgeRepository.deleteAll();
//        vertexRepository.deleteAll();
        assertThat(edgeRepository.count(), not(0L));
        assertThat(vertexRepository.count(), not(0L));
        assertThat(roomRepository.count(), not(0L));
        assertThat(beaconRepository.count(), not(0L));
        assertThat(indoorMapRepository.count(), not(0L));

        indoorMapRepository.deleteAll();

        assertThat(edgeRepository.count(), is(0L));
        assertThat(vertexRepository.count(), is(0L));
        assertThat(roomRepository.count(), is(0L));
        assertThat(beaconRepository.count(), is(0L));
        assertThat(indoorMapRepository.count(), is(0L));
    }

    @Test
    public void testOrphanRemove() throws Exception {
        indoorMapRepository.save(indoorMap);
        Long edgeCount1 = edgeRepository.count();
        Long vertexCount1 = vertexRepository.count();
        Vertex vertex = indoorMap.getVertexes().get(0);
        vertexRepository.delete(vertex);
        Long edgeCount2 = edgeRepository.count();
        Long vertexCount2 = vertexRepository.count();
        assertThat(vertexCount1, not(vertexCount2));
        assertThat(edgeCount1, not(edgeCount2));
    }
}
