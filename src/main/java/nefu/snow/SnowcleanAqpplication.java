package nefu.snow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : pc
 * @date : 2019/05/06
 * @since : Java 8
 */

@SpringBootApplication
@RestController
public class SnowcleanAqpplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SnowcleanAqpplication.class, args);
    }

   /* @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SnowcleanAqpplication.class);
    }*/
}
