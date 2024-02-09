package com.analysis.analysis.controller;

import com.analysis.analysis.model.WriterEntity;
import com.analysis.analysis.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/writers")
public class WriterController {

    @Autowired
    private WriterService writerService;

    @PostMapping
    public ResponseEntity<WriterEntity> create(@RequestBody WriterEntity writer) {
        writerService.create(writer);
        return ResponseEntity.ok(writer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WriterEntity> update(@PathVariable("id") Long id, @RequestBody WriterEntity writerEntity) {
        writerService.update(writerEntity, id);
        return ResponseEntity.ok(writerEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        writerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WriterEntity> getById(@PathVariable Long id) {
        WriterEntity writer = writerService.findByWriterId(id);
        if (writer != null) {
            return ResponseEntity.ok(writer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<WriterEntity>> getAll() {
        return ResponseEntity.ok(writerService.getAll());
    }


}
