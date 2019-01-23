package controller;

import POJO.Client;
import POJO.GroupSMS;
import POJO.Instructor;
import POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import service.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller

public class ManagementPanelController {

    @Autowired
    UserService userService;

    @Autowired
    ClientService clientService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    GroupSMSService groupSMSService;

    @Autowired
    SMSService smsService;

    @RequestMapping(value = "/management", method = RequestMethod.GET)
    public String managementPanel(Model model){
        model.addAttribute("clients", clientService.list());
        model.addAttribute("instructors", instructorService.list());
        return "managementP";
    }

    @RequestMapping(value= "/management/{id}", method = RequestMethod.POST)
    public String processDelete(@PathVariable long id){
        userService.setInactive(id);
        return "redirect:/management";
    }

    //nowy klient
    @RequestMapping(value = "/management/register/client", method = RequestMethod.GET)
    public String getFormForClient(Model model){

        model.addAttribute("new_client", new Client());
        model.addAttribute("instructors", instructorService.list());
        return "register-client";
    }

    /**
	 *
	 * @param new_client
	 * @param errors
	 */
	@RequestMapping(value="/management/register/client", method=RequestMethod.POST)
    public String processFormForClient(@Valid
	@ModelAttribute("new_client")Client new_client, Errors errors){
        if(errors.hasErrors()){
            System.out.println("error:"+errors.getAllErrors());
            return "register-client";
        }
        new_client.getUser().setRole("ROLE_CLIENT");
        new_client.setInstructor(instructorService.getInstructorById(new_client.getIdinstructor()));
        clientService.addClient(new_client);

        return "redirect:/management";
    }

    //nowy instruktor
    @RequestMapping(value = "/management/register/instructor", method = RequestMethod.GET)
    public String getFormForInstructor(Model model){

        model.addAttribute("new_instructor", new Instructor());
        return "register-instructor";
    }

    /**
	 *
	 * @param new_instructor
	 * @param errors
	 */
	@RequestMapping(value="/management/register/instructor", method=RequestMethod.POST)
    public String processFormForInstructor(@Valid
	@ModelAttribute("new_instructor")Instructor new_instructor, Errors errors){
        if(errors.hasErrors()){
            System.out.println("error:"+errors.getAllErrors());
            return "register-instructor";
        }
        new_instructor.getUser().setRole("ROLE_INSTRUCTOR");
        instructorService.addInstructor(new_instructor);

        return "redirect:/management";
    }

    @RequestMapping(value="/groupSMS", method = RequestMethod.GET)
    public String groupSMS(Model model){

        model.addAttribute("groupSMSlist", groupSMSService.list());
        model.addAttribute("newGroupSMS", new GroupSMS());

        return "managementSMS";
    }

    /**
	 *
	 * @param principal
	 * @param groupSMS
	 * @param model
	 */
	@RequestMapping(value="/groupSMS", method=RequestMethod.POST)
    public String groupSMSprocessNew(Principal principal, @ModelAttribute("newGroupSMS")GroupSMS groupSMS, Model model) {
        System.out.println("typ: "+groupSMS.getType());
        if(groupSMS.getType().equals("a")){
            smsService.sendSMSAll(userService.getUserByUsername(principal.getName()), groupSMS.getText());

        } else if(groupSMS.getType().equals("b")){
            smsService.sendSMSClients(userService.getUserByUsername(principal.getName()), groupSMS.getText());

        } else if(groupSMS.getType().equals("c")){
            smsService.sendSMSInstructors(userService.getUserByUsername(principal.getName()), groupSMS.getText());

        }



        /*switch (groupSMS.getType()) {
            case "a":
                smsService.sendSMSAll(userService.getUserByUsername(principal.getName()), groupSMS.getText());
                //groupSMSService.save(groupSMS);
                break; // optional

            case "b":
                smsService.sendSMSClients(userService.getUserByUsername(principal.getName()), groupSMS.getText());
                //groupSMSService.save(groupSMS);
                break; // optional

            case "c":
                smsService.sendSMSInstructors(userService.getUserByUsername(principal.getName()), groupSMS.getText());
                //groupSMSService.save(groupSMS);
                break; // optional
        }*/
        return "redirect:/groupSMS";
    }

}
