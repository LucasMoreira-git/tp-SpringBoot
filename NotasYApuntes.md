# Notas y apuntes de Spring Boot

Este resumen reúne los conceptos de los seis temas de la carpeta `Teoria` y los relaciona con el proyecto práctico `TpSpringBoot`. La idea no es memorizar anotaciones aisladas, sino entender qué problema resuelve cada mecanismo y cómo se conectan entre sí.

## 1. ¿Qué es Spring?

Spring es un framework para construir aplicaciones Java organizadas, extensibles y fáciles de probar. Su idea central es que las clases no tengan que crear y administrar por sí mismas todos los objetos que necesitan. En lugar de eso, Spring administra esos objetos y los conecta.

### Una analogía: una fábrica

Imaginemos una fábrica:

- Cada clase es una máquina que realiza una tarea.
- Las dependencias son los materiales o herramientas que la máquina necesita.
- El contenedor de Spring es el encargado de fabricar las máquinas, entregarles sus materiales y coordinar el funcionamiento.
- La aplicación contiene las reglas del negocio; Spring se ocupa de la infraestructura.

Sin Spring, una clase podría crear directamente sus dependencias:

```java
PedidoService service = new PedidoService(new PedidoRepository());
```

Esto ata `PedidoService` a una implementación concreta. Con Spring, la clase declara lo que necesita y el contenedor se lo entrega:

```java
public PedidoService(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
}
```

La clase conoce una abstracción o un colaborador, pero no se encarga de construirlo. Esto se llama **inversión de control**: el control de creación y conexión de objetos pasa de las clases al framework.

### Problemas que Spring ayuda a resolver

- **Acoplamiento excesivo:** una clase no necesita conocer cómo se construyen sus colaboradores.
- **Configuración dispersa:** los componentes se registran en un contenedor común.
- **Dificultad para probar:** se pueden reemplazar dependencias por implementaciones de prueba.
- **Código repetido de infraestructura:** transacciones, acceso a datos, web y configuración se integran mediante módulos.

Spring no reemplaza Java ni contiene la lógica del negocio. Proporciona una forma organizada de ensamblar la aplicación.

## 2. Spring Framework y Spring Boot

Spring Framework es el conjunto de módulos y conceptos fundamentales: el contenedor IoC, Beans, inyección de dependencias, configuración, Spring MVC, acceso a datos y otros proyectos relacionados.

Spring Boot se construye sobre Spring Framework y agrega una experiencia de arranque más rápida. No es otro framework completamente separado: es una manera opinionada de preparar y ejecutar una aplicación Spring.

### Spring tradicional frente a Spring Boot

En Spring tradicional era habitual tener que:

- elegir y compatibilizar manualmente muchas versiones;
- declarar explícitamente gran cantidad de configuraciones;
- configurar el servidor web;
- registrar componentes y recursos;
- preparar el empaquetado y el despliegue.

Spring Boot mantiene la posibilidad de personalizar todo, pero ofrece valores predeterminados razonables. Su objetivo es que la aplicación pueda empezar a funcionar con poca configuración y que solo sea necesario sobrescribir lo que realmente cambia.

| Spring Framework | Spring Boot |
|---|---|
| Proporciona el contenedor y los módulos principales. | Usa Spring y simplifica su puesta en marcha. |
| Puede requerir más configuración explícita. | Favorece convenciones y auto-configuración. |
| La selección de dependencias suele ser manual. | Ofrece starters y un control de versiones centralizado. |
| El servidor y el empaquetado pueden configurarse aparte. | Puede incluir un servidor embebido y ejecutarse como aplicación Java. |

### Las tres ideas principales de Spring Boot

1. **Starters:** conjuntos de dependencias preparados para una capacidad, como web o JPA.
2. **Auto-configuración:** Spring Boot analiza las dependencias, las clases disponibles y la configuración para registrar componentes habituales automáticamente.
3. **Servidor embebido:** una aplicación web puede incluir el servidor y ejecutarse con `main`, sin instalar un servidor externo.

