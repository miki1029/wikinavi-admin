package khc.wikinavi.admin.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by miki on 15. 10. 13..
 */
@Entity
@Table
@DiscriminatorValue("room")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Room extends Vertex {
    @Column(nullable = false)
    private String name;
}
