package com.dentalcare.g5.main.service.impl;

import com.dentalcare.g5.main.dto.CitaDto;
import com.dentalcare.g5.main.payload.RequestCitaDto;
import com.dentalcare.g5.main.service.CitaService;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {
    private final List<CitaDto> citas = new ArrayList<>();
    private int nextId = 1; // Contador para generar IDs únicos

    public CitaServiceImpl() {
        generarDataDummy();
    }

    public void generarDataDummy() {
        citas.add(new CitaDto(nextId++, 4, 5, LocalDate.now(), new Time(15, 25, 0), "Pendiente", "Protesis dental"));
        citas.add(new CitaDto(nextId++, 2, 4, LocalDate.now(), new Time(11, 25, 0), "Pendiente", "Ortodoncia"));
        citas.add(new CitaDto(nextId++, 4, 2, LocalDate.now(), new Time(10, 25, 0), "Finalizado", "Limpieza"));
    }

    @Override
    public CitaDto addCita(CitaDto cita) {
        cita.setId(nextId++); // Asigna un ID único y autoincremental
        citas.add(cita);
        return cita;
    }

    @Override
    public CitaDto updateCita(CitaDto cita) {
        Optional<CitaDto> existingCita = citas.stream()
                .filter(c -> c.getId() == cita.getId())
                .findFirst();

        if (existingCita.isPresent()) {
            CitaDto updatedCita = existingCita.get();
            updatedCita.setHora(cita.getHora());
            updatedCita.setEstado(cita.getEstado());
            updatedCita.setFecha(cita.getFecha());
            updatedCita.setPacienteID(cita.getPacienteID());
            updatedCita.setDoctorID(cita.getDoctorID());
            updatedCita.setMotivo(cita.getMotivo()); // Asegúrate de actualizar todos los campos
            return updatedCita;
        }
        return null; // Retorna null si la cita no existe
    }

    @Override
    public CitaDto getCitaById(int id) {
        return citas.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CitaDto> getAllCitas() {
        return new ArrayList<>(citas); // Retorna una copia de la lista para evitar modificaciones externas
    }

    @Override
    public List<CitaDto> filterCitas(RequestCitaDto payload) {
        return citas.stream()
                .filter(cita -> {
                    Integer id = (payload.getId() != null) ? payload.getId() : 0;
                    Integer doctorId = (payload.getDoctorId() != null) ? payload.getDoctorId() : 0;
                    Integer pacienteId = (payload.getPacienteId() != null) ? payload.getPacienteId() : 0;
                    String motivo = (payload.getMotivo() != null) ? payload.getMotivo() : "";

                    return (id == 0 || id.equals(cita.getId())) &&
                            (motivo.isEmpty() || cita.getMotivo().contains(motivo)) &&
                            (doctorId == 0 || doctorId.equals(cita.getDoctorID())) &&
                            (pacienteId == 0 || pacienteId.equals(cita.getPacienteID()));
                })
                .toList();
    }


    @Override
    public void deleteCita(int idCita) {
        citas.removeIf(cita -> cita.getId() == idCita);
    }
}
