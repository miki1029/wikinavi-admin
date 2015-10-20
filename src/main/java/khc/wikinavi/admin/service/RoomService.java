package khc.wikinavi.admin.service;

import khc.wikinavi.admin.domain.Room;
import khc.wikinavi.admin.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
@Service
@Transactional
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findOne(Integer id) {
        return roomRepository.findOne(id);
    }

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public Room update(Room room) {
        return roomRepository.save(room);
    }

    public void delete(Integer id) {
        roomRepository.delete(id);
    }

    public List<Room> findAllByMapId(Integer mapId) {
        return roomRepository.findAllByMapId(mapId);
    }
}
