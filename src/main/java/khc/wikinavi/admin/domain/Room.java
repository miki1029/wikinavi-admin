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
public class Room extends Vertex {
    @Column(nullable = false)
    private String name;
}
