
# URL BASE

> URL ----  /localhost:PORT/forohub/

> PORT ----  por defecto 8080


# SUMARIO

- [URL BASE](#url-base)
    - [http//:localhost:3008/api/peliculas](#httplocalhost3008apipeliculas)
    - [http//:localhost:3008/api/peliculas/vistas](#httplocalhost3008apipeliculasvistas)
    - [http//:localhost:3008/api/peliculas/poster](#httplocalhost3008apipeliculasposter)
- [SUMARIO](#sumario)
  - [Introduccion](#introduccion)
  - [Instalacion](#instalacion)
  - [EndPoints](#endpoints)
    - [catalogo](#catalogo)
    - [vistas](#vistas)
    - [EJS](#ejs)
  - [Codigos de PUT y POST](#codigos-de-put-y-post)
  - [Variables de Entorno (.Env)](#variables-de-entorno-env)
  - [Configuracion Acceso a MySQL](#configuracion-acceso-a-mysql)
  - [Asociaciones de SEQUELIZE](#asociaciones-de-sequelize)
  - [Plantillas EJS y carpeta de Posters](#plantillas-ejs-y-carpeta-de-posters)


## Introduccion



## Instalacion

*  Crear la base de datos en MySQL u otro workbench de SQL
   
 > puede realizarse en la terminal ingresando

 > mysql -u root -p
 
 > create database ***forohub*** 

* Setear las variables de usuario / contrasenia de la base de datos MySQL en 
> application.properties
* al lanzar el servidor se ejecutan las migraciones y se crea un usuario admin 
  para poder hacer el login inicial. La clave **admin** se migra encriptada 
  con Bcrypt hash

> V5__insert-table-autores.sql

> INSERT INTO autores (id, nombre, correo_electronico, contrasenia)
  VALUES (1, 'admin', 'admin@gmail.com', '2a\$12\$ReXJJVNNAjRha1.JNU.2B.0TfXQfG7qBPDzK52jm0B5h40HttyYOy');

* postear en la ruta de LOGIN con el usuario y obtener el JWToken para agregar a las web service de INSOMNIA o POSTMAN 
  
 ```json
 {
	"nombre" : "admin",
	"correoElectronico" : "admin@gmail.com",
	"contrasenia" : "admin"
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
  "contrasenia" : "admin"
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

