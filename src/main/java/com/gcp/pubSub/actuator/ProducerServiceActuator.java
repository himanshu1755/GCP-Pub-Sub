package com.gcp.pubSub.actuator;

import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;

@Component
public class ProducerServiceActuator {

	@Bean
	@ServiceActivator(inputChannel = "outboundMsgChanel")
	public PubSubMessageHandler messageSender(PubSubTemplate pubsubTemplate) {
		return new PubSubMessageHandler(pubsubTemplate, "order_topic");
	}








	@Bean
	public PubSubInboundChannelAdapter messageAdapter (
			@Qualifier("inputChannel") MessageChannel inputChannel,
			PubSubTemplate template
	) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(template, "notification_sub");
		adapter.setOutputChannel(inputChannel);
		return adapter;
	}

	@Bean
	MessageChannel inputChannel() {
		return new DirectChannel();
	}


}
