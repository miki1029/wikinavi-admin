package khc.wikinavi.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by miki on 15. 10. 13..
 */
@Entity
@Table
@DiscriminatorValue("beacon")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Beacon extends Vertex {

    @Column
    private String name;

    @Column(nullable = false)
    private String macAddr;
}
