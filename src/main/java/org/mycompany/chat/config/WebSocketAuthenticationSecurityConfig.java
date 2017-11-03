package org.mycompany.chat.config;

import lombok.RequiredArgsConstructor;
import org.mycompany.chat.security.jwt.JWTInterceptor;
import org.mycompany.chat.security.jwt.TokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@RequiredArgsConstructor
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationSecurityConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private final TokenProvider tokenProvider;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new JWTInterceptor(tokenProvider));
    }

}
