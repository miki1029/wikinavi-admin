package khc.wikinavi.admin.service;

import khc.wikinavi.admin.domain.IndoorMap;
import khc.wikinavi.admin.repository.IndoorMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
@Service
@Transactional
public class IndoorMapService {
    @Autowired
    IndoorMapRepository indoorMapRepository;

    public List<IndoorMap> findAll() {
        return indoorMapRepository.findAll();
    }

    public List<IndoorMap> findLikeTitleAndAddress(String title, String address) {
        return indoorMapRepository.findByTitleContainingAndAddressContaining(title, address);
    }

    public IndoorMap findOne(Integer id) {
        return indoorMapRepository.findOne(id);
    }

    public IndoorMap create(IndoorMap indoorMap) {
        return indoorMapRepository.save(indoorMap);
    }

    public IndoorMap update(IndoorMap indoorMap) {
        return indoorMapRepository.save(indoorMap);
    }

    public void delete(Integer id) {
        indoorMapRepository.delete(id);
    }

}
