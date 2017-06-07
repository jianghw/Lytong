cordova.define("cordova-plugin-hwidget.lifes",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            lifeJump: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "CTTXLifeJump",//feature name
                "lifeJump",//action
                [content]//要传递的参数，json格式
                );
            },
            backlife: function(content){
                            exec(
                            function(message){//成功回调function
                                console.log(message);
                            },
                            function(message){//失败回调function
                                console.log(message);
                            },
                            "CTTXLifeJump",//feature name
                            "backlife",//action
                            [content]//要传递的参数，json格式
                            );
            }
        }
});