package com.fininco.finincoserver.global.auth;

import com.fininco.finincoserver.global.exception.AuthenticationException;
import com.fininco.finincoserver.user.auth.domain.JwtResolver;
import com.fininco.finincoserver.user.entity.User;
import com.fininco.finincoserver.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Objects;

import static com.fininco.finincoserver.user.auth.AuthErrorCode.USER_NOT_FOUND;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtResolver jwtResolver;
    private final AuthContext authContext;
    private final UserRepository userRepository;

    @Autowired
    public JwtAuthenticationInterceptor(JwtResolver jwtResolver, AuthContext authContext, UserRepository userRepository) {
        this.authContext = authContext;
        this.jwtResolver = jwtResolver;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod))
            return true;

        if (isAuthNotAnnotated(handlerMethod)) {
            return true;
        }

        String accessToken = getValueFromHeader(request, AUTHORIZATION);

        if (accessToken == null && !isAuthRequired(handlerMethod)) {
            return true;
        }

        jwtResolver.validateToken(accessToken);
        Authentication authentication = jwtResolver.getAuthentication(accessToken);

        try {
            User user = userRepository.getById(authentication.userId());
            authContext.registerAuth(user);
        } catch (IllegalArgumentException exception) {
            throw new AuthenticationException(USER_NOT_FOUND);
        }

        return true;
    }

    private boolean isAuthNotAnnotated(HandlerMethod method) {
        MethodParameter[] methodParameters = method.getMethodParameters();
        return Arrays.stream(methodParameters)
                .noneMatch(parameter -> parameter.getParameterType().isAssignableFrom(UserInfo.class)
                                        && parameter.hasParameterAnnotation(Auth.class));
    }

    private boolean isAuthRequired(HandlerMethod method) {
        MethodParameter[] methodParameters = method.getMethodParameters();
        return Arrays.stream(methodParameters)
                .map(parameter -> parameter.getParameterAnnotation(Auth.class))
                .filter(Objects::nonNull)
                .findFirst()
                .filter(Auth::required)
                .isPresent();
    }

    private String getValueFromHeader(HttpServletRequest request, String headerName) {
        return request.getHeader(headerName);
    }

}
