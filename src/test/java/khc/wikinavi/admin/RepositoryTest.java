package khc.wikinavi.admin;

import khc.wikinavi.admin.domain.Room;
import khc.wikinavi.admin.repository.IndoorMapRepository;
import khc.wikinavi.admin.repository.RoomRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RepositoryTest {

    @Autowired
    IndoorMapRepository indoorMapRepository;

    @Autowired
    RoomRepository roomRepository;

    @Test
    public void indoorMapTest() {
        Room room = new Room();
        room.setX(5);
        room.setY(5);
//        room.setMapId(1);
        room.setName("5145 강의실");
//        IndoorMap indoorMap = new IndoorMap(null, "신공학관 4층", "서울시 중구 필동", "images/20150904_170026.jpg", 50, 60,
//                new Date(), new Date());
//        indoorMapRepository.save(indoorMap);
    }

    @Test
    public void roomTest() {
//        Room room = new Room();
//        room.setX(5);
//        room.setY(5);
//        room.setMapId(1);
//        room.setName("신공학관 강당");
//
//        roomRepository.save(room);
    }

    @Test
    public void findByMapIdTest() {
//        List<Room> rooms = roomRepository.findAllByMapId(1);
//        System.out.println(rooms);
    }

}
