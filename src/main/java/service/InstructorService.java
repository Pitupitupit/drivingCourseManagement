package service;

import POJO.Client;
import POJO.Instructor;

import java.util.List;

public interface InstructorService {
    void addInstructor(Instructor instructor);
    List<Instructor> list();
    Instructor getInstructorById(long idinstructor);
    Instructor getInstructorByUserId(long idinstructor);
    List<Client> getClients(long idinstructor);
    void update(Instructor instructor);
}
