package service;

import DAO.DriveDAO;
import DAO.SMSDao;
import POJO.Drive;
import POJO.SMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class DriveServiceImp implements DriveService {

    @Autowired
    DriveDAO driveDAO;
    @Autowired
    SMSService smsService;

    public List<Drive> listDrivesOfClient(long clientId) {
        return driveDAO.listDrivesOfClient(clientId);
    }

    public List<Drive> listDrivesOfInstructor(long instructorId) {
        return driveDAO.listDrivesOfInstructor(instructorId);
    }

    public void save(Drive drive) {
        System.out.println(drive.getDate().toString()+" "+drive.getStart().toString());
        drive.setMyTimestamp(Timestamp.valueOf(drive.getDate().toString()+" "+drive.getStart().toString()));
        drive.setDone(Boolean.FALSE);

        String duration = Drive.calculateDifferenceBetweenTime(drive.getStart(), drive.getStop());
        Timestamp t = new Timestamp((drive.getMyTimestamp().getTime())-(long)Math.pow(10,3)*60*60);
        System.out.println("t.tostring: "+t.toString());
        System.out.println("timestamp t: "+t);

        drive.setDuration(Drive.calculateDifferenceBetweenTime(drive.getStart(),drive.getStop()));
        Drive newdrive = (Drive) driveDAO.save(drive);
        System.out.println("Nowe id to:"+newdrive.getId());

        SMS sms = new SMS(
                "Witaj "+drive.getClient().getUser().getFirstname()+". Przypomnienie o nauce jazdy. Data: "+drive.getDate()+", godzina: "+drive.getStart()+", czas trwania: "+duration+". Tel. instruktora: "+drive.getInstructor().getUser().getTelephone(),
                drive.getClient().getUser().getTelephone(),false,newdrive,newdrive.getId(), t);
        sms.setSent(Boolean.FALSE);

        System.out.println("sms.getMytimestamp: "+sms.getMyTimestamp());
        System.out.println("sms.getMytimestamp.toString: "+sms.getMyTimestamp().toString());
        System.out.println("sms.getSENT: "+sms.isSent());



        smsService.save(sms);




    }

    public void update(Drive drive) {
        driveDAO.update(drive);
    }

    @Override
    public List<Drive> listUndone() {
        return driveDAO.listUndone();
    }

    public List<Integer> sumDurationClient(long clientId) {
        return driveDAO.sumDurationClient(clientId);
    }

    public List<Integer> sumDurationInstructor(long instructorId) {
        return driveDAO.sumDurationInstructor(instructorId);
    }


}
