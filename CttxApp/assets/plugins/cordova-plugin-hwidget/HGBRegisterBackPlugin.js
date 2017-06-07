cordova.define("cordova-plugin-hwidget.backRegisterStatus",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            backRegisterStatus: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "HGBRegisterBackPlugin",//feature name
                "backRegisterStatus",//action
                [content]//要传递的参数，json格式
                );
            }
        }
});