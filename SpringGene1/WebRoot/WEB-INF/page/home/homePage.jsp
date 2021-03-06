<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<script src="<%=path%>/pagejs/home/homepage.js"></script> 
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
		<li><a href="#">系统管理</a></li>
		<li class="active"><span>首页管理</span></li>
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
	        	首页滚动图搜索<i class="fa fa-plus-square"></i></a>
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
			  <form id="queryHomePageForm" class="form-horizontal" role="form">
				<!-- #section:elements.form -->
				<div class="form-group">
					<div class="col-md-3">
							<label class="col-sm-4 control-label no-padding-right" for="HomePageid">滚动图ID</label>
							<div class="col-sm-8">
								<input type="text" name="HomePageid" class="form-control" />
							</div>
					</div>
					<div class="col-md-3">
						<label class="col-sm-4 control-label no-padding-right" for="HomeName">首页滚动图名称</label>
						<div class="col-sm-8">
							<input type="text" name="HomeName" class="form-control" />
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
						<button type="button" class="btn btn-md" onclick="queryHomePage()">
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
        <button type="button" onclick="addHomePage()" class="btn-link"><i class="glyphicon glyphicon-log-in"></i> &nbsp;添加首页滚动图</button>
        <button type="button" onclick="editHomePage()" class="btn-link"><i class="glyphicon glyphicon-log-out"></i> &nbsp;修改首页滚动图</button>
        <button type="button" onclick="deleteHomePage()" class="btn-link"><i class="glyphicon glyphicon-remove"></i> &nbsp;删除首页滚动图</button>
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
<div class="modal fade" id="addHomePageModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >添加首页滚动图</h4>
		     </div>
		     <div class="modal-body">
		     	<div class="row">
		     		<div class="col-xs-12 ">
		            	<form class="form-horizontal" id="addOneClassifyform" >
		            	
		            		<div class="alert alert-danger" style="margin-bottom:20px">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>

									<strong>
										
												上传图片宽高比为2:1!				
									</strong>
									   图片链接务必前面加上http
								<br/>
							
							</div> 
		            		
		           			<div class="form-group">
								<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="HomeName">首页滚动图名称</label>
								<div class="col-xs-12 col-sm-8">
									<div class="clearfix">
										<input type="text" id="HomeName" name="HomeName" class="col-xs-12 col-sm-6" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="HomeUrl">首页滚动图链接</label>
								<div class="col-xs-12 col-sm-8">
									<div class="clearfix">
										<input type="text" id="HomeUrl" name="HomeUrl" class="col-xs-12 col-sm-10" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="HomeComment">首页滚动图备注</label>
								<div class="col-xs-12 col-sm-8">
									<div class="clearfix">
										<input type="text" id="HomeComment" name="HomeComment" class="col-xs-12 col-sm-6" />
									</div>
								</div>
							</div>
		 				</form>
	 				</div>
	 				<div class="space-2"></div>
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->
						<input type="hidden" id="HomePageId"></input>
						<div>
							<form action="<%=path%>/home/saveHomePage.do" enctype="multipart/form-data" class="dropzone" method="post" id="dropzone">
								<div class="fallback">
									<input name="file" id="HomePagepic" type="file" multiple="" />
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
<div class="modal fade" id="editHomePageModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >修改滚动图</h4>
		     </div>
		     <div class="modal-body">
		     	<div class="page-count">
		     	<div class="row">
		     		<div class="col-xs-12 ">
		            	<form class="form-horizontal" id="editOneClassifyform" >
		           			<div class="form-group">
								<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="editHomePageName">滚动图名称</label>
								<div class="col-xs-12 col-sm-8">
									<div class="clearfix">
										<input type="text" id="editHomePageName" name="editHomePageName" class="col-xs-12 col-sm-6" />
									</div>
								</div>
							</div>
							<div class="form-group">
									<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="editHomeUrl">滚动图链接</label>
									<div class="col-xs-12 col-sm-8">
										<div class="clearfix">
											<input type="text" id="editHomeUrl" name="editHomeUrl" class="col-xs-12 col-sm-10" />
										</div>
									</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="editHomeComment">滚动图备注</label>
								<div class="col-xs-12 col-sm-8">
									<div class="clearfix">
										<input type="text" id="editHomeComment" name="editHomeComment" class="col-xs-12 col-sm-6" />
									</div>
								</div>
							</div>
		 				</form>
	 				</div>
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->
						<input type="hidden" id="editHomePageId"></input>
						<div>
							<form action="<%=path%>/home/editHomePage.do" enctype="multipart/form-data" class="dropzone" method="post" id="editHomePageDropzone">
								<div class="fallback">
									<input name="file"  type="file" multiple="" />
								</div>
							</form>
						</div><!-- PAGE CONTENT ENDS -->
					</div><!-- /.col -->
					<div class="col-xs-12 center">
						<button class="btn" id="editOneClassify-submit-all"  style="margin:20px auto">保存</button>
					</div>
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
<div class="modal fade" id="viewHomePageModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
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
							<ul id="viewHomePagePic">
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
	initHomePageManager();
})
</script>