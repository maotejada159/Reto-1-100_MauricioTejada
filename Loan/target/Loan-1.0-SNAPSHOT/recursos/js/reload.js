/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var time = new Date().getTime();
$(document.body).bind("mousemove keypress", function (e) {
    time = new Date().getTime();
});

function refresh() {
    if (new Date().getTime() - time >= 1800000)
        window.location.reload(true);
    else
        setTimeout(refresh, 10000);
}

setTimeout(refresh, 10000);

function evitarRedirect() {

    if (window.history.replaceState) { // verificamos disponibilidad
        window.history.replaceState(null, null, window.location.href);
    }
}


