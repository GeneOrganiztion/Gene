function initOrderManager(){
	
	$("#orderDetialModal").draggable();
	$("#uploadReportPicModal").draggable();
	$("#viewReportPicModal").draggable();
	chosenSelectInit();
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
		
		
		subGrid : true,
		//subGridModel: [{ name : ['No','Item Name','Qty'], width : [55,200,80] }],
		//datatype: "xml",
		subGridOptions : {
			plusicon : "ace-icon fa fa-plus center bigger-110 blue",
			minusicon  : "ace-icon fa fa-minus center bigger-110 blue",
			openicon : "ace-icon fa fa-chevron-right center orange"
		},
		//for this example we are using local data
		subGridRowExpanded: function (subgridDivId, rowId) {
			var subgridTableId = subgridDivId + "_t";
			$("#" + subgridDivId).html("<table style='height:35px;' id='" + subgridTableId + "'></table>");
			$("#" + subgridTableId).jqGrid({
				url: webroot + "orderInfo/selectOrderAndPrductByOrderId.do",
				mtype: 'post',
				datatype: "json",
				width:800,
				//height: 500,
				postData: {orderId: rowId},
				colNames: ['111','','','产品Id','产品名称','产品价格','产品数量','已上传报告数量','操作'],
				colModel: [
				    { name: 'reportIds', hidden:true},
				    { name: 'map_order_product_id', hidden:true},
				    { name: 'ordState',hidden:true},
					{ name: 'product_id', width: 80 },
					{ name: 'proName', width: 80 },
					{ name: 'proPrice', width: 60 },
					{ name: 'proCount', width: 60 },
					{ name: 'reportCount', width: 80 },
					{ name: '', width: 80,formatter: formatterOperate}
				]
			});
		},
		url: webroot + "orderInfo/seletcOrder.do",
		mtype: 'post',
		datatype: "json",
		postData: {flag: "detectionOrder"},
		height: 370,
		colNames:['','订单编号','订单状态','订单价格','订单所属区域','支付方式','创建时间','最后更新时间','操作'],
		colModel:[
          	{name:'id',index:'id', width:80, sorttype:"int", hidden:true},
          	{name:'ordNum',index:'ord_num', width:80, sorttype:"int", editable: true},
          	{name:'ordState',index:'ord_state',width:80, editable:true,formatter:formatOrdState},
			{name:'ordPrice',index:'ord_price', width:80, sorttype:"int", editable: true},
			{name:'userPostal',index:'user_postal', width:80, editable: true},
			{name:'ordPay',index:'ord_Pay',width:80, editable:true,sortable:false,formatter:formatordPay},
			{name:'createTime',index:'create_time',width:80, editable:true, formatter:formatDate},
			{name:'lastModifiedTime',index:'last_modified_time',width:80,formatter:formatDate},
			{name: '', width: 80,sortable:false,formatter: formatterGridOperate}
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
            //querOrderDetial(rowData.id);//查询订单详情的方法
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
	if(!ace.vars['touch']) {
		$('.chosen-select').chosen({allow_single_deselect:true}); 
		//resize the chosen on window resize
		$(".chosen-search").hide();
		$(window)
		.off('resize.chosen')
		.on('resize.chosen', function() {
			$('.chosen-select').each(function() {
				 var $this = $(this);
				 $this.next().css({'width': "100%"});
			})
		}).trigger('resize.chosen');
		//resize chosen on sidebar collapse/expand
		$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
			if(event_name != 'sidebar_collapsed') return;
			$('.chosen-select').each(function() {
				 var $this = $(this);
				 $this.next().css({'width': "100%"});
			})
		});
	}
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
	
	try {
		  Dropzone.autoDiscover = false;
		  var myDropzone = new Dropzone("#dropzone" , {
		    paramName: "file", // The name that will be used to transfer the file
		    maxFilesize: 10, // MB
		    maxFiles:1,
		    dictMaxFilesExceeded: "您最多只能上传1个文件！",
		    dictFileTooBig:"文件过大上传文件最大支持.",
		    acceptedFiles: ".jpg,.gif,.png,.pdf",
		    dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.jpg,*.gif,*.png,*.pdf",
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
						//$("#deleteReportId").val(response.returnId);
						queryOrder();
					}else{
						alertmsg("error",empty(response.msg) == true ? "报告上传失败" : response.msg);
						$(file.previewTemplate).children('.dz-error-mark').css('opacity', '1');
					}
                });
                this.on("sending", function (file, xhr, formData) {
                	formData.append("mapOrderProductId",$("#mapOrderProductId").val());
                	formData.append("reportName",$("#reportName").val());
                	formData.append("reportResult",$("#reportResult").val());
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
	
	//删除报告
	/*function removeImage(id){
		if(empty(id)){
			return;
		}
		return;
		$.ajax({
			type: "post",
			data: {reportId: id},
			url: webroot + "orderInfo/removeReportById.do",
			success: function(msg){
				if(msg.success){
					alertmsg("success","报告删除成功");
					//重新加载已上传报告数量
                	reloadUploadRrportCount();
				}else{
					alertmsg("error", "报告删除失败");
				}
			}
		});
	}*/
	
	
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
		var idArr = rowObject.reportIds.split(",");
		var	detial = "";
		if(rowObject.reportCount == rowObject.proCount){
			for(var i = 0; i < idArr.length; i ++){
				detialTmp = "<button disabled=\"disabled\" onclick=\"uploadReportPic('" + rowObject.map_order_product_id + "," + idArr[i] + "')\" class=\"btn btn-minier btn-purple\">检测操作</button>";
				detial = detial + detialTmp;
			}
		}else{
			for(var i = 0; i < idArr.length; i ++){
				detialTmp = "<button onclick=\"uploadReportPic('" + rowObject.map_order_product_id + "," + idArr[i] + "')\" class=\"btn btn-minier btn-purple\">检测操作</button>";
				detial = detial + detialTmp;
			}
		}
		 
		var detial1 = "";
		if(rowObject.reportCount == 0){
			detial1 = "<button disabled=\"disabled\" onclick=\"viewReportPic(" + rowObject.map_order_product_id + ")\" class=\"btn btn-minier btn-yellow\">预览报告</button>";
		}else{
			detial1 = "<button onclick=\"viewReportPic(" + rowObject.map_order_product_id + ")\" class=\"btn btn-minier btn-yellow\">预览报告</button>";
		}
        return detial+ detial1;

	}
	
	function formatterGridOperate(cellvalue, options, rowObject){
		var detail = "<button onclick=\"querOrderDetial(" + rowObject.id + ")\" class=\"btn btn-minier btn-purple\">查看详情</button>"
        return detail;

	}
	
	
	function formatordPay(cellvalue, options, rowObject){
		switch (parseInt(cellvalue)) {
		case 1:
			return "微信支付";
			break;
		case 2:
			return "支付宝支付";
			break;
		case 3:
			return "其他支付";
			break;
		default:
			return "无";
			break;
		}
	}
	function formatOrdState(cellvalue, options, rowObject){
		switch (parseInt(cellvalue)) {
		case 1:
			return "未支付";
			break;
		case 2:
			return "待发货";
			break;
		case 3:
			return "待寄回";
			break;
		case 4:
			return "检测中";
			break;
		case 5:
			return "已完成";
			break;
		default:
			return "无";
			break;
		}
	}
}

