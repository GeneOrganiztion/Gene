<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<script src="<%=path%>/pagejs/order/detectionOrder.js"></script>
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
		<li><a href="#">订单管理</a></li>
		<li class="active"><span>订单查询</span></li>
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
	        	订单搜索<i class="fa fa-plus-square"></i></a>
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
			  <form id="queryOrderListForm" class="form-horizontal" role="form">
				<!-- #section:elements.form -->
				<div class="form-group">
					<div class="col-md-3">
						<label class="col-sm-4 control-label no-padding-right" for="shortName">订单编号</label>
						<div class="col-sm-8">
							<input type="text" name="ordNum" class="form-control" />
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
						<button type="button" class="btn btn-md" onclick="queryOrder()">
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
	<div class="row">
		<div class="col-xs-12">
			<table id="grid-table"></table>

			<div id="grid-pager"></div>
			<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->
<!-- 订单详情model start -->
<div class="modal fade" id="orderDetialModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >订单详情</h4>
		     </div>
		     <div class="modal-body">
            	<form class="form-horizontal">
           			<div class="form-group">
						<label class="control-label col-xs-12 col-sm-2 ">订单编号:</label>
						<div class="col-xs-12 col-sm-4">
							<div class="clearfix">
								<input type="text" disabled="disabled" name="ordNum" class="col-xs-12 col-sm-12" />
							</div>
						</div>
						<label class="control-label col-xs-12 col-sm-2 ">订单价格:</label>
						<div class="col-xs-12 col-sm-4">
							<div class="clearfix">
								<input type="text" disabled="disabled" name="ordPrice" class="col-xs-12 col-sm-12" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-2 ">付款方式:</label>
						<div class="col-xs-12 col-sm-4">
							<div class="clearfix">
								<!-- <input type="text" disabled="disabled" name="ordPay" class="col-xs-12 col-sm-12" /> -->
								<select class="chosen-select form-control" disabled="disabled" name="ordPay" >
									<option value="1">微信支付</option>
									<option value="2">支付宝支付</option>
									<option value="3">其他支付</option>
								</select>
							</div>
						</div>
						<label class="control-label col-xs-12 col-sm-2 ">买家姓名:</label>
						<div class="col-xs-12 col-sm-4">
							<div class="clearfix">
								<input type="text" disabled="disabled" name="userName" class="col-xs-12 col-sm-12" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-2 ">买家电话:</label>
						<div class="col-xs-12 col-sm-4">
							<div class="clearfix">
								<input type="text" disabled="disabled" name="userPhone" class="col-xs-12 col-sm-12" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-2 ">买家地址:</label>
						<div class="col-xs-12 col-sm-10">
							<div class="clearfix">				               		
		               	 		<textarea class ="form-control" id="userAddress" rows="4" disabled="disabled"></textarea>
		                	</div>
						</div>
					</div>
				</form>				     
		     </div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-md" id="modalClose" data-dismiss="modal">
			    	关闭
			    </button>
			</div>
		</div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 订单详情model end -->
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
						</form>
		     		</div>
		     		<div class="space-2"></div>
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->
						<input type="hidden" id="mapOrderProductId"></input>
						<!-- <input type="hidden" id="subGridId"></input>
						<input type="hidden" id="subGridLine"></input> -->
						<!-- <input type="hidden" id="deleteReportId"></input> -->
						<div>
							<form action="<%=path%>/orderInfo/uploadReportPic.do" enctype="multipart/form-data" class="dropzone" method="post" id="dropzone">
								<div class="fallback">
									<input name="file" id="reportpic" type="file" multiple="" />
								</div>
							</form>
						</div><!-- PAGE CONTENT ENDS -->
					</div><!-- /.col -->
					<div class="col-xs-12 center">
						<button class="btn" id="submit-all" disabled="disabled" style="margin:20px auto">上传报告</button>
					</div>
					<!-- <div class="col-xs-12 center">
						<ul class="ace-thumbnails clearfix" id="gread">
							<li>
								<a href="../assets/images/gallery/image-1.jpg" title="请选择图片右键拷贝图片路径" data-rel="colorbox">
									<img width="150" height="150" alt="150x150" src="../assets/images/gallery/thumb-1.jpg" />
								</a>
							</li>
						</ul>
					</div> -->
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

<!-- 预览报告model begin -->
<div class="modal fade" id="viewReportPicModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title">预览报告</h4>
		     </div>
		     <div class="modal-body">
		     	<div class="row">
					<div class="col-xs-12 center">
						<div >
							<ul id="viewReportPicli">
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
<!-- 预览报告model end -->

<script type="text/javascript">
jQuery(function($) {
	initOrderManager();
})
</script>

