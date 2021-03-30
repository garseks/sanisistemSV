/*
 * The MIT License
 *
 * Copyright 2021 Oscar.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.ausiasmarch.sanisistem.api;

import net.ausiasmarch.sanisistem.entity.EspecialidadEntity;
import net.ausiasmarch.sanisistem.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/especialidad")
public class EspecialidadController {

    @Autowired
    EspecialidadRepository oEspecialidadRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        if (oEspecialidadRepository.existsById(id)) {
            return new ResponseEntity<>(oEspecialidadRepository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oEspecialidadRepository.count() <= 1000) {
            return new ResponseEntity<>(oEspecialidadRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.PAYLOAD_TOO_LARGE);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody EspecialidadEntity oEspecialidadEntity) {
        if (oEspecialidadEntity.getId() == null) {
            return new ResponseEntity<>(oEspecialidadRepository.save(oEspecialidadEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0, HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody EspecialidadEntity oEspecialidadEntity) {

        oEspecialidadEntity.setId(id);
        if (oEspecialidadRepository.existsById(id)) {
            return new ResponseEntity<>(oEspecialidadRepository.save(oEspecialidadEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0L, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {

        oEspecialidadRepository.deleteById(id);

        if (oEspecialidadRepository.existsById(id)) {
            return new ResponseEntity<>(id, HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<>(0L, HttpStatus.OK);
        }

    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC) Pageable oPageable) {

        Page<EspecialidadEntity> oPage = oEspecialidadRepository.findAll(oPageable);
        return new ResponseEntity<>(oPage, HttpStatus.OK);
    }
}
