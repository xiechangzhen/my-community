package life.showlin.community.mycommunity;

import life.showlin.community.mycommunity.mapper.TuserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyCommunityApplicationTests {
@Autowired
private TuserMapper mapper;
    @Test
    public void contextLoads() {
        /*try {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection("jdbc:h2:~/community","sa","123");
            System.out.println("con"+con);
            //
            User u=new User();
            u.setName("aa");
            //mapper.createOrUpdate(u);
        }catch (Exception e){
            e.printStackTrace();
        }*/

    }

}
