<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<script src="<%=path%>/pagejs/classify/twoClassifyList.js"></script>
<!-- #section:basics/content.breadcrumbs -->
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>

	<ul class="breadcrumb">
		<!-- <li>
		        <div class="tip-box">
			        <div class="tip-title">位置</div>	
				    <div class=tip-arrow-breadcrumb></div>
			    </div>
		    </li> -->
		<li><i class="ace-icon fa fa-home home-icon"></i><a href="#">首页</a></li>
		<li><a href="#">分类管理</a></li>
		<li class="active"><span>二级分类</span></li>
	</ul>
	<!-- /.breadcrumb -->
</div>
<!-- /section:basics/content.breadcrumbs -->

<div class="page-content">
	<!-- #section:settings.box -->
	<div class="panel-group accordion" id="accordion">
	  <div class="panel panel-default">
	    <div class="panel-heading"> 
	      <div class="panel-title"  flag="show" id="searchTitle">
	        <a data-toggle="collapse" data-parent="#accordion" href="#searchDiv" onclick="change()">
	        	分类搜索<i class="fa fa-plus-square"></i></a>
	      </div>
	      <script>
	           function  change(){
	        	 var flag=$("#searchTitle").attr("flag");
	        	 if(flag=='show'){
	        		 $("#searchDiv").slideUp();
		        	 $("#searchTitle").find("i").addClass("fa-minus-square").removeClass("fa-plus-square").end().attr("flag","hide");
	        	 }else{
	        		 $("#searchDiv").slideDown();
		        	 $("#searchTitle").find("i").addClass("fa-plus-square").removeClass("fa-minus-square").end().attr("flag","show");
	        	 }
	          } 
	      </script>
	    </div>
	    <div id="searchDiv" class="panel-collapse collapse">
	      <div class="panel-body">
			  <form id="queryTwoClassifyForm" class="form-horizontal" role="form">
				<!-- #section:elements.form -->
				<div class="form-group">
					<div class="col-md-3">
							<label class="col-sm-4 control-label no-padding-right" for="claName">分类ID</label>
							<div class="col-sm-8">
								<input type="text" name="classifyId" class="form-control" />
							</div>
					</div>
					<div class="col-md-3">
						<label class="col-sm-4 control-label no-padding-right" for="claName">分类名称</label>
						<div class="col-sm-8">
							<input type="text" name="claName" class="form-control" />
						</div>
					</div>
					<div class="col-md-3">
						<label class="col-sm-4 control-label no-padding-right" for="beginTime">开始时间 </label>
						<div class="col-sm-8 input-group date form_date date-picker" data-date="" data-date-format="yyyy-mm-dd">
                                  <input type="text" name="beginTime" id="beginTime" class="form-control date-select">
							<span class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</span>
                        </div>
					</div>
					
					<div class="col-md-3" style="margin-bottom: 15px;">
						<label class="col-sm-4 control-label no-padding-right" for="endTime"> 结束时间 </label>									
						<div class="col-sm-8 input-group date form_date date-picker" data-date="" data-date-format="yyyy-mm-dd">
                                  <input type="text" name="endTime" id="endTime" class="form-control date-select">
							<span class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</span>
                        </div>	
					</div> 
				</div>
				<div class="form-group">
					<div class="col-md-12 text-right">
						<button type="button" class="btn btn-md" onclick="queryTwoClassify()">
							<i class="ace-icon fa fa-search orange"></i>搜索
						</button>
						<button type="button" class="btn btn-md" onclick="reset()">
							<i class="ace-icon fa fa-repeat"></i>重置
						</button>
					</div>								
				</div>								
			</form>
	      </div>
	    </div>
	  </div>
	</div>
	<div id="actions-before" class="btn-group">	
        <button type="button" onclick="addTwoClassify()" class="btn-link"><i class="glyphicon glyphicon-log-in"></i> &nbsp;添加分类</button>
        <button type="button" onclick="editTwoClassify()" class="btn-link"><i class="glyphicon glyphicon-log-out"></i> &nbsp;修改分类</button>
        <button type="button" onclick="deleteOneClassify()" class="btn-link"><i class="glyphicon glyphicon-remove"></i> &nbsp;删除分类</button>
    </div>
	<div class="row">
		<div class="col-xs-12">
			<table id="grid-table"></table>

			<div id="grid-pager"></div>
			<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->
