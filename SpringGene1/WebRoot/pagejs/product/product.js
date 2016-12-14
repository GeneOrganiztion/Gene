function initProductManager(){
	jQuery(function($) {
		$('#validation-form').addClass('hide');
		
		selectClassify();
		
		var flieuploadimagesize=null;
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
							 alert("从第一页跳转到第二页");	 
						}
					}
				}
				 if(info.step==2&&info.direction=="previous"){
					 alert("从第二页跳转到第第一页 不能跳转");
					 return true; //不能跳转
				 }
				 if(info.step==2&&info.direction=="next"){ 
					 console.log(info);
					 if(flieuploadimagesize==null){
					     alert("上传图片不能为空！")
					     return false;
					 }
					 
				 }
			
			//if(info.step == 1 && $validation) {
				console.log(info);
			
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
			  var myDropzone = new Dropzone("#dropzone" , {
			    paramName: "file", // The name that will be used to transfer the file
			    maxFilesize: 0.5, // MB
			    maxFiles:3,
			    dictMaxFilesExceeded: "您最多只能上传3个文件！",
			    dictFileTooBig:"文件过大上传文件最大支持.",
			    acceptedFiles: ".jpg,.gif,.png",
			    dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.jpg,*.gif,*.png。",
				addRemoveLinks : true,
				dictDefaultMessage :
				'<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i> Drop files</span> to upload \
				<span class="smaller-80 grey">(or click)</span> <br /> \
				<i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>'
			,
				dictResponseError: 'Error while uploading file!',
				previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-small progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>",
				sending: function(file, xhr, formData) {
					flieuploadimagesize=file.size;
				},
				success: function (file, response, e) {
						//var res = JSON.parse(response);
						console.log(response.message);
						/* console.log(response.message);
						if (response.message=="error") {
					        $(file.dictResponseError).children('.dz-error-mark').css('opacity', '1');
					        $(file.dictResponseError).children('.dz-error-message').innerHTML="上海传智播客";

					    } */
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
				state: "Please choose state",
				subscription: "Please choose at least one option",
				classify: "请选择商品类别",
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


//查询所有分类
function selectClassify(){
	$.ajax({
		type: "post",
		url: webroot + "classify/webclsall.do",
		success: function(msg){
			var json = eval(msg);
			var objSelect = $('#classify');
			console.log(objSelect);
			for(var i=0;i<json.length;i++){
				objSelect.append("<option value='"+json[i].id+"'>"+json[i].claName+"</option>");
			}
			$('#classify').trigger("chosen:updated");
			$('#validation-form').removeClass('hide');
			$('#cssloader').addClass('hide');
		}
	});
}
