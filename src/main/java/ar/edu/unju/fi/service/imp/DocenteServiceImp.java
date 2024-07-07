package ar.edu.unju.fi.service.imp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.dto.DocenteDTO;
import ar.edu.unju.fi.mapper.DocenteMapDTO;
import ar.edu.unju.fi.model.Docente;
import ar.edu.unju.fi.repository.DocenteRepository;
import ar.edu.unju.fi.service.IDocenteService;
@Service
public class DocenteServiceImp implements IDocenteService {
	
	private static final Log LOGGER = LogFactory.getLog(DocenteServiceImp.class);

    @Autowired
    DocenteRepository docenteRepository;
    @Autowired
    DocenteMapDTO docenteMapDTO;

    @Override
    public List<DocenteDTO> getListaDocentes() {
    	LOGGER.info("SERVICE: IDocenteService -> DocenteServiceImp");
        LOGGER.info("METHOD: getListaDocentes()");
        List<Docente> docentes = docenteRepository.findByEstado(true);
        return docenteMapDTO.listDocenteToListDocenteDTO(docentes);
    }

    @Override
    public DocenteDTO findDocenteByLegajo(Long legajo) {
    	LOGGER.info("SERVICE: IDocenteService -> DocenteServiceImp");
        LOGGER.info("METHOD: findDocenteByLegajo() ---- PARAMS: legajo '" + legajo + "'");
        return docenteMapDTO.toDto(docenteRepository.findById(legajo).get());
    }

    @Override
    public void agregarUnDocente(DocenteDTO docenteDTO) {
    	LOGGER.info("SERVICE: IDocenteService -> DocenteServiceImp");
        LOGGER.info("METHOD: agregarUnDocente() ---- PARAMS: docenteDTO '" + docenteDTO + "'");
        docenteDTO.setEstado(true);
        docenteRepository.save(docenteMapDTO.toEntity(docenteDTO));
    }

    @Override
    public void actualizarDocente(DocenteDTO docenteDTO) {
    	LOGGER.info("SERVICE: IDocenteService -> DocenteServiceImp");
        LOGGER.info("METHOD: actualizarDocente() ---- PARAMS: docenteDTO '" + docenteDTO + "'");
        docenteRepository.save(docenteMapDTO.toEntity(docenteDTO));
        LOGGER.info("RESULT: Docente actualizado con éxito");
    }

    @Override
    public void eliminarUnDocente(Long legajo) {
    	LOGGER.info("SERVICE: IDocenteService -> DocenteServiceImp");
        LOGGER.info("METHOD: eliminarUnDocente() ---- PARAMS: legajo '" + legajo + "'");
        DocenteDTO docenteDTO = findDocenteByLegajo(legajo);
        if (docenteDTO != null) {
            docenteDTO.setEstado(false);
            docenteRepository.save(docenteMapDTO.toEntity(docenteDTO));
            LOGGER.info("RESULT: Docente con legajo " + legajo + " desactivado con éxito");
        } else {
            throw new RuntimeException("El docente con legajo " + legajo + " no existe.");
        }
    }
}