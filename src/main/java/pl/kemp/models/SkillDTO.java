package pl.kemp.models;


import java.util.Objects;

public class SkillDTO {


  private Long id ;
  private String skillName;
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSkillName() {
    return skillName;
  }

  public void setSkillName(String skillName) {
    this.skillName = skillName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SkillDTO skillDTO = (SkillDTO) o;
    return Objects.equals(id, skillDTO.id) &&
            Objects.equals(skillName, skillDTO.skillName);
  }


  @Override
  public int hashCode() {

    return Objects.hash(id, skillName);
  }
}