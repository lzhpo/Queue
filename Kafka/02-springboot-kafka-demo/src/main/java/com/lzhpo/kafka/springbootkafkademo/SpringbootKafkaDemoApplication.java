package com.lzhpo.kafka.springbootkafkademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootKafkaDemoApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SpringbootKafkaDemoApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootKafkaDemoApplication.class, args);

        KafkaSender sender = context.getBean(KafkaSender.class);

        for (int i = 0; i < 6; i++) {

            sender.send();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
