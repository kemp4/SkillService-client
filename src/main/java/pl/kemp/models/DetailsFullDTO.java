package pl.kemp.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.LinkedList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailsFullDTO {

  private String fieldOfStudy ;
  private String firstName ;
  private String id ;
  private String lastName ;
  private String university ;
  private UserFullDTO user ;
  private Integer yearOfStudy ;

  public DetailsFullDTO() {
    user=new UserFullDTO();
    user.setSkills(new LinkedList<>());
  }

  public String getFieldOfStudy() {
    return fieldOfStudy;
  }

  public void setFieldOfStudy(String fieldOfStudy) {
    this.fieldOfStudy = fieldOfStudy;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUniversity() {
    return university;
  }

  public void setUniversity(String university) {
    this.university = university;
  }

  public UserFullDTO getUser() {
    return user;
  }

  public void setUser(UserFullDTO user) {
    this.user = user;
  }

  public Integer getYearOfStudy() {
    return yearOfStudy;
  }

  public void setYearOfStudy(Integer yearOfStudy) {
    this.yearOfStudy = yearOfStudy;
  }
}