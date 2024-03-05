package service;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import repository.Juego;
import repository.JuegoDao;
import repository.Optional;

public class JuegosServiceImpl implements JuegosService {
	
	@Autowired
	private JuegoDao juegoDao;

	@Override
	public List<Juego> findAll() {
		return juegoDao.findAll();
	}

	@Override
	public void cargarListaInicial() {
		int longCSV = 0;
        try (Scanner scanner = new Scanner(new File("res/juegos.csv"))) {
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				juegoDao.save(leerJuegoString(scanner.nextLine()));
				++longCSV;
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + "res/juegos.csv not found");
		}
	}

	private Juego leerJuegoString(String linea) {
		Juego juego = new Juego();
		int fecha = 0;
		
		if (linea.startsWith("\"")) {
			//Primero separamos el nombre
			String[] datos = linea.split("\"\"");
			String[] datosNombre = datos[1].split(",");
			String nombre = "";
			for(int i = 0; i < datosNombre.length; i++) {
				nombre += datosNombre[i];
			}
			juego.setNombre(nombre);
			
			//Resto de datos
			String[] restoDatos = datos[2].split(",");
			juego.setPlataforma(Platform.fromString(restoDatos[1]));
			try {
				fecha = Integer.parseInt(restoDatos[2]);
			} catch (NumberFormatException e) {
				e.getMessage();
			} finally {
				juego.setFechaPublicacion(fecha);
			}
			
			juego.setGenero(Genre.fromString(restoDatos[3]));
			juego.setEditor(restoDatos[4]);
		} else {
			String[] datosJuego = linea.split(",");
			juego.setNombre(datosJuego[1]);
			juego.setPlataforma(Platform.fromString(datosJuego[2]));
			try {
				fecha = Integer.parseInt(datosJuego[3]);
			} catch (NumberFormatException e) {
				e.getMessage();
			} finally {
				juego.setFechaPublicacion(fecha);
			}

			juego.setGenero(Genre.fromString(datosJuego[4]));
			juego.setEditor(datosJuego[5]);
		}
		
		return juego;
	}
}