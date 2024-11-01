package com.example.demo.implement.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
// TODO: Builder 사용해야 됨!!!
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

	private String accessToken;
	private String refreshToken;

}
