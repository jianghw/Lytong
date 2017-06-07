cordova.define("cordova-plugin-hwidget.selectPicker",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            selectPicker: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "CTTXSelectPicker",//feature name
                "selectPicker",//action
                [content]//要传递的参数，json格式
                );
            }
        }
});