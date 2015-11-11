package khc.wikinavi.admin.domain;

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public abstract class Vertex {

    // data
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)     protected Integer id;
    @Column(nullable = false)                                   protected Integer x;
    @Column(nullable = false)                                   protected Integer y;
    @ManyToOne(optional = false) @JoinColumn(name = "map_id")   private IndoorMap indoorMap;

    // graph data
    @OneToMany(mappedBy = "vertex1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Edge> edges1 = new ArrayList<>();

    @OneToMany(mappedBy = "vertex2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Edge> edges2 = new ArrayList<>();

    // getter, setter
    public abstract String getName();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return id.equals(vertex.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Vertex(id=" + this.id + ", x=" + this.x + ", y=" + this.y + ")";
    }

    // methods
    public double calculateDistance(int x, int y) {
        return Math.sqrt(Math.pow(Math.abs(this.x - x), 2) + Math.pow(Math.abs(this.y - y), 2));
    }

}
