package khc.wikinavi.admin.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by miki on 15. 10. 13..
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class Edge {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vertex_id1")
    private Vertex vertex1;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vertex_id2")
    private Vertex vertex2;

    @Column(nullable = false)
    private Integer weight;

    public Edge(Vertex vertex1, Vertex vertex2, Integer weight) {
        this.setVertex1(vertex1);
        this.setVertex2(vertex2);
        this.weight = weight;
    }

    public Edge(Vertex vertex1, Vertex vertex2) {
        this.setVertex1(vertex1);
        this.setVertex2(vertex2);
        this.setWeight(vertex1, vertex2);
    }

    private void setWeight(Vertex vertex1, Vertex vertex2) {
        this.weight = Integer.parseInt(String.valueOf(Math.round(Math.sqrt(
                Math.pow(Math.abs(vertex1.getX() - vertex2.getX()), 2) +
                Math.pow(Math.abs(vertex1.getY() - vertex2.getY()), 2)))));
    }

    public void setVertex1(Vertex vertex1) {
        // 기존 vertex와 관계를 제거
        if (this.vertex1 != null) {
            this.vertex1.getEdges1().remove(this);
        }

        this.vertex1 = vertex1;
        vertex1.getEdges1().add(this);
    }

    public void setVertex2(Vertex vertex2) {
        // 기존 vertex와 관계를 제거
        if (this.vertex2 != null) {
            this.vertex2.getEdges2().remove(this);
        }

        this.vertex2 = vertex2;
        vertex2.getEdges2().add(this);
    }
}
