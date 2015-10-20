package khc.wikinavi.admin.service;

import khc.wikinavi.admin.domain.Beacon;
import khc.wikinavi.admin.repository.BeaconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
@Service
@Transactional
public class BeaconService {
    @Autowired
    BeaconRepository beaconRepository;

    public List<Beacon> findAll() {
        return beaconRepository.findAll();
    }

    public Beacon findOne(Integer id) {
        return beaconRepository.findOne(id);
    }

    public Beacon create(Beacon beacon) {
        return beaconRepository.save(beacon);
    }

    public Beacon update(Beacon beacon) {
        return beaconRepository.save(beacon);
    }

    public void delete(Integer id) {
        beaconRepository.delete(id);
    }

    public List<Beacon> findAllByMapId(Integer id) {
        return beaconRepository.findAllByMapId(id);
    }
}
