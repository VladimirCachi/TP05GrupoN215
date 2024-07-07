package ar.edu.unju.fi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ar.edu.unju.fi.dto.AlumnoDTO;
import ar.edu.unju.fi.model.Materia;

@Service
public interface IAlumnoService {
    List<AlumnoDTO> getListaAlumnos();
    AlumnoDTO findAlumnoByLu(Long lu);
    void agregarUnAlumno(AlumnoDTO alumnoDTO);
    void actualizarAlumno(AlumnoDTO alumnoDTO);
    void eliminarUnAlumno(Long lu);
    List<AlumnoDTO> findAlumnosByCarreraId(Long carreraId); // Nuevo método
}
