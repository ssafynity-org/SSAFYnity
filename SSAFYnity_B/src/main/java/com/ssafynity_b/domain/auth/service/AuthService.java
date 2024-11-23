package com.ssafynity_b.domain.auth.service;

import com.ssafynity_b.domain.auth.dto.LoginRequest;
import com.ssafynity_b.domain.auth.dto.LoginResponse;

public interface AuthService {

    public LoginResponse authenticate(LoginRequest loginRequest);

}
