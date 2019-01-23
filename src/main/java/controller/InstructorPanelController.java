package controller;

import POJO.Drive;
import POJO.Instructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.ClientService;
import service.DriveService;
import service.InstructorService;
import service.UserService;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.Principal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller
public class InstructorPanelController {

    @Autowired
    ClientService clientService;
    @Autowired
    DriveService driveService;
    @Autowired
    InstructorService instructorService;
    @Autowired
    UserService userService;
    @Autowired
    SessionFactory sessionFactory;

    @RequestMapping(value = "/instructor", method = RequestMethod.GET)
    public String managementPanel(Model model, Principal principal){

        model.addAttribute("clients", instructorService.getClients(instructorService.getInstructorByUserId(userService.getUserByUsername(principal.getName()).getId()).getId()));

        return "instructorP";
    }

    @RequestMapping(value="/dodajJazde", method = RequestMethod.GET)
    public String addDrive(Model model, Principal principal){
        model.addAttribute("drive", new Drive());
        model.addAttribute("clients", instructorService.getClients(instructorService.getInstructorByUserId(userService.getUserByUsername(principal.getName()).getId()).getId()));
        return "addDrive";
    }

    /**
	 *
	 * @param drive
	 * @param principal
	 */
	@RequestMapping(value="/dodajJazde", method=RequestMethod.POST)
    public String addDrive(@ModelAttribute("drive") Drive drive, Principal principal){
        drive.setStart(Time.valueOf(drive.startString+":00"));
        drive.setStop(Time.valueOf(drive.endString+":00"));
        System.out.println("DATA w addDrive: "+drive.getDate());
        drive.setClient(clientService.getClientByClientId(drive.getIdclient()));
        drive.setInstructor(instructorService.getInstructorByUserId(userService.getUserByUsername(principal.getName()).getId()));
        drive.setIdinstructor(drive.getInstructor().getId());

        driveService.save(drive);

        return "redirect:/instructor/plan";
    }

    @RequestMapping(value="/instructor/plan", method = RequestMethod.GET)
    public String planInstructor(Model model, Principal principal){
        //System.out.println("username: "+userService.getUserByUsername(principal.getName()).getUsername());
        //System.out.println("user id: "+userService.getUserByUsername(principal.getName()).getId());
       // System.out.println("instructor id: "+instructorService.getInstructorByUserId(userService.getUserByUsername(principal.getName()).getId()).getId());

        //model.addAttribute("drives", driveService.listDrivesOfInstructor(instructorService.getInstructorByUserId(userService.getUserByUsername(principal.getName()).getId()).getId()));
        model.addAttribute("drives", driveService.listDrivesOfInstructor(instructorService.getInstructorByUserId(userService.getUserByUsername(principal.getName()).getId()).getId()));
        return "planInstructor";
    }
}
