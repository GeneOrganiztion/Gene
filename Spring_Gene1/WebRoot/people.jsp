<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <title> MAM </title>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        %>
        <meta name="description" content="overview &amp; stats" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

        <!-- bootstrap & fontawesome -->
        <link rel="stylesheet" href="<%=basePath%>/assets/css/bootstrap.css" />
        <link rel="stylesheet" href="<%=basePath%>/assets/css/font-awesome.css" />

        <!-- page specific plugin styles -->

        <link rel="stylesheet" href="<%=basePath%>/assets/css/jquery-ui.css" />
        <link rel="stylesheet" href="<%=basePath%>/assets/css/datepicker.css" />
        <link rel="stylesheet" href="<%=basePath%>/assets/css/ui.jqgrid.css" />

        <!-- jquery plugin styles -->
        <link rel="stylesheet" href="<%=basePath%>/assets/css/jquery-ui.custom.css"/>
        <link rel="stylesheet" href="<%=basePath%>/assets/css/jquery.gritter.css"/>
        <link rel="stylesheet" href="<%=basePath%>/assets/css/select2.css"/>
        <link rel="stylesheet" href="<%=basePath%>/assets/css/bootstrap-datetimepicker.css"/>
        <link rel="stylesheet" href="<%=basePath%>/assets/css/bootstrap-editable.css"/>
        <!-- colorbox 需要引入的 全部css -->
        <link rel="stylesheet" href="<%=basePath%>/assets/css/colorbox.css"/>
        <!-- Jcrop 需要引入的 全部css -->
        <link rel="stylesheet" href="<%=basePath%>/plugins/css/jquery-jcrop/jquery.Jcrop.css" type="text/css"/>

        <!-- text fonts -->
        <link rel="stylesheet" href="<%=basePath%>/assets/css/ace-fonts.css" />

        <!-- ace styles -->
        <link rel="stylesheet" href="<%=basePath%>/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
        
        <!--[if lte IE 9]>
            <link rel="stylesheet" href="<%=basePath%>/assets/css/ace-part2.css" class="ace-main-stylesheet" />
        <![endif]-->

        <!--[if lte IE 9]>
          <link rel="stylesheet" href="<%=basePath%>/assets/css/ace-ie.css" />
        <![endif]-->

        <!-- inline styles related to this page -->

        <!-- ace settings handler -->
        <script src="<%=basePath%>/assets/js/jquery.js"></script>
        <script src="<%=basePath%>/assets/js/ace-extra.js"></script>
        <script src="<%=basePath%>/pages/main.js"></script>

        <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

        <!--[if lte IE 8]>
        <script src="<%=basePath%>/assets/js/html5shiv.js"></script>
        <script src="<%=basePath%>/assets/js/respond.js"></script>
        <![endif]-->
        
        <!-- basic scripts -->
        
        <!--[if !IE]> -->
       <!--  <script type="text/javascript">
            window.jQuery || document.write("<script src='<c:url value="/assets/js/jquery.js"'>"+"<"+"/script>");
        </script> -->

        <!-- <![endif]-->
       <!--  <script type="text/javascript">
            if('ontouchstart' in document.documentElement) document.write("<script src='<c:url value="assets/js/jquery.mobile.custom.js"/>'>"+"<"+"/script>");
        </script> -->
        
        <script src="<%=basePath%>/plugins/js/echarts/dist/echarts-all.js"></script>
        <script src="<%=basePath%>/assets/js/bootstrap.js"></script>

        <!-- page specific plugin scripts -->

        <!--[if lte IE 8]>
          <script src="<%=basePath%>/assets/js/excanvas.js"></script>
        <![endif]-->
        
        <script src="<%=basePath%>/assets/js/jquery-ui.custom.js"></script>
        <script src="<%=basePath%>/assets/js/jquery.ui.touch-punch.js"></script>
        <%-- <script src="<%=basePath%>/assets/js/jquery.easypiechart.js"></script>
        <script src="<%=basePath%>/assets/js/jquery.sparkline.js"></script> --%>
        <script src="<%=basePath%>/assets/js/flot/jquery.flot.js"></script>
        <script src="<%=basePath%>/assets/js/flot/jquery.flot.pie.js"></script>
        <script src="<%=basePath%>/assets/js/flot/jquery.flot.resize.js"></script>
        
        <script src="<%=basePath%>/assets/js/jquery-ui.custom.js"></script>
        <script src="<%=basePath%>/assets/js/jquery.ui.touch-punch.js"></script>
        

        <!-- ace scripts -->
        <script src="<%=basePath%>/assets/js/ace/elements.scroller.js"></script>
        <script src="<%=basePath%>/assets/js/ace/elements.colorpicker.js"></script>
        <script src="<%=basePath%>/assets/js/ace/elements.fileinput.js"></script>
        <script src="<%=basePath%>/assets/js/ace/elements.typeahead.js"></script>
        <script src="<%=basePath%>/assets/js/ace/elements.wysiwyg.js"></script>
        <script src="<%=basePath%>/assets/js/ace/elements.spinner.js"></script>
        <script src="<%=basePath%>/assets/js/ace/elements.treeview.js"></script>
        <script src="<%=basePath%>/assets/js/ace/elements.wizard.js"></script>
        <script src="<%=basePath%>/assets/js/ace/elements.aside.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.ajax-content.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.touch-drag.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.sidebar.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.sidebar-scroll-1.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.submenu-hover.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.widget-box.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.settings.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.settings-rtl.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.settings-skin.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.widget-on-reload.js"></script>
        <script src="<%=basePath%>/assets/js/ace/ace.searchbox-autocomplete.js"></script>

        <script src="<%=basePath%>/assets/js/date-time/moment.js"></script>
        <script src="<%=basePath%>/assets/js/date-time/bootstrap-datepicker.js"></script>
        <script src="<%=basePath%>/assets/js/date-time/bootstrap-datetimepicker.js"></script>

        <!-- jqgrid -->
        <script src="<%=basePath%>/assets/js/jqGrid/jquery.jqGrid.src.js"></script>
        <script src="<%=basePath%>/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>
        <script src="<%=basePath%>/assets/js/jqGrid/i18n/grid.locale-en.js"></script>

        <!-- jquery plugin -->
        <script src="<%=basePath%>/assets/js/jquery-ui.js"></script>
        <script src="<%=basePath%>/assets/js/jquery.gritter.js"></script>
        <script src="<%=basePath%>/assets/js/jquery.easypiechart.js"></script>
        <script src="<%=basePath%>/assets/js/jquery.sparkline.js"></script>
        <script src="<%=basePath%>/assets/js/typeahead.jquery.js"></script>
        <script src="<%=basePath%>/assets/js/jquery.hotkeys.js"></script>
        <script src="<%=basePath%>/assets/js/bootstrap-wysiwyg.js"></script>
        <script src="<%=basePath%>/assets/js/fuelux/fuelux.wizard.js"></script>
        <script src="<%=basePath%>/assets/js/select2.js"></script>
        <script src="<%=basePath%>/assets/js/x-editable/bootstrap-editable.js"></script>
        <script src="<%=basePath%>/assets/js/x-editable/ace-editable.js"></script>
        <script src="<%=basePath%>/assets/js/jquery.colorbox.js"></script>
       <%--  <script src="<%=basePath%>/assets/people.js"></script> --%>
        
       
        
        
        
    </head>
    <body class="no-skin">
                <div class="main-content">
                <div class="main-content-inner">
                    <!-- #section:basics/content.breadcrumbs -->
                    <div class="breadcrumbs" id="breadcrumbs">
                        <script type="text/javascript">
                            try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
                        </script>

                    </div>

                    <div class="page-content">
                        <!-- #section:settings.box -->
                    <div id="createCopyright1" style="display: block;">
                        <div class="panel-group" id="accordion">
                          <div class="panel panel-default">
                            <div class="panel-heading"> 
                              <div class="panel-title"  flag="show" id="searchTitle">
                                <a data-toggle="collapse" data-parent="#accordion" href="#searchDiv" style="font-size:13px"  onclick="change()"><i class="glyphicon glyphicon-pushpin"></i>媒资搜索（点击隐藏）</a>
                              </div>
                              <script>
                                   function  change(){
                                     var flag=$("#searchTitle").attr("flag");
                                     if(flag=='show'){
                                       $("#searchTitle").html('<a data-toggle="collapse" data-parent="#accordion" href="#searchDiv" style="font-size:13px" onclick="change()" ><i class="glyphicon glyphicon-pushpin"></i>媒资搜索（点击隐藏）</a>'); 
                                       $("#searchTitle").attr("flag","hide");
                                     }else{
                                       $("#searchTitle").html('<a data-toggle="collapse" data-parent="#accordion" href="#searchDiv" style="font-size:13px" onclick="change()"><i class="glyphicon glyphicon-pushpin"></i>媒资搜索（点击展开）</a>');
                                       $("#searchTitle").attr("flag","show");
                                     }
                                  } 
                              </script>
                            </div>
                            <div id="searchDiv" class="panel-collapse collapse">
                              <div class="panel-body">
                                  <form class="form-horizontal" role="form">
                                    <!-- #section:elements.form -->
                                    <div class="form-group">
                                        <span class="col-sm-1 control-label no-padding-right" for="form-field-1"> 版权名称 </span>

                                        <div class="col-sm-3">
                                            <input type="text" id="form-field-1" class="col-xs-10 col-sm-5" />
                                        </div>
                                        
                                        <span class="col-sm-1 control-label no-padding-right" for="form-field-select-1"> 版权提供方 </span>

                                        <div class="col-sm-3">
                                            <select class="col-xs-10 col-sm-5" id="form-field-select-1">
                                                <option value="1">咪咕</option>
                                                <option value="2">1</option>
                                            </select>
                                        </div>
                                        <span class="col-sm-1 control-label no-padding-right" for="form-field-select-1"> 版权状态 </span>

                                        <div class="col-sm-3">
                                            <select class="col-xs-10 col-sm-5" id="form-field-select-1">
                                                <option value="1">待审核</option>
                                                <option value="2">审核通过</option>
                                                <option value="3">审核驳回</option>
                                            </select>
                                        </div>
                                        
                                        
                                    </div>
                                    
                                    <div class="form-group">
                                        <span class="col-sm-1 control-label no-padding-right" for="form-field-1"> 版权开始时间 </span>

                                        <div class="col-sm-3">
                                            <!-- <input type="text" id="form-field-1" class="col-xs-10 col-sm-5" /> -->
                                            <div class="input-group date form_date col-xs-10 col-sm-5" id ="search_ZBStartTime" data-date="" data-date-format="" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd" >
                                                <input type="text" id="datepicker" class="form-control date-select" readonly="readonly"/>
                                                <span class="input-group-addon">
                                                    <i class="ace-icon fa fa-calendar"></i>
                                                </span>
                                            </div>
                                        </div>
                                        
                                        <span class="col-sm-1 control-label no-padding-right" for="form-field-2"> 版权结束时间 </span>

                                        <div class="col-sm-3">
                                            <!-- <input type="text" id="form-field-2" class="col-xs-10 col-sm-5" /> -->
                                             <div class="input-group date form_date col-xs-10 col-sm-5"id ="search_ZBEndTime"  data-date="" data-date-format="" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd" >
                                                <input type="text" id="datepicker" class="form-control date-select" readonly="readonly"/>
                                                <span class="input-group-addon">
                                                    <i class="ace-icon fa fa-calendar"></i>
                                                </span>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-info btn-sm" onclick="list()">搜索</button>
                                        <button type="button" class="btn btn-info btn-sm" onclick="reset()">重置</button>
                                    </div>
                                </form>
                              </div>
                            </div>
                          </div>
                        </div>

                        <div id="actions" class="btn-group" >
                         <button type="button" class="btn-link" id= "confirmVerify"><i class="glyphicon glyphicon-upload"></i>提交入库</button>
                          
                         <button type="button" class="btn-link" id = "cancleVerify"><i class="glyphicon glyphicon-download"></i>取消提交</button>

                          <button type="button" class="btn-link" id="delVerify"><i class="glyphicon  glyphicon-remove"></i>删除</button>
                         
                       </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="grid-table"></table>

                                <div id="grid-pager"></div>

                                <script type="text/javascript">
                                    var $path_base = "..";//in Ace demo this will be used for editurl parameter
                                </script>

                                <!-- PAGE CONTENT ENDS -->
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                        </div>
                        <!-- 新增 页面 begin-->
                        <div id="createCopyright" style="display: none;">
                        </div>
                        <!-- 新增 页面 end-->
                    </div><!-- /.page-content -->
                </div>
                
            </div><!-- /.main-content -->
    </body>
     <script type="text/javascript">
        $(document).ready(function(){
            /* $( ".date-select" ).datepicker({
                 showOtherMonths: true,
                 selectOtherMonths: false,
             });*/
             $("#accordion a[href='#searchDiv']").click();
               //inline scripts related to this page
             //下拉多选start
             $('#id-disable-check').on('click', function() {
                   var inp = $('#form-input-readonly').get(0);
                   if(inp.hasAttribute('disabled')) {
                      inp.setAttribute('readonly' , 'true');
                      inp.removeAttribute('disabled');
                      inp.value="This text field is readonly!";
                   }
                   else {
                      inp.setAttribute('disabled' , 'disabled');
                      inp.removeAttribute('readonly');
                      inp.value="This text field is disabled!";
                   }
                });


                /*if(!ace.vars['touch']) {
                  $('.chosen-select').chosen({allow_single_deselect:true});
                   //resize the chosen on window resize

                   $(window)
                   .off('resize.chosen')
                   .on('resize.chosen', function() {
                      $('.chosen-select').each(function() {
                          var $this = $(this);
                          $this.next().css({'width': $this.parent().width()});
                      })
                   }).trigger('resize.chosen');
                   //resize chosen on sidebar collapse/expand
                   $(document).on('settings.ace.chosen', function(e, event_name, event_val) {
                      if(event_name != 'sidebar_collapsed') return;
                      $('.chosen-select').each(function() {
                          var $this = $(this);
                          $this.next().css({'width': $this.parent().width()});
                      })
                   });


                   $('#chosen-multiple-style .btn').on('click', function(e){
                      var target = $(this).find('input[type=radio]');
                      var which = parseInt(target.val());
                      if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
                       else $('#form-field-select-4').removeClass('tag-input-style');
                   });
                }*/

             //下拉多选end
              var grid_data =
                     [
                         {id:"1",name:"Desktop Computer",note:"note",stock:"Yes",ship:"待提交", sdate:"2007-12-03"},
                         {id:"2",name:"Laptop",note:"Long text ",stock:"Yes",ship:"审核驳回",sdate:"2007-12-03"},
                         {id:"3",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"审核通过",sdate:"2007-12-03"},
                         {id:"4",name:"Speakers",note:"note",stock:"No",ship:"审核通过",sdate:"2007-12-03"},
                         {id:"5",name:"Laser Printer",note:"note2",stock:"Yes",ship:"审核通过",sdate:"2007-12-03"},
                         {id:"6",name:"Play Station",note:"note3",stock:"No", ship:"审核驳回",sdate:"2007-12-03"},
                         {id:"7",name:"Mobile Telephone",note:"note",stock:"Yes",ship:"审核驳回",sdate:"2007-12-03"},
                         {id:"8",name:"Server",note:"note2",stock:"Yes",ship:"待提交",sdate:"2007-12-03"},
                         {id:"9",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
                         {id:"10",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
                         {id:"11",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
                         {id:"12",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
                         {id:"13",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"},
                         {id:"14",name:"Laser Printer",note:"note2",stock:"Yes",ship:"FedEx",sdate:"2007-12-03"},
                         {id:"15",name:"Play Station",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
                         {id:"16",name:"Mobile Telephone",note:"note",stock:"Yes",ship:"ARAMEX",sdate:"2007-12-03"},
                         {id:"17",name:"Server",note:"note2",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
                         {id:"18",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
                         {id:"19",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
                         {id:"20",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
                         {id:"21",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
                         {id:"22",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
                         {id:"23",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"}
                     ];

                          var grid_selector = "#grid-table";
                          var pager_selector = "#grid-pager";

                     jQuery(function($) {

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

                         //if your grid is inside another element, for example a tab pane, you should use its parent's width:
                         /**
                         $(window).on('resize.jqGrid', function () {
                             var parent_width = $(grid_selector).closest('.tab-pane').width();
                             $(grid_selector).jqGrid( 'setGridWidth', parent_width );
                         })
                         //and also set width when tab pane becomes visible
                         $('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                           if($(e.target).attr('href') == '#mygrid') {
                             var parent_width = $(grid_selector).closest('.tab-pane').width();
                             $(grid_selector).jqGrid( 'setGridWidth', parent_width );
                           }
                         })
                         */





                         jQuery(grid_selector).jqGrid({
                             //direction: "rtl",

                             url:"http://localhost:8080/SpringGene1/CoreServlet/test",
                             //data: grid_data,
                             mtype : "get",
                             datatype: "json",
                             height: 350,
                             colNames:['人物Id','人物姓名'],
                             colModel:[

                                 {name:'personId',index:'id', width:90, sorttype:"int", editable: true},
                                 {name:'personName',index:'sdate',width:90, editable:true, sorttype:"date",unformat: pickDate},
                             ],

                            /*  data: grid_data,
                             datatype: "local",
                             height: 350,
                             colNames:['版权ID','N_CPID','版权名称','版权提供方','资费类型','是否最优选', '版权状态', '驳回理由','版权开始时间','版权结束时间',"操作"],
                             colModel:[

                                 {name:'id',index:'id', width:90, sorttype:"int", editable: true, hidden:true},
                                 {name:'sdate',index:'sdate',width:90, editable:true, sorttype:"date",unformat: pickDate},
                                 {name:'sdate',index:'sdate',width:90, editable:true, sorttype:"date",unformat: pickDate},
                                 {name:'sdate',index:'sdate',width:90, editable:true, sorttype:"date",unformat: pickDate},
                                 {name:'name',index:'name', width:90,editable: true,editoptions:{size:"20",maxlength:"30"}},
                                 {name:'stock',index:'stock', width:90, editable: true,edittype:"checkbox",editoptions: {value:"Yes:No"},unformat: aceSwitch},
                                 {name:'ship',index:'ship', width:90, editable: true,edittype:"select",editoptions:{value:"FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX"}},
                                 {name:'note',index:'note', width:90, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}} ,
                                 {name:'note',index:'note', width:90, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}},
                                 {name:'note',index:'note', width:90, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}},
                                 {name:'Edit',index:'Edit', width:90},
                             ], */



                             viewrecords : true,
                             rowNum:10,
                             rowList:[10,20,30],
                             pager : pager_selector,
                             altRows: true,
                             //toppager: true,

                             multiselect: true,
                             //multikey: "ctrlKey",
                             multiboxonly: true,

                             loadComplete : function() {
                                 var table = this;
                                 setTimeout(function(){
                                     styleCheckbox(table);

                                     updateActionIcons(table);
                                     updatePagerIcons(table);
                                     enableTooltips(table);
                                 }, 0);
                             },
                         /*  onCellSelect:function(iRow){
                                 if (iRow ==3) {
                                     alert(1);
                                 }
                             },*/
                             ondblClickRow :function(rowid, iRow, iCol,e){
                                 if (iCol ==11) {
                                     _edit(rowid);
                                 }else{
                                      newCopyright();
                                 }
                               },

                             editurl: "/dummy.html",//nothing is saved
                             //caption: "媒资列表"

                             //,autowidth: true,

                             gridComplete: function () {

                                  var ids = jQuery("#grid-table").jqGrid('getDataIDs');
                                  for (var i = 0; i < ids.length; i++) {
                                      var id = ids[i];
                                      var rowData = $("#grid-table").getRowData(id);
                                      if(rowData.ship == '待提交') {
                                          var editBtn = "<a href='#' style='color:#f60' onclick='_edit(\""+id+"\")' ><i class='ace-icon fa fa-key'>绑定媒资</i>" +"                                     </a>";//此处会将点击行id传给_edit(id) js函数
                                          jQuery("#grid-table").jqGrid('setRowData', ids[i], { Edit: editBtn });
                                      }
                                 }
                            },
                             /**
                             ,
                             grouping:true,
                             groupingView : {
                                  groupField : ['name'],
                                  groupDataSorted : true,
                                  plusicon : 'fa fa-chevron-down bigger-110',
                                  minusicon : 'fa fa-chevron-up bigger-110'
                             },
                             caption: "Grouping"
                             */

                         });
                         $(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size



                         //enable search/filter toolbar
                         //jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
                         //jQuery(grid_selector).filterToolbar({});


                         //switch element when editing inline
                         function aceSwitch( cellvalue, options, cell ) {
                             setTimeout(function(){
                                 $(cell) .find('input[type=checkbox]')
                                     .addClass('ace ace-switch ace-switch-5')
                                     .after('<span class="lbl"></span>');
                             }, 0);
                         }
                         //enable datepicker
                         function pickDate( cellvalue, options, cell ) {
                             setTimeout(function(){
                                 $(cell) .find('input[type=text]')
                                         .datepicker({format:'yyyy-mm-dd' , autoclose:true});
                             }, 0);
                         }


                         //navButtons
                         jQuery(grid_selector).jqGrid('navGrid',pager_selector,
                             {   //navbar options
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
                         )



                         function style_edit_form(form) {
                             //enable datepicker on "sdate" field and switches for "stock" field
                             form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})

                             form.find('input[name=stock]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
                                        //don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
                                       //.addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');


                             //update buttons classes
                             var buttons = form.next().find('.EditButton .fm-button');
                             buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
                             buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
                             buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')

                             buttons = form.next().find('.navButton a');
                             buttons.find('.ui-icon').hide();
                             buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
                             buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
                         }

                         function style_delete_form(form) {
                             var buttons = form.next().find('.EditButton .fm-button');
                             buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();//ui-icon, s-icon
                             buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
                             buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
                         }

                         function style_search_filters(form) {
                             form.find('.delete-rule').val('X');
                             form.find('.add-rule').addClass('btn btn-xs btn-primary');
                             form.find('.add-group').addClass('btn btn-xs btn-success');
                             form.find('.delete-group').addClass('btn btn-xs btn-danger');
                         }
                         function style_search_form(form) {
                             var dialog = form.closest('.ui-jqdialog');
                             var buttons = dialog.find('.EditTable')
                             buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
                             buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
                             buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
                         }

                         function beforeDeleteCallback(e) {
                             var form = $(e[0]);
                             if(form.data('styled')) return false;

                             form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                             style_delete_form(form);

                             form.data('styled', true);
                         }

                         function beforeEditCallback(e) {
                             var form = $(e[0]);
                             form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                             style_edit_form(form);
                         }



                         //it causes some flicker when reloading or navigating grid
                         //it may be possible to have some custom formatter to do this as the grid is being created to prevent this
                         //or go back to default browser checkbox styles for the grid
                         function styleCheckbox(table) {
                         /**
                             $(table).find('input:checkbox').addClass('ace')
                             .wrap('<label />')
                             .after('<span class="lbl align-top" />')


                             $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
                             .find('input.cbox[type=checkbox]').addClass('ace')
                             .wrap('<label />').after('<span class="lbl align-top" />');
                         */
                         }


                         //unlike navButtons icons, action icons in rows seem to be hard-coded
                         //you can change them like this in here if you want
                         function updateActionIcons(table) {
                             /**
                             var replacement =
                             {
                                 'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
                                 'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
                                 'ui-icon-disk' : 'ace-icon fa fa-check green',
                                 'ui-icon-cancel' : 'ace-icon fa fa-times red'
                             };
                             $(table).find('.ui-pg-div span.ui-icon').each(function(){
                                 var icon = $(this);
                                 var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
                                 if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
                             })
                             */
                         }

                         //replace icons with FontAwesome icons like above
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
         });
             $("#delVerify").click(function(){
                 var gr = jQuery(grid_selector).jqGrid('getGridParam','selarrrow');
                 if (gr==null || gr=="") {
                     alert("请选择所要删除的数据");
                 }else{
                 alert(gr);
                 }
             });

             $("#confirmVerify").click(function(){
                 var gr = jQuery(grid_selector).jqGrid('getGridParam','selarrrow');
                 if (gr==null || gr=="") {
                     alert("请选择所要提交的数据");
                 }else{
                 alert(gr);
                 }
             });

             $("#cancleVerify").click(function(){
                 var gr = jQuery(grid_selector).jqGrid('getGridParam','selarrrow');
                 if (gr==null || gr=="") {
                     alert("请选择所要取消提交的数据");
                 }else{
                 alert(gr);
                 }
             });
             $("#editVerify").click(function(){
                 var gr = jQuery(grid_selector).jqGrid('getGridParam','selarrrow');
                 if (gr.length !=1) {
                     alert("请选择一条所要编辑的数据");
                 }else{
                 alert(gr);
                 }
             });
         });
        </script>
</html>
