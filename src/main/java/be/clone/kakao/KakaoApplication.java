package be.clone.kakao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KakaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KakaoApplication.class, args);
    }

}
