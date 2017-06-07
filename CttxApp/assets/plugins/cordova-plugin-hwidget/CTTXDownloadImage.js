cordova.define("cordova-plugin-hwidget.downloadImageWithUrl",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            downloadImageWithUrl: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "CTTXDownloadImage",//feature name
                "downloadImageWithUrl",//action
                [content]//要传递的参数，json格式
                );
            }
        }
});