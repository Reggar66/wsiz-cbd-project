package com.example.wsizcbdproject;

import com.example.wsizcbdproject.client.DesktopClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WsizCbdProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WsizCbdProjectApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        DesktopClient desktopClient = new DesktopClient();
        desktopClient
                .printItemList()
                .launchClient();
    }
}
