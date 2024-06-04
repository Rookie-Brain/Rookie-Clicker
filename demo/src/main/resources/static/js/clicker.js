document.addEventListener('DOMContentLoaded', (event) => {
    const urlParams = new URLSearchParams(window.location.search);
    const level = urlParams.get('level');
    const nickname = urlParams.get('nickname');

    if (!level || !nickname) {
        alert('Faltan datos necesarios.');
        window.location.href = 'game.html';
        return;
    }

    let clicks = 0;
    let tiempo = 0;
    let clicksNecesarios = 0;
    let vidasRestantes = 0;
    let interval;

    function startLevel() {
        fetch('/api/gameTest')
            .then(response => response.json())
            .then(data => {
                console.log(data);
                const nivel = data.niveles[level - 1];
                if (!nivel) {
                    alert('Nivel no encontrado.');
                    window.location.href = 'game.html';
                    return;
                }
                tiempo = nivel.time;
                clicksNecesarios = nivel.score;
                vidasRestantes = nivel.lives;
                clicks = 0;
                document.getElementById('temporizador').textContent = tiempo;
                document.getElementById('contClicks').textContent = clicks;
                document.getElementById('clicks-necesarios').textContent = clicksNecesarios;
                document.getElementById('vidas-restantes').textContent = vidasRestantes;

                interval = setInterval(() => {
                    if (tiempo > 0) {
                        tiempo--;
                        document.getElementById('temporizador').textContent = tiempo;
                    } else {
                        clearInterval(interval);
                        vidasRestantes--;
                        document.getElementById('vidas-restantes').textContent = vidasRestantes;
                        if (vidasRestantes > 0) {
                            document.getElementById('revive-button').style.display = 'block';
                        } else {
                            fetch('/api/gameTest/score', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify({ nickname, score: clicks })
                            })
                            .then(response => response.json())
                            .then(data => {
                                alert(`Tiempo terminado. Puntuación: ${clicks}`);
                                window.location.href = 'ranking.html';  // Redirigir al ranking
                            });
                        }
                    }
                }, 1000);
            });
    }

    document.getElementById('clicker-image').addEventListener('click', () => {
        if (tiempo > 0) {
            clicks++;
            document.getElementById('contClicks').textContent = clicks;
            if (clicks >= clicksNecesarios) {
                clearInterval(interval);
                alert('¡Nivel completado!');
                fetch('/api/gameTest/score', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ nickname, score: clicks })
                })
                .then(response => response.json())
                .then(data => {
                    alert(`¡Felicidades! Has pasado el nivel con una puntuación de ${clicks}`);
                    window.location.href = 'ranking.html';  // Redirigir al ranking
                });
            }
        }
    });

    document.getElementById('revive-button').addEventListener('click', () => {
        fetch('/api/gameTest/revive', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nickname, level })
        })
        .then(response => response.json())
        .then(data => {
            vidasRestantes = data.lives;
            document.getElementById('vidas-restantes').textContent = vidasRestantes;
            clicks = 0;
            document.getElementById('contClicks').textContent = clicks;
            document.getElementById('revive-button').style.display = 'none';
            startLevel();
        });
    });

    startLevel();
});

