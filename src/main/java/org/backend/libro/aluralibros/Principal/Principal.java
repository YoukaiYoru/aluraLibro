package org.backend.libro.aluralibros.Principal;

import org.backend.libro.aluralibros.Repository.AutoresRepository;
import org.backend.libro.aluralibros.Repository.LibrosRepository;
import org.backend.libro.aluralibros.Service.ConsumoAPI;
import org.backend.libro.aluralibros.Service.ConvierteDatos;
import org.backend.libro.aluralibros.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibrosRepository repositorio;

    private AutoresRepository respositorioAutor;


    public Principal(LibrosRepository repository, AutoresRepository respositorioAutor) {
        this.repositorio = repository;
        this.respositorioAutor = respositorioAutor;
    }





    public void mostrarElMenu() throws Exception {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ----------------------------------
                    1 - Buscar libro por título\s
                    2 - Libros Registrados
                    3 - Lista de autores registrados
                    4 - Lista de autores vivos en un determinado año
                    5 - Libros por idioma\s
                    0 - Salir
                    --------------------------------
                    \t
                   \s""";
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibros();
                    break;
                case 2:
                    buscarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosEnAno();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }


    private void buscarLibros() {
        System.out.println("Escribe el libro que desea buscar: ");
        String libro = teclado.nextLine().toLowerCase();
        String json = consumoApi.obtenerDatos(URL_BASE + "search=" + libro.replace(" ", "%20"));
        ResponseAPI apiResponse = conversor.obtenerDatos(json, ResponseAPI.class);

        if (apiResponse.resultados() != null && !apiResponse.resultados().isEmpty()) {
            DatosLibro datosLibro = apiResponse.resultados().get(0);

            Autor autor = null;
            if (datosLibro.autor() != null && !datosLibro.autor().isEmpty()) {
                DatosAutores datosAutor = datosLibro.autor().get(0); // Tomamos el primer autor por simplicidad
                String nombreCompletoAutor = datosAutor.nombre();

                autor = respositorioAutor.findByNombre(nombreCompletoAutor);
                if (autor == null) {
                    autor = new Autor(datosAutor);
                    respositorioAutor.save(autor);
                }
            } else {
                System.out.println("La respuesta de la API no contiene información del autor.");
            }
            Libro libroNuevo = new Libro(datosLibro);
            libroNuevo.setAutor(autor);
            repositorio.save(libroNuevo);
        } else {
            System.out.println("La respuesta de la API no contiene resultados.");
        }
    }

    private void buscarLibrosRegistrados() {
        List<Libro> libros = repositorio.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(libro -> {
                    System.out.println("----------Libro----------");
                    System.out.println(libro);
                    System.out.println("-------------------------");
                    System.out.println('\n');
                });
    }
    private void mostrarAutoresRegistrados() {
        List<Autor> autores = respositorioAutor.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getId))
                .forEach(autor -> {
                    System.out.println("----------Autor----------");
                    System.out.println(autor);
                    System.out.println("------------------------");
                    System.out.println('\n');;
                });
    }
    private void mostrarAutoresVivosEnAno(){
        System.out.println("Ingrese un año: \n" );
        long ano = teclado.nextLong();
        List<Autor> autores = respositorioAutor.findAutoresVivosEnAno(ano);
            autores.stream()
                .sorted(Comparator.comparing(Autor::getAnoNacimiento))
                .forEach(autor -> {
                    System.out.println("----------Autor----------");
                    System.out.println(autor);
                    System.out.println("------------------------");
                    System.out.println('\n');
                });
    }
    private void mostrarLibrosPorIdioma(){
        System.out.println("""
                Ingrese un idioma para buscar los libros:
                es- Español
                en- Inglés
                fr- Francés
                pt- Portugués
                """);
        String idioma = teclado.nextLine().toLowerCase();
        List<Libro> libros = repositorio.findByIdioma(idioma);
        libros.stream().forEach(libro -> {
            System.out.println("----------Libro----------");
            System.out.println(libro);
            System.out.println("------------------------");
            System.out.println('\n');
        });
    }

}
