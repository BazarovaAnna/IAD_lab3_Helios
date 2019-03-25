function fillCanvas(name, width, height) {
    let canvas = document.getElementById(name);
    if (canvas == null)
        return;
    let ctx = canvas.getContext("2d");

    ctx.fillStyle = "#5FC0CE";
    ctx.fillRect(0, 0, width, height);
}

function paintClock(name, width, height) {

    let canvas = document.getElementById(name);
    if (canvas == null)
        return;
    let ctx = canvas.getContext("2d");
    let time = new Date();

    ctx.lineWidth = 3;

    ctx.fillStyle = "black";
    ctx.beginPath();
    ctx.arc(width / 2, height / 2, width / 2 - 1, 0, Math.PI * 2, true);
    ctx.stroke();

    drawMarkers(ctx, width, height);

    ctx.beginPath();
    ctx.moveTo(width / 2 - (width / 2 - 130) * Math.cos(Math.PI / 2 - 6 * (time.getMinutes() + (1 / 60) * (time.getSeconds()+(time.getMilliseconds()/1000))) * (Math.PI / 180)),
        height / 2 + (height / 2 - 130) * Math.sin(Math.PI / 2 - 6 * (time.getMinutes() + (1 / 60) * (time.getSeconds()+(time.getMilliseconds()/1000))) * (Math.PI / 180)));
    ctx.lineTo(width / 2 + (width / 2 - 15) * Math.cos(Math.PI / 2 - 6 * (time.getMinutes() + (1 / 60) * (time.getSeconds()+(time.getMilliseconds()/1000))) * (Math.PI / 180)),
        height / 2 - (height / 2 - 15) * Math.sin(Math.PI / 2 - 6 * (time.getMinutes() + (1 / 60) * (time.getSeconds()+(time.getMilliseconds()/1000))) * (Math.PI / 180)));
    ctx.stroke();

    ctx.lineWidth = 5;
    ctx.beginPath();
    ctx.moveTo(width / 2 - (width / 2 - 135) * Math.cos(Math.PI / 2 - 30 * (time.getHours() + (1 / 60) * time.getMinutes()) * (Math.PI / 180)),
        height / 2 + (height / 2 - 135) * Math.sin(Math.PI / 2 - 30 * (time.getHours() + (1 / 60) * time.getMinutes()) * (Math.PI / 180)));
    ctx.lineTo(width / 2 + (width / 2 - 70) * Math.cos(Math.PI / 2 - 30 * (time.getHours() + (1 / 60) * time.getMinutes()) * (Math.PI / 180)),
        height / 2 - (height / 2 - 70) * Math.sin(Math.PI / 2 - 30 * (time.getHours() + (1 / 60) * time.getMinutes()) * (Math.PI / 180)));
    ctx.stroke();
    ctx.lineWidth = 3;

    ctx.strokeStyle = "red";
    drawLine(ctx, 110, 10, (time.getSeconds()+(time.getMilliseconds()/1000)), width, height);

    ctx.strokeStyle = "black";
    ctx.fillStyle = "yellow";
    ctx.beginPath();
    ctx.arc(width / 2, height / 2, 3, 0, Math.PI * 2, true);
    ctx.stroke();
    ctx.fill();
}

function paintExtraClock(name, width, height) {
    let canvas = document.getElementById(name);
    if (canvas == null)
        return;
    let ctx = canvas.getContext("2d");

    ctx.lineWidth = 1;

    ctx.beginPath();

    roundRect(ctx, width, height, 60, 25, 5);
    ctx.stroke();

    ctx.strokeStyle = "black";
    ctx.fillStyle = "white";
    ctx.font = "20px Arial";
    date = new Date().getDate();
    if (date < 10)
        date = "0" + date;
    month = new Date().getMonth() + 1;
    if (month < 10)
        month = "0" + month;
    ctx.fillStyle = "black";
    ctx.fillText(date + "." + month, width + 5, height + 20);
}

function roundRect(ctx, x, y, w, h, r) {
    if (w < 2 * r) r = w / 2;
    if (h < 2 * r) r = h / 2;
    ctx.beginPath();
    ctx.moveTo(x + r, y);
    ctx.arcTo(x + w, y, x + w, y + h, r);
    ctx.arcTo(x + w, y + h, x, y + h, r);
    ctx.arcTo(x, y + h, x, y, r);
    ctx.arcTo(x, y, x + w, y, r);
    ctx.closePath();
}

function drawLine(ctx, a, b, c, width, height) {
    ctx.beginPath();
    ctx.moveTo(width / 2 - (width / 2 - a) * Math.cos(Math.PI / 2 - 6 * c * (Math.PI / 180)),
        height / 2 + (height / 2 - a) * Math.sin(Math.PI / 2 - 6 * c * (Math.PI / 180)));
    ctx.lineTo(width / 2 + (width / 2 - b) * Math.cos(Math.PI / 2 - 6 * c * (Math.PI / 180)),
        height / 2 - (height / 2 - b) * Math.sin(Math.PI / 2 - 6 * c * (Math.PI / 180)));
    ctx.stroke();
}

function drawMarkers(ctx, width, height) {
    for (let i = 0; i <= 30; i += 5) {
        drawLine(ctx, 25, height - 10, i, width, height);
        drawLine(ctx, width - 10, 25, i, width, height);
    }
}

function paintClocks() {
    const canvasName = "clock";
    const width = 300;
    const height = 300;
    fillCanvas(canvasName, width, height);
    paintExtraClock(canvasName, width - 120, height / 2 - 10);
    paintClock(canvasName, width, height);
}

$(() => {
    paintClocks();
addHover();
});

setInterval(() => {
    paintClocks();
}, 50);

function addHover() {
    $("canvas").hover(function() {
        $(this).attr("title", new Date().toLocaleString());
    }, function() {
        $(this).attr("title", new Date().toLocaleString());
    });
}