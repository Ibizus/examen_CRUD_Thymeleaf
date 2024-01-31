package org.iesvdm.pruebaud3.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.pruebaud3.domain.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j // Anotación de Lombok para poder usar el objeto LOG y guardar una traza con diferentes niveles (info, error, debug)
@Repository // Componente de Spring de la capa de persistencia
public class PeliculaDAOImpl implements PeliculaDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(Pelicula pelicula) {

        // RECUPERANDO ID:
        String sqlInsert = """
							INSERT INTO pelicula (titulo, descripcion, fecha_lanzamiento, id_idioma, duracion_alquiler, rental_rate, duracion, replacement_cost) 
							VALUES  (     ?,         ?,         ?,          ?,         ?,            ?,          ?,         ?)
							""";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
            int idx = 1;
            ps.setString(idx++, pelicula.getTitulo());
            ps.setString(idx++, pelicula.getDescripcion());
            ps.setDate(idx++, new java.sql.Date(pelicula.getFecha_lanzamiento().getTime()));
            ps.setShort(idx++, pelicula.getId_idioma());
            ps.setShort(idx++, pelicula.getDuracion_alquiler());
            ps.setFloat(idx++, pelicula.getRental_rate());
            ps.setShort(idx++, pelicula.getDuracion());
            ps.setFloat(idx, pelicula.getReplacement_cost());
            return ps;
        },keyHolder);

        pelicula.setId_pelicula(keyHolder.getKey().shortValue());
        log.info("Insertados {} registros.", rows);
    }

    @Override
    public List<Pelicula> getAll() {

        List<Pelicula> peliculaList = jdbcTemplate.query(
                "SELECT * FROM pelicula",
                (rs, rowNum) -> new Pelicula(rs.getShort("id_pelicula"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha_lanzamiento"),
                        rs.getShort("id_idioma"),
                        rs.getShort("duracion_alquiler"),
                        rs.getFloat("rental_rate"),
                        rs.getShort("duracion"),
                        rs.getFloat("replacement_cost"),
                        rs.getTimestamp("ultima_actualizacion")
                )
        );

        log.info("Devueltos {} registros.", peliculaList.size());
        return peliculaList;
    }

    @Override
    public Optional<Pelicula> find(int id) {

        Pelicula encontrada =  jdbcTemplate
                .queryForObject("SELECT * FROM pelicula WHERE id_pelicula = ?"
                        , (rs, rowNum) -> new Pelicula(rs.getShort("id_pelicula"),
                                rs.getString("titulo"),
                                rs.getString("descripcion"),
                                rs.getDate("fecha_lanzamiento"),
                                rs.getShort("id_idioma"),
                                rs.getShort("duracion_alquiler"),
                                rs.getFloat("rental_rate"),
                                rs.getShort("duracion"),
                                rs.getFloat("replacement_cost"),
                                rs.getTimestamp("ultima_actualizacion"))
                        , id
                );

        if (encontrada != null) {
            return Optional.of(encontrada);
        }
        else {
            log.info("Pelicula no encontrada.");
            return Optional.empty();
        }
    }

    @Override
    public void update(Pelicula pelicula) {

    }

    @Override
    public void delete(int id) {

    }

    public int numPeliculasTotales(){

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(p.id_pelicula) as numPeliculas FROM pelicula p",
                (rs, numRow) -> rs.getInt("numPeliculas"));
    }
}
