package khc.wikinavi.admin.repository;

import khc.wikinavi.admin.domain.IndoorMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
public interface IndoorMapRepository extends JpaRepository<IndoorMap, Integer> {
    List<IndoorMap> findByTitleContainingAndAddressContaining(String title, String address);
}
