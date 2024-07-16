package org.backend.libro.aluralibros.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nombre;

    private String anoNacimiento;
    private String anoFallecimiento;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Libro> libro;

    public Autor() {}


    @Override
    public String toString() {
        return "Autor: " + nombre + "\n"+
                "Ano de nacimiento: " + anoNacimiento + "\n" +
                "Ano de fallecimiento: " + anoFallecimiento + "\n"+
                "Libros: [" + libro.stream().map(Libro::getTitulo).collect(Collectors.joining(",")) +"]";
    }

    public Autor(DatosAutores d){
        this.nombre = d.nombre();
        this.anoNacimiento = d.anoNacimiento();
        this.anoFallecimiento = d.anoMuerte();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(String anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public List<Libro> getLibro() {
        return libro;
    }

    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }

    public String getAnoFallecimiento() {
        return anoFallecimiento;
    }

    public void setAnoFallecimiento(String anoFallecimiento) {
        this.anoFallecimiento = anoFallecimiento;
    }
}
