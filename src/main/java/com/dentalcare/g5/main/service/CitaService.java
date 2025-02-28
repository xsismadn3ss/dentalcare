package com.dentalcare.g5.main.service;


import com.dentalcare.g5.main.dto.CitaDto;
import com.dentalcare.g5.main.payload.RequestCitaDto;

import java.util.List;

public interface CitaService {
    CitaDto addCita(CitaDto cita);
    CitaDto updateCita(CitaDto cita);
    CitaDto getCitaById(int id);
    List<CitaDto> getAllCitas();
    List<CitaDto> filterCitas(RequestCitaDto payload);
    void deleteCita(int idCita);
}