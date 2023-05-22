package com.gcp.pubSub.controller;

import com.gcp.pubSub.gateway.OutboundChanel;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Component
@RestController
public class ProducerRestController {

	@Autowired
	OutboundChanel gateway;

	String message;

	@ServiceActivator(inputChannel = "inputChannel")
	public void receiveMessage(String paylod) {
		this.message = paylod;
	}

	@GetMapping("getMessage")
	public String getMessage() {
		return "Message from GCP "+message;
	}

	@PostMapping("/publishMessage")
	public String publishMessage(@RequestBody String msg) {
		gateway.sendMsgToPubSub(msg);
		return "Message sent to PubSub successfully";
	}

}
