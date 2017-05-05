package net.uranus.astra.ws.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserData {
    private String username;
    private String password;
}
