function waterPlant() {
    const humidity = 'kpi/solaris/humidity/ad408gh/cmd';

    let client = connectToMother({humidity: humidity});

    client.on('connect', () => {
        const message = JSON.stringify({command: 'turn_on', value: true});
        sendDataToMQTT(humidity, message, client);
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