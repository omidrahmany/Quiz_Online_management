package io.spring.quiz_online.service;

import org.springframework.security.core.Authentication;

    public interface AuthenticationFacade {
        Authentication getAuthentication();
    }

