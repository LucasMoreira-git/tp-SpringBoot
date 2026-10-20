# Notas y apuntes de Spring Boot

## 1. Spring Framework y Spring Boot

En Spring clásico era necesario realizar gran parte de la configuración manualmente: archivos XML, resolución de versiones, configuración del servidor y creación de objetos. Spring Boot simplifica ese proceso mediante convenciones, auto-configuración y dependencias agrupadas.

Spring Boot se apoya principalmente en:

1. **Starters**: dependencias agrupadas para una funcionalidad.
2. **Auto-configuración**: configuración automática basada en las dependencias disponibles.
3. **Servidor embebido**: permite ejecutar la aplicación sin instalar un servidor externo.

### JPA puro y Spring Boot

Con JPA puro normalmente se deben configurar manualmente el `EntityManagerFactory`, el archivo `persistence.xml`, el dialecto, las credenciales y las transacciones.

Spring Boot automatiza gran parte de esta configuración cuando se agrega `spring-boot-starter-data-jpa`.

## 2. Estructura de un proyecto Spring Boot

La estructura principal del proyecto es:

- `src/main/java`: código fuente Java.
- `src/main/resources`: configuración y recursos de la aplicación.
- `src/test/java`: pruebas automatizadas.
- `pom.xml`: dependencias y configuración de Maven.

En este proyecto, las clases Java se encuentran dentro del paquete base:

```text
src/main/java/com/example/TpSpringBoot/
```

La clase principal es `TpSpringBootApplication.java`.

## 3. `@SpringBootApplication`

La anotación `@SpringBootApplication` combina tres anotaciones:

1. `@Configuration`: permite declarar configuración y Beans.
2. `@EnableAutoConfiguration`: activa la configuración automática de Spring Boot.
3. `@ComponentScan`: busca componentes en el paquete actual y sus subpaquetes.

Al ejecutar:

```java
SpringApplication.run(TpSpringBootApplication.class, args);
```

Spring crea el contexto de la aplicación y comienza el ciclo de vida de los componentes.

## 4. ApplicationContext y Beans

El `ApplicationContext` es el contenedor principal de Spring. Se encarga de crear, organizar y conectar los componentes de la aplicación.

Los Beans son objetos administrados por Spring. Su ciclo de vida simplificado es:

1. Spring detecta el componente.
2. Crea una instancia.
3. Inyecta sus dependencias.
4. Inicializa y utiliza el objeto.
5. Lo destruye cuando finaliza el contexto.

No todos los objetos del proyecto son Beans. Los DTO se instancian con `new` porque son objetos de datos simples:

```java
UsuarioCreate usuario = new UsuarioCreate(
        "Ana",
        "Gomez",
        "ana@mail.com",
        "111111111",
        "1234",
        "USUARIO");
```

## 5. Estereotipos

Los estereotipos indican el rol de una clase:

- `@Component`: componente general.
- `@Service`: lógica de negocio.
- `@Repository`: acceso a datos.
- `@Controller`: controlador web.
- `@RestController`: controlador que responde normalmente con JSON.
- `@Configuration`: clase que declara configuración y Beans.

En el proyecto, `CargaDeDatosInicial` utiliza `@Configuration` porque declara un Bean `CommandLineRunner`.

## 6. Inyección de dependencias

La inyección de dependencias permite que Spring entregue a una clase los objetos que necesita, en lugar de que la clase los cree manualmente.

La forma recomendada es la inyección por constructor:

```java
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
}
```

Esto reduce el acoplamiento y facilita las pruebas.

## 7. DTO y records

Un DTO (`Data Transfer Object`) transporta información entre distintas capas de una aplicación. No debería contener lógica de negocio compleja.

En este proyecto se utilizan `record`:

```java
public record CategoriaCreate(
        String nombre,
        String descripcion) {
}
```

Java genera automáticamente:

- Constructor.
- Métodos de acceso.
- `equals`.
- `hashCode`.
- `toString`.

Los records son apropiados para DTO porque son concisos e inmutables.

Se definieron tres tipos por entidad:

- `Dto`: representación completa de salida.
- `Create`: datos necesarios para crear.
- `Edit`: datos necesarios para editar.

No se creó DTO para `DetallePedido` porque es una composición de `Pedido`.

## 8. Carga inicial con `CommandLineRunner`

La clase [CargaDeDatosInicial.java](./src/main/java/com/example/TpSpringBoot/config/CargaDeDatosInicial.java) utiliza:

```java
@Configuration
public class CargaDeDatosInicial {

    @Bean
    CommandLineRunner cargarDatosIniciales() {
        return args -> {
            // datos iniciales
        };
    }
}
```

`CommandLineRunner` permite ejecutar instrucciones una vez que Spring Boot terminó de iniciar.

La carga crea:

- 2 usuarios.
- 3 categorías.
- 10 productos.
- 3 pedidos.
- 2 o más productos asociados a cada pedido.

En la implementación actual los DTO se instancian en memoria. Para guardarlos en H2 harían falta entidades JPA, repositorios y servicios.

## 9. Base de datos H2

H2 es una base de datos relacional liviana. En este proyecto se usa en memoria:

```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

La consola web se habilita así:

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

La configuración JPA es:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

Con la aplicación ejecutándose, la consola se abre en:

```text
http://localhost:8080/h2-console
```

Datos de conexión:

```text
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password:
```

Como es una base en memoria, la información se elimina cuando se detiene la aplicación.

## 10. Imágenes del material

Las imágenes se referencian mediante rutas relativas al repositorio. Esto permite que se visualicen correctamente en GitHub, GitLab y otros visores Markdown:

### Ejemplo de record en forma clásica

![Ejemplo de record en forma clásica](./image.png)

### Ejemplo de record en forma moderna

![Ejemplo de record en forma moderna](./image-1.png)

No se utiliza `attachment://...` porque ese formato depende del editor donde se adjuntó la imagen y no funciona al subir el archivo al repositorio.
