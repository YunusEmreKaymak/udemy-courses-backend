package com.yunus.udemycoursesbackend.repository;

import com.yunus.udemycoursesbackend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    Course findCourseByName(String name);

    Boolean existsCourseByName(String name);

    void deleteCourseByName(String name);

    List<Course> getAllByCategoryEquals(String category);
}