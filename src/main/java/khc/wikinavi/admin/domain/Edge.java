package khc.wikinavi.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by miki on 15. 10. 13..
 */
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Integer vertexId1;

    @Column(nullable = false)
    private Integer vertexId2;

    @Column(nullable = false)
    private Integer weight;
}
