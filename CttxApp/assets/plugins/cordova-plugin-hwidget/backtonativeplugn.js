cordova.define("cordova-plugin-hwidget.BackToNativePlugn",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            backToNatiove: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "BackToNative",//feature name
                "backToNatiove",//action
                [content]//要传递的参数，json格式
                );
            }
        }
});