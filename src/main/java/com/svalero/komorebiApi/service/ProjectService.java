package com.svalero.komorebiApi.service;

import com.svalero.komorebiApi.domain.Project;
import com.svalero.komorebiApi.domain.dto.ProjectInDto;
import com.svalero.komorebiApi.domain.dto.ProjectOutDto;
import com.svalero.komorebiApi.exception.ProjectNotFoundException;
import com.svalero.komorebiApi.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private ModelMapper modelMapper;


    public List<ProjectOutDto> getAll(String name, String description) {
        List<Project> projectList;

        if (name.isEmpty() && description.isEmpty()) {
            projectList = projectRepository.findAll();
        } else if (name.isEmpty() && !description.isEmpty()) {
            projectList = projectRepository.findByDescription(description);
        } else if (!name.isEmpty() && description.isEmpty()) {
            projectList = projectRepository.findByName(name);
        } else {
            projectList = projectRepository.findByNameAndDescription(name, description);
        }
//
        List<ProjectOutDto> projectOutDtos = modelMapper.map(projectList, new TypeToken<List<ProjectOutDto>>() {
        }.getType());
        return projectOutDtos;
    }

    public Project get(long id) throws ProjectNotFoundException {
        return projectRepository.findById(id)
                .orElseThrow(ProjectNotFoundException::new);
    }

    public ProjectOutDto add(long clubId, ProjectInDto projectInDto) throws SchoolNotFoundException {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(SchoolNotFoundException::new);

        Project project = modelMapper.map(projectInDto, Project.class);
        //si hubiese atributo de date swimmer.setRegistrationDate(LocalDate.now())-,
        project.setSchool(school);
        Project newProject = projectRepository.save(project);

        return modelMapper.map(newProject, ProjectOutDto.class);

    }

    public ProjectOutDto modify(long projectId,ProjectInDto projectInDto) throws ProjectNotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        modelMapper.map(projectInDto, project);
        projectRepository.save(project);
        return modelMapper.map(project, ProjectOutDto.class);
    }

    public void remove(long id) throws ProjectNotFoundException {
        projectRepository.findById(id)
                .orElseThrow(ProjectNotFoundException::new);
        projectRepository.deleteById(id);
    }
}
