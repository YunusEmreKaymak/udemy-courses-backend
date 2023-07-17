package com.yunus.udemycoursesbackend.dto;

import com.yunus.udemycoursesbackend.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseDtoConverter {
    public CourseDto convert(Course course){
        return new CourseDto(course.getName(), course.getOwnerName(), course.getPrice(), course.getImageUrl(), course.getRate(), course.getCategory());
    }
}
