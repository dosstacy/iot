function waterPlant() {
    const humidity = 'kpi/solaris/humidity/ad408gh/cmd';

    let client = connectToMother({humidity: humidity});

    client.on('connect', () => {
        const message = JSON.stringify({command: 'turn_on', value: true});
        sendDataToMQTT(humidity, message, client);
    });
}

function lightAdjust(percent) {
    const light = 'kpi/solaris/light/ad408gh/cmd';


    let client = connectToMother({light: light});

    client.on('connect', () => {
        const message = JSON.stringify({command: 'light_on', value: percent});
        sendDataToMQTT(light, message, client);
    });
}

function sendDataToMQTT(topic, message, client) {
    client.publish(topic, message, (err) => {
        if (err) {
            console.error('Error while publishing:', err);
        } else {
            console.log(`Message sent to topic ${topic}: ${message}`);
        }
    });
}