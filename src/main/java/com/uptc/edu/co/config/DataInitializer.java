package com.uptc.edu.co.config;

import com.uptc.edu.co.model.Estudiante;
import com.uptc.edu.co.model.Materia;
import com.uptc.edu.co.repository.EstudianteRepository;
import com.uptc.edu.co.repository.MateriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    public DataInitializer(EstudianteRepository estudianteRepository, MateriaRepository materiaRepository) {
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
    }

    @Override
    public void run(String... args) {
        if (estudianteRepository.count() == 0) {
            List<Estudiante> estudiantes = new ArrayList<>();
            for (int i = 1; i <= 1000; i++) {
                Estudiante estudiante = new Estudiante();
                estudiante.setNombre("nombre" + i);
                estudiante.setApellido("apellido" + i);
                estudiante.setCorreo("correo" + i + "@uptc.edu.co");
                estudiante.setFechaNacimiento(LocalDate.of(2000, 1, 1).plusDays(i));
                estudiante.setPrograma("programa" + ((i % 20) + 1));

                estudiantes.add(estudiante);
            }
            estudianteRepository.saveAll(estudiantes);
        }

        if (materiaRepository.count() == 0) {
            List<Materia> materias = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                Materia materia = new Materia();
                materia.setNombre("materia" + i);
                materia.setCodigo("codigo" + i);
                materia.setCreditos(i);
                materia.setSemestre(i);

                materias.add(materia);
            }
            materiaRepository.saveAll(materias);
        }
    }
}