<!-- 添加分类model start -->
<div class="modal fade" id="addTwoClassifyModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >添加分类</h4>
		     </div>
		     <div class="modal-body">
		     	<div class="row">
		     		<div class="col-xs-12 ">
		            	<form class="form-horizontal" id="addTwoClassifyform" >
		           			<div class="form-group">
		           				<div class="col-xs-6 ">
									<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="claName">分类名</label>
									<div class="col-xs-12 col-sm-8">
										<div class="clearfix">
											<input type="text" id="addclaName" name="claName" class="col-xs-12 col-sm-12" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-xs-6 ">
										<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="claName">一级分类</label>
										<div  class="col-xs-12 col-sm-8">
											<select class="chosen-select form-control" id="oneClassify" data-placeholder="请选择一级分类">					
											</select>
										</div>	
										<!-- <select id="oneClassify" name="oneclassify" class="input-medium" data-placeholder="请选择一级分类">
											<option value=""></option>
										</select> -->
									</div>
								</div>
							</div>
		 				</form>
	 				</div>
	 			</div>
		     </div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-md" onclick="saveTwoClassify()" >
			    	保存
			    </button>
			    <button type="button" class="btn btn-md" id="modalClose" data-dismiss="modal">
			    	关闭
			    </button>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 添加分类model end -->
<!-- 修改分类model start -->
<div class="modal fade" id="editTwoClassifyModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >修改分类</h4>
		     </div>
		     <div class="modal-body">
		     	<div class="row">
		     		<div class="col-xs-12 ">
		            	<form class="form-horizontal" id="editTwoClassifyform" >
		           			<div class="form-group">
		           				<div class="col-xs-6 ">
		           					<input type="hidden" id="twoClassifyId" class="col-xs-12 col-sm-12" />
									<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="claName">分类名</label>
									<div class="col-xs-12 col-sm-8">
										<div class="clearfix">
											<input type="text" id="editclaName" name="editclaName" class="col-xs-12 col-sm-12" />
										</div>
									</div>
								</div>
							
									<div class="col-xs-6 ">
										<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="claName">一级分类</label>
										<div class="col-xs-12 col-sm-8">
											<select class="chosen-select form-control" id="editOneClassify" data-placeholder="请选择一级分类">					
											</select>
										</div>
										<!-- <select id="editOneClassify" name="editOneClassify" class="input-medium" data-placeholder="请选择一级分类">
											<option value=""></option>
										</select> -->
									</div>
								
							</div>
		 				</form>
	 				</div>
	 			</div>
		     </div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-md" onclick="saveAndEditTwoClassify()" >
			    	保存
			    </button>
			    <button type="button" class="btn btn-md" id="modalClose" data-dismiss="modal">
			    	关闭
			    </button>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 修改分类model start -->

<!-- 删除图片model begin -->
<div class="modal fade" id="viewOneClassifyPicModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title">预览图片</h4>
		     </div>
		     <div class="modal-body">
		     	<div class="row">
					<div class="col-xs-12 center">
						<div >
							<ul id="viewOneClassifyPicli">
							</ul>
						</div><!-- PAGE CONTENT ENDS -->
					</div><!-- /.col -->
				</div><!-- /.row -->
		     </div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-md" id="modalClose" data-dismiss="modal">
			    	关闭
			    </button>
			</div>
		</div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 删除图片model end -->

<script type="text/javascript">
jQuery(function($) {
	initTwoClassifyManager();
})
</script>

