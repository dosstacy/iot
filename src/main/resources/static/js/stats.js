function updateStats(plantType) {
    const temp = parseFloat(document.querySelector('.temp-stats').textContent);
    const air = parseFloat(document.querySelector('.air-stats').textContent);
    const soil = parseFloat(document.querySelector('.soil-stats').textContent);

    const stats = document.querySelectorAll('.temp-stats, .air-stats, .soil-stats');

    switch(plantType) {
        case 'dry':
            stats.forEach(stat => {
                if ((stat.classList.contains('temp-stats') && (temp < 5 || temp > 45)) ||
                    (stat.classList.contains('air-stats') && (air < 30 || air > 50)) ||
                    (stat.classList.contains('soil-stats') && (soil < 10 || soil > 30))) {
                    stat.style.color = 'red';
                }
            });
            break;

        case 'med':
            stats.forEach(stat => {
                if ((stat.classList.contains('temp-stats') && (temp < 10 || temp > 35)) ||
                    (stat.classList.contains('air-stats') && (air < 50 || air > 70)) ||
                    (stat.classList.contains('soil-stats') && (soil < 40 || soil > 60))) {
                    stat.style.color = 'red';
                }
            });
            break;

        case 'water':
            stats.forEach(stat => {
                if ((stat.classList.contains('temp-stats') && (temp < 15 || temp > 30)) ||
                    (stat.classList.contains('air-stats') && (air < 70 || air > 80)) ||
                    (stat.classList.contains('soil-stats') && (soil < 60 || soil > 80))) {
                    stat.style.color = 'red';
                }
            });
            break;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const plantType = new URLSearchParams(window.location.search).get('plant');
    updateStats(plantType);
});

function checkPlantState(){
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
