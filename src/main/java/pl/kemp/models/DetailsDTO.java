package pl.kemp.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailsDTO {

  private String fieldOfStudy = null;
  private String firstName = null;
  private String id = null;
  private String lastName = null;
  private String university = null;
  private Integer yearOfStudy = null;

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

  public Integer getYearOfStudy() {
    return yearOfStudy;
  }

  public void setYearOfStudy(Integer yearOfStudy) {
    this.yearOfStudy = yearOfStudy;
  }
}