//重新加载上传报告数量
/*function reloadUploadRrportCount(){
	var mapOrderProductId = $("#mapOrderProductId").val();
	$.ajax({
		type: "post",
		url: webroot + "orderInfo/reloadUploadRrportCount.do",
		data: {mapOrderProductId: mapOrderProductId},
		success: function(msg){
			if(msg.success){
				var suGrid = $("#subGridId").val(); 
				var subGridLine = $("#subGridLine").val();
				console.log("suGrid:" + suGrid + "-----" + "subGridLine:" + subGridLine + "-----" + "value:" + msg.returnId);
				jQuery("#" + suGrid).setCell(subGridLine, 'reportCount', msg.returnId);
			}
		}		
	});
}*/

//查询
function queryOrder(){
	var data = $("#queryOrderListForm").serialize();
	var url = webroot + "orderInfo/seletcOrder.do";
	$("#grid-table").jqGrid('setGridParam',{ 
        url: url + "?" + data + "&flag=detectionOrder", 
        //postData:jsonData, 
        page:1,
        mtype:"post"
    }).trigger("reloadGrid"); //重新载入 
}
//查看订单详情
function querOrderDetial(orderId){
	//清空上次model中的数据
	$("#orderDetialModal :input").val("");
	$.ajax({
		type: "post",
		url: webroot + "orderInfo/selectOrderDetail.do",
		data: {orderId: orderId},
		success: function(msg){
			if(!isNoEmpty(msg)){
				return;
			}
			$("#orderDetialModal input[name='ordNum']").val(msg.ordNum);
			$("#orderDetialModal input[name='ordPrice']").val(msg.ordPrice);
			$("#orderDetialModal input[name='ordPay']").val(msg.ordPay);
			$("#orderDetialModal input[name='userName']").val(msg.userName);
			$("#orderDetialModal input[name='userPhone']").val(msg.userPhone);
			$("#orderDetialModal input[name='ordNum']").val(msg.ordNum);
			$("#orderDetialModal select[name='ordPay']").val(msg.ordPay);
			$("#userAddress").val(msg.userAddress);
			$("#orderDetialModal").modal("show");
		}
	});
}
//上传报告
function uploadReportPic(str){
	var strArr = str.split(",");
	//strArr[0]   mapOrderProductId
	//strArr[1]   report表的id
	//打开model之前清空上一次的数据    
	$("#uploadReportPicModal :input").val("");
	
	
	$("#mapOrderProductId").val(strArr[0]);
	$("#reportId").val(strArr[1]);
	//加载数据库数据
	$.ajax({
		type: "post",
		url: webroot + "orderInfo/selectReoprt.do",
		data: {reportId:strArr[1]},
		success: function(report){
			$("#reportName").val(report.repName);
			$("#reportResult").val(report.repResult);
			$("#reportState").val(report.repState);
		}
	});
	$("#uploadReportPicModal").modal("show");
}
//保存报告
function saveReport(){
	var reportId = $("#reportId").val();
	var reportName = $("#reportName").val();
	var repResult = $("#reportResult").val();
	var repState = $("#reportState").val();
	$.ajax({
		type: "post",
		url: webroot + "orderInfo/saveReoprt.do",
		data: {reportId:reportId,reportName:reportName,repResult:repResult,repState:repState},
		success: function(msg){
			if(msg.success){
				alertmsg("success", "保存成功");
			}
		}
	});
}
//预览报告
function viewReportPic(mapOrderProductId){
	//清空上次的报告
	$("#viewReportPicli").html("");
	$.ajax({
		type: "post",
		url: webroot + "orderInfo/selectOrderByMapOrderProductId.do",
		data: {mapOrderProductId:mapOrderProductId},
		success: function(msg){
			console.log(msg);
			if(empty(msg)){
				alertmsg("error","预览报告失败");
				return;
			}
			var html = "";
			for(var i =0; i < msg.length; i ++){
				var data = "<li>" + "<a target=\"_bank\" href='"+msg[i].repPdf+"' >" + msg[i].repName + "</a>" + "</li>";
				html = html +data;
			}
			$("#viewReportPicli").append(html);
			$("#viewReportPicModal").modal("show");
		}
	});
}
function chosenSelectInit(){
	if(!ace.vars['touch']) {
		$('.chosen-select').chosen({allow_single_deselect:true}); 
		//resize the chosen on window resize
		$(".chosen-search").hide();
		$(window)
		.off('resize.chosen')
		.on('resize.chosen', function() {
			$('.chosen-select').each(function() {
				 var $this = $(this);
				 $this.next().css({'width': "100%"});
			})
		}).trigger('resize.chosen');
		//resize chosen on sidebar collapse/expand
		$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
			if(event_name != 'sidebar_collapsed') return;
			$('.chosen-select').each(function() {
				 var $this = $(this);
				 $this.next().css({'width': "100%"});
			})
		});
	}
}
