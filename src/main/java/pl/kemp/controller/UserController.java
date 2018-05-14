package pl.kemp.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.kemp.models.*;
import pl.kemp.services.SkillsService;
import pl.kemp.services.UserService;
import pl.kemp.services.implementation.LoginServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private SkillsService skillsService;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("apiURI", loginService.getApiURIFromSession());

        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("userNewDTO", new UserNewDTO());
        model.addAttribute("apiURI", new String());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(Model model,
                         @ModelAttribute UserNewDTO userNewDTO,
                         @ModelAttribute("apiURI") String apiURI,
                         HttpSession session) {
        session.setAttribute("apiURI", apiURI);
        UserCreatedDTO userCreated = userService.createUser(userNewDTO);
        model.addAttribute("userCreated", userCreated);
        return "userCreated";
    }

    @RequestMapping(value = "/changeApi", method = RequestMethod.POST)
    public String changeAPI(@ModelAttribute("apiURI") String apiURI,
                            HttpSession session) {
        session.setAttribute("apiURI", apiURI);
        return "redirect:/";
    }

    @RequestMapping(value = "/changeApi", method = RequestMethod.GET)
    public String changeAPI(Model model) {
        model.addAttribute("apiURI", loginService.getApiURIFromSession());
        return "changeApi";
    }

    @RequestMapping(value = "/searchUser", method = RequestMethod.GET)
    public String searchUser(Model model) {
        model.addAttribute("detailsFullDTO", new DetailsFullDTO());
        model.addAttribute("userId", new String());
        return "searchUser";
    }

    @RequestMapping(value = "/searchUser", method = RequestMethod.POST)
    public String showUser(Model model, @ModelAttribute("userId") String userId) {
        DetailsFullDTO userFull = userService.getAllUserDetailsById(userId);
        model.addAttribute("detailsFullDTO", userFull);
        return "searchUser";
    }

    @RequestMapping(value = "/editUserDetails", method = RequestMethod.GET)
    public String editUserDetails(Model model) {
        model.addAttribute("detailsNewDTO", new DetailsNewDTO());
        model.addAttribute("userId", new String());
        model.addAttribute("detailsId", new String());
        return "editUserDetails";
    }

    @RequestMapping(value = "/editUserDetails", method = RequestMethod.POST)
    public String updateUserDetails(Model model, @ModelAttribute("detailsNewDTO") DetailsNewDTO detailsNewDTO) {
        DetailsDTO detailsCreated = userService.updateUserDetails(detailsNewDTO);
        model.addAttribute("detailsNewDTO", detailsCreated);
        model.addAttribute("userId", new String());
        model.addAttribute("detailsId", new String());
        return "editUserDetails";
    }

    @RequestMapping(value = "/getUserDetails", method = RequestMethod.GET)
    public String redirectToEditUser() {
        return "redirect:/editUserDetails";
    }

    @RequestMapping(value = "/getUserDetails", method = RequestMethod.POST)
    public String checkUserDetails(Model model,
                                   @ModelAttribute("userId") String userId,
                                   @ModelAttribute("detailsId") String detailsId) {

        DetailsDTO details;
        model.addAttribute("userId", new String());
        model.addAttribute("detailsId", new String());


        if (!userId.equals(null) && !userId.equals("")) {
            details = userService.getDetailsByUserId(userId);
        } else if (!detailsId.equals(null) && !detailsId.equals("")) {
            details = userService.getDetailsById(detailsId);
        } else {
            model.addAttribute("message", "you have to type one of parameters at least");
            model.addAttribute("detailsNewDTO", new DetailsDTO());
            return "editUserDetails";
        }
        model.addAttribute("detailsNewDTO", details);
        return "editUserDetails";
    }

    @RequestMapping(value = "/editUserSkills", method = RequestMethod.GET)
    public String editUserSkills(Model model,
                                 @ModelAttribute("userId") String userId,
                                    @ModelAttribute("detailsFullDTO") DetailsFullDTO detailsFullDTO) {


        if(detailsFullDTO!=null&&detailsFullDTO.getUser().getId()!=null&&!detailsFullDTO.getUser().getId().equals("")){
            userId=detailsFullDTO.getUser().getId();
        }
        if (!userId.equals("")) {
            DetailsFullDTO detailsFull = userService.getAllUserDetailsById(userId);
            model.addAttribute("userFullDTO", detailsFull.getUser());
            List<SkillDTO> allSkills = new LinkedList<>(skillsService.getAllSkills());
            allSkills.removeAll(detailsFull.getUser().getSkills());
            model.addAttribute("skillsList", allSkills);
            model.addAttribute("userId", userId);
            SaveSkillsRequest saveSkillRequest = new SaveSkillsRequest();
            saveSkillRequest.setUserId(userId);
            model.addAttribute("saveSkillsRequest",saveSkillRequest );
        }else{
            model.addAttribute("userId", new String());
            model.addAttribute("userFullDTO", new UserFullDTO());
            model.addAttribute("skillsList", new ArrayList<SkillDTO>());
            model.addAttribute("saveSkillsRequest", new SaveSkillsRequest());
        }


        return "editUserSkills";
    }

    @RequestMapping(value = "/editUserSkills", method = RequestMethod.POST)
    public String editUserSkills(Model model,
                                 @ModelAttribute("userFullDTO") UserFullDTO userFullDTO,
                                 @ModelAttribute("saveSkillsRequest") SaveSkillsRequest saveSkillsRequest,
                                 @ModelAttribute("userId") String userId) {
        userService.saveSkillsToUser(saveSkillsRequest);
        model.addAttribute("userId", new String());
        model.addAttribute("userFullDTO", new UserFullDTO());
        model.addAttribute("saveSkillsRequest", new SaveSkillsRequest());
        model.addAttribute("skillsList", new ArrayList<SkillDTO>());
        return "editUserSkills";
    }
}
