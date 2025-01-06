
# URL BASE

> URL ----  /localhost:PORT/forohub/

> PORT ----  por defecto 8080


## Introduccion

Es un Foro de topicos y respuestas . Los usuarios pueden responder hasta 2 veces por topico , siempre que este se encuentre ACTIVO. 
Las acciones de DELETE y PUT de un Topico solamente pueden realizarse con usuarios que han realizado el LOGIN con el **Rol** de **ADMIN**

*Challenge ONE Alura Oracle G7*

Profesores :

- Génesys Rondón

- Diego Arguelles Rojas

- Eric Monné Fraga de Oliveira

- Bruno Darío Fernández Ellerbach

- Luri, la IA de Alura

## Instalacion

*  Crear la base de datos en MySQL u otro workbench de SQL
   
 > puede realizarse en la terminal ingresando

 > mysql -u root -p
 
 > create database ***forohub*** 

* Setear las variables de usuario / contrasenia de la base de datos MySQL en 
> application.properties
* al lanzar el servidor se ejecutan las migraciones y se crea un usuario admin 
  para poder hacer el login inicial. La contrasenia **admin** se migra encriptada 
  con Bcrypt hash

> V5__insert-table-autores.sql

> INSERT INTO autores (id, nombre, correo_electronico, contrasenia)
  VALUES (1, 'admin', 'admin@gmail.com', '2a\$12\$ReXJJVNNAjRha1.JNU.2B.0TfXQfG7qBPDzK52jm0B5h40HttyYOy');

* postear en la ruta de LOGIN con el usuario y obtener el JWToken para agregar a las web service de INSOMNIA o POSTMAN 
  > - El campo Rol solamente acepta valores ADMIN y USER
  > - El rol de ADMIN es el unico que puede realizar DELETE o PUT de un Topico (se utilizaron GrantedAuthority y @PreAuthorize("hasRole('ADMIN')") emn estos endpoints)
  > - Recordar que el usuario admin@gmail.com ya se crea al lanzarse el proyecto (hay un test de Junit que lo comprueba)


 ```json
{
  "nombre" : "admin",
  "correoElectronico" : "admin@gmail.com",
  "contrasenia" : "admin",
  "rol" : "ADMIN"
}
 ```
* agregar el JWToken en las variables de entorno de INSOMNIA o POSTMAN para autorizar cada ruta a probar


## EndPoints

### catalogo topicos y respuestas y autores

|PETICION |URL |DESCRIPCION|
--- | --- | ---|
|POST|http://localhost:8080/forohub/login|ruta para obtener el token con el usuario administrador |
|POST|http://localhost:8080/forohub/topicos|ruta para agregar un topico nuevo. La respuesta se agregan en otra ruta|
|POST|http://localhost:8080/forohub/respuestas/:idTopico|ruta para postear una nueva respuesta a un topico por el ID|
|GET|http://localhost:8080/forohub/topicos/:id|ruta para listar un topico buscado por su ID |
|GET|http://localhost:8080/forohub/topicos/topicos+respuestas|ruta para listar todos los topicos y sus respuestas |
|GET|http://localhost:8080/forohub/topicos/paginacion|ruta para listar los topicos en formato paginado|
|GET|http://localhost:8080/forohub/topicos/primeros10topicos|ruta para listar los primeros diez topicos|
|PUT|http://localhost:8080/forohub/topicos/:id|actualizar campos de un topico buscado por su ID|
|DELETE|http://localhost:8080/forohub/topicos/:id|borrar un topico buscado por su ID|

### swagger

|PETICION | URL                                            | DESCRIPCION                                     |
--- |------------------------------------------------|-------------------------------------------------|
|GET| http://localhost:8080/forohub/swagger-ui.html/ | ruta para visualizar la Swagger UI de SpringDoc |


formato JSON para LOGIN de usuario :

```json
{
  "nombre" : "admin",
  "correoElectronico" : "admin@gmail.com",
  "contrasenia" : "admin",
  "rol" : "USER"
}
```
formato JSON para POST de topicos :

