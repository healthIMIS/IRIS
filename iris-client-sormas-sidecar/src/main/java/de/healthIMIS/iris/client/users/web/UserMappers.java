package de.healthIMIS.iris.client.users.web;

import de.healthIMIS.iris.client.auth.db.model.UserAccount;
import de.healthIMIS.iris.client.users.web.dto.UserDTO;
import de.healthIMIS.iris.client.users.web.dto.UserRoleDTO;

public class UserMappers {

  public static UserDTO map(UserAccount account) {
    return new UserDTO()
        .userName(account.getUserName())
        .firstName(account.getFirstName())
        .lastName(account.getLastName())
        .id(account.getUser_id().toString())
        .role(UserRoleDTO.fromValue(account.getRole().getValue()));
  }

}
