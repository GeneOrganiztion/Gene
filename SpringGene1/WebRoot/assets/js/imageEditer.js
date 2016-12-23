$.extend({
    imageEditer:function (select, param) {
    	this.param = param;
        var ImageList = [];
        var selectItem = null;
        var uploadDialog = webroot + "pages/asset/uploadImgModal.jsp?selector=" + param.selector;
        var deleteDialog =  webroot + "pages/asset/deleteModal.jsp";
        var viewType = param.viewType||"view";
        var selector = param.selector;
        var url = param.url;//加载图片
        var params = param.params;
        var saveBasicInfoSync = param.saveBasicInfoSync;
        //分页查询
        this.start = 0;
        this.limit = 5;
        this.count = 0;
        this.isloading = false;
        this.newsIdx = 0;//新闻图片索引
        var idPrex = "";
        if(viewType == "edit"){
        	idPrex = 'editimg-';
        }else{
        	idPrex = "img-";
        }
         
        
        var noImgSrc = webroot + "/assets/img/noimage.jpg";
        var htmlstr = "<div>"
		        	+	"<div class='clearfix counter' style='width:308px;position:absolute;background-color:rgba(244, 244, 244, 0.5);display:none;border-radius:5px;'>"
					+		"共&nbsp;<span class='count' style='color:red;'>0</span>&nbsp;张&nbsp;"
					+		"已加载&nbsp;<span class='loaded' style='color:blue;'>0</span>&nbsp;张"
					+	"</div>"
					+	"<span class='profile-picture jd-picture'>"
					+		"<img class='jd-preview jd-preview-w' src='"+webroot +"assets/img/noimage.jpg'>"
					+	"</span>"
					+	"<button class='btn btn-sm more' title='未显示所有图片，点击继续加载..' style='right:20px;margin-top: 10px;position:absolute;display:none'><i class='ace-icon fa fa-cloud blue'></i>更多</button>"
					+	"<div>"
					+		"<hr class='hr hr2 dotted'>"
					+		"<div class='clearfix'>"
					+			"<div class='fa fa-chevron-left control spec-left orange'></div>"
					+			"<div class='spec-list'>"
					+				"<ul style='float:left'>"
					+				"</ul>"
					+			"</div>"
					+			"<div class='fa fa-chevron-right control spec-right orange'></div>"
					+		"</div>"
					+	"<hr class='hr hr2 dotted'>"
					+	"<div class='center ctrl-bar'><button class='btn btn-sm upload'><i class='ace-icon fa fa-plus orange'></i>添加</button><button class='btn btn-sm delete'><i class='ace-icon fa fa-remove'></i>删除</button></div>"
					+		"<div class='clearfix'>"
					+		"<i class='ace-icon fa fa-circle green'></i>&nbsp;"
					+		"<span class='name'></span>&nbsp;&nbsp;&nbsp;"
					+		"<span class='pixel'></span>"
					+		"</div>"
					+	"</div>"
					+	"</div>";
		var imodel = "<li><img class='#class' id='#id' name='#name' alt='#alt' title='#title' src='#src'/></li>";			
		var imgViewer = "";
        $(select).html(htmlstr);
        
        var a = this;
        this.obj = $(".spec-list", select);
        
        //自动改变大小
        $(".hr2", select).resize(function(){
    		var w = $(this).width();
    		$(".spec-list", select).width((w - 44)+"px");
		});
        
        $(".more", select).bind("click",function(){ 
        	a.loadImage(null, 2);
		});
        
        //图片列滚动
        $(".spec-left", select).bind("click",function(){ 
			var m=a.calculate.roll();
			if(m[0]<0){ 
				a.roll.next(2);	
			}
			
			a.obj.find("li.curr").prev().find("img").click();
		});
		
		$(".spec-right", select).bind("click",function(){ 
			var m=a.calculate.roll();
			var w=$(".spec-list", select).width();
			
			if(m[1]+m[0]>w){ 
				a.roll.foward(2);	
			}
			
			a.obj.find("li.curr").next().find("img").click();
		});
		
		if(viewType == 'view') {
			$(".ctrl-bar", select).hide();
		}
		
		$(".jd-preview", select).click(function() {
			var src = $(this).attr("src");
			var name = $(".name", select).html();
			var pixel = $(".pixel", select).html();
			
			var title = name + ' ' + pixel
			if(src != noImgSrc) {
				var param = {
					previous: '<i class="ace-icon fa fa-arrow-left"></i>',
		            next: '<i class="ace-icon fa fa-arrow-right"></i>',
		            overlayClose: true,    //点空白是否关闭
		            closeButton: true,
		            close: '&times;',
		            current: '{current} of {total}',
					ref : 'colorbox',
					href : src,
					title : title
				};
				
				$.colorbox(param);
			}
			
		});
		
		$(".delete", select).bind("click",function(){ 
			var obj = this;
			var id=$(obj).attr("idd");
			
			if(!id || id == '') {
				alertmsg("warning", "无图片"); 
				return;
			}
//			if(empty($("#" + selector).val()) && !saveBasicInfoSync){
//				alertmsg("warning", "请先保存基本信息！"); 
//				return;
//			}
			Lobibox.confirm({ 
			       title:"提示",      //提示框标题 
			            msg: "确认删除?",   //提示框文本内容 
			       callback: function ($this, type, ev) {               //回调函数 
			               if (type === 'yes') { 
			            	   a.delImage(id);  
			               } else if (type === 'no') { 
			                          
			               } 
			          } 

			});
		});
		
		this.delImage = function(id) {
			//var idx = a.getIndexById(idPrex+id);		
			var img = a.getImageById(idPrex+id);
			var idx = img.idx;
			if(idx != undefined && (idx+'') != '') {	
				if(img.isdb==false){
					
					a.removeImgAt(idx);
					return;
				}
				var delUrl = $("#deleteImgUrl").val();
				if(delUrl) {
					var request = $.ajax({
			            url: delUrl,
			            method: 'POST',
			            data: {imgId : id},
			            dataType: 'text'
			        });
			        request.done(function( msg ) {
			        	console.log(msg);
			        	
			        	a.removeImgAt(idx);
			        	alertmsg("success", "删除成功"); 
			        	
			        	a.count --;
			        	$(".count", select).html(a.count);
						$(".loaded", select).html(ImageList.length);
			        	return;
			        });
			        request.fail(function( jqXHR, textStatus ) {
			            console.log( "request failed: " + textStatus );
			            alertmsg("error", "删除失败"); 
			            return;
			        });
				} else {
					a.removeImgAt(idx);
				}
			}
		}
		
		this.loadDeleteDialog=function(selector, callback) {
            $(selector).html("");
            $(selector).load(deleteDialog,function () {
            	$("#deletemodal").modal("show"); 
            	
            	$(".delete", "#deletemodal").bind("click",function(){ 
            		callback();
            	});
            });
        }
		
		$(".upload", select).bind("click",function(){ 
//			var aid = $("#"+selector).val();
//			if(aid == null || aid == "") {
//				if(saveBasicInfoSync){
//					
//				}else{
//					alertmsg("warning", "请先保存基本信息！"); 
//					return;
//				}				
//			}
			document.hs = a;
			var obj = this;
			a.loadUploadDialog("#modalDialog", function() {
				
			});
			
		});
        
		this.loadUploadDialog=function(selector, callback) {
            $(selector).html("");
            $(selector).load(uploadDialog+"&newsIdx="+a.newsIdx,function () {
            	$("#uploadimgmodal").modal("show"); 
            	
            });
        }
		
        this.calculate = { 	
			swith:function(){ 
				var p=a.obj.find("li");
				var m=[];
				m[0]=p.length;
				m[1]=p.index($(".curr"));
				return m;	
			},
			roll:function(){ 
				var p=a.obj.find("ul");
				var m=[];
				m[0]=parseInt(p.css("margin-left"));
				m[1]=p.width();
				return m;
			}
		}
        
        this.roll={ 
			foward:function(step){ 
				var m=a.calculate.roll();
				a.obj.find("ul").animate({ "marginLeft":m[0]-60*step},100);
			},
			next:function(step){ 
				var m=a.calculate.roll();
				a.obj.find("ul").animate({ "marginLeft":m[0]+60*step},100)		
			}	
		}
        
        this.fixUlSize=function() {
        	var n = $("li", select).length;
        	$("ul", select).width((n*80)+"px");
        	
        	var w = $(".hr2", select).width();
        	$(".spec-list", select).width((w - 44)+"px");
        }
        
        /*
        添加一个img 
        img 基础属性 
        id string 图片ID。  
        name string 图片名称。  
        alt string 图片名称。
        title string 图片名称。
        pixel string 图片分辨率。
        src string 图片预览路径。
        */
        this.addImage=function(img, extra) {
            if(!img.id) {
            	img.id = getRandom(999)+''+getRandom(999);
            	img.isdb = false;
            }else {
            	img.isdb = true;
            }
            
            //获取新闻idx
            if(img.name.indexOf("_TW_")>-1) {
            	var idx = parseInt(img.name.replace(/.*\_TW\_|_sc\..*/gi, ""));
            	if(idx > a.newsIdx) {
            		a.newsIdx = idx;
            	}
            }
            
            img.id = idPrex+img.id;
            var id = ImageList.push(img);
            var cls = "spec-list-h";
            if(a.switchWH(img.pixel)) {
            	cls = "spec-list-w";
            }
            var dd=new Date().getTime();
            var i = imodel.replace('#name',img.name||'').replace('#alt',img.alt||'').replace('#title',img.title||'')
            	.replace('#src',(img.src + "?dd="+dd)||'').replace('#id',img.id||'').replace('#class',cls);
			$("ul", select).append(i);
			this.fixUlSize();
			
			$("#"+img.id).bind("click",function(){ 
				var src=$(this).attr("src");
				var idd=$(this).attr("id").replace(idPrex,'');
				
				$(".jd-preview", select).attr("src", src);
				$(".delete", select).attr("idd", idd);
				
				
				if($(".spec-list li", select).hasClass("curr")){
					$(".spec-list .curr", select).removeClass("curr")
				};
				$(this).parent().addClass("curr");
				
				$(".name", select).html(a.getName(img.name));
				$(".pixel", select).html(a.getPixel(img.pixel, src));
				
				if(a.switchWH(img.pixel)){
					$(".jd-preview", select).addClass("jd-preview-w");
        			$(".jd-preview", select).removeClass("jd-preview-h");
				}else {
					$(".jd-preview", select).removeClass("jd-preview-w");
        			$(".jd-preview", select).addClass("jd-preview-h");
				}
			});
			
			if(ImageList.length == 1) {
				$("#"+img.id).trigger("click");
			}
			
			if(img.pixel == '0*0') {
				$("#"+img.id).bind("load", function() {
					var pxl = a.getPixel(img.pixel, img.src);
					img.pixel = pxl;
					if($(".delete", select).attr("idd") == img.id.replace(idPrex,'')) {
						$(".pixel", select).html(pxl);
						
					}
					
					if(a.switchWH(pxl)){
						$("#"+img.id).addClass("spec-list-w");
						$("#"+img.id).removeClass("spec-list-h");
						
						if($(".delete", select).attr("idd") == img.id.replace(idPrex,'')) {
							$(".jd-preview", select).addClass("jd-preview-w");
		        			$(".jd-preview", select).removeClass("jd-preview-h");
						}
					}else {
						$("#"+img.id).removeClass("spec-list-w");
						$("#"+img.id).addClass("spec-list-h");
						
						if($(".delete", select).attr("idd") == img.id.replace(idPrex,'')) {
							$(".jd-preview", select).removeClass("jd-preview-w");
		        			$(".jd-preview", select).addClass("jd-preview-h");
						}
					}
				});
			}
			
			if(extra) {
				a.count ++;
				$(".count", select).html(a.count);
				$(".loaded", select).html(ImageList.length);
			}
			
            return id;
        }
        //100*100
        this.switchWH = function(pixel) {
        	if(pixel && pixel.indexOf("*")>-1) {
        		var s = pixel.split("*");
        		var w = ~~s[0],h = ~~s[1];
        		
        		if(w > h) {
        			return true;
        		}else {
        			return false;
        		}
        	}
        	return true;
        }
        
        this.getName = function(name) {
        	return ""+name;
        }
        
        this.getPixel = function(pixel, src) {
        	if(pixel == '0*0' && src) {
        		try {
        			var theImage = new Image();
        			theImage.src = src;

        			pixel = theImage.width+"*"+theImage.height;
        		} catch(e) {
        			
        		}
        	}
        	
        	return ""+pixel;
        }
        
        //根据下标删除一个Img
        this.removeImgAt=function (index) {
            var obj = ImageList.splice(index, 1)[0];
            $("#"+obj.id).parent().remove();
            if(ImageList.length>0) {
            	$("#"+ImageList[0].id).trigger("click");
            }else {
            	//TODO
            	$(".jd-preview", select).attr("src", noImgSrc);
            	$(".delete", select).attr("idd", "");
            	$(".name", select).html(a.getName(""));
				$(".pixel", select).html(a.getPixel(""));
            }
            return obj;
        }

        //获取所有的图片
        this.getImgs= function() {
            return ImageList.spliceObj();
        }

        //根据Id获取下标
        this.getIndexById= function(id) {
            for (var i = 0; i < ImageList.length; i++) {
                if (ImageList[i]["id"].toString() == id.toString()) {
                    return i;
                }
            }
            return '';
        }
        
        this.getImageById= function(id) {
            for (var i = 0; i < ImageList.length; i++) {
                if (ImageList[i]["id"].toString() == id.toString()) {
                	ImageList[i].idx = i;
                    return ImageList[i];
                }
            }
            return {};
        }
        
        this.resetEditer = function() {
        	ImageList = [];
        	$("ul", select).html("");
        	$(".jd-preview", select).attr("src", noImgSrc);
        	$(".delete", select).attr("idd", "");
        	$(".name", select).html(a.getName(""));
			$(".pixel", select).html(a.getPixel(""));
        }
        
        this.reloadImages = function() {
        	this.loadImage(this.resetEditer);
        }
        
        this.loadImage = function(callback, need) {
        	var me = this;
        	var imgDir = "store/asset/";
        	if(url && selector) {
        		var aid = $("#"+selector).val();
        		if(aid && aid !='' && !me.isloading) {
        			var data = {
        				aid : aid,
        				need : need||""
        			};
        			if(need == '2') {
        				data.start = me.start;
        				data.limit = me.limit;
        			}
        			
        			me.isloading = true;
        			var request = $.ajax({
        	            url: url,
        	            method: 'POST',
        	            data: data,
        	            dataType: 'text'
        	        });
        	        request.done(function( msg ) {
        	        	me.isloading = false;
        	        	var obj = $.parseJSON(msg);
        	        	
        	        	if(callback) {
        	        		callback();
        	        	}
        	        	if(obj.count) {
        	        		me.count = obj.count;
        	        		$(".count", me.select).html(obj.count);
        	        	}
        	        	if(obj.data) {
        	        		for(var i=0; i<obj.data.length; i++) {
                				var d = obj.data[i];
                				me.addImage({
                					id: d.id,
                					title: d.name,
                	                alt: d.name,
                	                name: d.name,
                	                pixel: d.width + "*" + d.height,
                	                src: imgDir + d.mpath
                				});
                    		}
        	        		
        	        		if(me.count > ImageList.length) {
        	        			$(".more", me.select).show();
        	        		}else {
        	        			$(".more", me.select).hide();
        	        		}
        	        		if(need==2) {
        	        			me.start = me.start + me.limit;
        	        			alertmsg("success", "已为您加载 "+ obj.data.length + " 张图片，点击 > 按钮至结尾查看"); 
        	        		}
        	        	}
        	        	
        	        	$(".loaded", me.select).html(ImageList.length);
        	        	
        	        	$(".counter", me.select).css('display', 'inline-block');
        	        });
        	        request.fail(function( jqXHR, textStatus ) {
        	        	me.isloading = false;
        	            alertmsg("error", "图片获取失败"); 
        	        });
        		}
        	}
        };
        
        this.loadImage(null, 1);
    }
});