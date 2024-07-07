package ar.edu.unju.fi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.dto.DocenteDTO;
import ar.edu.unju.fi.dto.MateriaDTO;
import ar.edu.unju.fi.service.ICarreraService;
import ar.edu.unju.fi.service.IDocenteService;
import ar.edu.unju.fi.service.IMateriaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/materia")
public class MateriaController {
	@Autowired
	MateriaDTO materiaDTO;

	@Autowired
	DocenteDTO docente;

	@Autowired
	IMateriaService materiaService;

	@Autowired
	ICarreraService carreraService;

	@Autowired
	IDocenteService docenteService;

	private static final Log LOGGER = LogFactory.getLog(MateriaController.class);

	@GetMapping("formularioMateria")
	public ModelAndView getFormulario() {
		LOGGER.info("CONTROLLER: MateriaController with /formularioMateria get method");
		LOGGER.info("METHOD: getFormulario()");
		ModelAndView mv = new ModelAndView("formMateria");
		mv.addObject("materia", materiaDTO);
		mv.addObject("isEdit", false);
		mv.addObject("docentes", docenteService.getListaDocentes());
		mv.addObject("carreras", carreraService.getListaCarreras());
		mv.addObject("materias", materiaService.getListaMaterias());
		LOGGER.info("RESULT: visualiza la página formMateria.html");
		return mv;
	}

	@GetMapping("listadoMaterias")
	public ModelAndView getListaMaterias() {
		LOGGER.info("CONTROLLER: MateriaController with /listadoMaterias get method");
		LOGGER.info("METHOD: getListaMaterias()");
		ModelAndView mv = new ModelAndView("listadoMaterias");
		mv.addObject("materias", materiaService.getListaMaterias());
		mv.addObject("docentes", docenteService.getListaDocentes());
		mv.addObject("carreras", carreraService.getListaCarreras());
		LOGGER.info("RESULT: visualiza la página listadoMaterias.html");
		return mv;
	}
	
	 @PostMapping("guardarMateria")
	    public ModelAndView guardarMateria(@Valid @ModelAttribute("materia") MateriaDTO materiaDTO, BindingResult result) {
	        LOGGER.info("CONTROLLER: MateriaController with /guardarMateria post method");
	        LOGGER.info("METHOD: guardarMateria() ---- PARAMS: materiaDTO '" + materiaDTO + "'");
	        if (result.hasErrors()) {
	            LOGGER.warn("RESULT: Error en la validación del formulario");
	            ModelAndView mv = new ModelAndView("formMateria");
	            mv.addObject("materia", materiaDTO);
	            mv.addObject("isEdit", materiaDTO.getCodigo() != null);
	            return mv;
	        } else {
	            if (materiaDTO.getCodigo() != null) {
	                LOGGER.info("ACTUALIZANDO: Materia existente con código " + materiaDTO.getCodigo());
	                materiaService.actualizarMateria(materiaDTO);
	            } else {
	                LOGGER.info("AGREGANDO: Nueva materia");
	                materiaService.agregarUnaMateria(materiaDTO);
	            }
	            LOGGER.info("RESULT: redirige a la página listadoMaterias");
	            return new ModelAndView("redirect:/materia/listadoMaterias");
	        }
	    }

	@GetMapping("/modificar/{codigo}")
	public ModelAndView modificarMateria(@PathVariable("codigo") Long codigo) {
		LOGGER.info("CONTROLLER: MateriaController with /modificar/{codigo} get method");
		LOGGER.info("METHOD: modificarMateria() ---- PARAMS: codigo '" + codigo + "'");
		ModelAndView mv = new ModelAndView("formMateria");
		mv.addObject("docentes", docenteService.getListaDocentes());
		mv.addObject("carreras", carreraService.getListaCarreras());
		mv.addObject("materia", materiaService.findMateriaByCodigo(codigo));
		mv.addObject("isEdit", true);
		LOGGER.info("RESULT: visualiza la página formMateria.html con datos de la materia a modificar");
		return mv;
	}

	@GetMapping("/borrarMateria/{codigo}")
	public ModelAndView deleteMateria(@PathVariable("codigo") Long codigo) {
		LOGGER.info("CONTROLLER: MateriaController with /borrarMateria/{codigo} get method");
		LOGGER.info("METHOD: deleteMateria() ---- PARAMS: codigo '" + codigo + "'");
		materiaService.eliminarUnaMateria(codigo);
		LOGGER.info("RESULT: redirige a la página listadoMaterias");
		return new ModelAndView("redirect:/materia/listadoMaterias");
	}

}