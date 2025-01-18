let selectedPlantType = null;

function selectPlant(type) {
    selectedPlantType = type;
    console.log(`Selected plant type: ${type}`);
}

async function savePlant() {
    const plantName = document.getElementById('plant-name').value;

    if (!plantName || !selectedPlantType) {
        alert('Please select a plant type and enter a plant name.');
        return;
    }

    const payload = {
        plantName: plantName,
        plantType: selectedPlantType,
    };

    try {
        const response = await fetch('/api/plants/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        });

        if (response.ok) {
            console.log('Plant saved successfully!');
            window.location.href = "/smartPlantie/stats";
        } else {
            const errorMessage = await response.text();
            alert(errorMessage);
        }
    } catch (error) {
        console.error('Error saving plant:', error);
        console.log('An error occurred while saving the plant.');
    }
}
