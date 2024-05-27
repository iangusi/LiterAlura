# Challenge LiterAlura

### Desarrollado por Ian Gutiérrez
### *Estatus: Finalizado* 

<h2> ¿Qué hace el proyecto? </h2>

+ Muestra al usuario un menu con la siguiente información:

> 1- Búsqueda de libro por título                   <br/>
  2- Lista de todos los libros                      <br/>
  3- Lista de autores                               <br/>
  4- Listar autores vivos en determinado año"       <br/>
  5- Listar libros en un determinado idioma         <br/>
  6- Mostrar estadisticas de descarga de libros     <br/>
  7- Lista de los 10 libros mas descargados         <br/>
  8- Búsqueda de autor por nombre                   <br/>

*Para el primer caso: se realiza una consulta a la API Gutendex que porporciona un catálogo de más de 70.000 libros.
Una vez que se haya hacho la busqueda, si es que esta no se encuentra registrada en la base de datos realizada en PostgreSQL,
se agrega el nuevo libro y tambien información sobre el autor.

Libro{            <br/>
  titulo: ""      <br/>
  descargas: #    <br/>
  idioma: ""      <br/>
  autores: []      <br/>
}

Autor{                    <br/>
  nombre: ""              <br/>
  nacimiento: #año        <br/>
  fallecimiento: #año     <br/>
  libros: []              <br/>
}

*Para todos los siguientes casos, las consultas se realizan a través de *derived queries* utilizando unicamente los registros existentes.
