package khc.wikinavi.admin.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by miki on 15. 10. 13..
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class IndoorMap {

    // data
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
    @Column(nullable = false, length = 45)                  private String title;
    @Column(nullable = false, length = 128)                 private String address;
    @Column(nullable = false, length = 256)                 private String imagePath;
    @Column(nullable = false)                               private Integer tileWidth;
    @Column(nullable = false)                               private Integer tileHeight;
    @Column(nullable = false) @Temporal(TemporalType.DATE)  private Date createdTime;
    @Column(nullable = false) @Temporal(TemporalType.DATE)  private Date modifiedTime;

    // graph data
    @OneToMany(mappedBy = "indoorMap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vertex> vertexes = new ArrayList<>();

    // constructor
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

    // getter
    public List<Room> getRooms() {
        // vertexes에서 Room인 인스턴스만 확인하여 반환
        return vertexes.stream().filter(vertex -> vertex instanceof Room).map(vertex -> (Room) vertex).collect(Collectors.toList());
    }

    public List<Beacon> getBeacons() {
        // vertexes에서 Beacon인 인스턴스만 확인하여 반환
        return vertexes.stream().filter(vertex -> vertex instanceof Beacon).map(vertex -> (Beacon) vertex).collect(Collectors.toList());
    }

    // methods
    // 가장 가까운 3개 Vertex 반환
    public List<Vertex> findNearbyVertexes(int x, int y) {
        TreeMap<Double, Vertex> vertexTreeMap = new TreeMap<>();
        for (Vertex vertex : vertexes) {
            double distance = vertex.calculateDistance(x, y);
            vertexTreeMap.put(distance, vertex);
        }

        List<Vertex> nearbyVertexes = new ArrayList<>(3);
        for (Map.Entry<Double, Vertex> entry : vertexTreeMap.entrySet()) {
            nearbyVertexes.add(entry.getValue());
            if (nearbyVertexes.size() == 3) break;
        }

        return nearbyVertexes;
    }

}
