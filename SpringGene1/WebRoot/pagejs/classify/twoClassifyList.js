function initTwoClassifyManager(){
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
		
		url: webroot + "classify/seletcClassify.do",
		mtype: 'post',
		datatype: "json",
		postData: {flag: "twoClassify"},
		height: 320,
		colNames:['分类ID','分类名称','创建时间','最后更新时间'],
		colModel:[
          	{name:'id',index:'classify_id', width:80, sorttype:"int", editable: true},
          	{name:'claName',index:'claName',width:80, editable:true,sortable:false},
			{name:'createTime',index:'create_time',width:80, editable:true,sortable:false, formatter:formatDate},
			{name:'lastModifiedTime',index:'last_modified_time',width:80,sortable:false,formatter:formatDate},
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
	//初始化查询列表的一级分类
	$('#queryOneClassify').html("");
	$.ajax({
		type: "post",
		url: webroot + "classify/selectAllOneClassify.do",
		success: function(msg){
			var json = eval(msg);
			var objSelect = $('#queryOneClassify');
			objSelect.append("<option value=''></option>");
			for(var i=0;i<json.length;i++){
				objSelect.append("<option value='"+json[i].id+"'>"+json[i].claName+"</option>");
			}
			$('#queryOneClassify').trigger("chosen:updated");
		}
	});
}

//查询
function queryTwoClassify(){
	var data = $("#queryTwoClassifyForm").serialize();
	var url = webroot + "classify/seletcClassify.do";
	$("#grid-table").jqGrid('setGridParam',{ 
        url: url + "?" + data + "&flag=twoClassify", 
        //postData:jsonData, 
        page:1,
        mtype:"post"
    }).trigger("reloadGrid"); //重新载入 
}
//删除分类
function deleteTwoClassify(){
	var selectedIds = $("#grid-table").jqGrid("getGridParam", "selarrrow");//选择多行记录
	if(selectedIds.length < 1){
		alertmsg("warning", "请至少选中一行!");
		return;
	}
	var ids = "";
	for(var i = 0; i < selectedIds.length; i ++){
		var rowData = $('#grid-table').getRowData(selectedIds[i]);//获取选中行的记录
		var classifyId = rowData.id;
		ids =ids + classifyId + ",";
	}
    Lobibox.confirm({ 
        title:"删除分类",      //提示框标题 
        msg: "请确保此分类下的商品已删除",   //提示框文本内容 
        callback: function ($this, type, ev) {               //回调函数 
            if (type === 'yes') { 
            	$.ajax({
            		type:"post",
            		url:webroot+"classify/deleteTwoClassify.do",
            		data:{"twoClassifyIds":ids},
            		success:function(msg){
            			//删除成功重新加载jqGrid
            			if(msg.success){
            				alertmsg("success", "删除成功");
            			}else{
            				
            				alertmsg("error",empty(msg.msg) == true ? "删除失败" : msg.msg);
            			}
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
function addTwoClassify(){
	//再次打开model之前清空上次的操作记录
	$("#addTwoClassifyModal :input").val("");
	$('#oneClassify').html("");
	$.ajax({
		type: "post",
		url: webroot + "classify/selectAllOneClassify.do",
		success: function(msg){
			var json = eval(msg);
			var objSelect = $('#oneClassify');
			objSelect.append("<option value=''></option>");
			for(var i=0;i<json.length;i++){
				objSelect.append("<option value='"+json[i].id+"'>"+json[i].claName+"</option>");
			}
			$('#oneClassify').trigger("chosen:updated");
		}
	});
	$("#addTwoClassifyModal").modal("show");
	
}
function saveTwoClassify(){
	var calName = $("#addclaName").val();
	var oneClassify = $("#oneClassify").val();
	$.ajax({
		type:"post",
		url: webroot + "classify/saveTwoClassify.do",
		data:{"claName":calName,"oneClassifyId":oneClassify},
		success:function(data){
			if(data.success){
				alertmsg("success","保存成功");
				queryTwoClassify();
				$("#addTwoClassifyModal").modal("hide");
			}
		}
	});
	
}
//修改分类
function editTwoClassify(){
	$("#editTwoClassifyModal :input").val("");
	$('#editOneClassify').html("");
	var lanId = $("#grid-table").jqGrid("getGridParam","selrow");
	var rowData = $('#grid-table').getRowData(lanId);//获取选中行的记录 
	var id = rowData.id;
	if(!isNoEmpty(id)){
		alertmsg("warning","请至少选中一行 !");
		return;
	}
	$.ajax({
		type: "post",
		url: webroot + "classify/selectAllOneClassify.do",
		async: false,
		success: function(msg){
			var json = eval(msg);
			var objSelect = $('#editOneClassify');
			for(var i=0;i<json.length;i++){
				objSelect.append("<option value='"+json[i].id+"'>"+json[i].claName+"</option>");
			}
			$('#editOneClassify').trigger("chosen:updated");
		}
	});
	
	
	$.ajax({
		type: "post",
		url: webroot + "classify/selectOneClassify.do",
		data: {classifyId: id},
		success: function(msg){
			$("#twoClassifyId").val(msg.id);
			$("#editclaName").val(msg.claName);
			$("#editOneClassify").val(msg.claPid);
			$('#editOneClassify').trigger("chosen:updated");
			$("#editTwoClassifyModal").modal("show");
		}
	});
}
function saveAndEditTwoClassify(){
	var twoClassifyId = $("#twoClassifyId").val();
	var editclaName = $("#editclaName").val();
	var editOneClassify = $("#editOneClassify").val();
	$.ajax({
		type: "post",
		url: webroot + "classify/updateTwoClassify.do",
		data: {twoClassifyId: twoClassifyId,editclaName:editclaName,editOneClassify:editOneClassify},
		success: function(msg){
			if(msg.success){
				alertmsg("success", "修改成功");
				$("#editTwoClassifyModal").modal("hide");
				queryTwoClassify();
			}
		}
	});
}
function chosenSelectInit(){
	if(!ace.vars['touch']) {
		$('.chosen-select').chosen({allow_single_deselect:true}); 
		//resize the chosen on window resize

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


		$('#chosen-multiple-style .btn').on('click', function(e){
			var target = $(this).find('input[type=radio]');
			var which = parseInt(target.val());
			if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
			 else $('#form-field-select-4').removeClass('tag-input-style');
		});
	}
}
