package org.backend.libro.aluralibros.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Double descargas;

    @ManyToOne(fetch = FetchType.EAGER)
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = String.join(", ", datosLibro.idioma());
        this.descargas = datosLibro.descargas();
        this.autor =  new Autor(datosLibro.autor().get(0));
    }

    @Override
    public String toString() {
        return  "id: " + id + '\n'+
                "Titulo: " + titulo + '\n' +
                "Autores: " + autor.getNombre() + '\n'+
                "Idioma: " + idioma + '\n' +
                "Descargas: " + descargas + '\'';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}

