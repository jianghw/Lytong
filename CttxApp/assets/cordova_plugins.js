cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
            "file": "plugins/cordova-plugin-toast/toast.js",//js文件路径
            "id": "cordova-plugin-dialog.AtoB",//js cordova.define的id
            "clobbers": [
                "CTTXNetworkRequest"//js 调用时的方法名
            ]
    },
    {
            "file": "plugins/cordova-plugin-hwidget/selectPicker.js",//js文件路径
            "id": "cordova-plugin-hwidget.selectPicker",//js cordova.define的id
            "clobbers": [
                "CTTXSelectPicker"//js 调用时的方法名
            ]
    },
    {
            "file": "plugins/cordova-plugin-hwidget/backtonativeplugn.js",//js文件路径
            "id": "cordova-plugin-hwidget.BackToNativePlugn",//js cordova.define的id
            "clobbers": [
                "BackToNative"//js 调用时的方法名
            ]
    },
    {
            "file": "plugins/cordova-plugin-hwidget/selectContry.js",//js文件路径
            "id": "cordova-plugin-hwidget.selectContry",//js cordova.define的id
            "clobbers": [
                "CTTXSelectContry"//js 调用时的方法名
            ]
    },
    {
            "file": "plugins/cordova-plugin-hwidget/lifes.js",//js文件路径
            "id": "cordova-plugin-hwidget.lifes",//js cordova.define的id
            "clobbers": [
                "CTTXLifeJump"//js 调用时的方法名
            ]
    },
    {
            "file": "plugins/cordova-plugin-hwidget/JumpToNative.js",//js文件路径
            "id": "cordova-plugin-hwidget.JumpToNative",//js cordova.define的id
            "clobbers": [
                "JumpToNative"//js 调用时的方法名
            ]
    },
    {
            "file": "plugins/cordova-plugin-hwidget/CTTXInsurancePay.js",//js文件路径
            "id": "cordova-plugin-hwidget.insurancePay",//js cordova.define的id
            "clobbers": [
                "CTTXInsurancePay"//js 调用时的方法名
            ]
    },
    {
            "file": "plugins/cordova-plugin-hwidget/CTTXLifeLoopImage.js",//js文件路径
            "id": "cordova-plugin-hwidget.queryLifeLoop",//js cordova.define的id
            "clobbers": [
                "CTTXLifeLoopImage"//js 调用时的方法名
            ]
    },
    {
            "file": "plugins/cordova-plugin-hwidget/CTTXDownloadImage.js",//js文件路径
            "id": "cordova-plugin-hwidget.downloadImageWithUrl",//js cordova.define的id
            "clobbers": [
                "CTTXDownloadImage"//js 调用时的方法名
            ]
    },
    {
            "file": "plugins/cordova-plugin-hwidget/HGBRegisterBackPlugin.js",//js文件路径
            "id": "cordova-plugin-hwidget.backRegisterStatus",//js cordova.define的id
            "clobbers": [
                "HGBRegisterBackPlugin"//js 调用时的方法名
            ]
    },
    {
            "file": "plugins/cordova-plugin-getuser/getuser.js",//js文件路径
            "id": "cordova-plugin-getuser.GetUser",//js cordova.define的id
            "clobbers": [
                "CTTXGetUserMessage"//js 调用时的方法名
            ]
    }
    ,
     {
             "file": "plugins/cordova-plugin-hwidget/ocrScanType.js",//js文件路径
             "id": "cordova-plugin-hwidget.ocrScanType",//js cordova.define的id
             "clobbers": [
                 "CTTXOCRScan"//js 调用时的方法名
             ]
     }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.2.2"
};
// BOTTOM OF METADATA
});