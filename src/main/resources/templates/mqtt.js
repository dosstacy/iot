import Paho from 'paho-mqtt';

const broker = "ws://147.232.205.176:1884/mqtt";
const topic = "kpi/solaris/temperature/ad408gh";

const client = new Paho.MQTT.Client(broker, "web_client_" + Math.random().toString(16).substr(2, 8));

client.onMessageArrived = function (message) {
    const data = JSON.parse(message.payloadString);
    console.log("Received data:", data);

    // Оновлення даних на сторінці
    if (data.temperature) {
        document.querySelector(".temp-stats").textContent = `${data.temperature} °C`;
    }
    if (data.airHumidity) {
        document.querySelector(".air-stats").textContent = `${data.airHumidity}%`;
    }
    if (data.soilHumidity) {
        document.querySelector(".soil-stats").textContent = `${data.soilHumidity}%`;
    }
    if (data.lightLevel) {
        document.querySelector(".light-stats").textContent = `${data.lightLevel}%`;
        document.querySelector(".slider").value = data.lightLevel; // Синхронізація слайдера
    }
};

client.connect({
    onSuccess: function () {
        console.log("Connected to broker");
        client.subscribe(topic);
    },
    onFailure: function (error) {
        console.error("Connection failed:", error);
    }
});
