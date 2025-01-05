package com.iot;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.util.Map;

public class MqttExample {
    private static final String MQTT_BROKER_URL = "tcp://147.232.205.176:1883";
    private static final String MQTT_USERID = "JavaClient";

    private static final String MQTT_USERNAME = "maker";
    private static final String MQTT_PASSWORD = "mother.mqtt.password";

    private static final String TEMP_TOPIC = "kpi/solaris/temperature/ad408gh";
    private static final String HUM_AIR_TOPIC = "kpi/solaris/humidity/air/ad408gh";
    private static final String HUM_SOIL_TOPIC = "kpi/solaris/humidity/soil/ad408gh";
    private static final String LIGHT_TOPIC = "kpi/solaris/light/ad408gh";

    private static final String CMD_LIGHT_TOPIC = "kpi/solaris/light/ad408gh/cmd";
    private static final String CMD_HUM_TOPIC = "kpi/solaris/humidity/ad408gh/cmd";

//    private void publishMqtt(MqttClient client, String broker, String topic, String content, int qos) {
//        try {
//            MqttConnectOptions connOpts = new MqttConnectOptions();
//            connOpts.setCleanSession(false);
//            connOpts.setUserName(MQTT_USERNAME);
//            connOpts.setPassword(MQTT_PASSWORD.toCharArray());
//
//            System.out.println("Connecting to broker: " + broker);
//            client.connect(connOpts);
//            System.out.println("Connected");
//
//            // Створення та публікація повідомлення
//            MqttMessage message = new MqttMessage(content.getBytes());
//            message.setQos(qos);
//            System.out.println("Publishing message: " + content);
//            client.publish(topic, message);
//            System.out.println("Message published");
//
//            // Відключення
//            client.disconnect();
//            System.out.println("Disconnected");
//        } catch (MqttException me) {
//            me.printStackTrace();
//        }
//    }

    private void publishMqtt(MqttClient client, Map<String, Object> data, int qos, String topic) {
        try {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);
            connOpts.setUserName(MQTT_USERNAME);
            connOpts.setPassword(MQTT_PASSWORD.toCharArray());

            client.connect(connOpts);

            String payload = new Gson().toJson(data);
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(qos);

            client.publish(topic, message);

            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace(System.out);
        }
    }

    private void receiveMqtt(MqttClient client, String username, String password, String topic) {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setCleanSession(true); // Чиста сесія (отримувати тільки нові повідомлення)

            // Підключення до брокера
            client.connect(options);
            System.out.println("Connected to broker ");

            // Підписка на тему
            client.subscribe(topic, (receivedTopic, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("New message received: " + payload);
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String content = "{\"status\" : \"updated\"}";
        int qos = 2;
        MqttClient client;
        try {
            client = new MqttClient(MQTT_BROKER_URL, MQTT_USERID, new MqttDefaultFilePersistence());
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        MqttExample example = new MqttExample();
        example.receiveMqtt(client, MQTT_USERNAME, MQTT_PASSWORD, TEMP_TOPIC);
//        Map<String, Object> data = new HashMap<>();
//        data.put("temperature", 21);
//        data.put("airHumidity", 34);
//        data.put("soilHumidity", 10);
//        data.put("lightLevel", 68);
//
//        example.publishMqtt(client, data, qos, CMD_LIGHT_TOPIC);
    }
}
