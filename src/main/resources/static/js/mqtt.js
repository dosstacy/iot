function connectToMother(topics){
    const client = mqtt.connect('ws://147.232.205.176:8000', {
        username: 'maker',
        password: 'mother.mqtt.password'
    });

    client.on('connect', () => {
        console.log('Connected to MQTT broker');

        Object.values(topics).forEach(topic => {
            client.subscribe(topic, (err) => {
                if (!err) {
                    console.log(`Subscribed to ${topic}`);
                }
            });
        });
    });

    client.on('error', (err) => {
        console.error('Connection error:', err);
    });

    client.on('close', () => {
        console.log('Connection closed');
    });

    return client;
}

function getAllDataFromMother() {
    const topics = {
        temperature: 'kpi/solaris/temperature/ad408gh',
        airHumidity: 'kpi/solaris/humidity/air/ad408gh',
        soilHumidity: 'kpi/solaris/humidity/soil/ad408gh',
        light: 'kpi/solaris/light/ad408gh'
    };

    let client = connectToMother(topics);

    client.on('message', (topic, message) => {
        console.log(`Received a message from topic ${topic}: ${message.toString()}`);
        const data = JSON.parse(message.toString());

        switch (topic) {
            case topics.temperature:
                document.querySelector(".temp-stats").textContent = `${data.metrics[0]?.value} °C`;
                break;
            case topics.airHumidity:
                document.querySelector(".air-stats").textContent = `${data.metrics[0]?.value} %`;
                break;
            case topics.soilHumidity:
                document.querySelector(".soil-stats").textContent = `${data.metrics[0]?.value} %`;
                break;
            case topics.light:
                document.querySelector(".light-stats").textContent = `${data.metrics[0]?.value} %`;
                document.querySelector(".slider").value = data.metrics[0]?.value;
                break;
        }
    });
}



