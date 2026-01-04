package com.codingshuttle.MyJwtDemo.services;

import com.codingshuttle.MyJwtDemo.entities.Session;
import com.codingshuttle.MyJwtDemo.entities.UserEntity;
import com.codingshuttle.MyJwtDemo.repository.SessionRepository;
import com.codingshuttle.MyJwtDemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;



@RequiredArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private  static int MAX_SESSIONS=2;

    public void generateNewSession(Long userId,String refreshToken){
        UserEntity user = userRepository.findById(userId).orElse(null);
        List<Session> sessions = sessionRepository.findByUser(user);
        if(sessions.size() == MAX_SESSIONS){
            sessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session leastRecentlyUsedSession = sessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session session = Session.builder()
                        .refreshToken(refreshToken)
                        .user(user)
                        .build();
        sessionRepository.save(session);
    }

    public void validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken).orElseThrow(()->new SessionAuthenticationException("Session not found for refresh token"+refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
