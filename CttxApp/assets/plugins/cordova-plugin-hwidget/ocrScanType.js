cordova.define("cordova-plugin-hwidget.ocrScanType",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            ocrScanType: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "CTTXOCRScan",//feature name类名
                "ocrScanType",//action方法名
                [content]//要传递的参数，json格式
                );
            }
        }
});