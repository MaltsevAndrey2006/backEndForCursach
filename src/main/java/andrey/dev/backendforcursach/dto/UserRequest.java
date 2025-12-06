package andrey.dev.backendforcursach.dto;

import lombok.Data;

@Data
public class UserRequest {

    private String login;

    private String password;

    private String email;

}
