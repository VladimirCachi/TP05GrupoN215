package ar.edu.unju.fi.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unju.fi.model.Alumno;
import ar.edu.unju.fi.model.Carrera;
import ar.edu.unju.fi.model.Docente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class MateriaDTO {
	private Long codigo;
	@NotBlank(message = "Debe ingresar el nombre de la materia")
    @Size(min=2,max = 50, message = "El nombre debe contener como mínimo 2 caracteres y como máximo 50 caracteres")
    @Pattern(regexp= "[a-zA-ZÁÉÍÓÚáéíóúÑñ ]*", message="Debe ingresar únicamente letras y espacios")
	private String nombre;
	
	@NotBlank(message = "Debe ingresar el curso")
    @Size(min=2,max = 50, message = "El nombre debe contener como mínimo 2 caracteres y como máximo 50 caracteres")
    @Pattern(regexp= "[a-zA-ZÁÉÍÓÚáéíóúÑñ ]*", message="Debe ingresar únicamente letras y espacios")
	private String curso;
	
	@NotNull(message = "Debe ingresar la cantidad de horas")
	private int cantidadHoras;
	 
	private String modalidad;
	private boolean estado;
	@Autowired
	private Docente docente;
	@Autowired
	private Carrera carrera;
}
