function initProductManager(){
	
	
	
	jQuery(function($) {
		
		$('#validation-form').addClass('hide');
		
		selectClassify();	
		
		var flieuploadimagesize=null;
		var productid=null;
		$('[data-rel=tooltip]').tooltip();
	
		$(".select2").css('width','200px').select2({allowClear:true})
		.on('change', function(){
			$(this).closest('form').validate().element($(this));
		}); 
	
	
		var $validation = true;
		$('#fuelux-wizard-container')
		.ace_wizard({
			//step: 2 //optional argument. wizard will jump to step "2" at first
			//buttons: '.wizard-actions:eq(0)'
		})
		.on('actionclicked.fu.wizard' , function(e, info){
			if(info.step == 1 && $validation) {
					if(!$('#validation-form').valid()){
						e.preventDefault();
					}else{
						if(info.step==1&&info.direction=="next"){
							$('#addloader').removeClass('hide');
							if(null==productid){
								productid=addproduct();
								console.log("productidaaaa="+productid);
								if(productid>0){
									$('#addloader').addClass('hide');
									alertmsg("success","商品基本信息入库成功");
									return true;
									Dropzoneinit();
									
								}else{
									alertmsg("warning","商品信息入库失败请重新上传");
									$('#addloader').addClass('hide');
									return false;
								}
							}else{
								var flag=updateproduct(productid);
								if(flag==true){
									$('#addloader').addClass('hide');
									alertmsg("success","商品信息更新成功");
									return true;
								}else{
									$('#addloader').addClass('hide');
									alertmsg("warning","商品信息更新失败 您可以选择删除商品重新编辑");
									return false;
								}
								
							}
							
						}
					}
				}
					
				/* if(info.step==2&&info.direction=="previous"){
					 return true; //不能跳转
				 }
				 if(info.step==2&&info.direction=="next"){ 
					 if(flieuploadimagesize==null){
						 alertmsg("error","您还有图片未上传");
					     return false;
					 }
					 
				 }*/
			
			//if(info.step == 1 && $validation) {
			//	console.log(info);
			
			//	if(!$('#validation-form').valid()) e.preventDefault();
			//}
		})
		.on('finished.fu.wizard', function(e) {
			bootbox.dialog({
				message: "Thank you! Your information was successfully saved!", 
				buttons: {
					"success" : {
						"label" : "OK",
						"className" : "btn-sm btn-primary"
					}
				}
			});
		}).on('stepclick.fu.wizard', function(e){
			//e.preventDefault();//this will prevent clicking and selecting steps
		});
	
		
		
		
	

		try {
			  Dropzone.autoDiscover = false;
			  var myDropzone1 = new Dropzone("#dropzone" , {
				url:webroot+"product/UploadImage.do",
			    paramName: "file", // The name that will be used to transfer the file
			    maxFilesize: 1, // MB
			    maxFiles:4,
			    dictMaxFilesExceeded: "您最多只能上传4个文件！",
			    dictFileTooBig:"文件过大上传文件最大支持.",
			    acceptedFiles: ".jpg,.gif,.png",
			    dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.jpg,*.gif,*.png。",
				addRemoveLinks : true,
				 //每次上传的最多文件数，经测试默认为2，坑啊
	            //记得修改web.config 限制上传文件大小的节
				autoProcessQueue :false,
				parallelUploads: 100,
				dictDefaultMessage :
				'<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i> Drop files</span> to upload \
				<span class="smaller-80 grey">(or click)</span> <br /> \
				<i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>'
			,
				dictResponseError: 'Error while uploading file!',
				previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-small progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>",
				
				init: function () {
	                var submitButton = document.querySelector("#submit-product")
	                myDropzone1 = this; // closure

	                //为上传按钮添加点击事件
	                submitButton.addEventListener("click", function () {
	                    //手动上传所有图片
	                    myDropzone1.processQueue();
	                    
	                });
	                
	                this.on("sending", function (file, xhr, formData) {
	                	formData.append("pro_id",productid);
	                });
	                
	                //当添加图片后的事件，上传按钮恢复可用
	                this.on("addedfile", function () {
	                    $("#submit-product").removeAttr("disabled");
	                  
	                });
	                
	                this.on("success", function (file, response, e) {
		                	if(response.message=="error"){
								alertmsg("error","商品展示图片上传失败，请重新上传");
								 $(file.previewTemplate).children('.dz-error-mark').css('opacity', '1')
							}else if(response.message=="tomore"){
								alertmsg("error","商品展示图片文件个数上传超过最大限制");
								  $(file.previewTemplate).children('.dz-error-mark').css('opacity', '1')
							}else{
								alertmsg("success","商品展示图片上传成功");	
							}
						
	                });
	                //当上传完成后的事件，接受的数据为JSON格式
	                this.on("complete", function (data) {
	                    if (this.getUploadingFiles().length === 0 && this.getQueuedFiles().length === 0) {
	                        var res =data;
	                        console.log(res);
	                        var msg;
	                        if (res.message=="error") {
	                        	alertmsg("error","商品展示图片上传失败，请重新上传");
	                        }
	                        else {
	                        	alertmsg("success","商品展示图片上传完成");
	                        }
	                        
	                    }
	                });
	                

	                //删除图片的事件，当上传的图片为空时，使上传按钮不可用状态
	                this.on("removedfile", function (file) {
	                    if (this.getAcceptedFiles().length === 0) {
	                        $("#submit-product").attr("disabled", true);
	                    }
	                    removeShowImage(file.name);
	                    
	                });
	            }
				
				
				/*init: function() {
				  var submitButton = document.querySelector("#submit-all");
				        myDropzone = this; // closure
				    submitButton.addEventListener("click", function() {
				      myDropzone.processQueue(); // Tell Dropzone to process all queued files.
				    });

				    // You might want to show the submit button only when 
				    // files are dropped here:
				  },				
				sending: function(file, xhr, formData) {
					formData.append("id",productid);
					flieuploadimagesize=file.size;
					
				},
				success: function (file, response, e) {
						console.log(response.message);
						if(response.message=="error"){
							alertmsg("error","商品展示图片上传失败，请重新上传");
						}else if(response.message=="tomore"){
							alertmsg("error","商品展示图片上传超过最大限制");
						}else{
							alertmsg("success","商品展示图片上传成功");
						}
						
					},
				*/
				//change the previewTemplate to use Bootstrap progress bars
				
			  });
			  
			  
			  
			   $(document).one('ajaxloadstart.page', function(e) {
					try {
						myDropzone.destroy();
					} catch(e) {}
			   });
			
			} catch(e) {
			  alert('Dropzone.js does not support older browsers!');
			}
			
	
			
			
			try {
				  Dropzone.autoDiscover = false;
				  var myDropzone = new Dropzone("#dropzone1" , {
				    paramName: "file", // The name that will be used to transfer the file
				    maxFilesize: 1, // MB
				    maxFiles:2,
				    dictMaxFilesExceeded: "您最多只能上传2张商品详情图片！",
				    dictFileTooBig:"文件过大上传文件最大支持.",
				    acceptedFiles: ".jpg,.gif,.png,.pdf",
				    dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.jpg,*.gif,*.png,*.pdf",
					addRemoveLinks : true,
					autoProcessQueue :false,
					 //每次上传的最多文件数，经测试默认为2，坑啊
		            //记得修改web.config 限制上传文件大小的节
					parallelUploads: 100,
					dictDefaultMessage :
					'<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i> Drop files</span> to upload \
					<span class="smaller-80 grey">(or click)</span> <br /> \
					<i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>'
				,
					dictResponseError: 'Error while uploading file!',
					previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-small progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>",
					
					init: function () {
		                var submitButton = document.querySelector("#submit-all")
		                myDropzone = this; // closure

		                //为上传按钮添加点击事件
		                submitButton.addEventListener("click", function () {
		                    //手动上传所有图片
		                    myDropzone.processQueue();
		                });

		                //当添加图片后的事件，上传按钮恢复可用
		                this.on("addedfile", function () {
		                    $("#submit-all").removeAttr("disabled");
		                });
		                
		                this.on("success", function (file, response, e) {
		                	if(response.message=="error"){
								alertmsg("error","商品详情图片上传失败，请重新上传");
							}else{
								alertmsg("success","商品详情图片上传成功");
								addpir(response.message);
							}
		                });
		                //当上传完成后的事件，接受的数据为JSON格式
		                this.on("complete", function (data) {
		                    if (this.getUploadingFiles().length === 0 && this.getQueuedFiles().length === 0) {
		                        var res =data;
		                        console.log(res);
		                        var msg;
		                        if (res.message=="error") {
		                        	alertmsg("error","商品详情图片上传失败，请重新上传");
		                        }
		                        else {
		                        	alertmsg("success","商品详情图片上传完成");
		                        }
		                        
		                    }
		                });
		                

		                //删除图片的事件，当上传的图片为空时，使上传按钮不可用状态
		                this.on("removedfile", function (file) {
		                    if (this.getAcceptedFiles().length === 0) {
		                        $("#submit-all").attr("disabled", true);
		                    }
		                    removeImage(file.name);
		                    delpir(file.name);
		                });
		            }
					
					
					
					
					
					
					//change the previewTemplate to use Bootstrap progress bars
					
				  });
				  
				  
				  
				   $(document).one('ajaxloadstart.page', function(e) {
						try {
							myDropzone.destroy();
						} catch(e) {}
				   });
				
				} catch(e) {
				  alert('Dropzone.js does not support older browsers!');
				}
		//jump to a step
		/**
		var wizard = $('#fuelux-wizard-container').data('fu.wizard')
		wizard.currentStep = 3;
		wizard.setState();
		*/
	
		//determine selected step
		//wizard.selectedItem().step
	
	
	
		//hide or show the other form which requires validation
		//this is for demo only, you usullay want just one form in your application
		/* $('#skip-validation').removeAttr('checked').on('click', function(){
			$validation = this.checked;
			if(this.checked) {
				$('#sample-form').hide();
				$('#validation-form').removeClass('hide');
			}
			else {
				$('#validation-form').addClass('hide');
				$('#sample-form').show();
			}
		}) */
	
	
	
		//documentation : http://docs.jquery.com/Plugins/Validation/validate
	
	
		$.mask.definitions['~']='[+-]';
		$('#phone').mask('(999) 999-9999');
	
		jQuery.validator.addMethod("phone", function (value, element) {
			return this.optional(element) || /^\(\d{3}\) \d{3}\-\d{4}( x\d{1,6})?$/.test(value);
		}, "Enter a valid phone number.");
	
		$('#validation-form').validate({
			errorElement: 'div',
			errorClass: 'help-block',
			focusInvalid: false,
			ignore: "",
			rules: {
				name: {
					required: true,
					maxlength: 10
				},
				head:{
					required: true,
					maxlength: 20
				},
				comment: {
					required: false
				},
				classify: {
					required: true
				},
				price: {
					required: true,
					digits:true,
					min:0    
				},
				rateprice: {
					required: true,
					digits:true,
					min:0   
				},
				isonline: {
					required: true
				},
				sum:{
					required: true,
					digits:true,
					min:1  
				},
			},
	
			messages: {
			
				name:{
					required:"商品名称为必填字段",
					maxlength: "商品名称不能超过10个字符"
				},
				head:{
					required:"商品标题为必填字段",
					maxlength: "商品标题不能超过20个字符"
				},
				price:{
					required:"商品价格为必填字段",
					digits: "商品价格必须正为整数",
					min:"商品价格不能小于0"
				},
				rateprice:{
					required:"商品折扣价为必填字段",
					digits: "商品折扣价必须为正整数",
					min:"商品折扣价不能小于0"
				},
				isonline:{
					required:"请选择商品状态"
				},
				sum:{
					required:"商品库存为必填字段",
					digits: "商品库存不能小于1",
					min:"商品库存不能小于1"
				},
				state: "Please choose state",
				subscription: "Please choose at least one option",
				classify: "请选择商品类别  如商品类别列表为空请先添加商品类别",
			},
	
	
			highlight: function (e) {
				$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
			},
	
			success: function (e) {
				$(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
				$(e).remove();
			},
	
			errorPlacement: function (error, element) {
				if(element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
					var controls = element.closest('div[class*="col-"]');
					if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
					else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
				}
				else if(element.is('.select2')) {
					error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
				}
				else if(element.is('.chosen-select')) {
					error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
				}
				else error.insertAfter(element.parent());
			},
	
			submitHandler: function (form) {
			},
			invalidHandler: function (form) {
			}
		});
	
		
		
	
		//文本编辑框js
		function showErrorAlert (reason, detail) {
			var msg='';
			if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
			else {
				//console.log("error uploading file", reason, detail);
			}
			$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ 
			 '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
		}

		$('#editor1').ace_wysiwyg({
			toolbar:
			[
				'font',
				null,
				'fontSize',
				null,
				{name:'bold', className:'btn-info'},
				{name:'italic', className:'btn-info'},
				{name:'strikethrough', className:'btn-info'},
				{name:'underline', className:'btn-info'},
				/*
				null,
				{name:'insertunorderedlist', className:'btn-success'},
				{name:'insertorderedlist', className:'btn-success'},
				{name:'outdent', className:'btn-purple'},
				{name:'indent', className:'btn-purple'},
				null,
				{name:'justifyleft', className:'btn-primary'},
				{name:'justifycenter', className:'btn-primary'},
				{name:'justifyright', className:'btn-primary'},
				{name:'justifyfull', className:'btn-inverse'},*/
				null,
				{name:'createLink', className:'btn-pink'},
				{name:'unlink', className:'btn-pink'},
				null,
				{name:'insertImage', className:'btn-success'},
				null,
				'foreColor',
				null,
				{name:'undo', className:'btn-grey'},
				{name:'redo', className:'btn-grey'}
			],
			'wysiwyg': {
				fileUploadError: showErrorAlert
			}
		}).prev().addClass('wysiwyg-style2');
	
		
		//相册显示框js
		var $overflow = '';
		var colorbox_params = {
			rel: 'colorbox',
			reposition:true,
			scalePhotos:true,
			scrolling:false,
			previous:'<i class="ace-icon fa fa-arrow-left"></i>',
			next:'<i class="ace-icon fa fa-arrow-right"></i>',
			close:'&times;',
			current:'{current} of {total}',
			maxWidth:'100%',
			maxHeight:'100%',
			onOpen:function(){
				$overflow = document.body.style.overflow;
				document.body.style.overflow = 'hidden';
			},
			onClosed:function(){
				document.body.style.overflow = $overflow;
			},
			onComplete:function(){
				$.colorbox.resize();
			}
		};

		
		
		
		$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
		$("#cboxLoadingGraphic").html("<i class='ace-icon fa fa-spinner orange fa-spin'></i>");//let's add a custom loading icon
		
		
		
		
		
		
		
		
		
		$('#modal-wizard-container').ace_wizard();
		$('#modal-wizard .wizard-actions .btn[data-dismiss=modal]').removeAttr('disabled');
		
		
		/**
		$('#date').datepicker({autoclose:true}).on('changeDate', function(ev) {
			$(this).closest('form').validate().element($(this));
		});
		
		$('#mychosen').chosen().on('change', function(ev) {
			$(this).closest('form').validate().element($(this));
		});
		*/
		
		
		$(document).one('ajaxloadstart.page', function(e) {
			//in ajax mode, remove remaining elements before leaving page
			$('[class*=select2]').remove();
		});
	});
}
function addpir(url){
	var parent1 = document.getElementById("gread");
	var div12 = document.createElement("li");
	div12.setAttribute("id",url);
	var a= document.createElement("a");
		a.setAttribute("href",url);
		a.setAttribute("data-rel","colorbox");
		a.setAttribute("class","cboxElement");
		a.setAttribute("title",url);
		var img= document.createElement("img");
		img.setAttribute("width",150);
		img.setAttribute("height",150);
		img.setAttribute("alt",150);
		img.setAttribute("src",url);
		a.appendChild(img);
　　　　div12.appendChild(a);
　　　　parent1.appendChild(div12);
}

function delpir(url){
	var rooturl='http://myfirst1990.oss-cn-shanghai.aliyuncs.com/';
	remove(rooturl+url);
}
function remove(id){
	var dd=document.getElementById("gread");
	var aa=dd.children;
	for(var i=0;i<aa.length;i++){
	if(aa[i].id==id){
	dd.removeChild(aa[i]);
	}
	}
}


function geteditor(){
	alert($('#editor1').html());
	
}
//查询所有分类
function selectClassify(){
	$.ajax({
		type: "post",
		url: webroot + "classify/webclsall.do",
		success: function(msg){
			var json = eval(msg);
			var objSelect = $('#classify');
			for(var i=0;i<json.length;i++){
				objSelect.append("<option value='"+json[i].id+"'>"+json[i].claName+"</option>");
			}
			//
			$('#classify').trigger("chosen:updated");
			$('#validation-form').removeClass('hide');
			$('#cssloader').addClass('hide');
		}
	});
}


//删除图片
function removeImage(filename){
	var message=null;
	$.ajax({
		type: "post",
		data: {filename: filename},
		url: webroot + "product/DeleteImage.do",
		success: function(msg){
			
			alert(msg.message);
			
			
		}
	});
	return message;
}
function removeShowImage(filename){
	var message=null;
	$.ajax({
		type: "post",
		data: {filename: filename},
		url: webroot + "product/DeleteShowImage.do",
		success: function(msg){
			alert(msg.message);
		}
	});
	return message;
}

function addproduct(){
	var pro_id=null;
	var data = $("#validation-form").serialize();
	$.ajax({
		type: "post",
		async: false,
		url: webroot + "product/insertProduct.do",
		data: data,
		success: function(msg){
			pro_id=msg;
		}
	
	});
	return pro_id;
}

function updateproduct(proid){
	var pro_id=proid;
	var flag=false;
	var data = $("#validation-form").serialize();
	$.ajax({
		type: "post",
		async: false,
		url: webroot + "product/updateProduct.do?id="+pro_id+"&",
		data: data,
		success: function(msg){
			flag=msg;
		}
	});
	return flag;
}