La auto-configuración no impide configurar manualmente. Es un punto de partida que se aplica cuando las condiciones se cumplen y permite reemplazar o ajustar los valores predeterminados.

### JPA puro y Spring Boot

Con JPA sin la integración de Spring normalmente habría que configurar explícitamente el `EntityManagerFactory`, `persistence.xml`, el proveedor JPA, el dialecto, el `DataSource` y las transacciones.

Al agregar `spring-boot-starter-data-jpa`, Spring Boot detecta la infraestructura disponible y prepara gran parte de esa integración. Esto no significa que se creen automáticamente las entidades del negocio: todavía hay que definir entidades, relaciones, repositorios y reglas de persistencia.

## 3. Crear el primer proyecto con Spring Boot

La forma habitual de iniciar un proyecto es utilizar **Spring Initializr**, desde `start.spring.io` o desde el IDE. Allí se seleccionan:

- sistema de construcción: Maven o Gradle;
- lenguaje: Java;
- versión de Spring Boot;
- grupo y artefacto;
- versión de Java;
- dependencias iniciales.

El resultado es un proyecto base con una estructura conocida y un archivo de construcción. En este proyecto se utiliza Maven, por eso el archivo central es [`pom.xml`](./pom.xml).

### Qué representa `pom.xml`

El `pom.xml` es el catálogo del proyecto para Maven. Allí se declara:

- la versión de Java;
- el identificador de la aplicación;
- el parent de Spring Boot;
- las dependencias;
- los plugins y sus fases de compilación.

El parent de Spring Boot administra versiones compatibles de muchas bibliotecas. Así se evita indicar manualmente cada versión y se reduce el riesgo de incompatibilidades.

En el proyecto se utilizan, entre otras, estas dependencias:

- `spring-boot-starter-webmvc`: aplicación web y MVC;
- `spring-boot-starter-data-jpa`: persistencia con JPA;
- `spring-boot-h2console` y `h2`: consola y base H2;
- `spring-boot-devtools`: herramientas de desarrollo;
- starters de prueba para validar el contexto y la capa web.

### Maven Wrapper

Los archivos `mvnw` y `mvnw.cmd` permiten ejecutar Maven sin exigir que cada integrante instale una versión global. En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Para ejecutar las pruebas:

```powershell
.\mvnw.cmd test
```

### Estructura estándar

```text
TpSpringBoot/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       └── java/
└── NotasYApuntes.md
```

- `src/main/java`: código de la aplicación.
- `src/main/resources`: configuración, plantillas y recursos estáticos.
- `src/test/java`: pruebas.
- `target`: resultado generado por Maven; no es código fuente.

En este caso, la clase principal está en el paquete `com.example.TpSpringBoot`. Es importante ubicarla en un paquete raíz, porque el escaneo de componentes parte normalmente desde allí.

## 4. Arranque de una aplicación Spring Boot

La clase principal del proyecto es [`TpSpringBootApplication.java`](./src/main/java/com/example/TpSpringBoot/TpSpringBootApplication.java):

```java
@SpringBootApplication
public class TpSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpSpringBootApplication.class, args);
    }
}
```

`SpringApplication.run` inicia el proceso principal:

1. crea y configura el entorno;
2. crea el `ApplicationContext`;
3. descubre los componentes;
4. crea los Beans y resuelve sus dependencias;
5. aplica la auto-configuración;
6. inicia el servidor embebido si la aplicación es web;
7. ejecuta las tareas de arranque, como `CommandLineRunner`.

### `@SpringBootApplication`

Esta anotación es un atajo que combina:

```java
@Configuration
@EnableAutoConfiguration
@ComponentScan
```

- `@Configuration`: indica que la clase puede declarar Beans y configuración.
- `@EnableAutoConfiguration`: habilita la configuración automática de Spring Boot.
- `@ComponentScan`: busca clases con estereotipos en el paquete de la aplicación y sus subpaquetes.

### Otra analogía: preparar un restaurante

Arrancar Spring Boot se parece a abrir un restaurante:

- `@ComponentScan` localiza las personas y áreas que forman parte del equipo.
- `@Bean` registra recursos preparados explícitamente.
- la auto-configuración prepara equipamiento estándar según el tipo de restaurante;
- el `ApplicationContext` coordina todo;
- el servidor embebido abre la atención al público.

