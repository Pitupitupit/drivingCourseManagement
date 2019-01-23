package controller;

import POJO.Client;
import POJO.Instructor;
import POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.ClientService;
import service.DriveService;
import service.InstructorService;
import service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    ClientService clientService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    DriveService driveService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showProfile(Model model, Principal principal, Authentication authentication){

        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        if(user.getRole().equals("ROLE_CLIENT")){
            Client c = clientService.getClientByUsername(principal.getName());
            //User instructor = userService.getUserById(c.getIdinstructor());
            Instructor instructor = instructorService.getInstructorById(c.getInstructor().getId());
            model.addAttribute("instructor", instructor);
            model.addAttribute("client", c);
        }

        return "profile";
    }

    /**
	 *
	 * @param user
	 * @param model
	 * @param principal
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
    public String updateUserData(@ModelAttribute("user") User user, Model model, Principal principal){
        User userToUpdate = userService.getUserByUsername(principal.getName());
        userToUpdate.setTelephone(user.getTelephone());
        userToUpdate.setEmail(user.getEmail());
        System.out.println("user.getPass:"+user.getPassword());
        if(!user.getPassword().equals("")){
            userToUpdate.setPassword(User.hashPassword(user.getPassword()));
        }
        userService.updateUser(userToUpdate);

        return "redirect:/profile";
    }

    @RequestMapping(value="/{username}", method = RequestMethod.GET)
    public String showProfileOfUsername(Model model, @PathVariable String username){
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        if(user.getRole().equals("ROLE_CLIENT")){
            Client c = clientService.getClientByUsername(username);
            //User instructor = userService.getUserById(c.getIdinstructor());
            Instructor instructor = instructorService.getInstructorById(c.getInstructor().getId());
            model.addAttribute("instructor", instructor);
            model.addAttribute("client", c);
            model.addAttribute("drives", driveService.listDrivesOfClient(c.getId()));
            List<Integer> listIntegerClient = driveService.sumDurationClient(c.getId());
            model.addAttribute("hoursClient", listIntegerClient.get(0));
            model.addAttribute("minutesClient", listIntegerClient.get(1));
        }
        else if(user.getRole().equals("ROLE_INSTRUCTOR")){
            Instructor i = instructorService.getInstructorByUserId(user.getId());
            model.addAttribute("instructorROLE", i);

            model.addAttribute("clientsOfInstructor", instructorService.getClients(i.getId()));

            model.addAttribute("drives", driveService.listDrivesOfInstructor(i.getId()));
            List<Integer> listIntegerClient = driveService.sumDurationInstructor(i.getId());
            model.addAttribute("hoursInstructor", listIntegerClient.get(0));
            model.addAttribute("minutesInstructor", listIntegerClient.get(1));

        }

        return "profile-username";
    }

    @RequestMapping(value = "/{username}/edytuj", method = RequestMethod.GET)
    public String editProfileOfUsername(Model model, @PathVariable String username){
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        if(user.getRole().equals("ROLE_CLIENT")){
            Client c = clientService.getClientByUsername(username);
            model.addAttribute("instructors", instructorService.list());
            //User instructor = userService.getUserById(c.getIdinstructor());
            Instructor instructor = instructorService.getInstructorById(c.getInstructor().getId());
            model.addAttribute("instructor", instructor);
            model.addAttribute("client", c);
        }
        else if(user.getRole().equals("ROLE_INSTRUCTOR")){
            Instructor i = instructorService.getInstructorByUserId(user.getId());
            model.addAttribute("instructor", i);

            model.addAttribute("clientsOfInstructor", instructorService.getClients(i.getId()));
        }
         return "profile-username-edit";
    }

    /**
	 *
	 * @param model
	 * @param username
	 * @param client
	 * @param instructor
	 */
	@RequestMapping(value="/{username}/edytuj", method=RequestMethod.POST)
    public String processEditProfileOfUsername(Model model, @PathVariable String username, @ModelAttribute("client") Client client, @ModelAttribute("instructor") Instructor instructor){

        if(userService.getUserByUsername(username).getRole().equals("ROLE_CLIENT")) {
            Client clientToUpdate = clientService.getClientByUsername(username);
            clientToUpdate.getUser().setUsername(client.getUser().getUsername());
            clientToUpdate.getUser().setFirstname(client.getUser().getFirstname());
            clientToUpdate.getUser().setLastname(client.getUser().getLastname());
            clientToUpdate.getUser().setEmail(client.getUser().getEmail());
            clientToUpdate.getUser().setTelephone(client.getUser().getTelephone());
            clientToUpdate.setIdinstructor(client.getIdinstructor());
            clientToUpdate.setInstructor(instructorService.getInstructorById(client.getIdinstructor()));
            clientToUpdate.setCategory(client.getCategory());
            clientToUpdate.getUser().setRole("ROLE_CLIENT");
            client.setInstructor(instructorService.getInstructorById(client.getIdinstructor()));
            if (!client.getUser().getPassword().equals("")) {
                clientToUpdate.getUser().setPassword(User.hashPassword(client.getUser().getPassword()));
            }
            clientService.update(clientToUpdate);
        } else if(userService.getUserByUsername(username).getRole().equals("ROLE_INSTRUCTOR")){
            Instructor instructorToUpdate = instructorService.getInstructorByUserId(userService.getUserByUsername(username).getId());
            instructorToUpdate.getUser().setUsername(instructor.getUser().getUsername());
            instructorToUpdate.getUser().setFirstname(instructor.getUser().getFirstname());
            instructorToUpdate.getUser().setLastname(instructor.getUser().getLastname());
            instructorToUpdate.getUser().setEmail(instructor.getUser().getEmail());
            instructorToUpdate.getUser().setTelephone(instructor.getUser().getTelephone());
            instructorToUpdate.setCategories(instructor.getCategories());
            instructorToUpdate.getUser().setRole("ROLE_INSTRUCTOR");
            if (!instructor.getUser().getPassword().equals("")) {
                instructorToUpdate.getUser().setPassword(User.hashPassword(client.getUser().getPassword()));
            }
            instructorService.update(instructorToUpdate);
        }
        return "redirect:/profile/"+username;
    }
}

