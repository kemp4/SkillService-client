package pl.kemp.SkillController;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SkillController {

    @RequestMapping("/")
    public String index() {
        String endPoint ="http://internshipsoprastera.513d7f9c.svc.dockerapp.io/skills";

            RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //headers.add("Authorization","Basic amFuamFuOjhTcUR3d01lWXY=");

        String auth = "111111" + ":" + "222222222";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );


        headers.add("Authorization",authHeader);

        headers.add("Accept","*/*");
        HttpEntity<String> entity = new HttpEntity<>( headers);
            //ResponseEntity<SkillDTO[]> responseEntity = restTemplate.getForEntity(endPoint, SkillDTO[].class);
        ResponseEntity<SkillDTO[]> responseEntity=restTemplate.exchange(endPoint, HttpMethod.GET, entity, SkillDTO[].class);
        List<SkillDTO> list=Arrays.asList(responseEntity.getBody());

        list.forEach(p->System.out.println(p));



        return "Greetings from Spring Boot!";
    }

}

