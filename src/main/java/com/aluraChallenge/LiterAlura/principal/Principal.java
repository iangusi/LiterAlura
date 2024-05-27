package com.aluraChallenge.LiterAlura.principal;

import com.aluraChallenge.LiterAlura.model.Autores;
import com.aluraChallenge.LiterAlura.model.Libro;
import com.aluraChallenge.LiterAlura.model.Libros;
import com.aluraChallenge.LiterAlura.model.Resultados;
import com.aluraChallenge.LiterAlura.repository.AutoresRepository;
import com.aluraChallenge.LiterAlura.repository.LibrosRepository;
import com.aluraChallenge.LiterAlura.service.ConsumoAPI;
import com.aluraChallenge.LiterAlura.service.ConvierteDatos;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibrosRepository repositorioL;
    private AutoresRepository repositorioA;

    public Principal(LibrosRepository repositoryL, AutoresRepository repositoryA)
    {
        this.repositorioL = repositoryL;
        this.repositorioA = repositoryA;
    }

    public void menu(){
        String opc = "";
        while(!opc.equals("0")){
            System.out.println();
            System.out.println("LITERALURA");
            System.out.println("0- Salir");
            System.out.println("1- Búsqueda de libro por título");
            System.out.println("2- Lista de todos los libros");
            System.out.println("3- Lista de autores");
            System.out.println("4- Listar autores vivos en determinado año");
            System.out.println("5- Listar libros en un determinado idioma");
            System.out.println("6- Mostrar estadisticas de descarga de libros");
            System.out.println("7- Lista de los 10 libros mas descargados");
            System.out.println("8- Búsqueda de autor por nombre");
            System.out.print("Selecciona una opción: ");
            opc = teclado.nextLine();
            System.out.println();
            switch (opc) {
                case "0":
                    break;
                case "1":
                    BusquedaAPI();
                    break;
                case "2":
                    ConsultarTodosLosLibros();
                    break;
                case "3":
                    ConsultarTodosLosAutores();
                    break;
                case "4":
                    ConsultarAutoresVivos();
                    break;
                case "5":
                    ConsultarLibrosPorIdioma();
                    break;
                case "6":
                    EstadisticasDeLibros();
                    break;
                case "7":
                    Top10LibrosDescargados();
                    break;
                case "8":
                    ConsultarAutorPorNombre();
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    public int LibroRepetido(Libros libro){
        List<Libros> allLibros = repositorioL.findAll();

        Optional<Libros> libroRepetido = allLibros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(libro.getTitulo()))
                .findFirst();

        if(libroRepetido.isPresent()){
            System.out.println("Este libro ya ha sido buscado");
            return -1;
        }
        return 0;
    }

    public void AutorRepetido(Libros libro){
        List<Autores> allAutores = repositorioA.findAll(), autores=new ArrayList<>();

        for(Autores autor : libro.getAutores()){
            int repetido = 0;
            Autores Arepetido=null;
            for(Autores a : allAutores) {
                if (autor.getNombre().equalsIgnoreCase(a.getNombre())){
                    repetido = 1;
                    Arepetido = a;
                    break;
                }
            }
            if(repetido==0){
                autores.add(autor);
            }else{
                autores.add(Arepetido);
            }
        }
        libro.setAutores(autores);

        System.out.println(libro);
    }

    public void BusquedaAPI(){
        System.out.print("Nombre del Libro: ");
        String nombre = teclado.nextLine();
        String url = URL_BASE+nombre.replace(" ", "+");
        System.out.println(url);
        String json = consumoApi.obtenerDatos(url);
        System.out.println(json);

        Resultados resultados = conversor.obtenerDatos(json, Resultados.class);
        System.out.println(resultados);

        Optional<Libro> libros = resultados.results().stream()
                .filter(l -> l.titulo().toLowerCase().contains(nombre.toLowerCase()))
                .findFirst();

        if(libros.isPresent()) {
            Libros lib = new Libros(libros.get());
            if(LibroRepetido(lib)==0){
                AutorRepetido(lib);
                repositorioL.save(lib);
            }
        }else{
            System.out.println("No se encontró su libro");
        }
    }

    private void ConsultarTodosLosLibros() {
        System.out.println(repositorioL.findAll());
    }

    private void ConsultarTodosLosAutores() {
        List<Autores> allAutores = repositorioA.findAll();
        allAutores.forEach(a -> a.setLibros(repositorioA.EncontrarLibros(a.getId())));
        System.out.println(allAutores);
    }

    private void ConsultarAutoresVivos() {
        System.out.print("Año de referencia: ");
        int año = teclado.nextInt();
        List<Autores> autoresVivos = repositorioA.AutoresVivos(año);
        autoresVivos.forEach(a -> a.setLibros(repositorioA.EncontrarLibros(a.getId())));
        System.out.println(autoresVivos);
    }

    private void ConsultarLibrosPorIdioma() {
        System.out.println("Idioma de los libros (en,es,fr,...): ");
        String idioma = teclado.nextLine();
        List<Libros> libros = repositorioL.findByIdioma(idioma);
        System.out.println(libros);
    }

    private void EstadisticasDeLibros(){
        List<Libros> libros = repositorioL.findAll();
        DoubleSummaryStatistics est = libros.stream()
                .filter(d -> d.getDescargas() >0 )
                .collect(Collectors.summarizingDouble(Libros::getDescargas));

        System.out.println("Cantidad media de descargas: " + est.getAverage());
        System.out.println("Cantidad máxima de descargas: "+ est.getMax());
        System.out.println("Cantidad mínima de descargas: " + est.getMin());
        System.out.println(" Cantidad de registros evaluados para calcular las estadisticas: " + est.getCount());
    }

    private void Top10LibrosDescargados(){
        System.out.println(repositorioL.findTop10ByOrderByDescargasDesc());
    }

    private void ConsultarAutorPorNombre() {
        System.out.println("Nombre del autor: ");
        String nombre = teclado.nextLine();
        Optional<Autores> autor = repositorioA.findByNombreContainsIgnoreCase(nombre);
        if(autor.isPresent()){
            autor.get().setLibros(repositorioA.EncontrarLibros(autor.get().getId()));
            System.out.println(autor.get());
        }else{
            System.out.println("No se encontró el autor");
        }
    }
}