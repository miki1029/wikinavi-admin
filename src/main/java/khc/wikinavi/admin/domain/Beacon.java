package khc.wikinavi.admin.domain;

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
@NoArgsConstructor
public class Beacon extends Vertex {

    @Column(length = 45)
    private String name;

    @Column(nullable = false, length = 17, unique = true)
    private String macAddr;

    public Beacon(IndoorMap indoorMap, Integer x, Integer y, String name, String macAddr) {
        this.setIndoorMap(indoorMap);
        this.setX(x);
        this.setY(y);
        this.name = name;
        this.macAddr = macAddr;
    }
}
