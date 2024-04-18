package ssvv.example;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import domain.Nota;
import domain.Student;
import domain.Tema;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TemaTest {

    public static Service service;

    public static void createXML() {
        File xml = new File("studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Entitati>\n<student ID=\"1\">\n" +
                    "        <Nume>Ana</Nume>\n" +
                    "        <Grupa>221</Grupa>\n" +
                    "    </student>\n</Entitati>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File xml2 = new File("temeTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml2))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Teme>\n<tema ID=\"1\">\n" +
                    "        <Descriere>File</Descriere>\n" +
                    "        <Deadline>7</Deadline>\n" +
                    "        <Startline>6</Startline>\n" +
                    "    </tema>\n</Teme>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        File xml3 = new File("noteTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml3))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Entitati>\n" +
                    "    <nota IDStudent=\"1\" IDTema=\"1\">\n" +
                    "        <Nota>10.0</Nota>\n" +
                    "        <SaptamanaPredare>7</SaptamanaPredare>\n" +
                    "        <Feedback>done</Feedback>\n" +
                    "    </nota>\n</Entitati>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void setup() {
        createXML();

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studentiTest.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "temeTest.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "noteTest.xml");

        TemaTest.service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }
    
    @Test
    public void testValidTema() {
        assertThat(TemaTest.service.saveTema("2", "TestValid", 9, 7), is(1));
    }

    @Test
    public void testInvalidTema() {
        assertThat(TemaTest.service.saveTema("3", "TestInvalid", 20, 7), is(1));
    }

    @Test
    public void tc1() {
        assertThat(service.saveTema(null, "N/A", 2, 1), is(1));
    }

    @Test
    public void tc2() {
        assertThat(service.saveTema("1", "", 2, 1), is(1));
    }

    @Test
    public void tc3() {
        assertThat(service.saveTema("1", "N/A", -1, 2), is(1));
    }

    @Test
    public void tc4() {
        assertThat(service.saveTema("1", "N/A", 1, -1), is(1));
    }

    @Test
    public void tc5() {
        assertThat(service.saveTema("1", "N/A", 2, 1), is(0));
    }

    @Test
    public void tc6() {
        assertThat(service.saveTema("1", "N/A", 2, 1), is(0));
        assertThat(service.saveTema("1", "N/A", 2, 1), is(0));
    }
}