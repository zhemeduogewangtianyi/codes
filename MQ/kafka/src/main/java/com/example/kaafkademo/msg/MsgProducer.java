package com.example.kaafkademo.msg;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MsgProducer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","192.168.112.154:9092,192.168.112.154:9093,192.168.112.154:9094");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<>(properties);
        for(int i = 0 ; i < 500000 ; i++){
            ProducerRecord<String,String> producerRecord = new ProducerRecord<>("wty",0,Integer.toString(i),"这是消息"+ i );
//            Future<RecordMetadata> result = producer.send(producerRecord);
//            RecordMetadata recordMetadata = result.get();
//            String topic = recordMetadata.topic();
//            int partition = recordMetadata.partition();
//            long offset = recordMetadata.offset();
//            System.out.println("同步方式发送消息结果：" + "topic-" + topic + " | partition" + partition + " | offset-" + offset);

            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e != null){
                        System.out.println("发送消息失败：" + e.getStackTrace());
                    }
                    if(recordMetadata != null){
                        String topic = recordMetadata.topic();
                        int partition = recordMetadata.partition();
                        long offset = recordMetadata.offset();
                        System.out.println("异步方式发送消息结果：" + "topic-" + topic + " | partition" + partition + " | offset-" + offset);
                    }
                }
            });
        }

        producer.close();
    }

}
