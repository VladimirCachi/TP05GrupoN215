package ar.edu.unju.fi.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import ar.edu.unju.fi.model.Carrera;
import ar.edu.unju.fi.model.Materia;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
public class AlumnoDTO {
	
	@NotBlank(message = "Debe ingresar el DNI")
	@Size(min=6, max=8, message = "El DNI debe contener entre 6 y 8 caracteres")
	private String dni;
	
	@NotBlank(message = "Debe ingresar el nombre del alumno")
    @Size(min=2,max = 50, message = "El nombre debe contener como mínimo 2 caracteres y como máximo 50 caracteres")
    @Pattern(regexp= "[a-zA-ZÁÉÍÓÚáéíóúÑñ ]*", message="Debe ingresar únicamente letras y espacios")
	private String nombre;
	
	@NotBlank(message = "Debe ingresar el apellido del alumno")
    @Size(min=2,max = 50, message = "El apellido debe contener como mínimo 2 caracteres y como máximo 50 caracteres")
    @Pattern(regexp= "[a-zA-ZÁÉÍÓÚáéíóúÑñ ]*", message="Debe ingresar únicamente letras y espacios")
	private String apellido;
	
	@NotBlank(message = "Debe ingresar el email")
    @Email(message = "Debe ingresar un email válido")
	private String email;
	
	@NotBlank(message = "Debe ingresar el teléfono")
	@Size(min=10, max=15, message = "El teléfono debe contener como mínimo 10 caracteres y como máximo 15 caracteres")
	private String telefono;
	
	@NotNull(message = "Debe ingresar la fecha de nacimiento")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "La fecha de nacimiento debe estar en el pasado o ser la fecha actual")
	private LocalDate fechaNac;
	
	@NotBlank(message = "Debe ingresar el domicilio")
	@Size(min=2, max=100, message = "El domicilio debe contener como mínimo 2 caracteres y como máximo 100 caracteres")
	private String domicilio;
	
	private Long lu;
	private boolean estado;
	private Carrera carrera;
	private List<Materia> materias;
}
