package pl.kemp.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillNewDTO {
  private String skillName = null;

  public SkillNewDTO(String skillName) {
    this.skillName=skillName;
  }
  public SkillNewDTO() {
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
    SkillNewDTO that = (SkillNewDTO) o;
    return Objects.equals(skillName, that.skillName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(skillName);
  }
}