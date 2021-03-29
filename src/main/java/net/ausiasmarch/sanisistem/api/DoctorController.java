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


import net.ausiasmarch.sanisistem.entity.DoctorEntity;
import net.ausiasmarch.sanisistem.repository.ConsultaRepository;
import net.ausiasmarch.sanisistem.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorRepository oDoctorRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        if (oDoctorRepository.existsById(id)) {
            return new ResponseEntity<>(oDoctorRepository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oDoctorRepository.count() <= 1000) {
            return new ResponseEntity<>(oDoctorRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.PAYLOAD_TOO_LARGE);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody DoctorEntity oDoctorEntity) {
        if (oDoctorEntity.getId() == null) {
            return new ResponseEntity<>(oDoctorRepository.save(oDoctorEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0, HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody DoctorEntity oDoctorEntity) {

        oDoctorEntity.setId(id);
        if (oDoctorRepository.existsById(id)) {
            return new ResponseEntity<>(oDoctorRepository.save(oDoctorEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0L, HttpStatus.NOT_MODIFIED);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
    
    oDoctorRepository.deleteById(id);

                if (oDoctorRepository.existsById(id)) {
                    return new ResponseEntity<>(id, HttpStatus.NOT_MODIFIED);
                } else {
                    return new ResponseEntity<>(0L, HttpStatus.OK);
                }
               
    }
}
