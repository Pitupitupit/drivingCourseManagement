package DAO;

import POJO.Client;
import POJO.Instructor;

import java.util.List;

public interface InstructorDAO {
    public void save(Instructor instructor);
    public List<Instructor> list();
    public Instructor getInstructorById(long idinstructor);
    public Instructor getInstructorByUserId(long idinstructor);
    public List<Client> getClients(long idinstructor);
    public void update(Instructor instructor);
}
