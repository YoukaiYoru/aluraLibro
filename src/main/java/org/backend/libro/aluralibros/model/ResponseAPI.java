package org.backend.libro.aluralibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseAPI(
        @JsonAlias("results") List<DatosLibro> resultados
        ) {
}
