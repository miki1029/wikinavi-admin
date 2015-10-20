package khc.wikinavi.admin.repository;

import khc.wikinavi.admin.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
public interface RoomRepository extends JpaRepository<Room, Integer> {
    public List<Room> findAllByMapId(Integer mapId);
}
