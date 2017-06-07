cordova.define("cordova-plugin-hwidget.queryLifeLoop",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            queryLifeLoop: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "CTTXLifeLoopImage",//feature name
                "queryLifeLoop",//action
                [content]//要传递的参数，json格式
                );
            }
        }
});