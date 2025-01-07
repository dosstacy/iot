// // Використовуємо бібліотеку MQTT.js
// // mqtt = require('mqtt');
//
// // Конфігурація підключення
// const config = {
//     host: '147.232.205.176',
//     port: 1883, // Використовуємо WebSocket порт
//     protocol: 'ws', // WebSocket протокол
//     username: 'maker',
//     password: 'mother.mqtt.password'
// };
//
// // Топіки для підписки
// const topics = {
//     temperature: 'kpi/solaris/temperature/ad408gh',
//     airHumidity: 'kpi/solaris/humidity/air/ad408gh',
//     soilHumidity: 'kpi/solaris/humidity/soil/ad408gh',
//     light: 'kpi/solaris/light/ad408gh'
// };
//
// // Створюємо клієнт
// const client = mqtt.connect(`ws://147.232.205.176:8000/mqtt`, {
//     username: config.username,
//     password: config.password
// });
//
// // Обробка подій підключення
// client.on('connect', () => {
//     console.log('Підключено до MQTT брокера');
//
//     // Підписуємось на всі топіки
//     Object.values(topics).forEach(topic => {
//         client.subscribe(topic, (err) => {
//             if (!err) {
//                 console.log(`Підписано на топік: ${topic}`);
//             }
//         });
//     });
// });
//
// // Обробка вхідних повідомлень
// client.on('message', (topic, message) => {
//     console.log(`Отримано повідомлення з топіку ${topic}: ${message.toString()}`);
//
//     switch(topic) {
//         case topics.temperature:
//             handleTemperature(message);
//             break;
//         case topics.airHumidity:
//             handleAirHumidity(message);
//             break;
//         case topics.soilHumidity:
//             handleSoilHumidity(message);
//             break;
//         case topics.light:
//             handleLight(message);
//             break;
//     }
// });
//
// // Функції обробки даних з різних топіків
// function handleTemperature(message) {
//     const temperature = parseFloat(message);
//     console.log(`Оброблено температуру: ${temperature}°C`);
//     // Додайте власну логіку обробки температури
// }
//
// function handleAirHumidity(message) {
//     const humidity = parseFloat(message);
//     console.log(`Оброблено вологість повітря: ${humidity}%`);
//     // Додайте власну логіку обробки вологості повітря
// }
//
// function handleSoilHumidity(message) {
//     const humidity = parseFloat(message);
//     console.log(`Оброблено вологість ґрунту: ${humidity}%`);
//     // Додайте власну логіку обробки вологості ґрунту
// }
//
// function handleLight(message) {
//     const light = parseFloat(message);
//     console.log(`Оброблено освітленість: ${light}`);
//     // Додайте власну логіку обробки освітленості
// }
//
// // Функції для надсилання даних в топіки
// function publishTemperature(value) {
//     client.publish(topics.temperature, value.toString(), { qos: 1 }, (err) => {
//         if (!err) {
//             console.log(`Температуру ${value}°C надіслано`);
//         }
//     });
// }
//
// function publishAirHumidity(value) {
//     client.publish(topics.airHumidity, value.toString(), { qos: 1 }, (err) => {
//         if (!err) {
//             console.log(`Вологість повітря ${value}% надіслано`);
//         }
//     });
// }
//
// function publishSoilHumidity(value) {
//     client.publish(topics.soilHumidity, value.toString(), { qos: 1 }, (err) => {
//         if (!err) {
//             console.log(`Вологість ґрунту ${value}% надіслано`);
//         }
//     });
// }
//
// function publishLight(value) {
//     client.publish(topics.light, value.toString(), { qos: 1 }, (err) => {
//         if (!err) {
//             console.log(`Освітленість ${value} надіслано`);
//         }
//     });
// }
//
// // Обробка помилок
// client.on('error', (err) => {
//     console.error('Помилка підключення:', err);
// });
//
// client.on('close', (err) => {
//     console.log('Підключення закрито ', err);
// });
//
// // // Експортуємо функції для використання в інших модулях
// // module.exports = {
// //     publishTemperature,
// //     publishAirHumidity,
// //     publishSoilHumidity,
// //     publishLight
// // };

