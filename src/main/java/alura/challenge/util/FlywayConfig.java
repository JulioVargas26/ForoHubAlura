package alura.challenge.util;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

	@Bean
	public FlywayMigrationStrategy flywayMigrationStrategy() {
		return flyway -> {
			flyway.repair(); // Ejecuta la reparación para actualizar el historial de migraciones
			flyway.migrate(); // Aplica las migraciones
		};
	}
}
