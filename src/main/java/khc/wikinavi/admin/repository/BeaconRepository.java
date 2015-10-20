package khc.wikinavi.admin.repository;

import khc.wikinavi.admin.domain.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
public interface BeaconRepository extends JpaRepository<Beacon, Integer> {
    public List<Beacon> findAllByMapId(Integer mapId);
}
