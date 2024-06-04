package rookie.brain.clickerGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "rookie.brain.clickerGame")
@EnableJpaRepositories(basePackages = "rookie.brain.clickerGame.Repository")
public class ClickerGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClickerGameApplication.class, args);
	}
}
