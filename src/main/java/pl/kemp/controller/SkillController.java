package pl.kemp.controller;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.kemp.models.SkillDTO;
import pl.kemp.models.SkillNewDTO;
import pl.kemp.services.SkillsService;


@Controller
public class SkillController {

    private SkillsService skillsService;

    @Autowired
    public void setSkillsService(SkillsService skillsService) {
        this.skillsService = skillsService;
    }

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        return "home";
    }

    @RequestMapping("/showAllSkills")
    public String showAllSkills(Map<String, Object> model) {
        List<SkillDTO> skills = skillsService.getAllSkills();
        model.put("skills",skills);
        model.put("newSkill",new SkillNewDTO());
        return "skillsList";
    }

    @RequestMapping("/addNewSkill")
    public String addNewSkill(Map<String, Object> model, @ModelAttribute("newSkill") SkillNewDTO newSkill) {
        List<SkillDTO> skills = new LinkedList<>();
        if(skillsService.skillDoesNotExist(newSkill)){
            skills = skillsService.addNewSkill(newSkill);
        }
        model.put("skills",skills);
        //model.put("newSkill",new SkillNewDTO());
        return "redirect:/showAllSkills";
    }
}

