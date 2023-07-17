package com.yunus.udemycoursesbackend.service;

import com.yunus.udemycoursesbackend.dto.CourseDto;
import com.yunus.udemycoursesbackend.dto.CourseDtoConverter;
import com.yunus.udemycoursesbackend.exception.CourseNotFoundException;
import com.yunus.udemycoursesbackend.model.Course;
import com.yunus.udemycoursesbackend.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseDtoConverter courseDtoConverter;

    public CourseService(CourseRepository courseRepository, CourseDtoConverter courseDtoConverter) {
        this.courseRepository = courseRepository;
        this.courseDtoConverter = courseDtoConverter;
    }

    public List<CourseDto> getAllCourses() {
        List<Course> allCourses = courseRepository.findAll();
        return allCourses.stream().map(courseDtoConverter::convert).toList();
    }

    public CourseDto addCourse(Course course) {
        return courseDtoConverter.convert(courseRepository.save(course));
    }

        public List<CourseDto> getCourseByName(String name) {
        if (courseRepository.existsCourseByName(name)) {
            Course courseByName = courseRepository.findCourseByName(name);
            List<CourseDto> courseDtos = new ArrayList<>();
            courseDtos.add(courseDtoConverter.convert(courseByName));
            return courseDtos;
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
        if (courseRepository.getAllByCategoryEquals(category).isEmpty()) {
            throw new CourseNotFoundException("Course not found by category: " + category);
        } else {
            return courseRepository.getAllByCategoryEquals(category)
                    .stream().map(courseDtoConverter::convert).toList();
        }
    }

    public void updateCourse(Course course) {
        if (courseRepository.existsCourseByName(course.getName())) {
            Course courseByName = courseRepository.findCourseByName(course.getName());
            if (course.getOwnerName() != null) {
                courseByName.setOwnerName(course.getOwnerName());
            }
            if (course.getPrice() != null) {
                courseByName.setPrice(course.getPrice());
            }
            if (course.getImageUrl() != null) {
                courseByName.setImageUrl(course.getImageUrl());
            }
            if (course.getRate() != null) {
                courseByName.setRate(course.getRate());
            }
            if (course.getCategory() != null) {
                courseByName.setCategory(course.getCategory());
            }
            System.out.println(courseByName);
            courseRepository.save(courseByName);
        } else {
            throw new CourseNotFoundException("Course not found on update: " + course.getName());
        }
    }
}
