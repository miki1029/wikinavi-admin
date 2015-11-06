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
@DiscriminatorValue("room")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Room extends Vertex {
    @Column(nullable = false, length = 45)
    private String name;

    public Room(IndoorMap indoorMap,Integer x, Integer y, String name) {
        this.setIndoorMap(indoorMap);
        this.setX(x);
        this.setY(y);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Room(id=" + this.id + ", x=" + this.x + ", y=" + this.y + ", name=" + this.name + ")";
    }
}
