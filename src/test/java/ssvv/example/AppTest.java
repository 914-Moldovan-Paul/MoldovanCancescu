package ssvv.example;

import domain.Student;
import domain.Tema;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.mockito.Mockito.when;

/**
 * Unit test for simple App.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    @Mock
    private Student mockedStudent;

    @InjectMocks
    private StudentValidator studentValidator;

    @Mock
    private Tema mockedTema;

    @InjectMocks
    private TemaValidator temaValidator;

    @org.junit.Test(expected = ValidationException.class)
    public void testInvalidID() throws ValidationException {
        when(mockedStudent.getID()).thenReturn(null);
        studentValidator.validate(mockedStudent);
    }

    @org.junit.Test(expected = ValidationException.class)
    public void testEmptyID() throws ValidationException {
        when(mockedStudent.getID()).thenReturn("");
        studentValidator.validate(mockedStudent);
    }

    @org.junit.Test(expected = ValidationException.class)
    public void testInvalidName() throws ValidationException {
        when(mockedStudent.getID()).thenReturn("123");
        when(mockedStudent.getNume()).thenReturn(null);
        studentValidator.validate(mockedStudent);
    }

    @org.junit.Test(expected = ValidationException.class)
    public void testEmptyName() throws ValidationException {
        when(mockedStudent.getID()).thenReturn("123");
        when(mockedStudent.getNume()).thenReturn("");
        studentValidator.validate(mockedStudent);
    }

    @org.junit.Test(expected = ValidationException.class)
    public void testInvalidGroupLow() throws ValidationException {
        when(mockedStudent.getID()).thenReturn("123");
        when(mockedStudent.getNume()).thenReturn("John");
        when(mockedStudent.getGrupa()).thenReturn(100);
        studentValidator.validate(mockedStudent);
    }

    @org.junit.Test(expected = ValidationException.class)
    public void testInvalidGroupHigh() throws ValidationException {
        when(mockedStudent.getID()).thenReturn("123");
        when(mockedStudent.getNume()).thenReturn("John");
        when(mockedStudent.getGrupa()).thenReturn(1000);
        studentValidator.validate(mockedStudent);
    }

    @org.junit.Test
    public void testValidStudent() throws ValidationException {
        when(mockedStudent.getID()).thenReturn("123");
        when(mockedStudent.getNume()).thenReturn("John");
        when(mockedStudent.getGrupa()).thenReturn(500);
        studentValidator.validate(mockedStudent);
        // No exception should be thrown
    }
}
