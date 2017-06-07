webpackJsonp([0,2],[
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Created by dongfenghua on 2016/11/7.
	 */

	$(function(){
	    //require('../css/personal/index.css');
	    //const CTTXPlugin = require('./mJSSDKPlugin');
	    //const validation = require('./SRBankValidation');
	    const template = __webpack_require__(1);

	    $(document).on('pageInit', '#shear_index', function () {

	        var signIn_days = '';

	        //活动首页上送报文
	        var activityFirst_json = {"usrnum":"00335517717406260"};

	        $.ajax({
	            //有违章，无返回值
	            url: "http://139.196.183.121:8081/february/personalActivity",
	            type: 'POST',
	            data: JSON.stringify(activityFirst_json),
	            dataType: "json",
	            contentType: "text/plain;",
	            processData:false,
	            success: function (msg) {
	                console.log(msg);
	                signIn_days = msg.data.continuitySign;
	                if(!(signIn_days >= 0)){
	                    $('#signIn_days').html('+0');
	                }else{
	                    $('#signIn_days').html('+'+signIn_days);
	                }
	                $('#noViolation_count').html(msg.data.notPeccancy);
	                $('#user_information').html(msg.data.id);
	                $('#user_information_all').html(msg.data.ranking);
	                $('#user_integral').html(msg.data.points);
	            },
	            error: function (msg) {
	                alert("请求申请信息超时");
	            }
	        })

	        //报名活动人数

	        $.ajax({
	            url: "http://139.196.183.121:8081/february/activityCount",
	            type: 'POST',
	            data: '',
	            dataType: "json",
	            success: function (msg) {
	                //console.log(msg);
	                $('#activity_count').html(msg.data.activityCount);
	            },
	            error: function (msg) {
	                alert("请求申请信息超时");
	            }
	        })

	        //积分排行版前15名

	        $.ajax({
	            url: "http://139.196.183.121:8081/february/rankingList",
	            type: 'POST',
	            data: '',
	            dataType: "json",
	            success: function (msg) {
	                //模板
	                var arr=msg.data.concat(msg.data);
	                var data = {
	                    list:arr
	                };
	                var html = template('test', data);
	                document.getElementById('information_template').innerHTML = html;
	                //前三名添加图标
	               $(".rank_list").find(".font_red")[0].innerHTML='<img src="../img/images/1@2x.png">';
	               $(".rank_list").find(".font_red")[1].innerHTML='<img src="../img/images/2@2x.png">';
	               $(".rank_list").find(".font_red")[2].innerHTML='<img src="../img/images/3@2x.png">';
	               var len=msg.data.length;
	                $(".rank_list").find(".font_red")[len].innerHTML='<img src="../img/images/1@2x.png">';
	               $(".rank_list").find(".font_red")[len+1].innerHTML='<img src="../img/images/2@2x.png">';
	               $(".rank_list").find(".font_red")[len+2].innerHTML='<img src="../img/images/3@2x.png">';
	                //循环显示
	                var roll=document.getElementById("rank_ul");
	                var roll_height=document.getElementById("rank_ul").offsetHeight-375;
	                var temp=0;
	                var timer=setInterval(function(){
	                    if(temp>-roll_height){
	                        temp--;
	                        roll.style.top=temp+"px";
	                    }
	                    else{
	                        temp=0;
	                        roll.style.top=temp+"px";
	                    }
	                },30);
	            },
	            error: function (msg) {
	                alert("请求申请信息超时");
	            }
	        })

	        //获取中奖名单

	        $.ajax({
	            url: "http://139.196.183.121:8081/february/winningList",
	            type: 'POST',
	            data: '',
	            dataType: "json",
	            success: function (msg) {
	                //console.log(msg.data[0].winningDate);
	                var data = {
	                    list:msg.data
	                };
	                var htmlt = template('prize-list', data);
	                document.getElementById('prize-arae').innerHTML = htmlt;
	                $(".prize_date").each(function(index){
	                    var res=msg.data[index].winningDate.match(/\d+/g);
	                    this.innerHTML=parseInt(res[1])+'月'+parseInt(res[2])+'日';
	                })
	                //循环显示
	                if(msg.data.length>5){
	                    var roll=document.getElementById("prize_ul");
	                    var roll_height=document.getElementById("prize_ul").offsetHeight-220;
	                    var temp=0;
	                    var timer=setInterval(function(){
	                        if(temp>-roll_height){
	                            temp--;
	                            roll.style.top=temp+"px";
	                        }
	                        else{
	                            temp=0;
	                            roll.style.top=temp+"px";
	                        }
	                    },30);
	                }
	                
	            },
	            error: function (msg) {
	                alert("请求申请信息超时");
	            }
	        })

	        $('#sign_In').on('click',function(){
	            var _this = $(this);
	            var _this_html = _this.html();
	            //签到上送报文
	            var signIn_json = {"usrnum":"00335517717406260"};

	            if(_this_html == '签到'){
	                $.ajax({
	                    url: "http://139.196.183.121:8081/february/everydaySign",
	                    type: 'POST',
	                    data: JSON.stringify(signIn_json),
	                    dataType: "json",
	                    contentType: "text/plain;",
	                    processData:false,
	                    success: function (msg) {
	                        _this.html('已签到');
	                        //$.toast('获得' + msg.data.signScore + '积分');
	                        if (!(signIn_days >= 0)) {
	                            $('#signIn_days').html(0 + 1);
	                        } else {
	                            //console.log(signIn_days);
	                            $('#signIn_days').html('+' + (Number(signIn_days) + 1));
	                        }
	                    },
	                    error: function (msg) {
	                        alert("请求申请信息超时");
	                    }
	                })
	            }else{
	                $.toast('已签到过！！');
	            }


	        });

	        $("#a-bangka").on("click",function(){
	            window.CTTX.gotoBindJiaZhao();
	        })

	        




	    });


	    $(document).on('pageInit', '#page_rule', function () {
	        //积分明细接口

	        var integralDetail_json = {"usrnum":"00335517717406260"};

	        $.ajax({
	            url: "http://139.196.183.121:8081/february/activityPointsList",
	            type: 'POST',
	            data: JSON.stringify(integralDetail_json),
	            dataType: "json",
	            contentType: "text/plain;",
	            processData:false,
	            success: function (msg) {
	                var data = {
	                    list:msg.data
	                };
	                var htmlt = template('tpl-rule', data);
	                document.getElementById('rule-content').innerHTML = htmlt;

	            },
	            error: function (msg) {
	                alert("请求申请信息超时");
	            }
	        })
	    })

	    $.init();

	});



/***/ },
/* 1 */
/***/ function(module, exports, __webpack_require__) {

	var __WEBPACK_AMD_DEFINE_RESULT__;/*!art-template - Template Engine | http://aui.github.com/artTemplate/*/
	!function(){function a(a){return a.replace(t,"").replace(u,",").replace(v,"").replace(w,"").replace(x,"").split(y)}function b(a){return"'"+a.replace(/('|\\)/g,"\\$1").replace(/\r/g,"\\r").replace(/\n/g,"\\n")+"'"}function c(c,d){function e(a){return m+=a.split(/\n/).length-1,k&&(a=a.replace(/\s+/g," ").replace(/<!--[\w\W]*?-->/g,"")),a&&(a=s[1]+b(a)+s[2]+"\n"),a}function f(b){var c=m;if(j?b=j(b,d):g&&(b=b.replace(/\n/g,function(){return m++,"$line="+m+";"})),0===b.indexOf("=")){var e=l&&!/^=[=#]/.test(b);if(b=b.replace(/^=[=#]?|[\s;]*$/g,""),e){var f=b.replace(/\s*\([^\)]+\)/,"");n[f]||/^(include|print)$/.test(f)||(b="$escape("+b+")")}else b="$string("+b+")";b=s[1]+b+s[2]}return g&&(b="$line="+c+";"+b),r(a(b),function(a){if(a&&!p[a]){var b;b="print"===a?u:"include"===a?v:n[a]?"$utils."+a:o[a]?"$helpers."+a:"$data."+a,w+=a+"="+b+",",p[a]=!0}}),b+"\n"}var g=d.debug,h=d.openTag,i=d.closeTag,j=d.parser,k=d.compress,l=d.escape,m=1,p={$data:1,$filename:1,$utils:1,$helpers:1,$out:1,$line:1},q="".trim,s=q?["$out='';","$out+=",";","$out"]:["$out=[];","$out.push(",");","$out.join('')"],t=q?"$out+=text;return $out;":"$out.push(text);",u="function(){var text=''.concat.apply('',arguments);"+t+"}",v="function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);"+t+"}",w="'use strict';var $utils=this,$helpers=$utils.$helpers,"+(g?"$line=0,":""),x=s[0],y="return new String("+s[3]+");";r(c.split(h),function(a){a=a.split(i);var b=a[0],c=a[1];1===a.length?x+=e(b):(x+=f(b),c&&(x+=e(c)))});var z=w+x+y;g&&(z="try{"+z+"}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:"+b(c)+".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");try{var A=new Function("$data","$filename",z);return A.prototype=n,A}catch(B){throw B.temp="function anonymous($data,$filename) {"+z+"}",B}}var d=function(a,b){return"string"==typeof b?q(b,{filename:a}):g(a,b)};d.version="3.0.0",d.config=function(a,b){e[a]=b};var e=d.defaults={openTag:"<%",closeTag:"%>",escape:!0,cache:!0,compress:!1,parser:null},f=d.cache={};d.render=function(a,b){return q(a,b)};var g=d.renderFile=function(a,b){var c=d.get(a)||p({filename:a,name:"Render Error",message:"Template not found"});return b?c(b):c};d.get=function(a){var b;if(f[a])b=f[a];else if("object"==typeof document){var c=document.getElementById(a);if(c){var d=(c.value||c.innerHTML).replace(/^\s*|\s*$/g,"");b=q(d,{filename:a})}}return b};var h=function(a,b){return"string"!=typeof a&&(b=typeof a,"number"===b?a+="":a="function"===b?h(a.call(a)):""),a},i={"<":"&#60;",">":"&#62;",'"':"&#34;","'":"&#39;","&":"&#38;"},j=function(a){return i[a]},k=function(a){return h(a).replace(/&(?![\w#]+;)|[<>"']/g,j)},l=Array.isArray||function(a){return"[object Array]"==={}.toString.call(a)},m=function(a,b){var c,d;if(l(a))for(c=0,d=a.length;d>c;c++)b.call(a,a[c],c,a);else for(c in a)b.call(a,a[c],c)},n=d.utils={$helpers:{},$include:g,$string:h,$escape:k,$each:m};d.helper=function(a,b){o[a]=b};var o=d.helpers=n.$helpers;d.onerror=function(a){var b="Template Error\n\n";for(var c in a)b+="<"+c+">\n"+a[c]+"\n\n";"object"==typeof console&&console.error(b)};var p=function(a){return d.onerror(a),function(){return"{Template Error}"}},q=d.compile=function(a,b){function d(c){try{return new i(c,h)+""}catch(d){return b.debug?p(d)():(b.debug=!0,q(a,b)(c))}}b=b||{};for(var g in e)void 0===b[g]&&(b[g]=e[g]);var h=b.filename;try{var i=c(a,b)}catch(j){return j.filename=h||"anonymous",j.name="Syntax Error",p(j)}return d.prototype=i.prototype,d.toString=function(){return i.toString()},h&&b.cache&&(f[h]=d),d},r=n.$each,s="break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",t=/\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,u=/[^\w$]+/g,v=new RegExp(["\\b"+s.replace(/,/g,"\\b|\\b")+"\\b"].join("|"),"g"),w=/^\d[^,]*|,\d[^,]*/g,x=/^,+|,+$/g,y=/^$|,+/;e.openTag="{{",e.closeTag="}}";var z=function(a,b){var c=b.split(":"),d=c.shift(),e=c.join(":")||"";return e&&(e=", "+e),"$helpers."+d+"("+a+e+")"};e.parser=function(a){a=a.replace(/^\s/,"");var b=a.split(" "),c=b.shift(),e=b.join(" ");switch(c){case"if":a="if("+e+"){";break;case"else":b="if"===b.shift()?" if("+b.join(" ")+")":"",a="}else"+b+"{";break;case"/if":a="}";break;case"each":var f=b[0]||"$data",g=b[1]||"as",h=b[2]||"$value",i=b[3]||"$index",j=h+","+i;"as"!==g&&(f="[]"),a="$each("+f+",function("+j+"){";break;case"/each":a="});";break;case"echo":a="print("+e+");";break;case"print":case"include":a=c+"("+b.join(",")+");";break;default:if(/^\s*\|\s*[\w\$]/.test(e)){var k=!0;0===a.indexOf("#")&&(a=a.substr(1),k=!1);for(var l=0,m=a.split("|"),n=m.length,o=m[l++];n>l;l++)o=z(o,m[l]);a=(k?"=":"=#")+o}else a=d.helpers[c]?"=#"+c+"("+b.join(",")+");":"="+a}return a}, true?!(__WEBPACK_AMD_DEFINE_RESULT__ = function(){return d}.call(exports, __webpack_require__, exports, module), __WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__)):"undefined"!=typeof exports?module.exports=d:this.template=d}();

/***/ }
]);