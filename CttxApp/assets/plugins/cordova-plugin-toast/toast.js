cordova.define("cordova-plugin-dialog.AtoB",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            networkRequest: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "CTTXNetworkRequest",//feature name
                "networkRequest",//action
                [content]//要传递的参数，json格式
                );
            }
        }
});