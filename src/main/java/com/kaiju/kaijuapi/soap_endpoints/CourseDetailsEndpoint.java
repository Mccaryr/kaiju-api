package com.kaiju.kaijuapi.soap_endpoints;


import com.kaiju.kaijuapi.custom_exceptions.CourseNotFoundException;
import com.kaiju.kaijuapi.entity.Course;
import com.kaiju.kaijuapi.service.CourseDetailsService;
import com.soap_example.courses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {

    @Autowired
    CourseDetailsService service;

    @PayloadRoot(namespace = "http://soap-example.com/courses", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
        Course course = service.findById(request.getId());

        if(course == null) {
            throw new CourseNotFoundException("Invalid Course ID " + request.getId());
        }

        return getGetCourseDetailsResponse(course);
    }

    @PayloadRoot(namespace = "http://soap-example.com/courses", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        List<Course> courses = service.getCourses();

        for(Course course:courses) {
            CourseDetails courseDetails = new CourseDetails();

            courseDetails.setId(course.getId());
            courseDetails.setName(course.getName());
            courseDetails.setDescription(course.getDescription());
            response.getCourseDetails().add(courseDetails);
        }

        return response;
    }

    private static GetCourseDetailsResponse getGetCourseDetailsResponse(Course course) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        CourseDetails courseDetails = new CourseDetails();

        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        courseDetails.setDescription(course.getDescription());


        response.setCourseDetails(courseDetails);
        return response;
    }

    @PayloadRoot(namespace = "http://soap-example.com/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse processCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {
        CourseDetailsService.Status status = service.deleteCourse(request.getId());

        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        response.setStatus(mapStatus(status));

        return response;
    }

    private CourseDetailsService.Status mapStatus(CourseDetailsService.Status status) {
        if(status == CourseDetailsService.Status.FAILURE) {
            return CourseDetailsService.Status.FAILURE;
        }
        return CourseDetailsService.Status.SUCCESS;
    }
}
