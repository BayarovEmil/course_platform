package org.example.camp_boost.service;

import org.example.base.TestSupport;
import org.example.camp_boost.mapper.CourseMapper;
import org.example.camp_boost.model.dto.request.CourseRequest;
import org.example.camp_boost.model.dto.response.CourseResponse;
import org.example.camp_boost.model.entity.Course;
import org.example.camp_boost.repository.MyCourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest extends TestSupport {
    @InjectMocks
    private CourseServiceImpl courseService;
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private MyCourseRepository myCourseRepository;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add_courseRequest_success() {
        // Given
        CourseRequest mockCourseRequest = generateCourseRequest();
        Course mockCourse = generateCourse();
        CourseResponse mockCourseResponse = generateCourseResponse();

        when(courseMapper.convertToCourse(mockCourseRequest)).thenReturn(mockCourse);
        when(myCourseRepository.save(mockCourse)).thenReturn(mockCourse);
        when(courseMapper.convertToResponse(mockCourse)).thenReturn(mockCourseResponse);

        // When
        CourseResponse courseResponse = courseService.add(mockCourseRequest);
        // Then
        assertNotNull(courseResponse, "Course Response must not be null");
        assertEquals(mockCourseResponse.courseName(), courseResponse.courseName());
        assertEquals(mockCourseResponse.teacherName(), courseResponse.teacherName());
        assertEquals(mockCourseResponse.description(), courseResponse.description());
        assertEquals(mockCourseResponse.rating(), courseResponse.rating());
        assertEquals(mockCourseResponse.price(), courseResponse.price());
        assertEquals(mockCourseResponse.duration(), courseResponse.duration());

        Mockito.verify(courseMapper, times(1)).convertToCourse(mockCourseRequest);
        Mockito.verify(myCourseRepository, times(1)).save(mockCourse);
        Mockito.verify(courseMapper, times(1)).convertToResponse(mockCourse);

    }

    @Test
    void getAllCourses_success() {
        // Given
        Integer page = 1;Integer size = 2;Integer lowPrice = 10;Integer highPrice = 15;
        Course course1 = generateCourse();
        Course course2 = generateCourse();
        List<Course> courses = List.of(course1,course2);
        Pageable pageable = PageRequest.of(0,2, Sort.by("id").ascending());
        Page<Course> pageCourses = new PageImpl<>(courses, pageable, courses.size());

        CourseResponse courseResponse1 = generateCourseResponse();
        CourseResponse courseResponse2 = generateCourseResponse();
        List<CourseResponse> courseResponses = List.of(courseResponse1, courseResponse2);

        Pageable pageable1 = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        when(myCourseRepository.findAllByPriceBetween(pageable,lowPrice,highPrice)).thenReturn(pageCourses);
        when(courseMapper.convertToResponses(pageCourses)).thenReturn(courseResponses);
        // When
        List<CourseResponse> result = courseService.getAllCourses(page,size,lowPrice,highPrice);
        // Then
        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals(courseResponses.get(0).teacherName(),result.get(0).teacherName());
        assertEquals(courseResponses.get(0).description(),result.get(0).description());
        assertEquals(courseResponses.get(0).courseName(),result.get(0).courseName());
        assertEquals(courseResponses.get(0).price(),result.get(0).price());
        assertEquals(courseResponses.get(0).duration(),result.get(0).duration());
        assertEquals(courseResponses.get(0).rating(),result.get(0).rating());

        verify(myCourseRepository, times(1)).findAllByPriceBetween(pageable1,lowPrice,highPrice);
        verify(courseMapper, times(1)).convertToResponses(pageCourses);

    }

    @Test
    void getAll_courses_success() {
        // Given
        Integer page = 1;Integer size = 2;
        Course course1 = generateCourse();
        Course course2 = generateCourse();
        List<Course> courses = List.of(course1,course2);
        Pageable pageable = PageRequest.of(0,2, Sort.by("id").descending());
        Page<Course> pageCourses = new PageImpl<>(courses, pageable, courses.size());

        CourseResponse courseResponse1 = generateCourseResponse();
        CourseResponse courseResponse2 = generateCourseResponse();
        List<CourseResponse> courseResponses = List.of(courseResponse1, courseResponse2);

        Pageable pageable1 = PageRequest.of(page - 1, size, Sort.by("id").descending());
        when(myCourseRepository.findAll(pageable)).thenReturn(pageCourses);
        when(courseMapper.convertToResponses(pageCourses)).thenReturn(courseResponses);
        // When
        List<CourseResponse> result = courseService.getAll(page,size);
        // Then
        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals(courseResponses.get(0).teacherName(),result.get(0).teacherName());
        assertEquals(courseResponses.get(0).description(),result.get(0).description());
        assertEquals(courseResponses.get(0).courseName(),result.get(0).courseName());
        assertEquals(courseResponses.get(0).price(),result.get(0).price());
        assertEquals(courseResponses.get(0).duration(),result.get(0).duration());
        assertEquals(courseResponses.get(0).rating(),result.get(0).rating());

        verify(myCourseRepository, times(1)).findAll(pageable1);
        verify(courseMapper, times(1)).convertToResponses(pageCourses);

    }

    @Test
    void update_course_success() {
        // Given
        CourseRequest mockCourseRequest = generateCourseRequest();
        CourseResponse mockCourseResponse = generateCourseResponse();
        Course mockCourse = generateCourse();

        when(courseMapper.convertToCourse(mockCourseRequest)).thenReturn(mockCourse);
        when(courseMapper.convertToResponse(mockCourse)).thenReturn(mockCourseResponse);

        // When
        CourseResponse courseResponse = courseService.update(mockCourseRequest);
        // Then
        assertNotNull(courseResponse, "Course Response must not be null");
        assertEquals(mockCourseResponse.teacherName(), courseResponse.teacherName());
        assertEquals(mockCourseResponse.description(), courseResponse.description());
        assertEquals(mockCourseResponse.rating(), courseResponse.rating());
        assertEquals(mockCourseResponse.price(), courseResponse.price());
        assertEquals(mockCourseResponse.duration(), courseResponse.duration());


        Mockito.verify(courseMapper, times(1)).convertToCourse(mockCourseRequest);
        Mockito.verify(myCourseRepository, times(1)).save(mockCourse);
        Mockito.verify(courseMapper, times(1)).convertToResponse(mockCourse);

    }

    @Test
    void delete_course_succes() {
        // Given
        Integer courseId = 1;

        // When
        courseService.delete(courseId);
        // Then
        Mockito.verify(myCourseRepository, times(1)).deleteById(courseId);
    }
}