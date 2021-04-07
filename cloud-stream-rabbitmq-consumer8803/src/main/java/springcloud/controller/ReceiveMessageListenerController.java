package springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

/**
 * 注意，这儿在controller这儿写业务类，因为都不需要什么controller调用，直接注解完事
 * 对比下生产者service 的 @EnableBinding(Source.class) //定义消息的推送管道
 */
//@Component
@EnableBinding(Sink.class) //这是消费者  @EnableBinding注解包含了@Component注解
public class ReceiveMessageListenerController
{
    @Value("${server.port}")
    private String serverPort;


    @StreamListener(Sink.INPUT)//监听器，监听生产者产生的消息
    public void input(Message<String> message)
    {
        //注意生产者output.send(MessageBuilder.withPayload(serial).build());    这儿消费者getPayload
        System.out.println("消费者2号,----->接受到的消息: "+message.getPayload()+"\t  port: "+serverPort);
    }
}

