package pl.kemp.services;

import pl.kemp.models.SkillDTO;
import pl.kemp.models.SkillNewDTO;

import java.util.List;

public interface SkillsService {
    List<SkillDTO> getAllSkills();
    List<SkillDTO> addNewSkill(SkillNewDTO newSkill);

    boolean skillDoesNotExist(SkillNewDTO newSkill);
}
