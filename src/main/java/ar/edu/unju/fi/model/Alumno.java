package ar.edu.unju.fi.model;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Entity(name = "ALUMNOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alumno {

	@NonNull
	@Column(name = "alu_DNI", nullable = false)
	@NotBlank(message = "Debe ingresar el DNI")
	@Size(min=6, max=8, message = "El DNI debe contener entre 6 y 8 caracteres")
	private String dni;

	@NonNull
	@Column(name = "alu_Nombre", nullable = false)
	@NotBlank(message = "Debe ingresar el nombre del alumno")
    @Size(min=2,max = 50, message = "El nombre debe contener como mínimo 2 caracteres y como máximo 50 caracteres")
    @Pattern(regexp= "[a-zA-ZÁÉÍÓÚáéíóúÑñ ]*", message="Debe ingresar únicamente letras y espacios")
	private String nombre;

	@NonNull
	@Column(name = "alu_Apellido", nullable = false)
	@NotBlank(message = "Debe ingresar el apellido del alumno")
    @Size(min=2,max = 50, message = "El apellido debe contener como mínimo 2 caracteres y como máximo 50 caracteres")
    @Pattern(regexp= "[a-zA-ZÁÉÍÓÚáéíóúÑñ ]*", message="Debe ingresar únicamente letras y espacios")
	private String apellido;

	@NonNull
	@Column(name = "alu_Email", nullable = false)
	@NotBlank(message = "Debe ingresar el email")
    @Email(message = "Debe ingresar un email válido")
	private String email;

	@NonNull
	@Column(name = "alu_Telefono", nullable = false)
	@NotBlank(message = "Debe ingresar el teléfono")
	@Size(min=10, max=15, message = "El teléfono debe contener como mínimo 10 caracteres y como máximo 15 caracteres")
	private String telefono;

	@NonNull
	@Column(name = "alu_FechaNac", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Debe ingresar la fecha de nacimiento")
	@PastOrPresent(message = "La fecha de nacimiento debe estar en el pasado o ser la fecha actual")
	private LocalDate fechaNac;

	@NonNull
	@Column(name = "alu_Domicilio", nullable = false)
	@NotBlank(message = "Debe ingresar el domicilio")
	@Size(min=2, max=100, message = "El domicilio debe contener como mínimo 2 caracteres y como máximo 100 caracteres")
	private String domicilio;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alu_Lu")
	private Long lu;

	@NonNull
	@Column(name = "alu_Estado", nullable = false)
	private boolean estado;

	@ManyToOne
	@JoinColumn(name = "carrera_id")
	private Carrera carrera;

	@ManyToMany
	@JoinTable(name = "materia_alumno", joinColumns = @JoinColumn(name = "alumno_id"), inverseJoinColumns = @JoinColumn(name = "materia_id"))
	private List<Materia> materias;
}
