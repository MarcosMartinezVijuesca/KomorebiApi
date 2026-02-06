package com.svalero.komorebiApi.controller;

import com.svalero.komorebiApi.domain.School;
import com.svalero.komorebiApi.domain.dto.SchoolInDto;
import com.svalero.komorebiApi.domain.dto.SchoolOutDto;
import com.svalero.komorebiApi.exception.SchoolNotFoundException;
import com.svalero.komorebiApi.service.SchoolService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.svalero.komorebiApi.domain.dto.ErrorResponse;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/schools")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    private final Logger logger = LoggerFactory.getLogger(SchoolController.class);

    @GetMapping
    public ResponseEntity<List<SchoolOutDto>> getAllSchools(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city) {

        logger.info("GET /schools params: name={}, city={}", name, city);
        return ResponseEntity.ok(schoolService.findAll(name, city));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolOutDto> getSchool(@PathVariable long id) throws SchoolNotFoundException {
        logger.info("GET /schools/{}", id);
        return ResponseEntity.ok(schoolService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SchoolOutDto> addSchool(@Valid @RequestBody SchoolInDto schoolInDto) {
        logger.info("POST /schools");
        return new ResponseEntity<>(schoolService.addSchool(schoolInDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolOutDto> modifySchool(@PathVariable long id, @Valid @RequestBody SchoolInDto schoolInDto)
            throws SchoolNotFoundException{
        logger.info("PUT /schools/{}", id);
        return ResponseEntity.ok(schoolService.modifyActivity(id, schoolInDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable long id) throws SchoolNotFoundException {
        logger.info("DELETE /schools/{}", id);
        schoolService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(SchoolNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSchoolNotFoundException(
            SchoolNotFoundException exception) {

        ErrorResponse error = ErrorResponse.generalError(404, exception.getMessage());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(
                ErrorResponse.validationError(errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse error = ErrorResponse.generalError(500, "Internal Server Error");
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

