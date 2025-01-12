function updateStats(plantType) {
    const temp = parseFloat(document.querySelector('.temp-stats').textContent);
    const air = parseFloat(document.querySelector('.air-stats').textContent);
    const soil = parseFloat(document.querySelector('.soil-stats').textContent);

    const stats = document.querySelectorAll('.temp-stats, .air-stats, .soil-stats');

    switch (plantType) {
        case 'DRY_LOVER':
            console.log('this is dry-lover')
            stats.forEach(stat => {
                if ((stat.classList.contains('temp-stats') && (temp < 5 || temp > 45)) ||
                    (stat.classList.contains('air-stats') && (air < 30 || air > 50)) ||
                    (stat.classList.contains('soil-stats') && (soil < 10 || soil > 30))) {
                    stat.style.color = 'red';
                } else {
                    stat.style.color = 'white';
                }
            });
            break;

        case 'MEDIUM_LOVER':
            stats.forEach(stat => {
                if ((stat.classList.contains('temp-stats') && (temp < 10 || temp > 35)) ||
                    (stat.classList.contains('air-stats') && (air < 50 || air > 70)) ||
                    (stat.classList.contains('soil-stats') && (soil < 40 || soil > 60))) {
                    stat.style.color = 'red';
                } else {
                    stat.style.color = 'white';
                }
            });
            break;

        case 'WATER_LOVER':
            stats.forEach(stat => {
                if ((stat.classList.contains('temp-stats') && (temp < 15 || temp > 30)) ||
                    (stat.classList.contains('air-stats') && (air < 70 || air > 80)) ||
                    (stat.classList.contains('soil-stats') && (soil < 60 || soil > 80))) {
                    stat.style.color = 'red';
                } else {
                    stat.style.color = 'white';
                }
            });
            break;
    }
}

function checkPlantState() {
    const topic = 'kpi/solaris/allSensors/ad408gh/cmd';

    let client = connectToMother({topic: topic});

    client.on('connect', () => {
        const message = JSON.stringify({command: 'get_all_data'});
        sendDataToMQTT(topic, message, client);
    });
}

async function getCurrentPlantInfo(plantName) {
    console.log('Plant name in post: ', plantName)

    try {
        const response = await fetch(`/api/plants/plant?plantName=${plantName}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                console.log('fetch plant function')
                fetchPlant()
                window.location.href = '/smartPlantie/stats';
            })
    } catch (error) {
        console.error('Error fetching plants:', error.message);
    }
}

async function fetchPlant() {
    try {
        const response = await fetch('/api/plants/info');
        const plants = await response.json();
        let plant;

        console.log('here is fetchPlant')
        console.log('response from json ', plants)

        if (!plants) {
            window.location.href = '/smartPlantie/plantType';
        }

        let temperature = document.querySelector('.temp-stats');
        let air = document.querySelector('.air-stats');
        let soil = document.querySelector('.soil-stats');

        if (plants.plantType === "DRY_LOVER") {
            plant = "dry-lover plant"
            document.querySelector(".plant-description").textContent = `Dry-loving plants, such as cacti or succulents, require minimal water. They should be provided with direct sunlight and watering should be limited, allowing the soil to dry completely between waterings. Temperatures between 20-30°C are optimal. They can also withstand high temperatures, so it is important to ensure good drainage to avoid water stagnation.`
            createMutationObserver("DRY_LOVER", temperature, air, soil);
        } else if (plants.plantType === "MEDIUM_LOVER") {
            plant = "plant that like medium humidity"
            document.querySelector(".plant-description").textContent = `Medium-loving plants, such as ficus or gerberas, need moderate watering and can withstand periods of dryness as well as wetter conditions. They require moderate light and do not like direct sun, but should not be in places that are too dark. The optimal temperature for these plants ranges from 15 to 25°C. Watering should be regular, but the soil should have time to dry out between waterings to avoid root rot.`
            createMutationObserver("MEDIUM_LOVER", temperature, air, soil);
        } else if (plants.plantType === "WATER_LOVER") {
            plant = "water-loving plant"
            document.querySelector(".plant-description").textContent = `Water-loving plants such as ferns or tropical flowers need regular watering and high humidity. They like warmth and grow best at temperatures between 18 and 25°C. Since these plants do not tolerate drying out, it is important to keep the soil and the environment constantly moist, for example by using humidifiers.`
            createMutationObserver("WATER_LOVER", temperature, air, soil);
        }

        document.querySelector(".user-type-name").textContent = `${plants.plantName} (${plant})`
    } catch (error) {
        console.error('Error fetching plant:', error);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    fetchPlant();
});

async function fetchPlants() {
    try {
        const response = await fetch('/api/plants');
        const plants = await response.json();

        const container = document.getElementById('change-plant-container');
        container.innerHTML = '';

        plants.forEach(plant => {
            const plantElement = document.createElement('div');
            plantElement.className = 'change-plant-data-container';

            plantElement.innerHTML = `
                    <span class="change-plant-data">${plant.plantName}</span>
                    <span class="change-plant-data">${plant.plantType}</span>
                    <button class="change-plant-btn" onclick="getCurrentPlantInfo('${plant.plantName}')">Select</button>
                `;

            container.appendChild(plantElement);
        });
    } catch (error) {
        console.error('Error fetching plants:', error.message);
    }
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

function createMutationObserver(plantType, temp, air, soil) {
    const observer = new MutationObserver(function (mutations) {
        mutations.forEach(function (mutation) {
            if (mutation.type === "characterData" || mutation.type === "childList") {
                updateStats(plantType);
            }
        });
    });

    [temp, air, soil].forEach(element => {
        observer.observe(element, {
            characterData: true,
            childList: true,
            subtree: true
        });
    });
}