## 5. `ApplicationContext` y Beans

El `ApplicationContext` es el contenedor que mantiene el mapa de objetos administrados por Spring. Es responsable de crearlos, configurarlos, conectarlos y mantener su ciclo de vida.

Un **Bean** es un objeto cuya creación y administración están a cargo de Spring. Puede registrarse de varias formas:

### Detección por estereotipo

```java
@Service
public class ProductoService {
}
```

Spring encuentra la clase durante el component scan y la registra.

### Declaración explícita con `@Bean`

```java
@Configuration
public class CargaDeDatosInicial {

    @Bean
    CommandLineRunner cargarDatosIniciales() {
        return args -> {
            // instrucciones de inicio
        };
    }
}
```

En este proyecto, [`CargaDeDatosInicial.java`](./src/main/java/com/example/TpSpringBoot/config/CargaDeDatosInicial.java) declara un Bean `CommandLineRunner`.

### Ciclo de vida simplificado

1. Spring encuentra o registra la definición del Bean.
2. Construye el objeto.
3. Resuelve e inyecta sus dependencias.
4. Ejecuta la inicialización correspondiente.
5. El Bean queda disponible para la aplicación.
6. Al cerrar el contexto, Spring ejecuta la destrucción configurada.

### Alcance de un Bean

Por defecto, el alcance es `singleton`: el contexto mantiene una instancia principal de ese Bean. Otros alcances comunes son `prototype`, `request` y `session`, especialmente en aplicaciones web.

El hecho de que un Bean sea singleton no significa que todo objeto de Java lo sea. Los DTO del proyecto, por ejemplo, son datos creados con `new` dentro de la carga inicial y no necesitan ser administrados por Spring.

### Diferencia entre Bean y objeto común

Todo Bean es un objeto Java, pero no todo objeto Java es un Bean. Un objeto común puede crearse directamente:

```java
UsuarioCreate usuario = new UsuarioCreate(
        "Ana",
        "Gomez",
        "ana@mail.com",
        "111111111",
        "1234",
        "USUARIO");
```

El `ApplicationContext` administra servicios, configuraciones, repositorios y componentes de infraestructura. Un DTO inmutable que solo transporta datos no necesita formar parte del contenedor.

## 6. Inyección de dependencias

La inyección de dependencias consiste en entregar a una clase sus colaboradores desde afuera, en vez de construirlos dentro de ella.

### Qué problema evita

Este diseño es difícil de mantener:

```java
public class PedidoService {
    private final PedidoRepository repository = new PedidoRepository();
}
```

La clase queda atada a una implementación concreta y es más difícil reemplazarla en una prueba. La alternativa es:

```java
@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
}
```

Spring detecta `PedidoService`, observa el constructor y busca un Bean compatible para pasarlo como argumento.

### Analogía: pedir un taxi

Una clase que usa inyección de dependencias no fabrica un taxi ni conoce su motor. Solo indica que necesita transporte. Una central de coordinación elige y entrega el taxi disponible. En una prueba, la central puede entregar un taxi simulado.

### Tipos de inyección

1. **Por constructor:** recomendada. Hace visibles las dependencias, permite usar campos `final` y facilita crear la clase en una prueba.
2. **Por setter:** útil cuando la dependencia es opcional o puede cambiar, pero permite que el objeto quede parcialmente configurado.
3. **Por campo:** requiere reflexión, oculta las dependencias y dificulta las pruebas; por eso no suele ser la opción preferida.

### Reglas prácticas

- Preferir un único constructor con todas las dependencias obligatorias.
- Depender de interfaces cuando existan varias implementaciones.
- Evitar que los servicios creen repositorios o servicios con `new`.
- Mantener cada componente con una responsabilidad clara.
- Si existen dos Beans candidatos, resolver la ambigüedad con `@Primary` o `@Qualifier`.

## 7. Estereotipos de Spring

Los estereotipos son anotaciones que expresan el rol de una clase y, en la mayoría de los casos, hacen que sea detectada como componente.

