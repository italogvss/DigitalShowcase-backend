package italogvss.digitalshowcase.WebScoket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {


  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    //onde o cliente se inscreve para receber mensagens
    config.enableSimpleBroker("/topic");

    //onde o cliente envia mensagens
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/devws")
      .setAllowedOrigins("*")
      .addInterceptors(new PlaylistInterceptor()).withSockJS();
  }

}
