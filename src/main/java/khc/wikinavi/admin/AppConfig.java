package khc.wikinavi.admin;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;

/**
 * Created by miki on 15. 10. 11..
 */
// web.xml
@Configuration
public class AppConfig {
    @Autowired
    DataSourceProperties dataSourceProperties; // application.yml의 spring.datasource 속성들을 저장하는 클래스를 주입
    DataSource dataSource;

    @Bean
    DataSource realDataSource() {
        DataSourceBuilder factory = DataSourceBuilder
                .create(this.dataSourceProperties.getClassLoader()) // spring.datasource.driverClassName
                .url(this.dataSourceProperties.getUrl())            // spring.datasource.url
                .username(this.dataSourceProperties.getUsername())  // spring.datasource.username
                .password(this.dataSourceProperties.getPassword()); // spring.datasource.password
        this.dataSource = factory.build();
        return this.dataSource;
    }

    @Bean
    @Primary
    // dataSource Wrapping for Logging
    DataSource dataSource() {
        return new Log4jdbcProxyDataSource(this.dataSource);
    }

    @Bean
    String uploadPath() {
        return "/var/wikinavi/images/";
    }
}
