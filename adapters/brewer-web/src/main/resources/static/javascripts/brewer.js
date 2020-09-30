$(function() {
    $('.js-decimal').maskMoney();

    $('.js-integer').maskMoney({
        precision: 0
    });
});
