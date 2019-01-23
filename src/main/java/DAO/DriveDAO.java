package DAO;

import POJO.Client;
import POJO.Drive;

import java.util.List;

public interface DriveDAO {
    List<Drive> listDrivesOfClient(long clientId);
    List<Drive> listDrivesOfInstructor(long instructorId);
    Drive save(Drive drive);
    void update(Drive drive);
    List<Drive> listUndone();
    List<Integer> sumDurationClient(long clientId);
    List<Integer> sumDurationInstructor(long instructorId);
}
