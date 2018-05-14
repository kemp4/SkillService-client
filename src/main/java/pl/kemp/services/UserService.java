package pl.kemp.services;

import pl.kemp.models.*;

public interface UserService {
    UserCreatedDTO createUser(UserNewDTO newUser);

   // UserCreatedDTO getUserById(String id);

    DetailsFullDTO getAllUserDetailsById(String userId);

    DetailsDTO updateUserDetails(DetailsNewDTO detailsNew);

    DetailsDTO getDetailsById(String detailsId);

    DetailsDTO getDetailsByUserId(String userId);

    UserFullDTO saveSkillsToUser(SaveSkillsRequest saveSkillsRequest);
//
//    String changeUserDetails(DetailsNewDTO details);
//
//    DetailsDTO getUserDetailsById(String id);
//
//    UserFullDTO addSkillToUser(SaveSkillsRequest skill);
}
