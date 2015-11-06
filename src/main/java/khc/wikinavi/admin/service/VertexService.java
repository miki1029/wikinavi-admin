package khc.wikinavi.admin.service;

import khc.wikinavi.admin.domain.Vertex;
import khc.wikinavi.admin.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by miki on 15. 10. 31..
 */
@Service
@Transactional
public class VertexService {
    @Autowired
    VertexRepository vertexRepository;

    public List<Vertex> findAll() {
        return vertexRepository.findAll();
    }

    public Vertex findOne(Integer id) {
        return vertexRepository.findOne(id);
    }

    public Vertex create(Vertex room) {
        return vertexRepository.save(room);
    }

    public Vertex update(Vertex room) {
        return vertexRepository.save(room);
    }

    public void delete(Integer id) {
        vertexRepository.delete(id);
    }
}
