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