// const mqtt1 = require('mqtt');
//
// // Налаштування підключення
// const options = {
//     host: '147.232.205.176',
//     port: 1883,
//     protocol: 'mqtt',
//     username: 'maker',
//     password: 'mother.mqtt.password'
// };
//
// // Створюємо клієнт
// const client = mqtt1.connect(`${options.protocol}://${options.host}:${options.port}`, options);
//
// // Список топіків
// const topics = [
//     'kpi/solaris/temperature/ad408gh',
//     'kpi/solaris/humidity/air/ad408gh',
//     'kpi/solaris/humidity/soil/ad408gh',
//     'kpi/solaris/light/ad408gh'
// ];
//
// // Обробка підключення
// client.on('connect', () => {
//     console.log('Connected to MQTT broker');
//
//     // Підписуємося на всі топіки
//     topics.forEach(topic => {
//         client.subscribe(topic, (err) => {
//             if (!err) {
//                 console.log(`Subscribed to ${topic}`);
//             } else {
//                 console.error(`Error subscribing to ${topic}:`, err);
//             }
//         });
//     });
// });
//
// // Обробка повідомлень
// client.on('message', (topic, message) => {
//     try {
//         const data = JSON.parse(message.toString());
//         console.log(`Message from ${topic}:`, data);
//
//         // Тут можна додати логіку обробки даних
//         switch(topic) {
//             case topics[0]: // temperature
//                 handleTemperature(data);
//                 break;
//             case topics[1]: // air humidity
//                 handleAirHumidity(data);
//                 break;
//             case topics[2]: // soil humidity
//                 handleSoilHumidity(data);
//                 break;
//             case topics[3]: // light
//                 handleLight(data);
//                 break;
//         }
//     } catch (error) {
//         console.error('Error processing message:', error);
//     }
// });
//
// // Функції обробки даних
// function handleTemperature(data) {
//     if (data.temperature) {
//         console.log(`Temperature: ${data.temperature}°C`);
//         // Додайте вашу логіку обробки температури
//     }
// }
//
// function handleAirHumidity(data) {
//     if (data.airHumidity) {
//         console.log(`Air Humidity: ${data.airHumidity}%`);
//         // Додайте вашу логіку обробки вологості повітря
//     }
// }
//
// function handleSoilHumidity(data) {
//     if (data.soilHumidity) {
//         console.log(`Soil Humidity: ${data.soilHumidity}%`);
//         // Додайте вашу логіку обробки вологості ґрунту
//     }
// }
//
// function handleLight(data) {
//     if (data.lightLevel) {
//         console.log(`Light Level: ${data.lightLevel}`);
//         // Додайте вашу логіку обробки рівня освітлення
//     }
// }
//
// // Функція для публікації даних
// function publishData(topic, data) {
//     client.publish(topic, JSON.stringify(data), { qos: 1 }, (err) => {
//         if (!err) {
//             console.log(`Published to ${topic}:`, data);
//         } else {
//             console.error(`Error publishing to ${topic}:`, err);
//         }
//     });
// }
//
// // Обробка помилок
// client.on('error', (err) => {
//     console.error('Connection error:', err);
// });
//
// client.on('close', () => {
//     console.log('Connection closed');
// });
//
// // Обробка завершення роботи програми
// process.on('SIGINT', () => {
//     client.end();
//     process.exit();
// });

import mqtt from '/mqtt'

const client = mqtt.connect('mqtt://147.232.205.176:1883', {
    username: 'maker',
    password: 'mother.mqtt.password'
});

client.on('connect', () => {
    console.log('Connected to MQTT broker');
});

client.on('error', (err) => {
    console.error('Connection error:', err);
});

client.on('close', () => {
    console.log('Connection closed');
});

process.on('SIGINT', () => {
    client.end();
    process.exit();
});



