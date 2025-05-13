package com.tienda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.tienda")
public class IcebergMarketplaceApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(IcebergMarketplaceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("\n🔍 Buscando controladores registrados por Spring...\n");
        String[] beans = context.getBeanDefinitionNames();
        for (String bean : beans) {
            if (bean.toLowerCase().contains("controller")) {
                System.out.println(" Controlador encontrado: " + bean);
            }
        }
        System.out.println("\n🔎 Fin de escaneo de controladores.\n");
    }
}
