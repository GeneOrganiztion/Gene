<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<script src="<%=path%>/pagejs/classify/oneClassifyList.js"></script>
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
		<li class="active"><span>一级分类</span></li>
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
			  <form id="queryOneClassifyForm" class="form-horizontal" role="form">
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
						<button type="button" class="btn btn-md" onclick="queryOneClassify()">
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
        <button type="button" onclick="addOneClassify()" class="btn-link"><i class="glyphicon glyphicon-log-in"></i> &nbsp;添加分类</button>
        <button type="button" onclick="editOneClassify()" class="btn-link"><i class="glyphicon glyphicon-log-out"></i> &nbsp;修改分类</button>
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
<div class="modal fade" id="addOneClassifyModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >添加分类</h4>
		     </div>
		     <div class="modal-body">
		     	<div class="row">
		     		<div class="col-xs-12 ">
		            	<form class="form-horizontal" id="addOneClassifyform" >
		           			<div class="form-group">
								<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="claName">分类名</label>
								<div class="col-xs-12 col-sm-8">
									<div class="clearfix">
										<input type="text" id="claName" name="claName" class="col-xs-12 col-sm-6" />
									</div>
								</div>
							</div>
		 				</form>
	 				</div>
	 				<div class="space-2"></div>
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->
						<input type="hidden" id="oneClassifyId"></input>
						<div>
							<form action="<%=path%>/classify/saveOneClassify.do" enctype="multipart/form-data" class="dropzone" method="post" id="dropzone">
								<div class="fallback">
									<input name="file" id="oneClassifypic" type="file" multiple="" />
								</div>
							</form>
						</div><!-- PAGE CONTENT ENDS -->
					</div><!-- /.col -->
					<div class="col-xs-12 center">
						<button class="btn" id="submit-all" disabled="disabled" style="margin:20px auto">保存</button>
					</div>
	 			</div>
		     </div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-md" id="modalClose" data-dismiss="modal">
			    	关闭
			    </button>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 添加分类model end -->
<!-- 修改分类model start -->
<div class="modal fade" id="editOneClassifyModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >修改分类</h4>
		     </div>
		     <div class="modal-body">
		     	<div class="row">
		     		<div class="col-xs-12 ">
		            	<form class="form-horizontal" id="editOneClassifyform" >
		           			<div class="form-group">
								<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="claName">分类名</label>
								<div class="col-xs-12 col-sm-8">
									<div class="clearfix">
										<input type="text" id="editOneClassifyName" name="claName" class="col-xs-12 col-sm-6" />
									</div>
								</div>
							</div>
		 				</form>
	 				</div>
	 				<div class="space-2"></div>
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->
						<input type="hidden" id="editOneClassifyId"></input>
						<div>
							<form action="<%=path%>/classify/editOneClassify.do" enctype="multipart/form-data" class="dropzone" method="post" id="editOneClassifyDropzone">
								<div class="fallback">
									<input name="file"  type="file" multiple="" />
								</div>
							</form>
						</div><!-- PAGE CONTENT ENDS -->
					</div><!-- /.col -->
					<div class="col-xs-12 center">
						<button class="btn" id="editOneClassify-submit-all" disabled="disabled" style="margin:20px auto">保存</button>
					</div>
	 			</div>
		     </div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-md" id="modalClose" data-dismiss="modal">
			    	关闭
			    </button>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 修改分类model end -->

<!-- 删除图片model begin -->
<div class="modal fade" id="viewOneClassifyPicModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title">预览删除图片</h4>
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
	initOneClassifyManager();
})
</script>

