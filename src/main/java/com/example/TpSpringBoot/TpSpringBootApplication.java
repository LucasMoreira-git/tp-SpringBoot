package com.example.TpSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Esta anotación es el corazón de tu aplicación. Por detrás, es en realidad un atajo que agrupa tres anotaciones fundamentales (@EnableAutoConfiguration, @ComponentScan, @Configuration)
public class TpSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpSpringBootApplication.class, args);
	}

}
//El framework te entrega el paquete completo preconfigurado:
// el servidor Tomcat embebido, las herramientas para crear APIs REST y el conversor a JSON

//La Autoconfiguración es el mecanismo inteligente que revisa qué "combos" (dependencias) incluiste y configura la aplicación por defecto para que funcione inmediatamente,
// sin que escribas una sola línea de infraestructura.