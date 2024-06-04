/* Inicializar el almacenamiento en localStorage si no existe
if (!localStorage.getItem('mockGame')) {
    localStorage.setItem('mockGame', JSON.stringify({
        lives: 2,
        niveles: [
            { lives: 2, score: 100, time: 60 },
            { lives: 3, score: 150, time: 90 },
        ]
    }));
}

if (!localStorage.getItem('mockUsers')) {
    localStorage.setItem('mockUsers', JSON.stringify({}));
}

const getMockGame = () => JSON.parse(localStorage.getItem('mockGame'));
const setMockGame = (game) => localStorage.setItem('mockGame', JSON.stringify(game));

const getMockUsers = () => JSON.parse(localStorage.getItem('mockUsers'));
const setMockUsers = (users) => localStorage.setItem('mockUsers', JSON.stringify(users));

document.addEventListener("DOMContentLoaded", function() {
    if (window.location.pathname.endsWith("game.html")) {
        const nameInput = document.getElementById('nickname');
        nameInput.addEventListener('change', function() {
            const name = this.value;
            const player = { id: 0, lives: 2, name: name, score: 0 };  // Crear objeto Player con todas las propiedades necesarias

            fetch('http://localhost:8080/api/players', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(player)  // Enviar objeto Player
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(text); });
                }
                return response.json();  // Convertir la respuesta a JSON si es correcta
            })
            .then(data => {
                console.log(`Player created with Name: ${data.name}, Score: ${data.score}, Lives: ${data.lives}, ID: ${data.id}`);
            })
            .catch(error => {
                console.error('Error:', error.message);
            });
        });
    }
});



*/















/ Inicializar el almacenamiento en localStorage si no existe






if (!localStorage.getItem('mockGame')) {
    localStorage.setItem('mockGame', JSON.stringify({
        lives: 2,
        niveles: [
            { lives: 2, score: 100, time: 60 },
            { lives: 3, score: 150, time: 90 },
        ]
    }));
}

if (!localStorage.getItem('mockUsers')) {
    localStorage.setItem('mockUsers', JSON.stringify({}));
}

const getMockGame = () => JSON.parse(localStorage.getItem('mockGame'));
const setMockGame = (game) => localStorage.setItem('mockGame', JSON.stringify(game));

const getMockUsers = () => JSON.parse(localStorage.getItem('mockUsers'));
const setMockUsers = (users) => localStorage.setItem('mockUsers', JSON.stringify(users));

const realFetch = globalThis.fetch;

globalThis.mockFetch = (url, options) => {
    console.log(`Mock Fetching: ${url}`, options);

    const mockGame = getMockGame();
    const mockUsers = getMockUsers();

    if (url === '/api/gameTest') {
        return Promise.resolve({
            json: () => Promise.resolve(mockGame)
        });
    } else if (url === '/api/gameTest/nickname' && options.method === 'POST') {
        const { nickname } = JSON.parse(options.body);
        if (!mockUsers[nickname]) {
            mockUsers[nickname] = { score: 0 };
        }
        setMockUsers(mockUsers);
        console.log(`Nickname set: ${nickname}, Score: ${mockUsers[nickname].score}`);
        return Promise.resolve({
            json: () => Promise.resolve({ nickname, score: mockUsers[nickname].score })
        });
    } else if (url === 'http://localhost:8080/api/players' && options.method === 'POST') {
        return realFetch(url, options);
    } else if (url === '/api/gameTest/lives' && options.method === 'POST') {
        const change = JSON.parse(options.body).change;
        mockGame.lives += change;
        setMockGame(mockGame);
        console.log(`Lives changed: ${mockGame.lives}`);
        return Promise.resolve({
            json: () => Promise.resolve({ lives: mockGame.lives })
        });
    } else if (url === '/api/gameTest/levels' && options.method === 'POST') {
        const { action, scoreInput, timeInput } = JSON.parse(options.body);
        if (action === 'add') {
            mockGame.niveles.push({ lives: 2, score: scoreInput, time: timeInput });
            console.log(`Level added: ${JSON.stringify(mockGame.niveles)}`);
        } else if (action === 'remove' && mockGame.niveles.length > 0) {
            mockGame.niveles.pop();
            console.log(`Level removed: ${JSON.stringify(mockGame.niveles)}`);
        }
        setMockGame(mockGame);
        return Promise.resolve({
            json: () => Promise.resolve({ niveles: mockGame.niveles })
        });
    } else if (url === '/api/gameTest/levels/reset' && options.method === 'POST') {
        mockGame.niveles = [];
        setMockGame(mockGame);
        console.log(`Levels reset: ${JSON.stringify(mockGame.niveles)}`);
        return Promise.resolve({
            json: () => Promise.resolve({ niveles: mockGame.niveles })
        });
    } else if (url === '/api/gameTest/score' && options.method === 'POST') {
        const { nickname, score } = JSON.parse(options.body);
        if (mockUsers[nickname]) {
            mockUsers[nickname].score = Math.max(mockUsers[nickname].score, score);
        } else {
            mockUsers[nickname] = { score };
        }
        setMockUsers(mockUsers);
        console.log(`Score updated: ${nickname}, ${mockUsers[nickname].score}`);
        return Promise.resolve({
            json: () => Promise.resolve({ score: mockUsers[nickname].score })
        });
    } else if (url === '/api/gameTest/revive' && options.method === 'POST') {
        const { nickname, level } = JSON.parse(options.body);
        const nivel = mockGame.niveles[level - 1];
        nivel.lives--;
        setMockGame(mockGame);
        console.log(`Revive used: ${nickname}, Level: ${level}, Lives left: ${nivel.lives}`);
        return Promise.resolve({
            json: () => Promise.resolve({ lives: nivel.lives })
        });
    } else if (url === '/api/ranking') {
        const ranking = Object.entries(mockUsers)
            .map(([nickname, data]) => ({ nickname, score: data.score }))
            .sort((a, b) => b.score - a.score)
            .map((user, index) => ({ position: index + 1, ...user }));
        console.log(`Ranking fetched: ${JSON.stringify(ranking)}`);
        return Promise.resolve({
            json: () => Promise.resolve(ranking)
        });
    }

    return Promise.reject(new Error('Unknown URL or Method'));
};

document.addEventListener("DOMContentLoaded", function() {
    if (window.location.pathname.endsWith("game.html")) {
        const nameInput = document.getElementById('nickname');
        nameInput.addEventListener('change', function() {
            const name = this.value;
            const player = { id: 0, lives: 2, name: name, score: 0 };  // Crear objeto Player con todas las propiedades necesarias

            realFetch('http://localhost:8080/api/players', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(player)  // Enviar objeto Player
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(text); });
                }
                return response.json();
            })
            .then(data => {
                console.log(`Player created with Name: ${data.name}, Score: ${data.score}, Lives: ${data.lives}, ID: ${data.id}`);
            })
            .catch(error => {
                console.error('Error:', error.message);
            });
        });
    }
});
