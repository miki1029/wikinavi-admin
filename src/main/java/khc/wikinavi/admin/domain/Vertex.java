package khc.wikinavi.admin.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class Vertex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer x;

    @Column(nullable = false)
    private Integer y;

    @ManyToOne(optional = false)
    @JoinColumn(name = "map_id")
    private IndoorMap indoorMap;

    @OneToMany(mappedBy = "vertex1")
    private List<Edge> edges1 = new ArrayList<>();

    @OneToMany(mappedBy = "vertex2")
    private List<Edge> edges2 = new ArrayList<>();

    public List<Edge> getEdges() {
        if(edges1 == null || edges2 == null) return null;
        List<Edge> edges = new ArrayList<>(edges1.size() + edges2.size());
        edges.addAll(edges1);
        edges.addAll(edges2);
        return edges;
    }
    
    public void setIndoorMap(IndoorMap indoorMap) {
        // 기존 IndoorMap과 관계를 제거
        if (this.indoorMap != null) {
            this.indoorMap.getVertexes().remove(this);
        }

        this.indoorMap = indoorMap;
        indoorMap.getVertexes().add(this);
    }
}
