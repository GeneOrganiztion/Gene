
//获取表单数据
var getParams = function(selector) {
	var data = {};
	$("input",selector).each(function() {
		var name = $(this).attr("name");
		if(name) {
			if($(this).attr("type")=="checkbox") {
				data[name] = $(this).is(':checked');
			} else {
				data[name] = $(this).val();
			}
		}
	});
	$("select",selector).each(function() {
		var name = $(this).attr("name");
		if(name) {
			data[name] = ($(this).val()||'').toString();
		}
	});
	$("textArea",selector).each(function() {
		var name = $(this).attr("name");
		if(name) {
			data[name] = $(this).val().replace(/\n/g,"");
		}
	});
	console.log(data);
	return data;
};

var getValuesByAttr = function(selector, attr) {
	var data = [];
	$("input",selector).each(function() {
		var val = $(this).attr(attr);
		if(val && val != '') {
			data.push(val);
		}
	});
	$("select",selector).each(function() {
		var val = $(this).attr(attr);
		if(val && val != '') {
			data.push(val);
		}
	});
	$("textArea",selector).each(function() {
		var val = $(this).attr(attr);
		if(val && val != '') {
			data.push(val);
		}
	});
	console.log(data);
	return (data||'').toString();;
};
//去除字符串空格
/*去左空格*/
function ltrim(s) {
    return s.replace(/^(\s*|　*)/, "");
}

/*去右空格*/
function rtrim(s) {
    return s.replace(/(\s*|　*)$/, "");
}
/*去左右空格*/
function trim(s) {
    return ltrim(rtrim(s));
}

//判断对象不为空
var  isNoEmpty = function(obj) {
    if (obj != null && typeof(obj) != "undefined" && obj != "") {
        return true;
    }
    return false;
}

//判断变量是否空值 undefined, null, '', false, 0, [], {} 均返回true，否则返回false
function empty(v) {
    switch (typeof v) {
        case 'undefined' :
            return true;
        case 'string' :
            if (trim(v).length == 0)
                return true;
            break;
        case 'boolean' :
            if (!v)
                return true;
            break;
        case 'number' :
            if (0 === v)
                return true;
            break;
        case 'object' :
            if (null === v)
                return true;
            if (undefined !== v.length && v.length == 0)
                return true;
            for (var k in v) {
                return false;
            }
            return true;
            break;
    }
    return false;
}

//object类型转Json格式
function obj2str(o){   

    var r = [];   

    if(typeof o =="string") return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\"";   

    if(typeof o =="undefined") return "";   

    if(typeof o == "object"){   

        if(o===null) return "null";   

        else if(!o.sort){   

            for(var i in o)   

                 r.push(i+":"+obj2str(o[i]))   

             r="{"+r.join()+"}"  

         }else{   

            for(var i =0;i<o.length;i++)   

                 r.push(obj2str(o[i]))   

             r="["+r.join()+"]"  

         }   

        return r;   

     }   

    return o.toString();   

} 

//Object转String类型
function obj2string(o){ 
    var r=[]; 
    if(typeof o=="string"){ 
        return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\""; 
    } 
    if(typeof o=="object"){ 
        if(!o.sort){ 
            for(var i in o){ 
                r.push(i+":"+obj2string(o[i])); 
            } 
            if(!!document.all&&!/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)){ 
                r.push("toString:"+o.toString.toString()); 
            } 
            r="{"+r.join()+"}"; 
        }else{ 
            for(var i=0;i<o.length;i++){ 
                r.push(obj2string(o[i])) 
            } 
            r="["+r.join()+"]"; 
        }  
        return r; 
    }  
    return o.toString(); 
} 

/** * 对Date的扩展，将 Date 转化为指定格式的String * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
可以用 1-2 个占位符 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) * eg: * (new
Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423      
* (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04      
* (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04      
* (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04      
* (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18      
*/        
Date.prototype.pattern=function(fmt) {         
	var o = {         
	"M+" : this.getMonth()+1, //月份         
	"d+" : this.getDate(), //日         
	"h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
	"H+" : this.getHours(), //小时         
	"m+" : this.getMinutes(), //分         
	"s+" : this.getSeconds(), //秒         
	"q+" : Math.floor((this.getMonth()+3)/3), //季度         
	"S" : this.getMilliseconds() //毫秒         
	};         
	var week = {         
	"0" : "/u65e5",         
	"1" : "/u4e00",         
	"2" : "/u4e8c",         
	"3" : "/u4e09",         
	"4" : "/u56db",         
	"5" : "/u4e94",         
	"6" : "/u516d"        
	};         
	if(/(y+)/.test(fmt)){         
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
	}         
	if(/(E+)/.test(fmt)){         
	    fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
	}         
	for(var k in o){         
	    if(new RegExp("("+ k +")").test(fmt)){         
	        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
	    }         
	}         
	return fmt;         
}

