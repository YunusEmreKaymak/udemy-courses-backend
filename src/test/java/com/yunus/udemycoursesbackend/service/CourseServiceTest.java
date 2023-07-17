package com.yunus.udemycoursesbackend.service;

import com.yunus.udemycoursesbackend.dto.CourseDto;
import com.yunus.udemycoursesbackend.dto.CourseDtoConverter;
import com.yunus.udemycoursesbackend.exception.CourseNotFoundException;
import com.yunus.udemycoursesbackend.model.Course;
import com.yunus.udemycoursesbackend.repository.CourseRepository;
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
import static org.mockito.Mockito.verify;
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
    void whenCourseNotFoundOnGetCourseByName_ItShouldThrowCourseNotFoundException() {
        when(courseRepository.existsCourseByName("Spring Boot")).thenReturn(false);
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseByName("Spring Boot"));
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

    @Test
    void whenCourseNotFoundOnDeleteCourseByName_ItShouldThrowCourseNotFoundException() {
        when(courseRepository.existsCourseByName("Spring Boot")).thenReturn(false);
        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourseByName("Spring Boot"));
    }

    @Test
    void whenGetAllByCategoryEqualsIsEmpty_ItShouldThrowCourseNotFoundException() {
        when(courseRepository.getAllByCategoryEquals("IT")).thenReturn(new ArrayList<>());
        assertThrows(CourseNotFoundException.class, () -> courseService.getCoursesByCategory("IT"));
    }

    @Test
    void whenGetCoursesByCategoryGivenValidCategory_ItShouldGiveCourseDtoListByCategory() {
        String uuid = UUID.randomUUID().toString();
        Course course = new Course(
                uuid,
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4, "IT");
        List<Course> courses = new ArrayList<>();
        courses.add(course);

        when(courseRepository.getAllByCategoryEquals("IT")).thenReturn(courses);
        when(courseDtoConverter.convert(course)).thenReturn(convert(course));

        assertEquals(courses.stream().map(course1 -> courseDtoConverter.convert(course1)).toList(), courseService.getCoursesByCategory("IT"));
    }

    @Test
    void whenCourseNotFoundOneUpdateCourse_ItShouldThrowCourseNotFoundException() {
        String uuid = UUID.randomUUID().toString();
        Course course = new Course(
                uuid,
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4, "IT");

        when(courseRepository.existsCourseByName("MySQL")).thenReturn(false);
        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(course));
    }

    @Test
    void whenValidOwnerNameIsChanged_itShouldUpdateExistingCourse() {
        String uuid = UUID.randomUUID().toString();
        Course course = new Course(
                uuid,
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4, "IT");
        Course updatedCourse = new Course();
        updatedCourse.setName("MySQL");
        updatedCourse.setOwnerName("Owner Name");


        when(courseRepository.existsCourseByName("MySQL")).thenReturn(true);
        when(courseRepository.findCourseByName(course.getName())).thenReturn(course);
        courseService.updateCourse(updatedCourse);

        verify(courseRepository).save(course);
    }


    public CourseDto convert(Course course) {
        return new CourseDto(course.getName(), course.getOwnerName(), course.getPrice(), course.getImageUrl(), course.getRate(), course.getCategory());
    }
}