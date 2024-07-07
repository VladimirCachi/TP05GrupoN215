package ar.edu.unju.fi.controller;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.unju.fi.dto.AlumnoDTO;
import ar.edu.unju.fi.dto.CarreraDTO;
import ar.edu.unju.fi.service.ICarreraService;
import ar.edu.unju.fi.service.IAlumnoService;

@Controller
@RequestMapping("/carrera")
public class CarreraController {

    @Autowired
    private CarreraDTO nuevaCarrera;

    @Autowired
    private ICarreraService carreraService;

    @Autowired
    private IAlumnoService alumnoService;

    private static final Log LOGGER = LogFactory.getLog(CarreraController.class);

    @GetMapping("/formularioCarrera")
    public ModelAndView getFormCarrera() {
        LOGGER.info("CONTROLLER: CarreraController with /formularioCarrera get method");
        LOGGER.info("METHOD: getFormCarrera()");
        ModelAndView modelAndView = new ModelAndView("formCarrera");
        modelAndView.addObject("nuevaCarrera", nuevaCarrera);
        modelAndView.addObject("pageTitle", "Formulario de Carrera");
        LOGGER.info("RESULT: visualiza la página formCarrera.html");
        return modelAndView;
    }

    @PostMapping("/guardarCarrera")
    public String saveCarrera(@ModelAttribute("nuevaCarrera") CarreraDTO carreraParaGuardar) {
        LOGGER.info("CONTROLLER: CarreraController with /guardarCarrera post method");
        LOGGER.info("METHOD: saveCarrera() ---- PARAMS: carreraDTO '" + carreraParaGuardar + "'");
        carreraParaGuardar.setEstado(true); // Asume que una carrera nueva siempre está activa
        carreraService.agregarUnaCarrera(carreraParaGuardar);
        LOGGER.info("RESULT: redirige a la página listaCarreras");
        return "redirect:/carrera/listaCarreras";
    }

    @GetMapping("/borrarCarrera/{id}")
    public String deleteCarrera(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        LOGGER.info("CONTROLLER: CarreraController with /borrarCarrera/{id} get method");
        LOGGER.info("METHOD: deleteCarrera() ---- PARAMS: id '" + id + "'");
        carreraService.eliminarUnaCarrera(id);
        redirectAttributes.addFlashAttribute("mensaje", "La carrera ha sido eliminada exitosamente.");
        LOGGER.info("RESULT: redirige a la página listaCarreras");
        return "redirect:/carrera/listaCarreras";
    }

    @GetMapping("/listaCarreras")
    public ModelAndView listarCarreras() {
        LOGGER.info("CONTROLLER: CarreraController with /listaCarreras get method");
        LOGGER.info("METHOD: listarCarreras()");
        List<CarreraDTO> carrerasActivas = carreraService.getListaCarreras();
        ModelAndView modelView = new ModelAndView("listaDeCarreras");
        modelView.addObject("listadoCarreras", carrerasActivas);
        LOGGER.info("RESULT: visualiza la página listaDeCarreras.html");
        return modelView;
    }

    @GetMapping("/editarCarrera/{id}")
    public ModelAndView getFormEditarCarrera(@PathVariable Long id) {
        LOGGER.info("CONTROLLER: CarreraController with /editarCarrera/{id} get method");
        LOGGER.info("METHOD: getFormEditarCarrera() ---- PARAMS: id '" + id + "'");
        CarreraDTO carreraParaModificar = carreraService.findCarreraById(id);
        if (carreraParaModificar == null) {
            LOGGER.warn("RESULT: Carrera no encontrada, redirige a la página listaCarreras");
            return new ModelAndView("redirect:/carrera/listaCarreras");
        }
        ModelAndView modelView = new ModelAndView("formCarrera");
        modelView.addObject("nuevaCarrera", carreraParaModificar);
        modelView.addObject("pageTitle", "Editar Carrera");
        LOGGER.info("RESULT: visualiza la página formCarrera.html con datos de la carrera a modificar");
        return modelView;
    }

    @PostMapping("/modificarCarrera")
    public String saveCarreraModificada(@ModelAttribute("nuevaCarrera") CarreraDTO carreraModificada,
            RedirectAttributes redirectAttributes) {
        LOGGER.info("CONTROLLER: CarreraController with /modificarCarrera post method");
        LOGGER.info("METHOD: saveCarreraModificada() ---- PARAMS: carreraDTO '" + carreraModificada + "'");
        if (carreraModificada.getId() == null) {
            LOGGER.error("ERROR: El ID de la carrera no puede ser nulo.");
            throw new RuntimeException("El ID de la carrera no puede ser nulo.");
        }
        carreraService.actualizarCarrera(carreraModificada);
        redirectAttributes.addFlashAttribute("mensaje", "La carrera ha sido modificada exitosamente.");
        LOGGER.info("RESULT: redirige a la página listaCarreras");
        return "redirect:/carrera/listaCarreras";
    }

    @GetMapping("/alumnos")
    public String getAlumnosPorCarrera(@RequestParam("carreraId") Long carreraId, Model model) {
        LOGGER.info("CONTROLLER: CarreraController with /alumnos get method");
        LOGGER.info("METHOD: getAlumnosPorCarrera() ---- PARAMS: carreraId '" + carreraId + "'");
        List<AlumnoDTO> alumnos = alumnoService.findAlumnosByCarreraId(carreraId);
        model.addAttribute("alumnos", alumnos);
        LOGGER.info("RESULT: visualiza la página alumnosPorCarrera.html");
        return "alumnosPorCarrera";
    }

    @GetMapping("/formulario")
    public String getFormulario(Model model) {
        LOGGER.info("CONTROLLER: CarreraController with /formulario get method");
        LOGGER.info("METHOD: getFormulario()");
        List<CarreraDTO> carreras = carreraService.getListaCarreras();
        model.addAttribute("carreras", carreras);
        LOGGER.info("RESULT: visualiza la página formularioAlumnosPorCarrera.html");
        return "formularioAlumnosPorCarrera";
    }
}
