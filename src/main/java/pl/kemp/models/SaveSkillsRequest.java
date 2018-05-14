
package pl.kemp.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveSkillsRequest {

  private List<Long> skillsIds;
  private String userId ;

  public List<Long> getSkillsIds() {
    return skillsIds;
  }

  public void setSkillsIds(List<Long> skillsIds) {
    this.skillsIds = skillsIds;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}