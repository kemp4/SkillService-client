package pl.kemp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandlerController {
    @ControllerAdvice
    class GlobalControllerExceptionHandler {
        @ExceptionHandler(HttpClientErrorException.class)
        public String handleConflict(HttpClientErrorException e,Model model) {
            HttpStatus statusCode = e.getStatusCode();
            model.addAttribute("error",true);
            if(statusCode.equals(HttpStatus.BAD_REQUEST)){
                model.addAttribute("errorMessage","Bad data in form");
            }
            if(statusCode.equals(HttpStatus.NOT_FOUND)){
                model.addAttribute("errorMessage","This user or details does not exist");
            }
            if(statusCode.equals(HttpStatus.CONFLICT)){
                model.addAttribute("errorMessage","This data already exist");
            }
            if(statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)){
                model.addAttribute("errorMessage","Internal API error");
            }
            return "errorPage";
        }
    }
}