- `@Component`: componente general administrado por Spring.
- `@Service`: componente de lógica de negocio.
- `@Repository`: acceso a datos; además permite traducir ciertos errores de persistencia.
- `@Controller`: controlador MVC que normalmente devuelve vistas.
- `@RestController`: combina `@Controller` y `@ResponseBody`; normalmente devuelve datos como JSON.
- `@Configuration`: clase de configuración que puede declarar métodos `@Bean`.

La elección del estereotipo no reemplaza la separación por capas:

```text
Controlador -> Servicio -> Repositorio -> Base de datos
```

Una analogía útil es una empresa:

- el controlador recibe pedidos de los clientes;
- el servicio decide las reglas del negocio;
- el repositorio consulta el archivo o la base de datos;
- la entidad representa información persistente.

En la implementación actual, `CargaDeDatosInicial` usa `@Configuration` porque su función es registrar el `CommandLineRunner`, no actuar como servicio de negocio.

## 8. DTO, `record` y separación de capas

Un **DTO** (`Data Transfer Object`) es un objeto destinado a transportar datos entre capas o hacia/desde un cliente. No debe convertirse en una copia de toda la lógica de negocio.

### Por qué no exponer directamente entidades

Separar DTO y entidad permite:

- decidir qué campos se muestran;
- no exponer información sensible;
- evitar que el cliente modifique campos internos;
- desacoplar la API de la estructura de la base;
- controlar mejor las relaciones y el formato de respuesta.

### `record`

En el proyecto, los DTO se definen como `record`:

```java
public record CategoriaCreate(
        String nombre,
        String descripcion) {
}
```

Un `record` genera automáticamente constructor, accesores, `equals`, `hashCode` y `toString`. Sus componentes son finales, por lo que resulta apropiado para datos que no deberían modificarse después de crearse.

### Tipos usados en el proyecto

Cada entidad independiente tiene tres representaciones:

| Tipo | Propósito |
|---|---|
| `Dto` | Respuesta completa o representación de consulta. |
| `Create` | Datos mínimos necesarios para crear un registro. |
| `Edit` | Identificador y campos que pueden modificarse. |

Se aplica a `Usuario`, `Categoria`, `Producto` y `Pedido`. `DetallePedido` no tiene DTO propio porque en este trabajo se representa como una composición del pedido mediante los identificadores de productos.

Las relaciones se expresan con IDs para no transportar objetos completos innecesariamente:

- `ProductoCreate` usa `categoriaId`.
- `PedidoCreate` usa `usuarioId`.

Un DTO es como un formulario de envío: contiene los datos necesarios para una operación concreta, no todos los archivos internos de la empresa.

## 9. `CommandLineRunner` y carga inicial

`CommandLineRunner` permite ejecutar una función una vez creado el contexto y finalizado el arranque principal:

```java
@Bean
CommandLineRunner cargarDatosIniciales() {
    return args -> {
        // datos iniciales
    };
}
```

Es útil para:

- cargar datos de ejemplo;
- preparar catálogos;
- comprobar una integración al iniciar;
- ejecutar una tarea puntual de arranque.

En este proyecto la clase [`CargaDeDatosInicial.java`](./src/main/java/com/example/TpSpringBoot/config/CargaDeDatosInicial.java) crea en memoria:

- 2 usuarios;
- 3 categorías;
- 10 productos;
- 3 pedidos;
- al menos 2 productos asociados a cada pedido.

La carga valida que cada pedido tenga dos o más detalles y luego informa cantidades por consola. Actualmente se construyen DTO, pero no se persisten en H2. Para guardarlos realmente harían falta entidades JPA, repositorios, servicios y operaciones de persistencia.

## 10. Configuración en Spring Boot

La configuración permite cambiar el comportamiento de la aplicación sin modificar el código. En este proyecto se encuentra en [`application.properties`](./src/main/resources/application.properties).

