package com.example.kaafkademo.msg;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;

public class MsgConsumer {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers","192.168.112.154:9092,192.168.112.154:9093,192.168.112.154:9094");
        //消费组名
        properties.put("group.id","testGroup");
        //是否自动提交offset
        properties.put("enable.auto.commit","true");
        //自动提交offset的间隔时间
        properties.put("auto.commit.interval.ms","1000");
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        //消费主体
        consumer.subscribe(Arrays.asList("wty"));
        //指定消费区
//        consumer.assign(Arrays.asList(new TopicPartition("wty",0)));

        while(true){
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for(ConsumerRecord<String,String> record : records){
                System.out.printf("offset = %d , key = %s , value = %s%n",record.offset(),record.key(),record.value());
            }
//            if(records.count() > 0){
//                //提交
//                consumer.commitSync();
//            }
        }
    }

}
