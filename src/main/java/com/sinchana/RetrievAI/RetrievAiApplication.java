package com.sinchana.RetrievAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetrievAiApplication {

	public static void main(String[] args) {
//        String apiKey = System.getenv("GOOGLE_API_KEY");
//
//        System.out.println(
//                "GOOGLE_API_KEY present: " +
//                        (apiKey != null && !apiKey.isBlank())
//        );
		SpringApplication.run(RetrievAiApplication.class, args);
	}

}
