package org.example.base;

import org.example.camp_boost.model.dto.request.CourseRequest;
import org.example.camp_boost.model.dto.response.CourseResponse;
import org.example.camp_boost.model.entity.Course;

public class TestSupport {
    public static Course generateCourse() {
        return new Course(
                1,"Course name","Teacher name","Description",
                25,9.99,4.5,12
        );
    }

    public static CourseRequest generateCourseRequest() {
        return new CourseRequest(
                "Course name","Teacher name","Description",
                9.99,12
        );
    }

    public static CourseResponse generateCourseResponse() {
        return new CourseResponse(
                "Course name","Teacher name","Description",
                25,9.99,4.5,12
        );
    }
}
