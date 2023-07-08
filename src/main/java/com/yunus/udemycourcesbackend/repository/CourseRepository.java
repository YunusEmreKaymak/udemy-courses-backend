package com.yunus.udemycourcesbackend.repository;

import com.yunus.udemycourcesbackend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    public Course findCourseByName(String name);

    public Boolean existsCourseByName(String name);

    public void deleteCourseByName(String name);

    public List<Course> getAllByCategoryEquals(String category);
}