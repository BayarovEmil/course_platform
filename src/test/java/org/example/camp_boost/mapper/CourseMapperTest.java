package org.example.camp_boost.mapper;

import org.example.base.TestSupport;
import org.example.camp_boost.model.dto.response.CourseResponse;
import org.example.camp_boost.model.entity.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseMapperTest extends TestSupport {
    @InjectMocks
    private CourseMapper courseMapper;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convert_to_course_success() {
        // When
        Course mockCourse = courseMapper.convertToCourse(generateCourseRequest());
        // Then
        assertNotNull(mockCourse);
        assertEquals(generateCourseRequest().courseName(), mockCourse.getCourseName());
        assertEquals(generateCourseRequest().teacherName(), mockCourse.getTeacherName());
        assertEquals(generateCourseRequest().description(), mockCourse.getDescription());
        assertEquals(generateCourseRequest().price(), mockCourse.getPrice());
        assertEquals(generateCourseRequest().duration(), mockCourse.getDuration());
    }

    @Test
    void convert_to_response_success() {
        // Given
        Course mockCourse = generateCourse();
        CourseResponse mockResponse = generateCourseResponse();
        // When
        CourseResponse courseResponse = courseMapper.convertToResponse(mockCourse);
        // Then
        assertNotNull(courseResponse);
        assertEquals(mockResponse.courseName(), courseResponse.courseName());
        assertEquals(mockResponse.teacherName(), courseResponse.teacherName());
        assertEquals(mockResponse.description(), courseResponse.description());
        assertEquals(mockResponse.price(), courseResponse.price());
        assertEquals(mockResponse.duration(), courseResponse.duration());
        assertEquals(mockResponse.rating(), courseResponse.rating());
    }

//    @Test
    void convert_to_responses_success() {
        // GIVEN: Test üçün Course obyektləri yaradılır
        Course course1 = generateCourse();
        Course course2 = generateCourse();
        List<Course> courseList = List.of(course1, course2);
        Page<Course> coursePage = new PageImpl<>(courseList);

        // Mocking
        CourseMapper courseMapper = Mockito.spy(new CourseMapper());

        doReturn(generateCourse()).when(courseMapper).convertToResponse(course1);
        doReturn(generateCourse()).when(courseMapper).convertToResponse(course2);

        // When
        List<CourseResponse> coursesResponse = courseMapper.convertToResponses(coursePage);

        // Then
        assertNotNull(coursesResponse);
        assertEquals(2, coursesResponse.size());
        assertEquals(generateCourseResponse().courseName(), coursesResponse.get(0).courseName());
        assertEquals(generateCourseResponse().teacherName(), coursesResponse.get(0).teacherName());
        assertEquals(generateCourseResponse().description(), coursesResponse.get(0).description());
        assertEquals(generateCourseResponse().price(), coursesResponse.get(0).price());
        assertEquals(generateCourseResponse().duration(), coursesResponse.get(0).duration());
        assertEquals(generateCourseResponse().rating(), coursesResponse.get(0).rating());
        assertEquals(generateCourseResponse().courseName(), coursesResponse.get(1).courseName());
        assertEquals(generateCourseResponse().teacherName(), coursesResponse.get(1).teacherName());
        assertEquals(generateCourseResponse().description(), coursesResponse.get(1).description());

        verify(courseMapper, times(1)).convertToResponse(any(Course.class));

    }
}