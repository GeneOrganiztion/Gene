<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<script src="<%=path%>/pagejs/report/reportList.js"></script>
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
		<li><a href="#">报告管理</a></li>
		<li class="active"><span>基因检测报告</span></li>
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
	        	报告搜索<i class="fa fa-plus-square"></i></a>
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
			  <form id="queryOneReport" class="form-horizontal" role="form">
				<!-- #section:elements.form -->
				<div class="form-group">
					<div class="col-md-3">
							<label class="col-sm-4 control-label no-padding-right" for="orderNumber">报告所属订单编号</label>
							<div class="col-sm-8">
								<input type="text" name="orderNumber" class="form-control" />
							</div>
					</div>
					<div class="col-md-3">
						<label class="col-sm-4 control-label no-padding-right" for="reportName">报告名称</label>
						<div class="col-sm-8">
							<input type="text" name="reportName" class="form-control" />
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
						<button type="button" class="btn btn-md" onclick="queryReport()">
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
        <button type="button" onclick="uploadReportPic()" class="btn-link"><i class="glyphicon glyphicon-remove"></i> &nbsp;修改报告</button>
    </div>
	<div class="row">
		<div class="col-xs-12">
			<table id="grid-table"></table>

			<div id="grid-pager"></div>
			<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->
<!-- 上传报告model begin -->
<div class="modal fade" id="uploadReportPicModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title">上传报告</h4>
		     </div>
		     <div class="modal-body">
		     	<div class="row">
		     		<div class="col-xs-12 ">
		     			<form class="form-horizontal">
			     			<div class="form-group">
								<label class="control-label col-xs-12 col-sm-2 ">报告名称:</label>
								<div class="col-xs-12 col-sm-4">
									<div class="clearfix">
										<input type="text" id="reportName" class="col-xs-12 col-sm-12" />
									</div>
								</div>
								<label class="control-label col-xs-12 col-sm-2 ">报告结果:</label>
								<div class="col-xs-12 col-sm-4">
									<div class="clearfix">
										<input type="text" id="reportResult" class="col-xs-12 col-sm-12" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-12 col-sm-2 ">状态:</label>
								<div class="col-xs-12 col-sm-4">
									<select class="input-medium" id= "reportState"  >
										<option value="0">待检测</option>
										<option value="1">样本污染</option>
										<option value="2">正在检测</option>
										<option value="3">报告生成</option>
									</select>
								</div>
							</div>
							<div class="form-group center">
								<button type="button" class="btn btn-md" onclick="saveReport()" >保存</button>
							</div>
						</form>
						<div class="hr hr-double hr-dotted hr18"></div>
		     		</div>
		     		<div class="space-2"></div>
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->
						<input type="hidden" id="reportId"></input>
						<div>
							<form action="<%=path%>/report/uploadReportPic.do" enctype="multipart/form-data" class="dropzone" method="post" id="dropzone">
								<div class="fallback">
									<input name="file" id="reportpic" type="file" multiple="" />
								</div>
							</form>
						</div><!-- PAGE CONTENT ENDS -->
					</div><!-- /.col -->
					<div class="col-xs-12 center">
						<button class="btn" id="submit-all" disabled="disabled" style="margin:20px auto">上传报告</button>
					</div>
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
<!-- 上传报告model end -->

<!-- 删除报告model begin -->
<div class="modal fade" id="viewOneReportPdfModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title">删除报告</h4>
		     </div>
		     <div class="modal-body">
		     	<div class="row">
					<div class="col-xs-12 center">
						<div >
							<ul id="viewOneOneReportPdf">
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
<!-- 删除报告model end -->


								<!-- delectOneReportPdf -->

<script type="text/javascript">
jQuery(function($) {
	initOneClassifyManager();
})
</script>
