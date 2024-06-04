document.addEventListener("DOMContentLoaded", function() {
    if (window.location.pathname.endsWith("game.html")) {
        const nicknameInput = document.getElementById('nickname');
        nicknameInput.addEventListener('change', function() {
            const nickname = this.value;
            fetch('/api/gameTest/nickname', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ nickname })
            })
            .then(response => response.json())
            .then(data => {
                console.log(`Nickname set to: ${data.nickname}, Score: ${data.score}`);
            });
        });

        fetch('/api/gameTest')
            .then(response => response.json())
            .then(data => {
                document.getElementById('lives-count').textContent = data.lives;
                updateLevelsTable(data.niveles);
            });
    }
});

function modifyLives(change) {
    fetch('/api/gameTest/lives', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ change })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('lives-count').textContent = data.lives;
    });
}

function addLevel() {
    const scoreInput = parseInt(document.getElementById('score-input').value) || 0;
    const timeInput = parseInt(document.getElementById('time-input').value) || 0;
    fetch('/api/gameTest/levels', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ action: 'add', scoreInput, timeInput })
    })
    .then(response => response.json())
    .then(data => {
        console.log(`Levels after add: ${JSON.stringify(data.niveles)}`);
        updateLevelsTable(data.niveles);
        resetFormValues();
    });
}

function resetFormValues() {
    document.getElementById('score-input').value = '';
    document.getElementById('time-input').value = '';
    document.getElementById('lives-count').textContent = 2;
}

function removeLevel(level) {
    fetch('/api/gameTest/levels', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ action: 'remove', level })
    })
    .then(response => response.json())
    .then(data => {
        console.log(`Levels after remove: ${JSON.stringify(data.niveles)}`);
        updateLevelsTable(data.niveles);
    });
}

function resetLevels() {
    fetch('/api/gameTest/levels/reset', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log(`Levels after reset: ${JSON.stringify(data.niveles)}`);
        updateLevelsTable(data.niveles);
    });
}

function updateLevelsTable(niveles) {
    const table = document.getElementById('levels-table');
    while (table.rows.length > 0) {
        table.deleteRow(0);
    }
    niveles.forEach((nivel, index) => {
        const row = table.insertRow();
        const cell1 = row.insertCell(0);
        const cell2 = row.insertCell(1);
        const cell3 = row.insertCell(2);
        const cell4 = row.insertCell(3);
        const cell5 = row.insertCell(4);
        cell1.textContent = `Nivel ${index + 1}`;
        cell2.textContent = nivel.lives;
        cell3.textContent = nivel.score;
        cell4.textContent = nivel.time;
        cell5.innerHTML = `
            <button class="btn btn-danger" onclick="removeLevel(${index + 1})">Eliminar</button>
            <button class="btn btn-primary" onclick="playLevel(${index + 1})">Play</button>
        `;
    });
}

function playLevel(level) {
    const nickname = document.getElementById('nickname').value;
    if (!nickname) {
        alert('Por favor, ingrese un nickname.');
        return;
    }

    const queryParams = new URLSearchParams({ level, nickname }).toString();
    window.location.href = `clicker.html?${queryParams}`;
}
