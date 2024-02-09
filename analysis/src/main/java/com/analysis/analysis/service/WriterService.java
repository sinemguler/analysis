package com.analysis.analysis.service;

import com.analysis.analysis.exception.WriterNotFoundException;
import com.analysis.analysis.model.WriterEntity;
import com.analysis.analysis.repository.WriterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WriterService {

    private WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {

        this.writerRepository = writerRepository;
    }

    public WriterEntity create(WriterEntity writer) {

        return writerRepository.save(writer);
    }

    public WriterEntity update(WriterEntity updateWriter, Long id) {
        Optional<WriterEntity> writerEntity = writerRepository.findById(id);
        if (writerEntity.isPresent()) {
            WriterEntity writer = writerEntity.get();
            writer.setSurname(updateWriter.getSurname());
            writer.setName(updateWriter.getName());
            writer.setArticle(updateWriter.getArticle());
            return writerRepository.save(writer);
        } else {
            throw new WriterNotFoundException("Writer not found");
        }
    }


    public void delete(Long id) {
        writerRepository.deleteById(id);
    }

    public WriterEntity findByWriterId(Long id) {
        Optional<WriterEntity> writer = writerRepository.findById(id);
        return writer.orElseThrow(() -> new WriterNotFoundException("Writer not found : " + id));
    }

    public List<WriterEntity> getAll() {
        return writerRepository.findAll();
    }
}
