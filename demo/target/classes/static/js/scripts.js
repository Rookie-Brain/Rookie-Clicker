// Fetch all games
async function fetchGames() {
    try {
        const response = await axios.get('http://localhost:8080/api/games');
        console.log(response.data);
        // Update DOM or handle response
    } catch (error) {
        console.error('Error fetching games:', error);
    }
}