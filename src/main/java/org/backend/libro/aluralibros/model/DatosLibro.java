package org.backend.libro.aluralibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro
        (
                @JsonAlias("title") String titulo,
                @JsonAlias("languages") List<String> idioma,
                @JsonAlias("download_count") Double descargas,
                @JsonAlias("authors") List<DatosAutores> autor
        )
{ }
