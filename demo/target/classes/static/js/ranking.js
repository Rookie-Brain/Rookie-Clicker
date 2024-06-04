document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/ranking')
        .then(response => response.json())
        .then(data => {
            console.log(`Ranking data: ${JSON.stringify(data)}`);
            const rankingTable = document.getElementById('ranking-table');
            rankingTable.innerHTML = ''; // Asegúrate de que la tabla esté vacía antes de llenarla
            if (data.length === 0) {
                const emptyRow = document.createElement('tr');
                const emptyCell = document.createElement('td');
                emptyCell.colSpan = 3;
                emptyCell.textContent = 'No hay datos de ranking disponibles.';
                emptyRow.appendChild(emptyCell);
                rankingTable.appendChild(emptyRow);
            } else {
                data.forEach(player => {
                    const row = document.createElement('tr');
                    const positionCell = document.createElement('td');
                    const nameCell = document.createElement('td');
                    const scoreCell = document.createElement('td');

                    positionCell.textContent = player.position;
                    nameCell.textContent = player.nickname;
                    scoreCell.textContent = player.score;

                    row.appendChild(positionCell);
                    row.appendChild(nameCell);
                    row.appendChild(scoreCell);

                    rankingTable.appendChild(row);
                });
            }
        })
        .catch(error => console.error('Error fetching ranking:', error));
});
