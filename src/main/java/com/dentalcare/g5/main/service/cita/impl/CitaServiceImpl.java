package com.dentalcare.g5.main.service.cita.impl;

import com.dentalcare.g5.main.mapper.cita.CitaMapper;
import com.dentalcare.g5.main.mapper.cita.NotaMapper;
import com.dentalcare.g5.main.mapper.cita.TratamientoMapper;
import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.entity.cita.Cita;
import com.dentalcare.g5.main.model.entity.cita.Nota;
import com.dentalcare.g5.main.model.entity.cita.Tratamiento;
import com.dentalcare.g5.main.model.payload.cita.CitaCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.CitaFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.CitaUpdateRequest;
import com.dentalcare.g5.main.repository.cita.CitaRepository;
import com.dentalcare.g5.main.repository.cita.NotaRepository;
import com.dentalcare.g5.main.repository.cita.TratamientoRepository;
import com.dentalcare.g5.main.service.cita.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private TratamientoRepository tratamientoRepository;
    @Autowired
    private TratamientoMapper tratamientoMapper;

    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private NotaMapper notaMapper;

    @Override
    public CitaDto addCita(CitaCreateRequest payload) {
        Cita cita = new Cita();
        cita.setFecha(payload.getFecha());
        cita.setHora(payload.getHora());
        cita.setMotivo(payload.getMotivo());
        
        Cita savedCita = citaRepository.save(cita);
        return citaMapper.toDto(savedCita);
    }

    @Override
    public CitaDto updateCita(CitaUpdateRequest payload, int id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        if (payload.getFecha() != null) {
            cita.setFecha(payload.getFecha());
        }
        if (payload.getHora() != null) {
            cita.setHora(payload.getHora());
        }
        if (payload.getMotivo() != null) {
            cita.setMotivo(payload.getMotivo());
        }
        
        Cita updatedCita = citaRepository.save(cita);
        return citaMapper.toDto(updatedCita);
    }

    @Override
    public CitaDto getCitaById(int id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return citaMapper.toDto(cita);
    }

    @Override
    public List<CitaDto> getAllCitas() {
        List<Cita> citas = citaRepository.findAll();
        return citaMapper.toDtoList(citas);
    }

    @Override
    public List<CitaDto> filterCitas(CitaFilterRequest payload) {
        List<Cita> citas = citaRepository.findAll();
        
        List<Cita> citasFiltered = citas.stream()
            .filter(cita -> payload.getFecha() == null || payload.getFecha().equals(cita.getFecha())).toList();
        return  citaMapper.toDtoList(citas);
    }

    @Override
    public void deleteCita(int id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<TratamientoDto> joinTratamientos(int id) {
        List<Tratamiento> tratamientos = tratamientoRepository.findByCitaId(id);
        return tratamientoMapper.toDtoList(tratamientos);
    }

    @Override
    public List<NotaDto> joinNotas(int id) {
        List<Nota> notas = notaRepository.findByCitaId(id);
        return notaMapper.toDtoList(notas);
    }
}
