cordova.define("cordova-plugin-hwidget.insurancePay",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            insurancePay: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "CTTXInsurancePay",//feature name
                "insurancePay",//action
                [content]//要传递的参数，json格式
                );
            }
        }
});