package com.svalero.komorebiApi.controller;

import com.svalero.komorebiApi.domain.Project;
import com.svalero.komorebiApi.domain.dto.ProjectInDto;
import com.svalero.komorebiApi.domain.dto.ProjectOutDto;
import com.svalero.komorebiApi.exception.ProjectNotFoundException;
import com.svalero.komorebiApi.service.ProjectService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectOutDto>> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                                      @RequestParam(value = "description", defaultValue = "") String description) {
        logger.info("BEGIN getAll");
        List<ProjectOutDto> projects = projectService.getAll(name, description);
        logger.info("END getAll");
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable long projectId) throws ProjectNotFoundException {
        logger.info("BEGIN getProject");
        Project project = projectService.get(projectId);
        logger.info("END getProject");
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/schools/{schoolId}/projects")
    public ResponseEntity<ProjectOutDto> addProject(@PathVariable long schoolId, @Valid @RequestBody ProjectInDto project) throws SchoolNotFoundException {
        logger.info("BEGIN addProject");
        ProjectOutDto newProject = projectService.add(schoolId, project);
        logger.info("END addProject");
        return new ResponseEntity<>(newProject, HttpStatus.OK);
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ProjectOutDto> modifyProject(@PathVariable long projectId, @Valid @RequestBody ProjectInDto project) throws ProjectNotFoundException {
        logger.info("BEGIN modifyProject");
        ProjectOutDto modifiedProject = projectService.modify(projectId, project);
        logger.info("END modifyProject");
        return new ResponseEntity<>(modifiedProject, HttpStatus.OK);
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<ProjectOutDto> removeProject(@PathVariable long projectId) throws ProjectNotFoundException {
        logger.info("BEGIN removeProject");
        projectService.remove(projectId);
        logger.info("END removeProject");
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSwimmerNotFoundException(ProjectNotFoundException exception) {
        ErrorResponse error = ErrorResponse.generalError(404, exception.getMessage());
        logger.error(error.getMessage(), exception);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(SchoolNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClubNotFoundException(SchoolNotFoundException exception) {
        ErrorResponse error = ErrorResponse.generalError(404, exception.getMessage());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> MethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(ErrorResponse.validationError(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse error = ErrorResponse.generalError(500, "Internal Server Error");
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


