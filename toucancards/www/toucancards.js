cordova.define("ru.slowkazak.toucan.cards", function(require, exports, module) {
    var exec = require('cordova/exec');

    exports.payment = function(arg0, success, error) {
        exec(success, error, "toucancards", "payment", [arg0]);
    };

});
