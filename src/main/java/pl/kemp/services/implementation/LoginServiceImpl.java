package pl.kemp.services.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.kemp.services.LoginService;

import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {
    @Value("${api.defaultURI}")
    private String defaultURI;

    private static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }
    private static String getApiURI(){
        HttpSession session = session();
        return (String) session.getAttribute("apiURI");
    }

    public String getApiURIFromSession() {
        String apiURI =getApiURI();
        if(apiURI==null){
            apiURI=defaultURI;
            session().setAttribute("apiURI",apiURI);
        }
        return apiURI;
    }
}
