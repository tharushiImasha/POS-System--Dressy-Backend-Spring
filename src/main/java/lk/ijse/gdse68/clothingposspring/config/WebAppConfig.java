package lk.ijse.gdse68.clothingposspring.config;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "lk.ijse.gdse68.clothingposspring")
@EnableWebMvc
@EnableJpaRepositories(basePackages = "lk.ijse.gdse68.clothingposspring")
@EnableTransactionManagement
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 *2,     //2mb
        maxFileSize = 1024 * 1024 * 10,         //10mb
        maxRequestSize = 1024 * 1024 *50        //50mb
)
public class WebAppConfig {
}