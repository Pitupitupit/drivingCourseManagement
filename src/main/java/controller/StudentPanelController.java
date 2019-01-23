package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.ClientService;
import service.DriveService;
import service.InstructorService;
import service.UserService;

import java.security.Principal;

@Controller
public class StudentPanelController {

    @Autowired
    DriveService driveService;
    @Autowired
    UserService userService;
    @Autowired
    ClientService clientService;
    @Autowired
    InstructorService instructorService;

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String managementPanel(Model model, Principal principal){
        model.addAttribute("drives", driveService.listDrivesOfClient(clientService.getClientByUsername(principal.getName()).getId()));
        model.addAttribute("instructor", instructorService.getInstructorById(clientService.getClientByUsername(principal.getName()).getIdinstructor()));

        return "studentP";
    }
}
