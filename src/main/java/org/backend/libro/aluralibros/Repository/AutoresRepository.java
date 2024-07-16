package org.backend.libro.aluralibros.Repository;

import org.backend.libro.aluralibros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutoresRepository  extends JpaRepository<Autor,Long> {
    Autor findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE (a.anoFallecimiento IS NULL OR CAST(a.anoFallecimiento AS long) >= :ano) AND CAST(a.anoNacimiento AS long) <= :ano")
    List<Autor> findAutoresVivosEnAno(@Param("ano") long ano);
}
