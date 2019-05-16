package com.lzhpo.rabbitmq.rabbitmqtopicprovider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * @author Administrator
 *
 */
@Component
public class OrderSender {

	@Autowired
	private AmqpTemplate rabbitAmqpTemplate;
	
	//exchange 交换器名称
	@Value("${mq.config.exchange}")
	private String exchange;
	
	/*
	 * 发送消息的方法
	 */
	public void send(String msg){
		//向消息队列发送消息
		//参数一：交换器名称。
		//参数二：路由键
		//参数三：消息
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.debug", "order.log.debug....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.info", "order.log.info....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.warn","order.log.warn....."+msg);
		this.rabbitAmqpTemplate.convertAndSend(this.exchange,"order.log.error", "order.log.error....."+msg);
	}
}
