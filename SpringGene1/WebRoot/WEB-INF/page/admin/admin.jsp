<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();//asdadasdadsasd
	//asdadasdsadasfasfasf
	////dfasfsdfsdaf
%>
<script src="<%=path%>/pagejs/admin/admin.js"></script>
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
		<li><a href="#">系统管理</a></li>
		<li class="active"><span>用户管理</span></li>
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
	        	用户搜索<i class="fa fa-plus-square"></i></a>
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
						<label class="col-sm-4 control-label no-padding-right" for="shortName">姓名</label>
						<div class="col-sm-8">
							<input type="text" name="userName" class="form-control" />
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
					<div class="col-md-6 text-right">
						<button type="button" class="btn btn-md" onclick="queryAdmin()">
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
        <button type="button" onclick="addAdmin()" class="btn-link"><i class="glyphicon glyphicon-log-in"></i> &nbsp;添加</button>
        <button type="button" onclick="editAdmin()" class="btn-link"><i class="glyphicon glyphicon-log-out"></i> &nbsp;修改</button>
        <button type="button" onclick="deleteAdmin()" class="btn-link"><i class="glyphicon glyphicon-remove"></i> &nbsp;删除用户</button>
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
<div class="modal fade" id="addAdminModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >添加用户</h4>
		     </div>
		     <div class="modal-body">
            	<form class="form-horizontal " id="addAdminform" >
           			<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="username">用户名</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="text" name="username" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="realname">真实名</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="text" name="realname" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="phone">手机</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="tel" name="phone" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="email">email:</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="email" name="email"  class="col-xs-12 col-sm-7" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="password">密码:</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="password" name="password" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="password2">确认密码:</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="password" name="password2" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
				</form>
		     </div>
			<div class="modal-footer">
				<button type="button" class="btn btn-md" onclick="saveAdmin()" >
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
<div class="modal fade" id="editAdminModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
             <div class="modal-header">
             	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" >修改用户</h4>
		     </div>
		     <div class="modal-body">
            	<form class="form-horizontal " id="editAdminform" >
            		<input type="hidden" name="id" class="col-xs-12 col-sm-6" />
           			<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="username">用户名</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="text" name="username" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="realname">真实名</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="text" name="realname" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="phone">手机</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="tel" name="phone" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="email">email:</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="email" name="email"  class="col-xs-12 col-sm-7" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="password">密码:</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="password" name="password" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
					<div class="space-2"></div>
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-4 no-padding-right" for="password2">确认密码:</label>

						<div class="col-xs-12 col-sm-8">
							<div class="clearfix">
								<input type="password" name="password2" class="col-xs-12 col-sm-6" />
							</div>
						</div>
					</div>
				</form>				     
		     </div>
			<div class="modal-footer">
				<button type="button" class="btn btn-md" onclick="editAndSaveAdmin()">
			    	保存
			    </button>
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

