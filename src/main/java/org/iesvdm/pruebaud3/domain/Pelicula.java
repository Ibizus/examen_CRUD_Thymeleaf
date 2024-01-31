package org.iesvdm.pruebaud3.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {

    private short id_pelicula;


    @NotBlank(message="{msg.valid.blank}")
    @NotNull(message="{msg.valid.null}")
    @Length(min=3, max=255, message="{msg.valid.lenght}")
    private String titulo;

    @Length(max=300, message="{msg.valid.maxLenght}")
    private String descripcion;

    @NotNull(message="{msg.valid.null}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_lanzamiento;

    @Min(value=1, message = "{msg.valid.min}")
    private short id_idioma;

    private short duracion_alquiler;
    private float rental_rate;
    private short duracion;
    private float replacement_cost;
    private Timestamp ultima_actualizacion;

}