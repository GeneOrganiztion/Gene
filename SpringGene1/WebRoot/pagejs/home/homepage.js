function initHomePageManager(){
	
	
	$("#viewHomePageModal").draggable();
	$("#editHomePageModal").draggable();
	$("#addHomePageModal").draggable();
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	//resize to fit page size
	$(window).on('resize.jqGrid', function () {
		$(grid_selector).jqGrid( 'setGridWidth', $(".page-content").width() );
    })
	//resize on sidebar collapse/expand
	var parent_column = $(grid_selector).closest('[class*="col-"]');
	$(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
		if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
			//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
			setTimeout(function() {
				$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
			}, 0);
		}
    })
	jQuery(grid_selector).jqGrid({
		
		url: webroot + "home/seletcHomePage.do",
		mtype: 'post',
		datatype: "json",
		height: 320,
		colNames:['滚动图ID','滚动图名称','创建时间','最后更新时间','操作','图片'],
		colModel:[
          	{name:'id',index:'homepage_id', width:80, sorttype:"int", editable: true},
          	{name:'name',index:'name',width:80, editable:true},
			{name:'createTime',index:'create_time',width:80, editable:true,formatter:formatDate},
			{name:'lastModifiedTime',index:'last_modified_time',width:80,formatter:formatDate},
			{name: '', width: 80,sortable:false,formatter: formatterOperate},
			{name:'image',index:'image',width:80,hidden: true}
		], 
		viewrecords : true,
		rowNum:10,
		rowList:[10,20,30],
		loadonce: false,
		pager : pager_selector,
		altRows: true,
		jsonReader: {
			total: 'lastPage', 
			records: 'total',
			root: 'list',
			repeatitems: true
		},
		//multikey: "ctrlKey",
		multiselect: true,
        multiboxonly: false,
        ondblClickRow:function(rowid){
            var rowData = $('#grid-table').getRowData(rowid);//获取选中行的记录 
            alert(rowData.id);
        },
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
				//enableTooltips(table);
			}, 0);
		},			
	});
	
	$(window).triggerHandler('resize.jqGrid');
	//时间使用js 
	$(".date-select").datepicker({
		showOtherMonths: true,
		selectOtherMonths: false,
		todayHighlight: true
	});
	$('.date-select').datepicker().next().on(ace.click_event, function(){
		$(this).prev().focus();
	});

	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: false,
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: false,
			delicon : 'ace-icon fa fa-trash-o red',
			search: false,
			searchicon : 'ace-icon fa fa-search orange',
			refresh: false,
			refreshicon : 'ace-icon fa fa-refresh green',
			view: false,
			viewicon : 'ace-icon fa fa-search-plus grey',
		},
		{
			//edit record form
			//closeAfterEdit: true,
			//width: 700,
			recreateForm: true,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_edit_form(form);
			}
		},
		{
			//new record form
			//width: 700,
			closeAfterAdd: true,
			recreateForm: true,
			viewPagerButtons: false,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
				.wrapInner('<div class="widget-header" />')
				style_edit_form(form);
			}
		},
		{
			//delete record form
			recreateForm: true,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				if(form.data('styled')) return false;
				
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_delete_form(form);
				
				form.data('styled', true);
			},
			onClick : function(e) {
				//alert(1);
			}
		},
		{
			//search form
			recreateForm: true,
			afterShowSearch: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
				style_search_form(form);
			},
			afterRedraw: function(){
				style_search_filters($(this));
			}
			,
			multipleSearch: true,
			/**
			multipleGroup:true,
			showQuery: true
			*/
		},
		{
			//view record form
			recreateForm: true,
			beforeShowForm: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
			}
		}
	);
	
	function updatePagerIcons(table) {
		var replacement = 
		{
			'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
			'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
			'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
			'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
		};
		$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
			
			if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
		})
	}
	
	function enableTooltips(table) {
		$('.navtable .ui-pg-button').tooltip({container:'body'});
		$(table).find('.ui-pg-div').tooltip({container:'body'});
	}

	//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

	$(document).one('ajaxloadstart.page', function(e) {
		$(grid_selector).jqGrid('GridUnload');
		$('.ui-jqdialog').remove();
	});
	function TimeAdd0(time){
		return time < 10 ? ("0" + time) : time;
	}
	function formatDate(cellvalue, options, rowObject){
		var date = new Date(cellvalue);
		var time = date.getFullYear() + "-" + TimeAdd0((date.getMonth() + 1)) + "-" + TimeAdd0(date.getDate()) 
					+ " " + TimeAdd0(date.getHours()) + ":" + TimeAdd0(date.getMinutes()) + ":" + TimeAdd0(date.getSeconds());
		return time;
	}
	
	//格式化商品jqgrid之后的操作
	function formatterOperate(cellvalue, options, rowObject){
		var detial;
		if(empty(rowObject.image)){
			detial = "<button disabled=\"disabled\" onclick=\"viewHomePagePic(" + rowObject.id + ")\" class=\"btn btn-minier btn-yellow\">删除图片</button>";
		}else{
			detial = "<button onclick=\"viewHomePagePic(" + rowObject.id + ")\" class=\"btn btn-minier btn-yellow\">删除图片</button>";
		}
        return detial;
	}
	
	try {
		  Dropzone.autoDiscover = false;
		  var myDropzone = new Dropzone("#dropzone" , {
		    paramName: "file", // The name that will be used to transfer the file
		    maxFilesize: 1, // MB
		    maxFiles:1,
		    dictMaxFilesExceeded: "您最多只能上传1个文件！",
		    dictFileTooBig:"文件过大上传文件最大支持.",
		    acceptedFiles: ".jpg,.gif,.png",
		    dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.jpg,*.gif,*.png",
			addRemoveLinks : true,
			autoProcessQueue :false,
			dictDefaultMessage :
			'<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i> Drop files</span> to upload \
			<span class="smaller-80 grey">(or click)</span> <br /> \
			<i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>'
		,
			dictResponseError: 'Error while uploading file!',
			previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    " +
					"<div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    " +
					"<img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-small progress-striped active\">" +
					"<div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  " +
					"<div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\">" +
					"<span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>",
			init: function () {
              var submitButton = document.querySelector("#submit-all");
              myDropzone = this; // closure

              submitButton.addEventListener("click", function () {
              	//验证报告名称不能为空
              	var HomeName = $("#HomeName").val();
              	if(empty(HomeName)){
              		alertmsg("warning", "滚动图名称不能为空");
              		return;
              	}
              	Lobibox.confirm({ 
                      title:"确认提交",      //提示框标题 
                      msg: "是否确认新增滚动图",   //提示框文本内容 
                      callback: function ($this, type, ev) {               //回调函数 
                          if (type === 'yes') { 
                          	myDropzone.processQueue();
                          } else if (type === 'no') { 
                                     
                          } 
                     } 
                   });
              });

              //当添加图片后的事件，上传按钮恢复可用
              this.on("addedfile", function () {
                  $("#submit-all").removeAttr("disabled");
              });
              
              this.on("success", function (file, response, e) {
              	if(response.success){
          			$("#addHomePageModal").modal("hide");
					alertmsg("success","滚动图添加成功");
					$("#HomePageId").val(response.returnId);
					queryHomePage();
				}else{
					alertmsg("error",empty(response.msg) == true ? "滚动图添加失败" : response.msg);
					$(file.previewTemplate).children('.dz-error-mark').css('opacity', '1');
				}
              });
              this.on("sending", function (file, xhr, formData) {
              	formData.append("HomeName",$("#HomeName").val());
              	formData.append("HomeUrl",$("#HomeUrl").val());
              	formData.append("HomeComment",$("#HomeComment").val());
              });
              //当上传完成后的事件，接受的数据为JSON格式
              /*this.on("complete", function (data) {
                  if (this.getUploadingFiles().length === 0 && this.getQueuedFiles().length === 0) {
                      var res =data;
                      var msg;
                      if (res.message=="error") {
                      	alertmsg("error","商品详情图片上传失败，请重新上传");
                      }
                      else {
                      	alertmsg("success","商品详情图片上传完成");
                      }
                      
                  }
              });*/
              

              //删除图片的事件，当上传的图片为空时，使上传按钮不可用状态
              this.on("removedfile", function (file) {
                  if (this.getAcceptedFiles().length === 0) {
                      $("#submit-all").attr("disabled", true);
                  }
                  //上传失败的不删数据库中的数据
                  removeImage($("#oneClassifyId").val());
              });
          }
			
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
			  var editDropzone = new Dropzone("#editHomePageDropzone" , {
			    paramName: "file", // The name that will be used to transfer the file
			    maxFilesize: 1, // MB
			    maxFiles:1,
			    dictMaxFilesExceeded: "您最多只能上传1个文件！",
			    dictFileTooBig:"文件过大上传文件最大支持.",
			    acceptedFiles: ".jpg,.gif,.png",
			    dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.jpg,*.gif,*.png",
				addRemoveLinks : true,
				autoProcessQueue :false,
				dictDefaultMessage :
				'<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i> Drop files</span> to upload \
				<span class="smaller-80 grey">(or click)</span> <br /> \
				<i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>'
			,
				dictResponseError: 'Error while uploading file!',
				previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    " +
						"<div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    " +
						"<img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-small progress-striped active\">" +
						"<div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  " +
						"<div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\">" +
						"<span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>",
				init: function () {
	              var submitButton = document.querySelector("#editOneClassify-submit-all")
	              editDropzone = this; // closure

	              submitButton.addEventListener("click", function () {
	              	//验证报告名称不能为空
	              	var claName = $("#editHomePageName").val();
	              	if(empty(claName)){
	              		alertmsg("warning", "滚动图名称不能为空");
	              		return;
	              	}
	              	Lobibox.confirm({ 
	                      title:"确认提交",      //提示框标题 
	                      msg: "是否确认修改滚动图",   //提示框文本内容 
	                      callback: function ($this, type, ev) {               //回调函数 
	                          if (type === 'yes') { 
	                        	  editDropzone.processQueue();
	                          } else if (type === 'no') { 
	                                     
	                          } 
	                     } 
	                   });
	              });

	              //当添加图片后的事件，上传按钮恢复可用
	              this.on("addedfile", function () {
	                  //$("#editOneClassify-submit-all").removeAttr("disabled");
	              });
	              
	              this.on("success", function (file, response, e) {
	              	if(response.success){
	              			$("#editHomePageModal").modal("hide");
							alertmsg("success","滚动图修改成功");
							$("#editHomePageId").val(response.returnId);
							queryHomePage();
						}else{
							alertmsg("error",empty(response.msg) == true ? "滚动图修改失败" : response.msg);
							$(file.previewTemplate).children('.dz-error-mark').css('opacity', '1');
						}
	              });
	              this.on("sending", function (file, xhr, formData) {
	            	  formData.append("HomePageid",$("#editHomePageId").val());
	            	  formData.append("HomeName",$("#editHomePageName").val());
	                  formData.append("HomeUrl",$("#editHomeUrl").val());
	                  formData.append("HomeComment",$("#editHomeComment").val());
	              
	              });
	              //当上传完成后的事件，接受的数据为JSON格式
	              /*this.on("complete", function (data) {
	                  if (this.getUploadingFiles().length === 0 && this.getQueuedFiles().length === 0) {
	                      var res =data;
	                      var msg;
	                      if (res.message=="error") {
	                      	alertmsg("error","商品详情图片上传失败，请重新上传");
	                      }
	                      else {
	                      	alertmsg("success","商品详情图片上传完成");
	                      }
	                      
	                  }
	              });*/
	              

	              //删除图片的事件，当上传的图片为空时，使上传按钮不可用状态
	              this.on("removedfile", function (file) {
	                  if (this.getAcceptedFiles().length === 0) {
	                      //$("#editOneClassify-submit-all").attr("disabled", true);
	                  }
	                  
	                  removeImage($("#editOneClassifyId").val());
	              });
	          }
				
			  });
			  
			   $(document).one('ajaxloadstart.page', function(e) {
					try {
						myDropzone.destroy();
					} catch(e) {}
			   });
			
			} catch(e) {
			  alert('Dropzone.js does not support older browsers!');
			}
	
	
}
//预览图片
function viewHomePagePic(id){
	//清空上次的报告
	$("#viewHomePagePic").html("");
	$.ajax({
		type: "post",
		url: webroot + "home/selectOneHomePage.do",
		data: {HomePageId:id},
		success: function(msg){
			console.log(msg);
			if(empty(msg)){
				alertmsg("error","预览图片失败");
				return;
			}
			var html = "<li>" + "<a title='查看图片' target=\"_bank\" href='"+msg.image+"' >" + msg.name + "</a>" +"&nbsp;" +
				"<button onclick=\"delectHomePagePic(" + msg.id + ")\" class=\"btn btn-minier btn-yellow\">删除图片</button>"+ "</li>";
			$("#viewHomePagePic").append(html);
			$("#viewHomePageModal").modal("show");
		}
	});
}
function delectHomePagePic(id){
	Lobibox.confirm({ 
        title:"确认删除",      //提示框标题 
        msg: "是否确认删除滚动图",   //提示框文本内容 
        callback: function ($this, type, ev) {               //回调函数 
            if (type === 'yes') { 
            	$.ajax({
            		type:"post",
            		url:webroot+"home/delectHomePagePic.do",
            		data:{"HomePageId":id},
            		success:function(data){
            			queryHomePage();
            			$("#viewHomePageModal").modal("hide");
            		}
            	});
            } else if (type === 'no') { 
                       
            } 
       } 
     });
}

//查询
function queryHomePage(){
	var data = $("#queryHomePageForm").serialize();
	var url = webroot + "home/seletcHomePage.do";
	$("#grid-table").jqGrid('setGridParam',{ 
        url: url + "?" + data , 
        //postData:jsonData, 
        page:1,
        mtype:"post"
    }).trigger("reloadGrid"); //重新载入 
}
//删除分类
function deleteHomePage(){
	var selectedIds = $("#grid-table").jqGrid("getGridParam", "selarrrow");//选择多行记录
	if(selectedIds.length < 1){
		alertmsg("warning", "请至少选中一行!");
		return;
	}
	var ids = "";
	for(var i = 0; i < selectedIds.length; i ++){
		var rowData = $('#grid-table').getRowData(selectedIds[i]);//获取选中行的记录
		var HomePageId = rowData.id;
		ids =ids + HomePageId + ",";
	}
    Lobibox.confirm({ 
        title:"删除滚动图",      //提示框标题 
        msg: "确定要删除滚动图吗",   //提示框文本内容 
        callback: function ($this, type, ev) {               //回调函数 
            if (type === 'yes') { 
            	$.ajax({
            		type:"post",
            		url:webroot+"home/deleteHomePage.do",
            		data:{"HomePageIds":ids},
            		success:function(data){
            			if(data.success){
            				alertmsg("success","删除成功");
            			}else{
            				alertmsg("error",empty(data.msg) == true ? "删除失败" : data.msg);
            			}
            			
            			//删除成功重新加载jqGrid
            			$("#grid-table").jqGrid('setGridParam',{ 
            		        page:1,
            		        mtype:"post"
            		    }).trigger("reloadGrid"); //重新载入 
            		}
            	});
            } else if (type === 'no') { 
                       
            } 
       } 
     });
	
}
//添加分类
function addHomePage(){
	
	//再次打开model之前清空上次的操作记录
	$("#addHomePageModal :input").val("");
	$("#addHomePageModal").modal("show");
	
	
}
//删除分类
function removeHomePage(id){
	if(empty(id)){
		return;
	}
	$.ajax({
		type: "post",
		data: {oneClassifyId: id},
		url: webroot + "home/removeHomePageById.do",
		success: function(msg){
			if(msg.success){
				alertmsg("success","删除成功");
				queryOneClassify();
			}else{
				alertmsg("error", "删除失败");
			}
		}
	});
}
//修改滚动图
function editHomePage(){
	$("#editHomePageModal :input").val("");
	var lanId = $("#grid-table").jqGrid("getGridParam","selrow");
	var rowData = $('#grid-table').getRowData(lanId);//获取选中行的记录 
	var id = rowData.id;
	if(!isNoEmpty(id)){
		alertmsg("warning","请至少选中一行 !");
		return;
	}
	if(!empty(rowData.image)){
		alertmsg("warning","请先删除图片再修改滚动图");
		return;
	}
	$.ajax({
		type: "post",
		url: webroot + "home/selectOneHomePage.do",
		data: {HomePageId: id},
		success: function(msg){
			$("#editHomePageId").val(msg.id);
			$("#editHomePageName").val(msg.name);
			$("#editHomeUrl").val(msg.href);
			$("#editHomeComment").val(msg.comment);
			$("#editHomePageModal").modal("show");
		}
	});
}

