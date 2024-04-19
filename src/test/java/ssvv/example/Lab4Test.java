package ssvv.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Lab4Test {

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

        Lab4Test.service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @AfterAll
    public static void removeXML() {
        new File("studentiTest.xml").delete();
        new File("temeTest.xml").delete();
        new File("noteTest.xml").delete();
    }


    @Test
    public void testAddStudent() {
        assertThat(Lab4Test.service.saveStudent("70", "JohnDoe", 934), is(1));
    }

    @Test
    public void testAddAssignment() {
        assertThat(Lab4Test.service.saveTema("2", "test", 9, 7), is(1));
    }

    @Test
    public void testAddGrade() {
        assertThat(Lab4Test.service.saveNota("70", "2", 10, 7, "good"), is(1));
    }

    @Test
    public void testIntegration() {
        assertThat(Lab4Test.service.saveStudent("70", "Ion", 934), is(0));
        assertThat(Lab4Test.service.saveTema("2", "TestValid", 9, 7), is(0));
        assertThat(Lab4Test.service.saveNota("700", "2", 10, 7, "good"), is(-1));
    }
}
