document.getElementById('clicker-image').addEventListener('click', async function() {
    const playerId = localStorage.getItem('playerId'); // O cualquier método que uses para obtener el ID del jugador
    try {
        const response = await axios.post(`http://localhost:8080/api/click/${playerId}`);
        console.log(response.data);
        // Update score or handle response
    } catch (error) {
        console.error('Error incrementing score:', error);
    }
});

async function revivePlayer(playerId) {
    // Logic to revive player
}