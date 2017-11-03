package org.mycompany.chat.config;

import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
//            .nullDestMatcher().authenticated()
//            .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
//            .simpDestMatchers("/chat/**").hasRole("USER")
//            .simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER")
//            .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
            .anyMessage().permitAll();

    }
}
