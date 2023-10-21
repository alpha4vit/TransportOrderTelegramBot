package by.gurinovich.PricePodschet.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties("bot")
public class BotConfig {
    private String name;
    private String token;
    private String recipient;
}
