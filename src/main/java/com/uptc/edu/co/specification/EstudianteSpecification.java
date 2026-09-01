package com.uptc.edu.co.specification;

import com.uptc.edu.co.model.Estudiante;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstudianteSpecification {

    private EstudianteSpecification() {
    }

    public static Specification<Estudiante> buildSpecification(
            Long id,
            List<String> nombres,
            List<String> apellidos,
            String correo,
            List<String> programas,
            LocalDate fechaNacimientoDesde,
            LocalDate fechaNacimientoHasta,
            String search,
            Boolean and
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }

            if (nombres != null && !nombres.isEmpty()) {
                List<Predicate> nombrePredicates = new ArrayList<>();
                for (String nombre : nombres) {
                    nombrePredicates.add(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("nombre")),
                                    "%" + nombre.toLowerCase() + "%"
                            )
                    );
                }
                predicates.add(criteriaBuilder.or(nombrePredicates.toArray(new Predicate[0])));
            }

            if (apellidos != null && !apellidos.isEmpty()) {
                List<Predicate> apellidoPredicates = new ArrayList<>();
                for (String apellido : apellidos) {
                    apellidoPredicates.add(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("apellido")),
                                    "%" + apellido.toLowerCase() + "%"
                            )
                    );
                }
                predicates.add(criteriaBuilder.or(apellidoPredicates.toArray(new Predicate[0])));
            }

            if (correo != null && !correo.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("correo")),
                                "%" + correo.toLowerCase() + "%"
                        )
                );
            }

            if (programas != null && !programas.isEmpty()) {
                predicates.add(root.get("programa").in(programas));
            }

            if (fechaNacimientoDesde != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("fechaNacimiento"), fechaNacimientoDesde)
                );
            }

            if (fechaNacimientoHasta != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("fechaNacimiento"), fechaNacimientoHasta)
                );
            }

            if (search != null && !search.isBlank()) {
                String likeSearch = "%" + search.toLowerCase() + "%";
                Predicate searchPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), likeSearch),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("apellido")), likeSearch),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("correo")), likeSearch)
                );
                predicates.add(searchPredicate);
            }

            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            boolean combineWithAnd = and == null || and;

            return combineWithAnd
                    ? criteriaBuilder.and(predicates.toArray(new Predicate[0]))
                    : criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}