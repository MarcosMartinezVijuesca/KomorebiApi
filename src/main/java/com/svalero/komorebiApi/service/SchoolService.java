package com.svalero.komorebiApi.service;

import com.svalero.komorebiApi.domain.School;
import com.svalero.komorebiApi.domain.dto.SchoolInDto;
import com.svalero.komorebiApi.domain.dto.SchoolOutDto;
import com.svalero.komorebiApi.exception.SchoolNotFoundException;
import com.svalero.komorebiApi.repository.SchoolRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<SchoolOutDto> findAll(String name, String city) {
        List<School> schools;

        if (name == null && city == null) {
            schools = schoolRepository.findAll();
        } else {
            schools = schoolRepository.findByFilters(name, city);
        }

        return modelMapper.map(schools, new TypeToken<List<SchoolOutDto>>() {}.getType());
    }

    public SchoolOutDto findById(long id) throws SchoolNotFoundException {
        School school = schoolRepository.findById(id)
                .orElseThrow(SchoolNotFoundException::new);
        return modelMapper.map(school, SchoolOutDto.class);
    }

    public SchoolOutDto addSchool(SchoolInDto schoolInDto){
        School school = modelMapper.map(schoolInDto, School.class);

        School newSchool = schoolRepository.save(school);
        return modelMapper.map(newSchool, SchoolOutDto.class);
    }

    public SchoolOutDto modifyActivity(long id, SchoolInDto schoolInDto) throws SchoolNotFoundException {
        School school = schoolRepository.findById(id)
                .orElseThrow(SchoolNotFoundException::new);

        modelMapper.map(schoolInDto, school);
        school.setId(id);

        School modifiedSchool = schoolRepository.save(school);
        return modelMapper.map(modifiedSchool, SchoolOutDto.class);
    }

    public void deleteActivity(long id) throws SchoolNotFoundException {
        School school = schoolRepository.findById(id)
                .orElseThrow(SchoolNotFoundException::new);
        schoolRepository.delete(school);
    }
}
