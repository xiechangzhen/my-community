package life.showlin.community.mycommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
@MapperScan(basePackages = {"life.showlin.community.mycommunity.mapper"})
public class MyCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCommunityApplication.class, args);
    }

}
