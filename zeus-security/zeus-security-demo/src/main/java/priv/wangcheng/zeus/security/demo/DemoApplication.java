package priv.wangcheng.zeus.security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangcheng
 * @version $Id: DemoApplication.java, v0.1 2019/5/26 18:43 wangcheng Exp $$
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(DemoApplication.class,args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
