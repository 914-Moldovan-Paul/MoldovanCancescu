package ssvv.example;

import domain.Student;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Before;
import repository.StudentXMLRepository;
import service.Service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private Service service;
    private StudentXMLRepository studentRepoMock;

    @Before
    public void setUp() {
        studentRepoMock = mock(StudentXMLRepository.class);
        service = new Service(studentRepoMock, null, null);
    }

    @org.junit.Test
    public void testSaveStudent_Success() {
        String id = "1";
        String name = "John Doe";
        int group = 123;
        Student student = new Student(id, name, group);
        when(studentRepoMock.save(student)).thenReturn(student);

        int result = service.saveStudent(id, name, group);

        assertEquals(0, result);
        verify(studentRepoMock, times(1)).save(student);
    }

    @org.junit.Test
    public void testSaveStudent_Failure() {
        String id = "1";
        String name = "John Doe";
        int group = 123;
        Student student = new Student(id, name, group);
        when(studentRepoMock.save(student)).thenReturn(null);

        int result = service.saveStudent(id, name, group);

        assertEquals(1, result);
        verify(studentRepoMock, times(1)).save(student);
    }
}
