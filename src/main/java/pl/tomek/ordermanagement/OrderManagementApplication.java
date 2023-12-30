package pl.tomek.ordermanagement;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.tomek.ordermanagement.frontend.MenuFrame;


@Configuration
@SpringBootApplication
@ComponentScan(basePackages = "pl.tomek")
@EnableJpaRepositories(basePackages = "pl.tomek")
public class OrderManagementApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MenuFrame.class)
                .headless(false).web(WebApplicationType.NONE).run(args);
    }
}
