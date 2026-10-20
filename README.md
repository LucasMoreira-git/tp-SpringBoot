# TP Spring Boot

Proyecto práctico de Spring Boot desarrollado para aplicar los conceptos fundamentales del framework, la organización de un proyecto Maven, los DTO y la carga inicial de datos.

El modelo del sistema está compuesto por:

- `Usuario`
- `Pedido`
- `Producto`
- `Categoria`
- `DetallePedido`, que representa la composición de un pedido y no se modela como un DTO independiente.

## Objetivos

El proyecto permite practicar:

- Creación de una aplicación Spring Boot.
- Organización de paquetes Java.
- Uso de Maven y del Maven Wrapper.
- Configuración de una base de datos H2 en memoria.
- Uso de Spring Data JPA.
- Creación de DTO mediante `record`.
- Separación entre DTO de consulta, creación y edición.
- Instanciación de DTO al iniciar la aplicación.
- Uso de `ApplicationContext`, Beans, configuración e inyección de dependencias.
- Validación del contexto mediante pruebas de Spring Boot.

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 21 | Lenguaje configurado en el proyecto |
| Spring Boot 4.1.1 | Framework principal |
| Maven | Gestión de dependencias y ciclo de compilación |
| Spring Web MVC | Base para aplicaciones web y APIs |
| Spring Data JPA | Integración con persistencia y Hibernate |
| H2 | Base de datos relacional en memoria |
| Lombok | Dependencia disponible para reducir código repetitivo |
| JUnit | Pruebas automatizadas |

Las dependencias se encuentran en [pom.xml](./pom.xml).

## Requisitos

- JDK 21 o superior.
- Git, opcional para clonar y versionar el proyecto.
- No es necesario instalar Maven porque el proyecto incluye Maven Wrapper.

## Cómo ejecutar el proyecto

Desde la carpeta `TpSpringBoot`, ejecutar:

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux o macOS

```bash
./mvnw spring-boot:run
```

También se puede ejecutar la clase principal desde el IDE:

[TpSpringBootApplication.java](./src/main/java/com/example/TpSpringBoot/TpSpringBootApplication.java)

La aplicación utiliza el puerto `8080` por defecto.

## Cómo ejecutar las pruebas

### Windows

```powershell
.\mvnw.cmd test
```

### Linux o macOS

```bash
./mvnw test
```

La prueba disponible verifica que el contexto de Spring Boot pueda iniciar correctamente:

[TpSpringBootApplicationTests.java](./src/test/java/com/example/TpSpringBoot/TpSpringBootApplicationTests.java)

## Estructura del proyecto

```text
TpSpringBoot/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── image.png
├── image-1.png
├── NotasYApuntes.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/example/TpSpringBoot/
    │   │       ├── TpSpringBootApplication.java
    │   │       ├── config/
    │   │       │   └── CargaDeDatosInicial.java
    │   │       └── dto/
    │   │           ├── categoria/
    │   │           ├── pedido/
    │   │           ├── producto/
    │   │           └── usuario/
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/
            └── com/example/TpSpringBoot/
                └── TpSpringBootApplicationTests.java
```

Las clases Java deben permanecer dentro de `src/main/java`. Maven compila automáticamente esa carpeta y Spring Boot busca allí los componentes incluidos dentro del paquete base de la aplicación.

## Clase principal y arranque

La clase [TpSpringBootApplication.java](./src/main/java/com/example/TpSpringBoot/TpSpringBootApplication.java) contiene el método `main`:

```java
public static void main(String[] args) {
    SpringApplication.run(TpSpringBootApplication.class, args);
}
```

La anotación `@SpringBootApplication` combina:

- `@Configuration`: permite declarar configuración y Beans.
- `@EnableAutoConfiguration`: activa configuraciones automáticas según las dependencias.
- `@ComponentScan`: busca componentes en el paquete de la aplicación y sus subpaquetes.

Al ejecutar `SpringApplication.run`, Spring crea el `ApplicationContext`, configura los componentes detectados y levanta el servidor embebido proporcionado por Spring Boot.

