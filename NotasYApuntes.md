
# 1. El Problema Histórico: Spring Clásico
Levantar un proyecto implicaba lidiar con archivos XML gigantescos, resolver conflictos de versiones de dependencias manualmente y configurar servidores externos como Tomcat por separado.

**`La solucion: SpringBoot`**
Spring Boot es igual a Spring Framework más configuración automática y convenciones inteligentes. Elimina la configuración repetitiva para que puedas enfocarte directamente en tu lógica de negocio, esta agilidad se logra gracias a:

1. **Starters:** Son dependencias preconfiguradas que incluyen todas las librerías necesarias para una funcionalidad específica, asegurando que las versiones sean 100% compatibles entre sí.
2. **Autoconfiguración**: Es el "cerebro" detrás de la magia. Spring Boot examina automáticamente para detectar qué dependencias (starters) tienes instaladas y aplica configuraciones inteligentes por defecto. Por ejemplo, si detecta spring-boot-starter-web, automáticamente te levanta un servidor Tomcat embebido y te configura Spring MVC.

**`JPA Puro y SpringBoot`**
En JPA Puro hay que armar el archivo META-INF/persistence.xml, definir las credenciales de la base de datos, los dialectos, instanciar a mano el EntityManagerFactory y gestionar el inicio y cierre de transacciones en cada método.


# 2.Estructura Base y el Corazón de Spring Boot

Al generar tu proyecto, Spring Initializr te entrega una estructura estándar de Maven:

- **`src/main/java`**: Aquí va todo tu código fuente Java (tus entidades, DTOs, repositorios, etc.).
- **`src/main/resources`**: Contiene archivos de configuración, recursos estáticos y plantillas. Aquí reside tu `application.properties`, donde más adelante configuraremos la conexión a H2.
- **`pom.xml`**: El archivo que gestiona las dependencias de Maven.

Dentro de `src/main/java`, tienes tu clase principal (en tu caso, `TpSpringBootApplication.java`). Esta clase está decorada con la anotación **`@SpringBootApplication`**, la cual es el corazón de tu aplicación porque agrupa tres anotaciones vitales en una sola:

1. **`@Configuration`**: Le dice a Spring que esta clase puede definir beans de configuración.
2. **`@EnableAutoConfiguration`**: Activa la magia que discutimos en el módulo 1, configurando componentes automáticamente según el classpath.
3. **`@ComponentScan`**: Le indica a Spring que escanee el paquete actual (y sus subpaquetes) en busca de componentes para gestionarlos.

Al ejecutar esta clase desde tu IDE, Spring Boot levanta un servidor Tomcat embebido y tu aplicación queda disponible, por defecto, en el puerto 8080.



