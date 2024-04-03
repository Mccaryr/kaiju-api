package com.kaiju.kaijuapi.service;

import com.kaiju.kaijuapi.entity.Course;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Service
public class CourseDetailsService {

    public enum Status {
        SUCCESS, FAILURE
    }
    private static List<Course> courses = new ArrayList<>();

    static {
        Course course1 = new Course(1, "Spring", "10 Steps");
        courses.add(course1);

        Course course2 = new Course(2, "Spring MVC", "10 Examples");
        courses.add(course2);

        Course course3 = new Course(3, "Spring Boot", "Most popular Spring Framework");
        courses.add(course3);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Course findById(int id) {
        for(Course course : courses) {
            if(course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    public Status deleteCourse(int id) {
        Iterator<Course> iterator = courses.iterator();

        while(iterator.hasNext()) {
            Course course = iterator.next();
            if(course.getId() == id) {
                iterator.remove();
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }


}
