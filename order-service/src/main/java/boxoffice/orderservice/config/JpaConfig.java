package boxoffice.orderservice.config;

import org.springframework.context.annotation.Configuration;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class JpaConfig {
}