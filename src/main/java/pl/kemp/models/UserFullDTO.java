package pl.kemp.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFullDTO {

  private String email ;
  private String id ;
  private String name;
  private String password;
  private List<SkillDTO> skills;

  public UserFullDTO() {
    skills=new LinkedList<>();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {    return password;  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<SkillDTO> getSkills() {
    return skills;
  }

  public void setSkills(List<SkillDTO> skills) {
    this.skills = skills;
  }
}