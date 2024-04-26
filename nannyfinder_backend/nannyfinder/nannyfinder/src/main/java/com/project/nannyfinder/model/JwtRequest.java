package com.project.nannyfinder.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//simplify the construction of objects, especially when dealing with many optional parameters. It's commonly used in fluent APIs.
@Builder
@ToString //represents string representation of the object's state
public class JwtRequest {
    private String username;
    private String password;


}