## DTO utilizados

Los DTO se implementaron como `record`, una característica de Java que permite definir objetos inmutables destinados principalmente a transportar datos.

Cada entidad independiente tiene tres DTO:

| Entidad | Consulta | Creación | Edición |
|---|---|---|---|
| Usuario | `UsuarioDto` | `UsuarioCreate` | `UsuarioEdit` |
| Categoria | `CategoriaDto` | `CategoriaCreate` | `CategoriaEdit` |
| Producto | `ProductoDto` | `ProductoCreate` | `ProductoEdit` |
| Pedido | `PedidoDto` | `PedidoCreate` | `PedidoEdit` |

Los DTO están organizados en:

- [dto/usuario](./src/main/java/com/example/TpSpringBoot/dto/usuario)
- [dto/categoria](./src/main/java/com/example/TpSpringBoot/dto/categoria)
- [dto/producto](./src/main/java/com/example/TpSpringBoot/dto/producto)
- [dto/pedido](./src/main/java/com/example/TpSpringBoot/dto/pedido)

### Función de cada tipo

#### `Dto`

Representa los datos que se devolverían al consultar una entidad. Incluye información como `id`, `eliminado` y `createdAt`, además de sus datos propios.

#### `Create`

Representa los datos necesarios para crear un registro. No incluye el `id` ni campos que normalmente son generados por el sistema.

#### `Edit`

Representa los datos necesarios para editar un registro existente. Incluye el `id` y los campos modificables.

### Relaciones

Para evitar acoplar un DTO a objetos completos, las relaciones se representan mediante identificadores:

- `ProductoCreate` utiliza `categoriaId`.
- `PedidoCreate` utiliza `usuarioId`.

`DetallePedido` no tiene DTO propio porque es una composición de `Pedido`. En la carga inicial se representa mediante una relación entre el ID del pedido y los IDs de sus productos.

## Carga inicial de DTO

La clase [CargaDeDatosInicial.java](./src/main/java/com/example/TpSpringBoot/config/CargaDeDatosInicial.java) contiene la carga inicial.

```java
@Configuration
public class CargaDeDatosInicial {
```

`@Configuration` indica que la clase contiene configuración de Spring.

Dentro se declara un Bean de tipo `CommandLineRunner`:

```java
@Bean
CommandLineRunner cargarDatosIniciales() {
    return args -> {
        // carga inicial
    };
}
```

`CommandLineRunner` ejecuta el código después de que Spring Boot termina de iniciar el contexto. Esto permite instanciar los DTO automáticamente al iniciar la aplicación.

La carga crea:

- 2 usuarios.
- 3 categorías.
- 10 productos.
- 3 pedidos.
- Al menos 2 productos asociados a cada pedido como representación de sus detalles.

La carga utiliza directamente los constructores generados por los `record`, por ejemplo:

```java
new UsuarioCreate(
    "Ana",
    "Gomez",
    "ana@mail.com",
    "111111111",
    "1234",
    "USUARIO"
);
```

### Alcance actual de la carga

En esta etapa, los DTO se instancian y se mantienen en memoria para cumplir con el ejercicio de creación de datos. Todavía no se convierten en entidades JPA ni se guardan mediante repositorios en tablas de H2.

Para persistirlos en una siguiente etapa se necesitarían:

1. Entidades con `@Entity`.
2. Interfaces repositorio, normalmente extendiendo `JpaRepository`.
3. Servicios con `@Service`.
4. Conversión de DTO a entidad.
5. Inyección de los repositorios en un servicio o en el componente de carga.

## Configuración de H2

La configuración se encuentra en [application.properties](./src/main/resources/application.properties):

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### Propiedades principales

