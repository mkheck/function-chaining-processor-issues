package com.example.processor;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.function.Function;

@SpringBootApplication
public class ProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessorApplication.class, args);
    }

}

@Configuration
class CoffeeRoaster {
    private final Random rnd = new Random();

    @Bean
    Function<WholesaleCoffee, RetailCoffee> processit() {
        return wCoffee -> {
            RetailCoffee rCoffee = new RetailCoffee(wCoffee.getId(),
                    wCoffee.getName(),
                    rnd.nextInt(2) == 0
                            ? RetailCoffee.State.WHOLE_BEANS
                            : RetailCoffee.State.GROUND);

            System.out.println(rCoffee);
            return rCoffee;
        };
    }

    @Bean
    Function<RetailCoffee, RetailCoffee> fixit() {
        return coffee -> new RetailCoffee(coffee.getId(),
                coffee.getName(),
                RetailCoffee.State.WHOLE_BEANS);
    }
}

@Data
@AllArgsConstructor
class RetailCoffee {
    enum State {
        WHOLE_BEANS,
        GROUND
    }

    private final String id, name;
    private final State state;
}

@Data
@AllArgsConstructor
class WholesaleCoffee {
    private final String id, name;
}