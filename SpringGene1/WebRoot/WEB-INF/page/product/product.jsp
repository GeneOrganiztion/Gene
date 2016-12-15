<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<script src="<%=path%>/pagejs/product/product.js"></script>
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
		<li class="active"><span>商品上传</span></li>
	</ul>
	<!-- /.breadcrumb -->
			
			</div>
				<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div class="widget-box">
									<div class="widget-header widget-header-blue widget-header-flat">
										<h4 class="widget-title lighter">商品上传</h4>
									</div>							
									<div class="widget-body">
										<div class="widget-main">
											<!-- #section:plugins/fuelux.wizard -->
											<div id="fuelux-wizard-container">
												<div>
													<!-- #section:plugins/fuelux.wizard.steps -->
													<ul class="steps">
														<li data-step="1" class="active">
															<span class="step">1</span>
															<span class="title">商品基本信息</span>
														</li>

														<li data-step="2">
															<span class="step">2</span>
															<span class="title">商品展示图片上传</span>
														</li>

														<li data-step="3">
															<span class="step">3</span>
															<span class="title">商品详情图片上传</span>
														</li>

													</ul>

													<!-- /section:plugins/fuelux.wizard.steps -->
												</div>

												<hr />

												<!-- #section:plugins/fuelux.wizard.container -->
												<div class="step-content pos-rel">
													<div class="step-pane active" data-step="1">
														<h3 class="lighter block green">请在下方填写商品的基本信息</h3>
															   <div id="cssloader">
																   <div class="loader-inner ball-spin-fade-loader" style="margin: 40px auto;width:0%;height:1px;">
																      <div></div>
																      <div></div>
																      <div></div>
																      <div></div>
																      <div></div>
																      <div></div>
																      <div></div>
																      <div></div>
																    </div>
																    <label  class="control-label col-xs-12 col-sm-12 no-padding-right" style="color: #ff6900;text-align:center; margin: 20px auto;">加载中...</label>
																 </div>
														<form class="form-horizontal" id="validation-form" method="get">
														
															<div class="form-group" >
																<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="name">商品名称:</label>

																<div class="col-xs-12 col-sm-9">
																	<div class="clearfix">
																		<input type="text" id="name" name="name" class="col-xs-4 col-sm-3" />
																	</div>
																</div>
															</div>
															<div class="form-group">
																<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="email">商品标题:</label>
																<div class="col-xs-12 col-sm-9">
																	<div class="clearfix">
																		<input type="text" name="head" id="head" class="col-xs-12 col-sm-6" />
																	</div>
																</div>
															</div>
															
															
									
															<div class="hr hr-dotted" style="margin-bottom: 20px;"></div>
															<div class="form-group">
																<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="email">商品原价:</label>
																<div class="col-xs-3 col-sm-2">
																	<div class="clearfix">
																		<input type="text" name="price" id="price" class="col-xs-12 col-sm-6" />
																	</div>
																</div>
															</div>
															
															<div class="form-group">
																<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="email">商品折扣价:</label>
																<div class="col-xs-3 col-sm-2">
																	<div class="clearfix">
																		<input type="text" name="rateprice" id="rateprice" class="col-xs-12 col-sm-6" />
																	</div>
																</div>
															</div>
															
															<div class="form-group">
																<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="email">商品库存:</label>
																<div class="col-xs-3 col-sm-2">
																	<div class="clearfix">
																		<input type="text" name="sum" id="sum" class="col-xs-12 col-sm-6" />
																	</div>
																</div>
															</div>
															

															<div class="space-2" ></div>

															<div class="form-group">
																<label class="control-label col-xs-12 col-sm-3 no-padding-right">商品是否上线</label>

																<div class="col-xs-12 col-sm-9">
																	<div>
																		<label class="line-height-1 blue">
																			<input name="isonline" value="1" type="radio" class="ace" />
																			<span class="lbl">是</span>
																		</label>
																	</div>

																	<div>
																		<label class="line-height-1 blue">
																			<input name="isonline" value="0" type="radio" class="ace" />
																			<span class="lbl">否</span>
																		</label>
																	</div>
																</div>
															</div>

															<div class="hr hr-dotted" style="margin-bottom: 20px;"></div>

															<div class="form-group">
																<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="state">商品所属类别</label>
																<div class="col-xs-8 col-sm-9">
																	<div>
																		<select id="classify" name="classify" class="input-medium" data-placeholder="请选择商品所属类别...">
																		</select>
																	</div>
																</div>
																
															</div>

															<div class="space-2"></div>

															<div class="form-group">
																<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="comment">商品备注</label>
																<div class="col-xs-12 col-sm-9">
																	<div class="clearfix">
																		<textarea class="input-xlarge" name="comment" id="comment"></textarea>
																	</div>
																</div>
															</div>
														</form>
														
														<div id="addloader" class="hide">
															<div class="loader-inner ball-spin-fade-loader" style="margin: 40px auto;width:0%;height:2px;">
																      <div></div>
																      <div></div>
																      <div></div>
																      <div></div>
																      <div></div>
																      <div></div>
																      <div></div>
																      <div></div>
															</div>
															<label  class="control-label col-xs-12 col-sm-12 no-padding-right" style="color: #ff6900;text-align:center; margin: 20px auto;">商品信息入库中请耐心等待....</label>
														</div>
													</div>

													<div class="step-pane" data-step="2">
														<div>
															<div class="alert alert-success">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>

																<strong>
																	<i class="ace-icon fa fa-check"></i>
																	Well done!
																</strong>

																You successfully read this important alert message.
																<br />
															</div>

															<div class="alert alert-danger">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>

																<strong>
																	<i class="ace-icon fa fa-times"></i>
																	Oh snap!
																</strong>

																Change a few things up and try submitting again.
																<br />
															</div>

															<div class="alert alert-warning">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>
																<strong>Warning!</strong>

																Best check yo self, you're not looking too good.
																<br />
															</div>

															<div class="alert alert-info">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>
																<strong>Heads up!</strong>

																This alert needs your attention, but it's not super important.
																<br />
															</div>
															
															<form action="../product/UploadImage.do" class="dropzone" id="dropzone">
															
																<div class="fallback">
																	<input name="file" type="file" id="dropzonefile"/>
																</div>
															</form>
														</div>
													</div>

													<div class="step-pane" data-step="3">
														<div class="center">
															<h3 class="blue lighter">This is step 3</h3>
														</div>
													</div>
									
									
												</div>

												<!-- /section:plugins/fuelux.wizard.container -->
											</div>

											<hr />
											<div class="wizard-actions">
												<!-- #section:plugins/fuelux.wizard.buttons -->
												<button class="btn btn-prev">
													<i class="ace-icon fa fa-arrow-left"></i>
													Prev
												</button>

												<button class="btn btn-success btn-next" data-last="Finish">
													Next
													<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
												</button>

												<!-- /section:plugins/fuelux.wizard.buttons -->
											</div>

											<!-- /section:plugins/fuelux.wizard -->
										</div><!-- /.widget-main -->
									</div><!-- /.widget-body -->
								</div>

								<div id="modal-wizard" class="modal">
									<div class="modal-dialog">
										<div class="modal-content">
											<div id="modal-wizard-container">
												<div class="modal-header">
													<ul class="steps">
														<li data-step="1" class="active">
															<span class="step">1</span>
															<span class="title">Validation states</span>
														</li>

														<li data-step="2">
															<span class="step">2</span>
															<span class="title">Alerts</span>
														</li>

														<li data-step="3">
															<span class="step">3</span>
															<span class="title">Payment Info</span>
														</li>

														<li data-step="4">
															<span class="step">4</span>
															<span class="title">Other Info</span>
														</li>
													</ul>
												</div>

												<div class="modal-body step-content">
													<div class="step-pane active" data-step="1">
														<div class="center">
															<h4 class="blue">Step 1</h4>
														</div>
													</div>

													<div class="step-pane" data-step="2">
														<div class="center">
															<h4 class="blue">Step 2</h4>
														</div>
													</div>

													<div class="step-pane" data-step="3">
														<div class="center">
															<h4 class="blue">Step 3</h4>
														</div>
													</div>

													<div class="step-pane" data-step="4">
														<div class="center">
															<h4 class="blue">Step 4</h4>
														</div>
													</div>
												</div>
											</div>

											<div class="modal-footer wizard-actions">
												<button class="btn btn-sm btn-prev">
													<i class="ace-icon fa fa-arrow-left"></i>
													Prev
												</button>

												<button class="btn btn-success btn-sm btn-next" data-last="Finish">
													Next
													<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
												</button>

												<button class="btn btn-danger btn-sm pull-left" data-dismiss="modal">
													<i class="ace-icon fa fa-times"></i>
													Cancel
												</button>
											</div>
										</div>
									</div>
								</div><!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					<!-- </div>pagecontent -->

	<!-- inline scripts related to this page -->
	
<!-- 添加商品model end -->
<script type="text/javascript">
jQuery(function($) {
	initProductManager();
})
</script>
