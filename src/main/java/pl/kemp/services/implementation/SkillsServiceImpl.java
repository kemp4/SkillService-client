package pl.kemp.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kemp.models.SkillDTO;
import pl.kemp.models.SkillNewDTO;
import pl.kemp.services.LoginService;
import pl.kemp.services.SkillsService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class SkillsServiceImpl implements SkillsService {

    private LoginService loginService;

    @Autowired
    public void setSkillsService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<SkillDTO> getAllSkills() {
//        HttpEntity<String> entity = new HttpEntity<>( );
        String apiURI = loginService.getApiURIFromSession();
        ResponseEntity<SkillDTO[]> responseEntity = restTemplate.getForEntity(apiURI + "skills", SkillDTO[].class);
        java.util.List<SkillDTO> list = Arrays.asList(responseEntity.getBody());
        return list;
    }

    @Override
    public List<SkillDTO> addNewSkill(SkillNewDTO newSkill) {
        String apiURI = loginService.getApiURIFromSession();
        restTemplate.postForLocation(apiURI + "skills", newSkill);
        ResponseEntity<SkillDTO[]> resultResponseEntity = restTemplate.getForEntity(apiURI + "skills", SkillDTO[].class);
        List<SkillDTO> result = Arrays.asList(resultResponseEntity.getBody());
        return result;
    }

    @Override
    public boolean skillDoesNotExist(SkillNewDTO newSkill) {
        String apiURI = loginService.getApiURIFromSession();
        ResponseEntity<SkillDTO[]> responseEntity = restTemplate.getForEntity(apiURI + "skills", SkillDTO[].class);
        List<SkillDTO> list = Arrays.asList(responseEntity.getBody());
        return isSkillInList(newSkill, list);
    }

    private boolean isSkillInList(SkillNewDTO newSkill, List<SkillDTO> list) {
        List<SkillNewDTO> testList = list.stream()
                .map(skill -> new SkillNewDTO(skill.getSkillName()))
                .collect(Collectors.toList());
        return !testList.contains(newSkill);
    }
}