//---------------------------------------------------
//日期格式化
//格式 YYYY/yyyy/YY/yy 表示年份
//MM/M 月份
//W/w 星期
//dd/DD/d/D 日期
//hh/HH/h/H 时间
//mm/m 分钟
//ss/SS/s/S 秒
//---------------------------------------------------
Date.prototype.format = function(formatStr)
{
var str = formatStr;
var Week = ['日','一','二','三','四','五','六'];

str=str.replace(/yyyy|YYYY/,this.getFullYear());
str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));

str=str.replace(/MM/,(this.getMonth()+1)>9?(this.getMonth()+1).toString():'0' + (this.getMonth()+1));
str=str.replace(/M/g,this.getMonth()+1);

str=str.replace(/w|W/g,Week[this.getDay()]);

str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());
str=str.replace(/d|D/g,this.getDate());

str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());
str=str.replace(/h|H/g,this.getHours());
str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());
str=str.replace(/m/g,this.getMinutes());

str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());
str=str.replace(/s|S/g,this.getSeconds());

return str;
}

//+---------------------------------------------------
//| 日期计算
//+---------------------------------------------------
Date.prototype.dateAdd = function(strInterval, Number) {
var dtTmp = this;
switch (strInterval) {
case 's' :return new Date(Date.parse(dtTmp) + (1000 * Number));
case 'n' :return new Date(Date.parse(dtTmp) + (60000 * Number));
case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * Number));
case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * Number));
case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
case 'm' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
case 'y' :return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
}
}

//数组复制 元素全是object类型
Array.prototype.spliceObj = function () {
    var newArray = [];
    for (var i = 0; i < this.length; i++) {
        newArray.push(this[i]);
    }
    return newArray;
}

Array.prototype.remove=function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=this[dx])
        {
            this[n++]=this[i]
        }
    }
    this.length-=1
}

function getRandom(n){
	return Math.floor(Math.random()*n+1)
}

function isURL(str_url) {// 验证url
//	var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
//	+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
//	+ "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
//	+ "|" // 允许IP和DOMAIN（域名）
//	+ "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
//	+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
//	+ "[a-z]{2,6})" // first level domain- .com or .museum
//	+ "(:[0-9]{1,4})?" // 端口- :80
//	+ "((/?)|" // a slash isn't required if there is no file name
//	+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
//	var re = new RegExp(strRegex);
//	return re.test(str_url);
	return true;
}
//是否含有中文（也包含日文和韩文）
function isChineseChar(str){   
   var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
   return reg.test(str);
}
//同理，是否含有全角符号的函数
function isFullwidthChar(str){
   var reg = /[\uFF00-\uFFEF]/;
   return reg.test(str);
}  
function isNumWord(str) {
	var reg = /^[0-9a-zA-Z_.]+$/;
	return reg.test(str);
}
//判断是视频文件还是音频文件
function isVideoOrAudio(str){
	var video = "flv,mp4,3gp,mpg,mpeg,wmv,ts,mkv,m2t";
	var audio = "mpg,mp3,aac,m4a,amr,wma";
	if(video.indexOf(trim(str)) > -1){
		return "video";
	}else if(audio.indexOf(trim(str)) > -1){
		return "audio";
	}
}
//克隆object
function clone(obj) {
  // Handle the 3 simple types, and null or undefined
  if (null == obj || "object" != typeof obj) return obj;
 
  // Handle Date
  if (obj instanceof Date) {
    var copy = new Date();
    copy.setTime(obj.getTime());
    return copy;
  }
 
  // Handle Array
  if (obj instanceof Array) {
    var copy = [];
    for (var i = 0, len = obj.length; i < len; ++i) {
      copy[i] = clone(obj[i]);
    }
    return copy;
  }
 
  // Handle Object
  if (obj instanceof Object) {
    var copy = {};
    for (var attr in obj) {
      if (obj.hasOwnProperty(attr)) copy[attr] = clone(obj[attr]);
    }
    return copy;
  }
 
  throw new Error("Unable to copy obj! Its type isn't supported.");
}
