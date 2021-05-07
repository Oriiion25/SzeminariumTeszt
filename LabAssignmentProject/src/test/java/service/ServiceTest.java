package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class ServiceTest {

    public static Service service;

    @org.junit.jupiter.api.BeforeAll
    public static void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    //tests for student entity for seminar 5
    @Test
    void addStudent() {
        Student student = new Student("4", "Mike", 116);
        int result = service.saveStudent(student.getID(), student.getName(), student.getGroup());
        service.deleteStudent(student.getID());
        assertEquals(1, result);
    }

    @Test
    void deleteStudent() {
        Student student = new Student("25", "Mikel", 266);
        System.out.println(service.saveStudent(student.getID(), student.getName(), student.getGroup()));
        int result = service.deleteStudent(student.getID());
        assertEquals(1, result);
    }

    @org.junit.jupiter.api.Test
    void findAllStudents() {
    }

    //tests for homework entity for seminar 5
    @Test
    void addHomework() {
        Homework hw = new Homework("6", "some easy homework", 10, 8);
        int result = service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        service.deleteHomework(hw.getID());
        assertEquals(1, result);
    }

    @Test
    void deleteHomework() {
        Homework hw = new Homework("7", "some easy homework", 10, 8);
        service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        int result = service.deleteHomework(hw.getID());
        assertEquals(1, result);
    }

    @org.junit.jupiter.api.Test
    void findAllHomework() {
    }

    @Test
    void assertAllTest(){
        Student s = new Student("99", "Johan", 533);
        assertAll(
                "student",
                () -> assertEquals("99", s.getID()),
                () -> assertEquals("Johan", s.getName())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 55, 533})
    void testStudentAddByGroup(int group){
        assumeTrue(group >= 0);
        Student s = new Student("99", "Johan", group);
        int result = service.saveStudent(s.getID(), s.getName(), s.getGroup());
        assertEquals(1, result);
        service.deleteStudent(s.getID());
    }


}