- nota : las contrasenias se encriptan al persistir en la base de datos
- los valores posibles para el campo **"status"** son para ACTIVO --> "activo","act","true","ok","si" y para INACTIVO --> "inactivo","inact","false","no"
- el campo **"id"** es un Int para codificar internamente, ya que MySQL asigna dinamicamnete el ID

```json
{
  "id" : 0 ,
  "titulo" : "titulo topico " ,
  "mensaje" : "mensaje topico " ,
  "fechaCreacion" : "12/12/24" ,
  "status" : "ACTIVO" ,
  "autor" : {
    "id" : 0,
    "nombre" : "usuario topico",
    "correoElectronico" : "usuarioTopico@gmail.com",
    "contrasenia" : "contrasenia"
  } ,
  "curso" : {
    "id" : 0,
    "nombre" : "matematicas",
    "categoria" : "principiante"
  }
}
```

formato JSON para POST de respuestas a los topicos :

```json
{
  "id" : 0,
  "topico" : "titulo topico si se recuerda",
  "fechaDeCreacion" : "12/12/24",
  "autor" : {
    "id" : 0,
    "nombre" : "usuario respuesta",
    "correoElectronico" : "usuarioRespuesta@gmail.com",
    "contrasenia" : "contrasenia"
  } ,
  "solucion" : "solucion topico"
} 
```

formato JSON para PUT de actualizacion de topicos :

```json
{
  "id" : 0,
  "titulo" : "topico modificado " ,
  "mensaje" : "mensaje modificado" ,
  "fechaCreacion" : "22/12/24" ,
  "status" : "OK"
}
```

## Test Automatizados

- se enumeran dos tests. Los mismos utilizan la misma base de datos del proyecto :
 

## Dependencias instaladas

```XML
	<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
  </dependency>
  <dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>
  <dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>4.2.0</version>
  </dependency>
  <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
  </dependency>
  <dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.20.2</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## Tests

```Java
class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Test
    @DisplayName("comprueba que se creo el usuario admin@gmail.com que se utiliza para obtener el Token en la ruta de LOGIN")
    public void testVerSiSeCreaElUsuarioAdminParaLosLogin(){

        Usuario admin= (Usuario) usuarioRepository.findUsuarioByCorreoElectronico("admin@gmail.com");
        assertNotNull(admin,"el administrador admin@gmail.com tiene que existir");

    }
}
```

```Java
public class TopicoRepositoryTest {
    @Autowired
    TopicoRepository topicoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CursoRepository cursoRepository;


    @Test
    @DisplayName("crea un  nuevo topico completo y luego hace la busqueda del mismo")
    public void testSalvarYBuscarUnTopicoDePrueba(){

        DatosRegistroUsuario usuarioTest=new DatosRegistroUsuario(0,"usuario Test","test@gmail.com","testcontasenia");
        Usuario usuarioTestEntity=new Usuario(usuarioTest);
        DatosRegistroCurso cursoTest=new DatosRegistroCurso(0,"curso Test","categoria Test");
        Curso cursoTestEntity=new Curso(cursoTest);
        DatosRegistroTopico topicoTest = new DatosRegistroTopico(0,"topico test","mensaje test","22/22/22","ACTIVO",usuarioTest,cursoTest);
        Topico topico=new Topico(topicoTest);

        usuarioTestEntity.addTopico(topico);
        topico.setAutor(usuarioTestEntity);

        //var psw=usuarioTestEntity.getContrasenia();
        //psw=passwordEncoder.encode(psw);
        //usuarioTestEntity.setContrasenia();

        usuarioRepository.save(usuarioTestEntity);
        topicoRepository.save(topico);

        cursoTestEntity.addTopico(topico);
        topico.setCurso(cursoTestEntity);
        cursoRepository.save(cursoTestEntity);
        Topico topicoSalvado=topicoRepository.save(topico);

        assertThat(topicoSalvado).isNotNull();
        assertThat(topicoSalvado.getId()).isNotNull();

        Topico topicoBuscar=topicoRepository.findById(topicoSalvado.getId())
                .orElseThrow(()->new EntityNotFoundException());

        assertThat(topicoBuscar).isNotNull();
        assertThat(topicoBuscar.getTitulo()).isEqualTo("topico test");
        assertThat(topicoBuscar.getMensaje()).isEqualTo("mensaje test");
    }
}

```