package com.yunus.udemycourcesbackend.api;

import com.yunus.udemycourcesbackend.dto.CourseDto;
import com.yunus.udemycourcesbackend.model.Course;
import com.yunus.udemycourcesbackend.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@CrossOrigin("*")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public List<CourseDto> getCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping()
    public void addCourse(@RequestBody Course course){
        courseService.addCourse(course);
    }

    @GetMapping("/{name}")
    public CourseDto getCourseByName(@PathVariable("name") String name){
        return courseService.getCourseByName(name);
    }

    @DeleteMapping("{name}")
    public Boolean deleteCourseByName(@PathVariable("name") String name) {
        return courseService.deleteCourseByName(name);
    }

    @GetMapping("/category/{category}")
    public List<CourseDto> getCoursesByCategory(@PathVariable("category") String category){
        return courseService.getCoursesByCategory(category);
    }
}
