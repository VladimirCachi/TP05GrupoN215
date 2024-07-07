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
import ar.edu.unju.fi.service.IDocenteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/docente")
public class DocenteController {

    @Autowired
    DocenteDTO docenteDTO;

    @Autowired
    IDocenteService docenteService;

    private static final Log LOGGER = LogFactory.getLog(DocenteController.class);

    @GetMapping("formularioDocente")
    public ModelAndView getFormulario() {
        LOGGER.info("CONTROLLER: DocenteController with /formularioDocente get method");
        LOGGER.info("METHOD: getFormulario()");
        ModelAndView mv = new ModelAndView("formDocente");
        mv.addObject("maestro", docenteDTO);
        mv.addObject("isEdit", false);
        LOGGER.info("RESULT: visualiza la página formDocente.html");
        return mv;
    }

    @GetMapping("listaDocentes")
    public ModelAndView getListaDocentes() {
        LOGGER.info("CONTROLLER: DocenteController with /listaDocentes get method");
        LOGGER.info("METHOD: getListaDocentes()");
        ModelAndView mv = new ModelAndView("listaDocentes");
        mv.addObject("docentes", docenteService.getListaDocentes());
        LOGGER.info("RESULT: visualiza la página listaDocentes.html");
        return mv;
    }

    @PostMapping("guardarDocente")
    public ModelAndView guardarDocente(@Valid @ModelAttribute("maestro") DocenteDTO docenteDTO, BindingResult result) {
        LOGGER.info("CONTROLLER: DocenteController with /guardarDocente post method");
        LOGGER.info("METHOD: guardarDocente() ---- PARAMS: docenteDTO '" + docenteDTO + "'");
        if (result.hasErrors()) {
            LOGGER.warn("RESULT: Error en la validación del formulario");
            ModelAndView mv = new ModelAndView("formDocente");
            mv.addObject("maestro", docenteDTO);
            mv.addObject("isEdit", docenteDTO.getLegajo() != null);
            return mv;
        } else {
            if (docenteDTO.getLegajo() != null) {
                LOGGER.info("ACTUALIZANDO: Docente existente con legajo " + docenteDTO.getLegajo());
                docenteService.actualizarDocente(docenteDTO);
            } else {
                LOGGER.info("AGREGANDO: Nuevo docente");
                docenteService.agregarUnDocente(docenteDTO);
            }
            LOGGER.info("RESULT: redirige a la página listaDocentes");
            return new ModelAndView("redirect:/docente/listaDocentes");
        }
    }

    @GetMapping("/modificar/{legajo}")
    public ModelAndView modificarDocente(@PathVariable("legajo") Long legajo) {
        LOGGER.info("CONTROLLER: DocenteController with /modificar/{legajo} get method");
        LOGGER.info("METHOD: Se ejecuta Modifica Docente, modificarDocente() ---- PARAMS: legajo '" + legajo + "'");
        ModelAndView mv = new ModelAndView("formDocente");
        mv.addObject("maestro", docenteService.findDocenteByLegajo(legajo));
        mv.addObject("isEdit", true);
        LOGGER.info("RESULT: visualiza la página formDocente.html con datos del docente a modificar");
        return mv;
    }

    @GetMapping("/borrarDocente/{legajo}")
    public ModelAndView deleteDocente(@PathVariable("legajo") Long legajo) {
        LOGGER.info("CONTROLLER: DocenteController with /borrarDocente/{legajo} get method");
        LOGGER.info("METHOD: deleteDocente() ---- PARAMS: legajo '" + legajo + "'");
        docenteService.eliminarUnDocente(legajo);
        LOGGER.info("RESULT: Se ha eliminado de forma logica un Docente");
        LOGGER.info("RESULT: redirige a la página listaDocentes");
        return new ModelAndView("redirect:/docente/listaDocentes");
    }
}