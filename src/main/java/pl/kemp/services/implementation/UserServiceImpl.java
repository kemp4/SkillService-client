package pl.kemp.services.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pl.kemp.models.*;
import pl.kemp.services.LoginService;
import pl.kemp.services.UserService;

import java.net.URI;

@Service
public class UserServiceImpl implements UserService {

    private LoginService loginService;
    @Autowired
    public void setSkillsService(LoginService loginService) {
        this.loginService = loginService;
    }
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public UserCreatedDTO createUser(UserNewDTO newUser) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserNewDTO> request = new HttpEntity<>(newUser);
        URI createdUserLocation = restTemplate.postForLocation(loginService.getApiURIFromSession()+"users",request);
        UserCreatedDTO userCreated = restTemplate.getForObject(createdUserLocation,UserCreatedDTO.class);
        return userCreated;
    }

    @Override
    public DetailsFullDTO getAllUserDetailsById(String userId) {
        DetailsFullDTO detailsFull = restTemplate.getForObject(loginService.getApiURIFromSession()+"users/alldetails/"+userId,DetailsFullDTO.class);
        return detailsFull;
    }

    @Override
    public DetailsDTO updateUserDetails(DetailsNewDTO detailsNew) {
        HttpEntity<DetailsNewDTO> request = new HttpEntity<>(detailsNew);
        HttpEntity response = restTemplate.exchange(loginService.getApiURIFromSession()+"users/details",HttpMethod.PUT,request,Void.class);
        URI detailsNewURI = response.getHeaders().getLocation();
        DetailsDTO details = restTemplate.getForObject(detailsNewURI, DetailsDTO.class);
        return details;
    }

    @Override
    public DetailsDTO getDetailsById(String detailsId) {
        DetailsDTO details = restTemplate.getForObject(loginService.getApiURIFromSession()+"users/details/"+detailsId,DetailsDTO.class);
        return details;
    }

    @Override
    public DetailsDTO getDetailsByUserId(String userId) {

        ModelMapper modelMapper= new ModelMapper();
        DetailsFullDTO detailsFull = getAllUserDetailsById(userId);
        DetailsDTO detailsDTO = modelMapper.map(detailsFull,DetailsDTO.class);
        return detailsDTO;
    }

    @Override
    public UserFullDTO saveSkillsToUser(SaveSkillsRequest saveSkillsRequest) {
        HttpEntity<SaveSkillsRequest> request = new HttpEntity<>(saveSkillsRequest);
        ResponseEntity<UserFullDTO> response = restTemplate.exchange(loginService.getApiURIFromSession()+"users/skills",HttpMethod.PUT,request,UserFullDTO.class);
        return response.getBody();
    }
}
