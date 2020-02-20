$(function () {
    let numbers = $('.g-number');

    setInterval(() => {
        Array.prototype.slice.call(numbers).forEach(key => {
            $(key).attr('data-digit', parseInt(Math.random() * 9));
});
}, 2000);
});