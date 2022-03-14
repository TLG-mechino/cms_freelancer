package vn.compedia.website.config;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class Config {

    @Bean
    public void loadLog4j() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/log4j.properties"));
        PropertyConfigurator.configure(props);
    }
}
