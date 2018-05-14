package pl.kemp.models;


public class DetailsNewDTO {

  private String fieldOfStudy;
  private String firstName ;
  private String lastName ;
  private String university ;
  private Integer yearOfStudy ;

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