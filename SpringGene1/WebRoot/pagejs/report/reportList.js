function initOneClassifyManager(){
	
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
		
		url: webroot + "report/seletreport.do",
		mtype: 'post',
		datatype: "json",
		height: 320,
		colNames:['报告ID','报告名称','报告所属用户','报告所属订单编号','报告状态','创建时间','最后更新时间','删除报告pdf'],
		colModel:[
		    {name:'id',index:'report_id', width:80, sorttype:"int", editable: true},
          	{name:'repName',index:'rep_name', width:80, sorttype:"int", editable: true},
          	{name:'order.userName',index:'user_name',width:80, editable:true,sortable:false},
          	{name:'order.ordNum',index:'ord_num',width:80, editable:true,sortable:false},
        	{name:'repState',index:'rep_state',width:80, editable:true,sortable:false,formatter:formatReportState},
			{name:'createTime',index:'create_time',width:80, editable:true,sortable:false, formatter:formatDate},
			{name:'lastModifiedTime',index:'last_modified_time',width:80,sortable:false,formatter:formatDate},
			{name: '', width: 80,sortable:false,formatter: formatterOperate}
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
	
	
	function formatReportState(cellvalue, options, rowObject){
		switch (parseInt(cellvalue)) {
		case 0:
			return "待检测";
			break;
		case 1:
			return "样本污染";
			break;
		case 2:
			return "正在检测";
			break;
		case 3:
			return "报告生成";	
			break;
		}
	}
	
	
	
	
	//格式化报告jqgrid之后的操作
	function formatterOperate(cellvalue, options, rowObject){
		var detial;
		if(empty(rowObject.repPdf)){
			detial = "<button disabled=\"disabled\" onclick=\"viewOneReportPdf(" + rowObject.id + ")\" class=\"btn btn-minier btn-yellow\">删除报告</button>";
		}else{
			detial = "<button onclick=\"viewOneReportPdf(" + rowObject.id + ")\" class=\"btn btn-minier btn-yellow\">删除报告</button>";
		}
        return detial;
	}
	
	try {
		  Dropzone.autoDiscover = false;
		  var myDropzone = new Dropzone("#dropzone" , {
		    paramName: "file", // The name that will be used to transfer the file
		    maxFilesize: 10, // MB
		    maxFiles:1,
		    dictMaxFilesExceeded: "您最多只能上传1个文件！",
		    dictFileTooBig:"文件过大上传文件最大支持.",
		    acceptedFiles: ".pdf",
		    dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.pdf",
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
              var submitButton = document.querySelector("#submit-all")
              myDropzone = this; // closure

              submitButton.addEventListener("click", function () {
              	//验证报告名称不能为空
              	var reportName = $("#reportName").val();
              	if(empty(reportName)){
              		alertmsg("warning", "报告名称不能为空");
              		return;
              	}
              	Lobibox.confirm({ 
                      title:"确认提交",      //提示框标题 
                      msg: "请仔细确认，一旦提交将无法更改",   //提示框文本内容 
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
              		$("#uploadReportPicModal").modal("hide");
						alertmsg("success","报告上传成功");
						myDropzone.destroy();
						$("#grid-table").jqGrid('setGridParam',{ 
					        page:1,
					        mtype:"post"
					    }).trigger("reloadGrid"); //重新载入 
						
					}else{
						alertmsg("error",empty(response.msg) == true ? "报告上传失败" : response.msg);
						$(file.previewTemplate).children('.dz-error-mark').css('opacity', '1');
					}
              });
              this.on("sending", function (file, xhr, formData) {
              	formData.append("reportId",$("#reportId").val());
              });
              

              //删除图片的事件，当上传的图片为空时，使上传按钮不可用状态
              this.on("removedfile", function (file) {
                  if (this.getAcceptedFiles().length === 0) {
                      $("#submit-all").attr("disabled", true);
                  }
                  //上传失败的不删数据库中的数据
                  //removeImage($("#deleteReportId").val());
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
//查询
function queryReport(){
	var data = $("#queryOneReport").serialize();
	var url = webroot + "report/seletreport.do";
	$("#grid-table").jqGrid('setGridParam',{ 
        url: url + "?" + data , 
        //postData:jsonData, 
        page:1,
        mtype:"post"
    }).trigger("reloadGrid"); //重新载入 
}


function uploadReportPic(){
	var lanId = $("#grid-table").jqGrid("getGridParam","selrow");
	if(!isNoEmpty(lanId)){
		alertmsg("warning","请至少选中一行 !");
		return;
	}
	var rowData = $('#grid-table').getRowData(lanId);//获取选中行的记录 
	var id = rowData.id;
	$("#uploadReportPicModal :input").val("");
	//$("#mapOrderProductId").val(strArr[0]);
	$("#reportId").val(id);
	//加载数据库数据
	$.ajax({
		type: "post",
		url: webroot + "orderInfo/selectReoprt.do",
		data: {reportId:id},
		success: function(report){
			$("#reportName").val(report.repName);
			$("#reportResult").val(report.repResult);
			$("#reportState").val(report.repState);
		}
	});
	$("#uploadReportPicModal").modal("show");
}

//预览图片
function viewOneReportPdf(id){
	//清空上次的报告
	$("#viewOneOneReportPdf").html("");
	$.ajax({
		type: "post",
		url: webroot + "orderInfo/selectReoprt.do",
		data: {reportId:id},
		success: function(msg){
			console.log(msg);
			if(empty(msg)){
				alertmsg("error","预览报告失败");
				return;
			}
			var html = "<li>" + "<a title='查看报告' target=\"_bank\" href='"+msg.repPdf+"' >" + msg.repName + "</a>" +"&nbsp;" +
				"<button onclick=\"delectOneReportPdf(" + msg.id + ")\" class=\"btn btn-minier btn-yellow\">删除报告</button>"+ "</li>";
			$("#viewOneOneReportPdf").append(html);
			$("#viewOneReportPdfModal").modal("show");
		}
	});
}
function delectOneReportPdf(id){
	$.ajax({
		type:"post",
		url:webroot+"report/removeReportById.do",
		data:{"reportId":id},
		success:function(data){
			$("#grid-table").jqGrid('setGridParam',{ 
		        page:1,
		        mtype:"post"
		    }).trigger("reloadGrid"); //重新载入 
			$("#viewOneReportPdfModal").modal("hide");
		}
	});
}

