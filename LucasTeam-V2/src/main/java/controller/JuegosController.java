package controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import model.Juego;
import service.JuegosService;

@RestController
@RequestMapping("/juegos")
@Tag(name = "Juegos", description = "LucaSteam API")
public class JuegosController {

	@Autowired
	private JuegosService service;
	
	private static final Logger log = LoggerFactory.getLogger(JuegosController.class);
	
	@GetMapping("/local")
	public ResponseEntity<?> cargarListaLocal() {
		int datosCargados = service.cargarListaInicial();
		String responseBody;
		if(datosCargados == 0) {
			responseBody = "Ha habido un error al cargar los datos.";
			return ResponseEntity.internalServerError().body(responseBody);
		}
		else {
			responseBody = "Se han cargado " + datosCargados + " juegos en la BBDD.";
			return ResponseEntity.ok(responseBody);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> listadoJuegos() {
		List<Juego> listado = service.findAll();
		String responseBody;
		if(listado.size() == 0) {
			responseBody = "La BBDD no contiene ningun juego.";
			return ResponseEntity.internalServerError().body(responseBody);
		}
		else
			return ResponseEntity.ok(listado);
	}
}
