package pl.tomek.ordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "pl.tomek")
public class OrdermanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdermanagementApplication.class, args);
    }

}
