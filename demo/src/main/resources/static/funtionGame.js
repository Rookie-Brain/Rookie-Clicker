document.addEventListener('DOMContentLoaded', (event) => {
    var clicks = 0;
    var clicksNecesarios = 1;
    const codigosArrays = ["codigo1", "codigo2", "codigo3"];
    var tiempo = 25
    document.getElementById("temporizador").innerHTML = tiempo;
    var tempo = tiempo;
    var fps = 60;

    var clickerImage = document.getElementById('clicker-image');
    if (clickerImage) {
        clickerImage.addEventListener('click', clic);
    }

    function clic(playerId=1) {
        if (tempo > 0) {
            clicks++;
            guardarClic(playerId=1); // Llama a la función para guardar el clic con el playerId
            render();

            if (clicks == 1) {
                temporizador();
            }
        }
    }

    function guardarClic(playerId=1) {
        fetch(`/api/click/${playerId=1}`, {
                method: 'POST'
            })
            .then(response => response.json())
            .then(score => {
                console.log(`El score actualizado del jugador es: ${score}`);
            })
            .catch(error => {
                console.error('Error al guardar el clic:', error);
            });
    }

    function render() {
        document.getElementById("contClicks").innerHTML = clicks;
    }

    function temporizador() {
        var tempElement = document.getElementById("temporizador");
        if (tempElement) {
            if (tempo <= 0) {
                alert('Se acabó el tiempo');
            } else {
                tempElement.innerHTML = tempo;
                tempo--;
                setTimeout(temporizador, 1000);
            }
        }
    }

    function mostrarCodigoSiEsNecesario() {
        if (clicks >= clicksNecesarios && tempo == 0) {
            const elementoAleatorio = codigosArrays[Math.floor(Math.random() * codigosArrays.length)];
            document.getElementById("codigos").innerHTML = elementoAleatorio;
            document.getElementById("codigoContenedor").style.display = "block";
        }
    }


    const intervalo = setInterval(function () {
        if (clicks >= clicksNecesarios && tempo == 0) {
            mostrarCodigoSiEsNecesario();
            clearInterval(intervalo);
        }
    }, 1000 / fps);
});

