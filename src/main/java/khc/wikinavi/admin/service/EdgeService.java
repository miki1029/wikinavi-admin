package khc.wikinavi.admin.service;

import khc.wikinavi.admin.domain.Edge;
import khc.wikinavi.admin.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
@Service
@Transactional
public class EdgeService {
    @Autowired
    EdgeRepository edgeRepository;

    public List<Edge> findAll() {
        return edgeRepository.findAll();
    }

    public Edge findOne(Integer id) {
        return edgeRepository.findOne(id);
    }

    public Edge create(Edge edge) {
        return edgeRepository.save(edge);
    }

    public Edge update(Edge edge) {
        return edgeRepository.save(edge);
    }

    public void delete(Integer id) {
        edgeRepository.delete(id);
    }
}
