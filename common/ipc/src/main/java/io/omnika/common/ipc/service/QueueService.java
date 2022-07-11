package io.omnika.common.ipc.service;

import io.omnika.common.model.channel.ServiceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaAdmin kafkaAdmin;
    private final DiscoveryService discoveryService;
    private final PartitionService partitionService;
    private final ConsumerFactory consumerFactory;

    public void sendMessage(ServiceType serviceType, String topic, Object payload) {
        sendMessage(topic, payload, null, null);
    }

    public void sendMessage(ServiceType serviceType, String topic, Object payload, String replyTopic) {
        sendMessage(topic, payload, null, replyTopic);
    }

    public void sendMessage(ServiceType serviceType, String topic, Object payload, Object partitionKey) {
        // todo: kafkaAdmin.describeTopics - get partitions count - calculate partition index
        Integer partitionIndex = partitionService.getPartitionIndex(serviceType, partitionKey);
        // todo: 10 partitions but 2 instances - will go to first two partitions
        sendMessage(topic, payload, partitionIndex, null);
    }

    public void sendMessage(String topic, Object payload, Integer partitionIndex) {
        sendMessage(topic, payload, partitionIndex, null);
    }

    public void sendMessage(String topic, Object payload, Integer partitionIndex, String replyTopic) {
        MessageBuilder<Object> messageBuilder = MessageBuilder.withPayload(payload);
        messageBuilder.setHeader(KafkaHeaders.TOPIC, topic);
        if (partitionIndex != null) {
            messageBuilder.setHeader(KafkaHeaders.PARTITION_ID, partitionIndex);
        }
        if (replyTopic != null) {
            messageBuilder.setHeader(KafkaHeaders.REPLY_TOPIC, replyTopic);
            messageBuilder.setHeader(KafkaHeaders.REPLY_PARTITION, discoveryService.getInstanceIndex());
        }
        log.debug("Pushing message to topic {} for partition {}: {}", topic, partitionIndex, payload);
        kafkaTemplate.send(messageBuilder.build());
    }


    public <T> void subscribe(String topic, Consumer<T> processor) {
        log.debug("Subscribing to topic {}", topic);
        this.<T>registerConsumer(topic, message -> {
            T payload = message.value();
            log.debug("Received new message for topic {}: {}", topic, payload);
            processor.accept(payload);
        });
    }

    public <T> void subscribeAndRespond(String topic, BiConsumer<T, Consumer<Object>> processor) {
        log.debug("Subscribing to topic {}", topic);
        this.<T>registerConsumer(topic, message -> {
            T payload = message.value();
            log.debug("Received new message for topic {}: {}", topic, payload);
            processor.accept(payload, response -> {
                String replyTopic = getHeader(message, KafkaHeaders.REPLY_TOPIC);
                Integer replyPartition = Integer.parseInt(getHeader(message, KafkaHeaders.REPLY_PARTITION));
                log.debug("Pushing reply to topic {} for partition {}: {}", replyTopic, replyPartition, response);
                sendMessage(replyTopic, response, replyPartition);
            });
        });
    }

    private <T> void registerConsumer(String topic, MessageListener<String, T> messageListener) {
        ContainerProperties containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener(messageListener);
        ConcurrentMessageListenerContainer<String, T> listenerContainer = new ConcurrentMessageListenerContainer<String, T>(consumerFactory, containerProperties);
        listenerContainer.start();
    }

    private <T> String getHeader(ConsumerRecord<String, T> message, String key) {
        return new String(message.headers().lastHeader(key).value());
    }

}
