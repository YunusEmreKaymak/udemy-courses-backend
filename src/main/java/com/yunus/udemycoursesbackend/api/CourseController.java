package com.yunus.udemycoursesbackend.api;

import com.yunus.udemycoursesbackend.dto.CourseDto;
import com.yunus.udemycoursesbackend.model.Course;
import com.yunus.udemycoursesbackend.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@CrossOrigin("*")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public List<CourseDto> getCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping()
    public void addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @GetMapping("/{name}")
    public List<CourseDto> getCourseByName(@PathVariable("name") String name) {
        return courseService.getCourseByName(name);
    }

    @DeleteMapping("{name}")
    public Boolean deleteCourseByName(@PathVariable("name") String name) {
        return courseService.deleteCourseByName(name);
    }

    @GetMapping("/category/{category}")
    public List<CourseDto> getCoursesByCategory(@PathVariable("category") String category) {
        return courseService.getCoursesByCategory(category);
    }

    @PutMapping
    public void updateCourse(@RequestBody Course course) {
        courseService.updateCourse(course);
    }
}
