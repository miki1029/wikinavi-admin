package khc.wikinavi.admin.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by miki on 15. 10. 13..
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class IndoorMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String title;

    @Column(nullable = false, length = 128)
    private String address;

    @Column(nullable = false, length = 256)
    private String imagePath;

    @Column(nullable = false)
    private Integer tileWidth;

    @Column(nullable = false)
    private Integer tileHeight;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdTime;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date modifiedTime;

    @OneToMany(mappedBy = "indoorMap")
    private List<Vertex> vertexes = new ArrayList<>();

    public IndoorMap(String title, String address, String imagePath,
                     Integer tileWidth, Integer tileHeight,
                     Date createdTime, Date modifiedTime) {
        this(title, address, imagePath, tileWidth, tileHeight);
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public IndoorMap(String title, String address, String imagePath,
                     Integer tileWidth, Integer tileHeight) {
        this.title = title;
        this.address = address;
        this.imagePath = imagePath;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.createdTime = new Date();
        this.modifiedTime = new Date();
    }
}
