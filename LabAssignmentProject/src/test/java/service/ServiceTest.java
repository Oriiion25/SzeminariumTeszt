package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.Test;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Student student = new Student("23","Mike",123);
        Iterable<Student> students = service.findAllStudents();
        long firstSize = students.spliterator().getExactSizeIfKnown();
        service.saveStudent(student.getID(),student.getName(), student.getGroup());
        long secondSize = students.spliterator().getExactSizeIfKnown();
        service.deleteStudent(student.getID());
        assertEquals(secondSize,firstSize+1);
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
        Homework hw = new Homework("6", "some easy homework", 10, 8);
        Iterable<Homework> homeworks = service.findAllHomework();
        long firstSize = homeworks.spliterator().getExactSizeIfKnown();
        service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        long secondSize = homeworks.spliterator().getExactSizeIfKnown();
        service.deleteHomework(hw.getID());
        assertEquals(firstSize+1,secondSize);
    }

    //szeminarium 6 teszt
    @Test
    void extendValid(){
        Homework hw = new Homework("6","to do",13,10);
        service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        int result = service.extendDeadline(hw.getID(), 1);
        service.deleteHomework(hw.getID());
        assertEquals(1,result);
    }

    @Test
    void extendInvalid(){
        Homework hw = new Homework("6","to do",14,10);
        service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        int result = service.extendDeadline(hw.getID(), 1);
        service.deleteHomework(hw.getID());
        assertEquals(1,result);
    }

}