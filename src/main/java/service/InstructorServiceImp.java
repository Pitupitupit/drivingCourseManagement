package service;

import DAO.ClientDAO;
import DAO.InstructorDAO;
import POJO.Client;
import POJO.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InstructorServiceImp implements InstructorService {

    @Autowired
    InstructorDAO instructorDAO;

    public void addInstructor(Instructor instructor) {
        instructorDAO.save(instructor);
    }

    public List<Instructor> list() {
        return instructorDAO.list();
    }

    public Instructor getInstructorById(long idinstructor) {
        return instructorDAO.getInstructorById(idinstructor);
    }

    public Instructor getInstructorByUserId(long idinstructor) {
        return instructorDAO.getInstructorByUserId(idinstructor);
    }

    public List<Client> getClients(long idinstructor) {
        return instructorDAO.getClients(idinstructor);
    }

    public void update(Instructor instructor){
        instructorDAO.update(instructor);
    }
}
