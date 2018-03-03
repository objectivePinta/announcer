package net.uranus.astra.rest.requests;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
@Setter
public class AuthToken {
    String token;
}
