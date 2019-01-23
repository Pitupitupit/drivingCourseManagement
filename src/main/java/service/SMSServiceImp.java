package service;

import DAO.GroupSMSDAO;
import DAO.SMSDao;
import DAO.UserDAO;
import POJO.*;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class SMSServiceImp implements SMSService {
    @Autowired
    SMSDao smsDao;
    @Autowired
    UserService userService;
    @Autowired
    SSHClient sshClient;
    @Autowired
    InstructorService instructorService;

    @Autowired
    ClientService clientService ;
    @Autowired
    GroupSMSService groupSMSService;

    public void save(SMS sms) {
        smsDao.save(sms);
    }

    @Override
    public List<SMS> list() {
        return smsDao.list();
    }

    @Override
    public List<SMS> listCheckTime(int minutesBefore) {
        return smsDao.listCheckTime(minutesBefore);
    }

    @Override
    public void updateSent(SMS sms) {
        smsDao.updateSent(sms);
    }

    @Override
    public void sendSMSAll(User user, String text) {
        for(User u : userService.list()) {
            try {
                Session session = sshClient.startSession();
                Session.Command cmd = session.exec("termux-sms-send -n "+u.getTelephone()+" "+text);
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        GroupSMS groupSMS = new GroupSMS(user,user.getId(),text,null,"Do wszystkich");
        groupSMSService.save(groupSMS);
    }

    @Override
    public void sendSMSInstructors(User user, String text) {
        for(Instructor i : instructorService.list()) {
            try {
                Session session = sshClient.startSession();
                Session.Command cmd = session.exec("termux-sms-send -n "+i.getUser().getTelephone()+" "+text);
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        GroupSMS groupSMS = new GroupSMS(user,user.getId(),text,null,"Do instruktorów");
        groupSMSService.save(groupSMS);
    }

    @Override
    public void sendSMSClients(User user, String text) {
        for(Client c : clientService.list()) {
            try {
                Session session = sshClient.startSession();
                Session.Command cmd = session.exec("termux-sms-send -n "+c.getUser().getTelephone()+" "+text);
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        GroupSMS groupSMS = new GroupSMS(user,user.getId(),text,null,"Do klientów");
        groupSMSService.save(groupSMS);
    }


}
