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

    function clic() {
        if (tempo > 0) {
            clicks++;
            render();

            if (clicks==1){
            temporizador();
            }
        }
    }

    function enviarClic() {
            fetch('/api/clicks/1', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ click: true })
            })
            .then(response => response.json())
            .then(data => {
                // Actualizar la cantidad de clics recibidos del servidor
                clicks = data.clicks;
                render();
            })
            .catch(error => console.error('Error al enviar clic al servidor:', error));
        }

    function render() {
        document.getElementById("contClicks").innerHTML = clicks;
    }

    function temporizador() {
       var tempElement = document.getElementById("temporizador");
           if (tempElement) {
               var tiempo = parseInt(tempElement.innerHTML);
               if (!isNaN(tiempo)) {
                   if (tiempo <= 0) {
                       alert('Se acabó el tiempo');
                   } else {
                       tiempo--;
                       tempElement.innerHTML = tiempo;
                       setTimeout(temporizador, 1000);
               }
               }
               }

    function mostrarCodigoSiEsNecesario() 
    {
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