```properties
spring.application.name=TpSpringBoot
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### Qué significa cada propiedad

- `spring.application.name`: nombre identificatorio de la aplicación.
- `spring.h2.console.enabled`: habilita la consola web de H2.
- `spring.h2.console.path`: define la ruta de la consola.
- `spring.datasource.url`: indica que la base es H2 en memoria y se llama `testdb`.
- `DB_CLOSE_DELAY=-1`: mantiene viva la base mientras la JVM siga ejecutándose.
- `DB_CLOSE_ON_EXIT=FALSE`: evita que H2 intente cerrar la base por su cuenta al salir.
- `spring.datasource.username` y `password`: credenciales de conexión.
- `spring.jpa.hibernate.ddl-auto=update`: permite que Hibernate actualice el esquema según las entidades.
- `spring.jpa.show-sql=false`: evita imprimir las consultas SQL en la salida normal.

### Properties, YAML y variables de entorno

Spring Boot también permite usar `application.yml` o variables de entorno. Por ejemplo, la propiedad:

```properties
server.port=9090
```

puede reemplazarse en Windows con:

```powershell
$env:SERVER_PORT=9090
```

Las variables externas son útiles para no guardar credenciales o diferencias entre desarrollo, pruebas y producción dentro del código.

### Perfiles

Los perfiles permiten tener configuraciones según el entorno:

```text
application.properties
application-dev.properties
application-test.properties
application-prod.properties
```

Se activa uno con una propiedad o variable de entorno, por ejemplo:

```properties
spring.profiles.active=dev
```

La configuración específica del perfil complementa o sobrescribe la común. No conviene colocar secretos reales en el repositorio.

### `@Value` y `@ConfigurationProperties`

Para leer una propiedad puntual puede usarse `@Value`:

```java
@Value("${mi.aplicacion.nombre}")
private String nombre;
```

Cuando existe un grupo de propiedades relacionadas, `@ConfigurationProperties` suele ser más claro y tipado:

```properties
tienda.paginacion.tamanio=20
tienda.paginacion.orden=nombre
```

La configuración tipada evita dispersar strings de propiedades por el código.

### Analogía: controles de un auto

El código es el motor y las propiedades son los controles: el mismo motor puede usar otro puerto, otra base o mostrar más logs sin ser reconstruido. El archivo de configuración permite ajustar el entorno; no debería contener la lógica del negocio.

## 11. Auto-configuración

La auto-configuración es el mecanismo mediante el cual Spring Boot registra configuraciones comunes cuando detecta determinadas condiciones.

Por ejemplo, si están disponibles las dependencias web y no se deshabilita la configuración, Spring Boot puede preparar la infraestructura MVC y el servidor embebido. Si están disponibles JPA, un driver y propiedades de datasource, puede preparar la integración de persistencia.

### Cómo decide Spring Boot

La decisión se basa principalmente en:

- clases presentes en el classpath;
- Beans que ya existen;
- propiedades de configuración;
- tipo de aplicación;
- condiciones como `@ConditionalOnClass` y `@ConditionalOnMissingBean`.

Una forma de entenderlo:

```text
¿Está disponible la biblioteca? -> ¿Falta una configuración propia?
-> ¿Se cumplen las propiedades? -> Registrar la configuración predeterminada
```

La condición “si falta un Bean” es importante: si el desarrollador define su propia configuración, Spring Boot suele respetarla en lugar de duplicarla.

### No es magia ni una caja negra

La auto-configuración es código condicional que puede inspeccionarse y reemplazarse. Cuando algo no funciona, conviene revisar:

- dependencias del `pom.xml`;
- propiedades activas;
- Beans registrados;
- perfil utilizado;
- logs de arranque y reporte de condiciones.

También se puede excluir una auto-configuración específica cuando existe una razón concreta, pero no conviene deshabilitarla sin entender qué infraestructura se está reemplazando.

## 12. Starters

Un starter es una dependencia agrupada que expresa una intención. En vez de seleccionar biblioteca web, conversor JSON, servidor y soporte MVC uno por uno, se agrega el starter web correspondiente.

Ejemplos habituales:

- `spring-boot-starter-webmvc`: aplicaciones web y MVC;
- `spring-boot-starter-data-jpa`: JPA, Hibernate y acceso a datos;
- `spring-boot-starter-test`: herramientas de pruebas;
- starters de validación, seguridad, mensajería o monitoreo según la necesidad.

El starter no contiene necesariamente toda la implementación funcional: sirve como una entrada coherente a un conjunto compatible de dependencias. El parent de Spring Boot administra sus versiones.

### Analogía: un combo

Comprar un combo de cocina no significa que el restaurante deje de poder cambiar una pieza. El combo entrega los elementos que normalmente se necesitan juntos, pero cada elemento puede personalizarse si el proyecto lo requiere.

### Riesgo de agregar dependencias sin criterio

Cada dependencia puede activar auto-configuraciones, aumentar el tamaño del proyecto y producir conflictos. Conviene agregar solo los starters necesarios y revisar el árbol de dependencias cuando aparecen incompatibilidades.

## 13. Developer Experience (DX)

DX significa experiencia de desarrollo. Spring Boot busca que el ciclo de escribir, ejecutar, probar y diagnosticar sea simple:

- proyecto inicializable rápidamente;
- dependencias agrupadas y versiones compatibles;
- servidor embebido;
- configuración externa;
- mensajes de arranque;
- Maven Wrapper;
- herramientas como DevTools;
- integración sencilla con pruebas.

En este proyecto, la experiencia se ve en:

```powershell
.\mvnw.cmd spring-boot:run
.\mvnw.cmd test
```

No es necesario instalar un Tomcat externo para iniciar la aplicación. La clase `main` levanta la aplicación y el servidor queda disponible normalmente en el puerto `8080`.

DevTools puede ayudar durante el desarrollo con reinicios y recarga de cambios, pero no debe confundirse con una dependencia necesaria para producción.

Una buena DX no significa ocultar todo: significa reducir trabajo repetitivo sin impedir comprender ni personalizar el funcionamiento.

## 14. Base de datos H2 y consola

H2 es una base de datos relacional liviana, útil para prácticas y pruebas. En este proyecto se usa en memoria:

```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
```

Con la aplicación ejecutándose, la consola se encuentra en:

```text
http://localhost:8080/h2-console
```

Datos de conexión:

```text
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password:
```

Una base en memoria pierde sus datos cuando finaliza la JVM. Es adecuada para aprendizaje, no una elección automática para producción. La propiedad `ddl-auto=update` debe utilizarse con criterio, especialmente fuera de entornos de prueba.

## 15. Flujo completo del proyecto

El funcionamiento general puede resumirse así:

```text
Maven lee pom.xml
        |
        v
