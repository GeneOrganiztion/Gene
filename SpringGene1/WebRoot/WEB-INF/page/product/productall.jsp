<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<script src="<%=path%>/pagejs/product/productall.js"></script>
<!-- #section:basics/content.breadcrumbs -->

<style type="text/css">

   #productcontent img{
	width: 100%;
	}
</style>
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
		<li><a href="#">商品管理</a></li>
		<li class="active"><span>商品查询</span></li>
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
	        	商品搜索<i class="fa fa-plus-square"></i></a>
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
			  <form id="queryAssetForm" class="form-horizontal" role="form">
				<!-- #section:elements.form -->
				<div class="form-group">
				<div class="col-md-3">
						<label class="col-sm-4 control-label no-padding-right" for="shortName">商品ID</label>
						<div class="col-sm-8">
							<input type="text" name="productID" class="form-control" />
						</div>
					</div>
					<div class="col-md-3">
						<label class="col-sm-4 control-label no-padding-right" for="shortName">商品名称</label>
						<div class="col-sm-8">
							<input type="text" name="productName" class="form-control" />
						</div>
					</div>
					<div class="col-md-3">
						<label class="col-sm-4 control-label no-padding-right" for="shortName">商品是否上架</label>
						<div class="col-sm-8">

							<select name="productOnline" id="number"  class="form-control">
								<option value=></option>	
								<option value=true>是</option>
								<option value=false>否</option>
							</select>
							<!-- <input type="text" name="productOnline" class="form-control" /> -->
						</div>
					</div>
				
					
				</div>
				<div class="form-group">
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
					
						<div class="col-md-4" >
							<label class="col-sm-3 control-label no-padding-right" for="shortName">商品所属类别</label>
							<div class="col-sm-9">
								<div>
									<select id="loc_province" name="oneclassify" style="width:120px;"></select>
									<select id="loc_city" name="classify" style="width:120px;"></select>
									
									</div>
							</div>									
					</div>								
					
					<div class="col-md-2 text-right">
						<button type="button" class="btn btn-md" onclick="queryProduct()">
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
        <button type="button" onclick="SelectProduct()" class="btn-link"><i class="glyphicon glyphicon-log-in"></i> &nbsp;预览商品</button>
        <button type="button" onclick="editProduct()" class="btn-link"><i class="glyphicon glyphicon-log-out"></i> &nbsp;修改商品基本信息</button>
        <button type="button" onclick="deleteProduct()" class="btn-link"><i class="glyphicon glyphicon-remove"></i> &nbsp;删除商品</button>
    </div>
	<div class="row">
		<div class="col-xs-12">
			<table id="grid-table"></table>

			<div id="grid-pager"></div>
			<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->
<!-- 添加用户model start -->
<div class="modal fade" id="editProductModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >修改商品基本信息</h4>
		     </div>
		     <div class="modal-body">
            	<form class="form-horizontal " id="addProductform" >
            		<input type="text" name="id"  class="hide"/>
           			<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="proName">商品名称</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="text" name="name" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="proHead">商品标题</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="text" name="head" class="col-xs-12 col-sm-12" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="productPrice">商品原价</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="text" name="price" class="col-xs-12 col-sm-3" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="proRateprice">商品折扣价</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="text" name="rateprice"  class="col-xs-12 col-sm-3" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="proSum">商品库存</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="text" name="sum" class="col-xs-12 col-sm-2" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="genenum">商品基因检测个数</label>
						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="text" name="genenum" class="col-xs-12 col-sm-2" />
							</div>
						</div>
					</div>
					
					<div class="space-2"></div>
					
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="proOnline">商品是否上线</label>
						<div class="col-xs-12 col-sm-8">
								 <div>
									<select id="isonlinePro" name="isonline" class="input-medium" data-placeholder="请选择商品状态">
										<option value="1">是</option>
										<option value="0">否</option>
									</select>
								</div>		 			
						</div>
					</div>
					<div class="form-group">
						
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="shortName">商品所属类别</label>
							<div class="col-xs-12 col-sm-4">
								<div>
									<!-- <select id="classify1" name="classify" class="input-medium" data-placeholder="请选择商品所属类别...">
									</select> -->
									<select id="classify1" name="classify" class="js-states form-control">				
						
									</select>
								</div>
						</div>									
								
					</div>
					
					
				</form>
		     </div>
			<div class="modal-footer">
				<button type="button" class="btn btn-md" onclick="saveProduct()" >
			    	保存
			    </button>
			    <button type="button" class="btn btn-md" id="modalClose" data-dismiss="modal">
			    	关闭
			    </button>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 添加用户model end -->
<!-- 修改用户model start -->
<div class="modal fade" id="SelectProductModol" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:450px;">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >预览商品</h4>
		     </div>
		     <div class="modal-body" style="align-text:center" >
		     	<h4 id="proName" style="magin:5px auto;margin-left: 10px;"></h4>
  				<div class="col-sm-12 text-center">
					<div id="imageEditer"></div>
				</div>		
				
			<!-- 	
							
			              
				                    <div>
								       <span> <span style="color: #f0721f;font-size: 14px;">￥</span><span id="prodRatePrice" style="color: #f0721f;"></span></span>
							        </div>
							        <div >
								       <span class="font_size12">原价</span>
								       <span><span class="font_size12">￥</span><span id="prodPrice" style="text-decoration:line-through"></span></span>
							        </div>
					            </div>
				            </div>
				            <hr style="margin-top: 10px;margin-bottom: 10px;" />
				            <div>
					   <span class="pull-left">
				       <span>库存&nbsp;&nbsp;&nbsp;<span style="color: #000000;"></span id="productsum">&nbsp1101&nbsp件（获取库存）</span>
					   </span> -->
				
				
				
				<div class="col-sm-12 text-center">
					<div id="productcontent"></div>
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
<!-- 修改用户model end -->
<script type="text/javascript">

jQuery(function($) {
	initAdminManager();
})
</script>

