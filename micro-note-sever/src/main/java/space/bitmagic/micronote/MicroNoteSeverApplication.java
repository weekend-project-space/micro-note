package space.bitmagic.micronote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import space.bitmagic.micronote.config.MicroNoteProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(MicroNoteProperties.class)
public class MicroNoteSeverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroNoteSeverApplication.class, args);
	}


}
