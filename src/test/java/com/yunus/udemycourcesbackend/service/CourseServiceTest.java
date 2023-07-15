package com.yunus.udemycourcesbackend.service;

import com.yunus.udemycourcesbackend.dto.CourseDto;
import com.yunus.udemycourcesbackend.dto.CourseDtoConverter;
import com.yunus.udemycourcesbackend.model.Course;
import com.yunus.udemycourcesbackend.repository.CourseRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseDtoConverter courseDtoConverter;
    @InjectMocks
    private CourseService courseService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCourses_ReturnsCourseDto() {
        String uuid = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();
        Course course = new Course(
                uuid,
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4,
                "IT");
        Course course2 = new Course(
                uuid2,
                "React",
                "Mike",
                4.3,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/React-icon.svg/1200px-React-icon.svg.png",
                4.7,
                "IT");

        List<CourseDto> coursesDto = new ArrayList<>();
        coursesDto.add(convert(course));
        coursesDto.add(convert(course2));
        when(courseDtoConverter.convert(course)).thenReturn(coursesDto.get(0));
        when(courseDtoConverter.convert(course2)).thenReturn(coursesDto.get(1));
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course, course2));

        List<CourseDto> allCourses = courseService.getAllCourses();

        assertEquals(allCourses, coursesDto);
    }

    @Test
    public void addCourse_shouldSaveCourse() {
        String uuid = UUID.randomUUID().toString();
        Course course = new Course(
                uuid,
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4,
                "IT"
        );

        when(courseRepository.save(course)).thenReturn(course);
        when(courseDtoConverter.convert(course)).thenReturn(convert(course));
        assertNotNull(courseService.addCourse(course));
    }

    @Test
    public void getCourseByName_shouldReturnTheCourse() {
        String uuid = UUID.randomUUID().toString();
        Course course = new Course(
                uuid,
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4,
                "IT");
        when(courseRepository.existsCourseByName(course.getName())).thenReturn(true);
        when(courseRepository.findCourseByName(course.getName())).thenReturn(course);
        when(courseDtoConverter.convert(course)).thenReturn(convert(course));

        assertNotNull(courseService.getCourseByName(course.getName()));
    }

    @Test
    public void deleteCourseByName_shouldDeleteTheCourse() {
        String uuid = UUID.randomUUID().toString();
        Course course = new Course(
                uuid,
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4, "IT");
        when(courseRepository.existsCourseByName(course.getName())).thenReturn(true);

        assertEquals(courseService.deleteCourseByName(course.getName()), true);

    }

    public CourseDto convert(Course course) {
        return new CourseDto(course.getName(), course.getOwnerName(), course.getPrice(), course.getImageUrl(), course.getRate(), course.getCategory());
    }
}