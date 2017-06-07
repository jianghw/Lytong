cordova.define("cordova-plugin-hwidget.JumpToNative",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            JumpToNative: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "JumpToNative",//feature name类名
                "JumpToNative",//action方法名
                [content]//要传递的参数，json格式
                );
            }
        }
});