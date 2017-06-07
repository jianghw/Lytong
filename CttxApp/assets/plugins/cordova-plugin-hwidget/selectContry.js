cordova.define("cordova-plugin-hwidget.selectContry",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            selectContry: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "CTTXSelectContry",//feature name
                "selectContry",//action
                [content]//要传递的参数，json格式
                );
            }
        }
});