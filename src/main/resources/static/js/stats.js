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
