package khc.wikinavi.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by miki on 15. 10. 13..
 */
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndoorMap {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private Integer tileWidth;

    @Column(nullable = false)
    private Integer tileHeight;

    @Column(nullable = false)
    private Date createdTime;

    @Column(nullable = false)
    private Date modifiedTime;
}
