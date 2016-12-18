function initAdminManager(){
	
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
		
		url: webroot + "admin/selectAdmin.do",
		mtype: 'post',
		datatype: "json",
		height: 320,
		colNames:['用户ID','用户姓名','邮箱','电话','创建时间','最后更新时间'],
		colModel:[
          	{name:'id',index:'admin_id', width:80, sorttype:"int", editable: true},
          	{name:'username',index:'username',width:80, editable:true},
			{name:'email',index:'email', width:80, sorttype:"int", editable: true},
			{name:'phone',index:'phone',width:80, editable:true},
			{name:'createTime',index:'create_time',width:80, editable:true, formatter:formatDate},
			{name:'lastModifiedTime',index:'last_modified_time',width:80,formatter:formatDate}
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
	
	
	$('#addAdminform').validate({
		errorElement: 'div',
		errorClass: 'help-block',
		focusInvalid: false,
		ignore: "",
		rules: {
			email: {
				required: true,
				email:true
			},
			password: {
				required: true,
				minlength: 5
			},
			password2: {
				required: true,
				minlength: 5,
				equalTo: "#addAdminModal input[name='password']"
			},
			username: {
				required: true
			},
			realname: {
				required: true
			},
			phone: {
				required: true,
				minlength: 11
			}
		},

		messages: {
			email: {
				required: "请输入email",
				email: "email格式错误"
			},
			password: {
				required: "请输入密码",
				minlength: "密码格式错误",
			},
			password2: {
				required: "请输入密码",
				password2: "密码格式错误"
			},
			phone: {
				required: "请输入手机号",
				minlength: "手机号小于11位",
			},
			username: "请输入用户名",
			realname: "请输入真实姓名"
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
	
	$('#editAdminform').validate({
		errorElement: 'div',
		errorClass: 'help-block',
		focusInvalid: false,
		ignore: "",
		rules: {
			email: {
				required: true,
				email:true
			},
			password: {
				required: true,
				minlength: 5
			},
			password2: {
				required: true,
				minlength: 5,
				equalTo: "#editAdminform input[name='password']"
			},
			username: {
				required: true
			},
			realname: {
				required: true
			},
			phone: {
				required: true,
				minlength: 11
			}
		},

		messages: {
			email: {
				required: "请输入email",
				email: "email格式错误"
			},
			password: {
				required: "请输入密码",
				minlength: "密码格式错误",
			},
			password2: {
				required: "请输入密码",
				password2: "密码格式错误"
			},
			phone: {
				required: "请输入手机号",
				minlength: "手机号小于11位",
			},
			username: "请输入用户名",
			realname: "请输入真实姓名"
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
	
}



//查询
function queryAdmin(){
	var data = $("form").serialize();
	var url = webroot + "admin/selectAdmin.do";
	$("#grid-table").jqGrid('setGridParam',{ 
        url: url + "?" + data, 
        //postData:jsonData, 
        page:1,
        mtype:"post"
    }).trigger("reloadGrid"); //重新载入 
}
//删除用户
function deleteAdmin(){
	var selectedIds = $("#grid-table").jqGrid("getGridParam", "selarrrow");//选择多行记录
	if(selectedIds.length < 1){
		alertmsg("warning", "请至少选中一行!");
		return;
	}
	var ids = "";
	for(var i = 0; i < selectedIds.length; i ++){
		var rowData = $('#grid-table').getRowData(selectedIds[i]);//获取选中行的记录
		var adminId = rowData.id;
		ids =ids + adminId + ",";
	}
    Lobibox.confirm({ 
        title:"删除用户",      //提示框标题 
        msg: "是否确认确认删除",   //提示框文本内容 
        callback: function ($this, type, ev) {               //回调函数 
            if (type === 'yes') { 
            	$.ajax({
            		type:"post",
            		url:webroot+"admin/delete.do",
            		data:{"adminIds":ids},
            		success:function(data){
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
//添加用户
function addAdmin(){
	
	//再次打开model之前清空上次的操作记录
	$("#addAdminModal :input").val("");
	$("#addAdminModal").modal("show");
	
	
}
//保存用户
function saveAdmin(){
	//form中验证不通过直接返回
	if(!($('#addAdminform').valid())){
		return;
	}
	//验证是否已存在此用户名
	var name = $("#addAdminform input[name='username'").val();
	$.ajax({
		type: "post",
		url: webroot + "admin/validateAdmin.do",
		data: {name: name},
		success: function(msg){
			if(msg.success){
				alertmsg("warning","用户名已存在!");
				return;
			}else{
				//保存用户信息
				var data = getParams("#addAdminModal");
				$.ajax({
					type: "post",
					url: webroot + "admin/saveAdmin.do",
					data: data,
					success: function(msg){
						if(msg.success){
							alertmsg("success", "新增用户成功!");
							var data = $("form").serialize();
							var url = webroot + "admin/selectAdmin.do";
							$("#grid-table").jqGrid('setGridParam',{ 
						        url: url + "?" + data, 
						        page:1,
						        mtype:"post"
						    }).trigger("reloadGrid"); //重新载入 
						}
					}
				});
				$("#addAdminModal").modal("hide");
			}
		}
	});
}
//修改用户
function editAdmin(){
	var id = $("#grid-table").jqGrid("getGridParam","selrow");
	if(!isNoEmpty(id)){
		alertmsg("warning","请至少选中一行 !");
		return;
	}
	$.ajax({
		type: "post",
		url: webroot + "admin/selectAdminByAdminId.do",
		data: {adminId: id},
		success: function(msg){
			$("#editAdminModal input[name='id']").val(msg.id);
			$("#editAdminModal input[name='username']").val(msg.username);
			$("#editAdminModal input[name='realname']").val(msg.realname);
			$("#editAdminModal input[name='password']").val(msg.password);
			$("#editAdminModal input[name='password2']").val(msg.password);
			$("#editAdminModal input[name='phone']").val(msg.phone);
			$("#editAdminModal input[name='email']").val(msg.email);
			$("#editAdminModal").modal("show");
		}
	});
}
//修改用户
function editAndSaveAdmin(){
	//form中验证不通过直接返回
	if(!($('#editAdminform').valid())){
		return;
	}
	//验证是否已存在此用户名
	var name = $("#editAdminform input[name='username']").val();
	$.ajax({
		type: "post",
		url: webroot + "admin/validateAdmin.do",
		data: {name: name},
		success: function(msg){
			if(msg.success){
				alertmsg("warning","用户名已存在!");
				return;
			}else{
				//保存用户信息
				var data = getParams("#editAdminform");
				$.ajax({
					type: "post",
					url: webroot + "admin/updateAdmin.do",
					data: data,
					success: function(msg){
						if(msg.success){
							alertmsg("success", "修改用户成功!");
							var data = $("form").serialize();
							var url = webroot + "admin/selectAdmin.do";
							$("#grid-table").jqGrid('setGridParam',{ 
						        url: url + "?" + data, 
						        page:1,
						        mtype:"post"
						    }).trigger("reloadGrid"); //重新载入 
						}
					}
				});
				$("#editAdminModal").modal("hide");
			}
		}
	});
}