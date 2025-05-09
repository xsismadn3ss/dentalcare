package com.dentalcare.g5.main.service.cita.impl;

import com.dentalcare.g5.main.mapper.cita.NotaMapper;
import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.entity.cita.Nota;
import com.dentalcare.g5.main.model.payload.cita.NotaCreateRequest;
import com.dentalcare.g5.main.model.payload.cita.NotaFilterRequest;
import com.dentalcare.g5.main.model.payload.cita.NotaUpdateRequest;
import com.dentalcare.g5.main.repository.cita.NotaRepository;
import com.dentalcare.g5.main.service.cita.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotaServiceImpl implements NotaService {
    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private NotaMapper notaMapper;

    @Override
    public NotaDto addNota(NotaCreateRequest payload) {
        Nota nota = new Nota();
        nota.setTitulo(payload.getTitulo());
        nota.setDescripcion(payload.getDescripcion());
        nota.setCita_id(payload.getCita_id());
        Nota savedNota = notaRepository.save(nota);
        return notaMapper.toDto(savedNota);
    }

    @Override
    public NotaDto updateNota(NotaUpdateRequest payload, int id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));
        nota.setTitulo(payload.getTitulo());
        nota.setDescripcion(payload.getDescripcion());
        Nota updatedNota = notaRepository.save(nota);
        return notaMapper.toDto(updatedNota);
    }

    @Override
    public NotaDto getNotaById(int id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));
        return notaMapper.toDto(nota);
    }

    @Override
    public List<NotaDto> getAllNotas() {
        List<Nota> notas = notaRepository.findAll();
        return notaMapper.toDtoList(notas);
    }

    @Override
    public List<NotaDto> filterNotas(NotaFilterRequest payload) {
        List<Nota> notas = notaRepository.findAll();
        List<Nota> filteredNotas = notas.stream()
                .filter(nota -> {
                    // Filtro por ID
                    if (payload.getId() != null && !payload.getId().equals(nota.getId())) {
                        return false;
                    }
                    // Filtro por citaId
                    return payload.getCita_id() == null ||
                           payload.getCita_id().equals(nota.getCita_id());
                })
                .toList();
        return notaMapper.toDtoList(filteredNotas);
    }

    @Override
    public void deleteNota(int id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));
        notaRepository.delete(nota);
    }
}
