package khc.wikinavi.admin.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
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

    @Override
    public String toString() {
        return "Beacon(id=" + this.id + ", x=" + this.x + ", y=" + this.y + ", name=" + this.name + ", macAddr=" + this.macAddr + ")";
    }
}
