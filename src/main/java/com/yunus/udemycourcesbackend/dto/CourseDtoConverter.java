package com.yunus.udemycourcesbackend.dto;

import com.yunus.udemycourcesbackend.model.Course;

public class CourseDtoConverter {
    public CourseDto convert(Course course){
        return new CourseDto(course.getName(), course.getOwnerName(), course.getPrice(), course.getImageUrl(), course.getRate());
    }
}
