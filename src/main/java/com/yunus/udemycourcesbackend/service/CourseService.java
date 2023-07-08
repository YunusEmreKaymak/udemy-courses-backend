package com.yunus.udemycourcesbackend.service;

import com.yunus.udemycourcesbackend.dto.CourseDto;
import com.yunus.udemycourcesbackend.dto.CourseDtoConverter;
import com.yunus.udemycourcesbackend.exception.CourseNotFoundException;
import com.yunus.udemycourcesbackend.model.Course;
import com.yunus.udemycourcesbackend.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private CourseDtoConverter courseDtoConverter;

    public CourseService(CourseRepository courseRepository, CourseDtoConverter courseDtoConverter) {
        this.courseRepository = courseRepository;
        this.courseDtoConverter = courseDtoConverter;
    }

    public List<CourseDto> getAllCourses() {
        List<Course> allCourses = courseRepository.findAll();
        List<CourseDto> allCoursesDto = allCourses.stream().map(course -> courseDtoConverter.convert(course)).toList();
        return allCoursesDto;
    }

    public CourseDto addCourse(Course course) {
        return courseDtoConverter.convert(courseRepository.save(course));
    }

    public CourseDto getCourseByName(String name) {
        if (courseRepository.existsCourseByName(name)) {
            Course courseByName = courseRepository.findCourseByName(name);
            CourseDto courseDto = courseDtoConverter.convert(courseByName);
            return courseDto;
        } else {
            throw new CourseNotFoundException("Course not found by name: " + name);
        }
    }

    @Transactional
    public Boolean deleteCourseByName(String name) {
        if (courseRepository.existsCourseByName(name)) {
            courseRepository.deleteCourseByName(name);
            return true;
        } else {
            throw new CourseNotFoundException("Course not found by name: " + name);
        }
    }

    public List<CourseDto> getCoursesByCategory(String category) {
        if(courseRepository.getAllByCategoryEquals(category).isEmpty()){
            throw new CourseNotFoundException("Course not found by category: " + category);
        } else {
            return courseRepository.getAllByCategoryEquals(category)
                    .stream().map(course -> courseDtoConverter.convert(course)).toList();
        }
    }
}
