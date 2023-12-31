package com.yunus.udemycoursesbackend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunus.udemycoursesbackend.dto.CourseDto;
import com.yunus.udemycoursesbackend.exception.CourseNotFoundException;

import com.yunus.udemycoursesbackend.model.Course;
import com.yunus.udemycoursesbackend.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
    }

    @Test
    void getCourses_success() throws Exception {
        CourseDto courseDto = new CourseDto(
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4,
                "IT");
        CourseDto courseDto2 = new CourseDto(
                "React",
                "Mike",
                4.3,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/React-icon.svg/1200px-React-icon.svg.png",
                4.7,
                "IT");

        ArrayList<CourseDto> courseDtoList = new ArrayList<>(Arrays.asList(courseDto, courseDto2));

        Mockito.when(courseService.getAllCourses()).thenReturn(courseDtoList);
        mockMvc.perform(get("/course")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name").value("React"));
    }

    @Test
    void getCourses_not_found() throws Exception {
        CourseDto courseDto = new CourseDto(
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4,
                "IT");

        Mockito.when(courseService.getCourseByName(courseDto.getName())).thenThrow(CourseNotFoundException.class);
        mockMvc.perform(get("/course/MySQL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCourse_success() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Course course = new Course(
                uuid,
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4,
                "IT");

        String requestBody = objectMapper.writeValueAsString(course);

        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void getCourseByName_success() throws Exception {
        CourseDto courseDto = new CourseDto(
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4,
                "IT");

        ArrayList<CourseDto> courseDtoList = new ArrayList<>(List.of(courseDto));

        Mockito.when(courseService.getCourseByName("MySQL")).thenReturn(courseDtoList);
        mockMvc.perform(get("/course/MySQL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("MySQL"));
    }

    @Test
    void getCourseByName_not_found() throws Exception {
        Mockito.when(courseService.getCourseByName("MySQL")).thenThrow(CourseNotFoundException.class);
        mockMvc.perform(get("/course/MySQL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCourseByName_success() throws Exception {
        Mockito.when(courseService.deleteCourseByName("MySQL")).thenReturn(true);
        mockMvc.perform(delete("/course/MySQL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCourseByName_not_found() throws Exception {
        Mockito.when(courseService.deleteCourseByName("MySQL")).thenThrow(CourseNotFoundException.class);
        mockMvc.perform(delete("/course/MySQL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCoursesByCategory_success() throws Exception {
        CourseDto courseDto = new CourseDto(
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4,
                "IT");
        CourseDto courseDto2 = new CourseDto(
                "React",
                "Mike",
                4.3,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/React-icon.svg/1200px-React-icon.svg.png",
                4.7,
                "IT");

        ArrayList<CourseDto> courseDtoList = new ArrayList<>(Arrays.asList(courseDto, courseDto2));

        Mockito.when(courseService.getCoursesByCategory("IT")).thenReturn(courseDtoList);
        mockMvc.perform(get("/course/category/IT")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name").value("React"));
    }

    @Test
    void getCoursesByCategory_not_found() throws Exception {
        Mockito.when(courseService.getCoursesByCategory("IT")).thenThrow(CourseNotFoundException.class);
        mockMvc.perform(get("/course/category/IT")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCourse_success() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Course course = new Course(
                uuid,
                "MySQL",
                "Jack",
                3.2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
                3.4,
                "IT");

        String requestBody = objectMapper.writeValueAsString(course);

        mockMvc.perform(put("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

//    @Test
//    void updateCourse_not_found() throws Exception {
//        String uuid = UUID.randomUUID().toString();
//        Course course = new Course(
//                uuid,
//                "MySQL",
//                "Jack",
//                3.2,
//                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Database-mysql.svg/1200px-Database-mysql.svg.png",
//                3.4,
//                "IT");
//
//        String requestBody = objectMapper.writeValueAsString(course);
//
//        Mockito.doThrow(CourseNotFoundException.class).when(courseService).updateCourse(course);
//        mockMvc.perform(put("/course")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isNotFound());
//    }


}