SpringApplication.run(...)
        |
        v
Se crea el ApplicationContext
        |
        v
ComponentScan encuentra componentes y Configuration
        |
        v
La auto-configuración prepara web, JPA y H2
        |
        v
Se registran los Beans y se inyectan dependencias
        |
        v
Se ejecuta CommandLineRunner
        |
        v
Se inicia el servidor embebido en el puerto 8080
```

Cuando se agrega una capa web real, el flujo de una solicitud sería:

```text
Cliente HTTP -> Controller/RestController -> Service
             -> Repository -> Base de datos
             <- DTO de respuesta
```

Cada capa tiene una responsabilidad y se comunica con la siguiente mediante contratos claros.

## 16. Ideas clave para recordar

1. Spring administra objetos; Spring Boot simplifica la configuración y el arranque de Spring.
2. El `ApplicationContext` es el contenedor de Beans.
3. La inyección de dependencias evita que las clases construyan sus colaboradores.
4. `@SpringBootApplication` combina configuración, auto-configuración y escaneo.
5. Los starters agrupan dependencias compatibles para una capacidad.
6. La auto-configuración aplica valores predeterminados condicionales, no una configuración inexplicable.
7. Las propiedades permiten adaptar la aplicación al entorno sin cambiar el código.
8. Los DTO transportan datos y ayudan a no exponer directamente el modelo interno.
9. `CommandLineRunner` sirve para tareas de arranque; en este proyecto la carga es en memoria.
10. H2 en memoria es útil para aprender, pero sus datos desaparecen al detener la aplicación.