- `spring.h2.console.enabled=true`: habilita la consola web de H2.
- `spring.h2.console.path=/h2-console`: define la ruta de la consola.
- `jdbc:h2:mem:testdb`: crea una base de datos llamada `testdb` en memoria.
- `DB_CLOSE_DELAY=-1`: mantiene la base disponible durante la ejecución de la aplicación.
- `DB_CLOSE_ON_EXIT=FALSE`: evita cerrar la base al finalizar una conexión.
- `spring.datasource.username=sa`: usuario predeterminado de H2.
- `spring.datasource.password=`: contraseña vacía para el entorno práctico.
- `spring.jpa.hibernate.ddl-auto=update`: permite que Hibernate actualice el esquema a partir de las entidades JPA.
- `spring.jpa.show-sql=false`: evita imprimir todas las consultas SQL.

### Acceso a la consola H2

Con la aplicación ejecutándose, abrir:

```text
http://localhost:8080/h2-console
```

Utilizar estos valores:

```text
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password:
```

H2 es una base de datos en memoria. Por eso, los datos se pierden cuando se detiene la aplicación, salvo que se configure una URL de almacenamiento persistente en disco.

## Conceptos de Spring Boot aplicados

### ApplicationContext

Es el contenedor de Spring que administra la configuración, los Beans y sus dependencias. Se crea al ejecutar `SpringApplication.run` en la clase principal.

### Beans

Son objetos administrados por Spring. En este proyecto:

- La clase de configuración es detectada por Spring.
- El método `cargarDatosIniciales` produce un Bean `CommandLineRunner`.
- Spring administra la ejecución de ese Bean durante el arranque.

Los DTO no son Beans porque solamente transportan datos y se instancian explícitamente con `new`.

### Inyección de dependencias

La inyección de dependencias permite que Spring entregue automáticamente los objetos que una clase necesita, preferentemente mediante el constructor.

La clase de carga actual no necesita recibir un repositorio o servicio, porque todavía solo instancia DTO en memoria. Cuando se agregue persistencia, se podrá inyectar un servicio en el método `@Bean` o mediante constructor.

### Estereotipos

Los estereotipos identifican el rol de una clase:

- `@Component`: componente general administrado por Spring.
- `@Service`: lógica de negocio.
- `@Repository`: acceso a datos.
- `@Controller` o `@RestController`: recepción de peticiones HTTP.
- `@Configuration`: clase que define configuración y Beans.

En el estado actual se utiliza `@Configuration` en [CargaDeDatosInicial.java](./src/main/java/com/example/TpSpringBoot/config/CargaDeDatosInicial.java). Todavía no se agregaron `@Service`, `@Repository` ni `@RestController` porque esas capas aún no forman parte de la implementación.

### Auto-configuración

Spring Boot analiza las dependencias del `pom.xml` y configura automáticamente componentes como Spring MVC, JPA, Hibernate, H2 y el servidor embebido.

### Starters

Los starters agrupan dependencias relacionadas:

- `spring-boot-starter-data-jpa`: JPA, Hibernate y soporte de acceso a datos.
- `spring-boot-starter-webmvc`: Spring MVC y servidor web.
- `spring-boot-h2console`: consola web para H2.
- `spring-boot-devtools`: herramientas de desarrollo.

## Documentación de estudio

Las notas ampliadas se encuentran en [NotasYApuntes.md](./NotasYApuntes.md). Allí se explican Spring Boot, `ApplicationContext`, Beans, inyección de dependencias, estereotipos, records y H2.

Las imágenes utilizadas por las notas se guardan en la raíz del proyecto:

- [image.png](./image.png)
- [image-1.png](./image-1.png)

## Estado del proyecto

Actualmente el proyecto:

- Inicia correctamente el contexto de Spring Boot.
- Configura H2 en memoria.
- Habilita la consola H2.
- Instancia los DTO solicitados al arrancar.
- Valida que cada pedido tenga al menos dos productos asociados.
- Incluye una prueba de carga del contexto.

Próximos pasos posibles:

- Crear las entidades JPA.
- Crear los repositorios.
- Implementar servicios.
- Persistir la carga inicial en H2.
- Crear controladores REST.
- Agregar validaciones de entrada.
- Implementar formalmente la entidad de composición `DetallePedido`.

## Licencia

Proyecto académico desarrollado con fines educativos.
