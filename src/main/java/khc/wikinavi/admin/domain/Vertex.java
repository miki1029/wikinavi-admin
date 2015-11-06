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

    // data
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)     private Integer id;
    @Column(nullable = false)                                   private Integer x;
    @Column(nullable = false)                                   private Integer y;
    @ManyToOne(optional = false) @JoinColumn(name = "map_id")   private IndoorMap indoorMap;

    // graph data
    @OneToMany(mappedBy = "vertex1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Edge> edges1 = new ArrayList<>();

    @OneToMany(mappedBy = "vertex2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Edge> edges2 = new ArrayList<>();

    // getter, setter
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
        if(!indoorMap.getVertexes().contains(this)) {
            indoorMap.getVertexes().add(this);
        }
    }

    // methods
    public double calculateDistance(int x, int y) {
        return Math.sqrt(Math.pow(Math.abs(this.x - x), 2) + Math.pow(Math.abs(this.y - y), 2));
    }

    public List<Vertex> findShortestPathByStartPoint(int x, int y) {
        return null;
    }

    public List<Vertex> findShortestPathByStartVertex(Vertex startVertex) {

        return null;
    }
}
