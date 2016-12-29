<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<script src="<%=path%>/pagejs/product/producteditor.js"></script>
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
		<li class="active"><span>商品修改</span></li>
	</ul>
	<!-- /.breadcrumb -->
</div>
				<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div class="widget-box">
									<div class="widget-header widget-header-blue widget-header-flat">
										<h4 class="widget-title lighter">商品修改</h4>
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
															<span class="title">商品基本信息修改</span>
														</li>

														<li data-step="2">
															<span class="step">2</span>
															<span class="title">商品展示图片修改</span>
														</li>

														<li data-step="3">
															<span class="step">3</span>
															<span class="title">商品详情图片修改</span>
														</li>

													</ul>

													<!-- /section:plugins/fuelux.wizard.steps -->
												</div>

												<hr />

												<!-- #section:plugins/fuelux.wizard.container -->
												<div class="step-content pos-rel">
													<div class="step-pane active" data-step="1">
														<h3 class="lighter block green">请在下方选择需要修改的商品的名称</h3>
															   <!-- <div id="cssloader">
																		<i class="ace-icon fa fa-spinner fa-spin orange bigger-125" style="text-align:center; margin: 20px auto;width:100%;height:100%;font-size: 600% !important;"></i>
																		<div style="color: #ff6900 !important;text-align:center; margin: 0px auto;width:100%;height:100%" class="control-label col-xs-12 col-sm-3 no-padding-right" for="name">
																		加载中.......
																		</div>							
																</div> -->
																
																<!-- <form class="form-horizontal" id="validation-form" method="get"> -->
																	
																		<input type="text" name="product_id" id="product_id" class="hide"/>
																	<form class="form-horizontal">
																		<div class="form-group">
																			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="email">商品名称:</label>
																			<div class="col-xs-12 col-sm-2">
																				<select class="chosen-select form-control" id="form-field-select-3" data-placeholder="load product...">					
																				</select>
																			</div>
																			<div class="col-xs-12 col-sm-5">
																			</div>
																			<!-- <div style="text-align:center; margin: 0px auto;">
																				<button class="btn" onclick="editProduct()" style="margin:20px auto">加载该商品基本信息</button>
																			</div> -->
																		</div>
																	</form>
																
																<div style="text-align:center; margin: 0px auto;">
																		<div >
																			
																		</div>
																	<button class="btn" onclick="editProduct()" style="margin:20px auto;margin-top:0px;">加载该商品基本信息</button>
																</div>
																
															
														<div id="addloader" class="hide">
															<i class="ace-icon fa fa-spinner fa-spin orange bigger-125" style="text-align:center; margin: 20px auto;width:100%;height:100%;font-size:300% !important;"></i>
																		<div style="color: #ff6900 !important;text-align:center; margin: 0px auto;width:100%;height:100%" class="control-label col-xs-12 col-sm-3 no-padding-right" for="name">
																		商品信息加载中请耐心等待.......
																		</div>	
														</div>		
																<!-- </form> -->
														<form class="form-horizontal" id="validation-form" method="get">
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
																			<option value=""></option>
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
														
														
														
														
														
														
														
													
													</div>

													<div class="step-pane" data-step="2">
														<div>
															<div class="alert alert-success">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>

																<strong>
																	<i class="ace-icon fa fa-check"></i>
																	商品基本信息修改成功!
																</strong>
																下方为已上传商品展示图片,点击可删除
																<br />
																
															</div>

															<div style="width:450px; margin:10px auto">
																
																<div id="imageEditer"></div>
																
															</div>
															
															
													<!-- 		<div class="alert alert-danger">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>

																<strong>
																	<i class="ace-icon fa fa-times"></i>
																	警告!
																</strong>
																	商品展示图片未上传将会导致商品搜索失败,即使商品已上架也不会显示
																<br />
															</div> -->
															<div class="alert alert-warning">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>
																<strong>温馨提示!</strong>
																商品展示图片名称请不要携带中文,商品上传成功后会有提示信息
																<br />
															</div>
															
														<!-- 	<div class="alert alert-info">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>
																<strong>展示预览!</strong>
																商品展示图片上传的第一张图片将作为商品展示的封面图片,商品展示图片最多上传四张
																<br />
															</div>  -->
															
															<form  class="dropzone" id="dropzone">
																<div class="fallback">
																	<input name="file" type="file" id="dropzonefile"/>
																</div>
																
															</form>
															
															<button class="btn" id="submit-product" disabled="disabled" style="margin:20px auto">点击开始上传商品展示图片</button>
														</div>
													</div>

													<div class="step-pane" data-step="3">
													
														<div class="alert alert-success">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>

																<strong>
																	<i class="ace-icon fa fa-check"></i>
																	商品展示图片添加成功!
																</strong>
																请将编辑好的商品详情图片拖拽到下方框图或者点击向上箭头,上传成功会在下方显示出已上传的商品详情图片
																<br />
															</div>
													
														<div class="alert alert-danger">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>

																<strong>
																	<i class="ace-icon fa fa-times"></i>
																	警告!
																</strong>
																	商品详情请在编辑框中进行,插入的图片要先上传到云端再将商品的路径粘帖进来。不要将本地图片直接上传
																<br />
															</div>
															<div class="alert alert-warning"  style="margin-bottom:20px">
																<button type="button" class="close" data-dismiss="alert">
																	<i class="ace-icon fa fa-times"></i>
																</button>
																<strong>温馨提示!</strong>
																商品详情图片名称请不要携带中文,图片上传上传成功后会在下方显示，请鼠标移动到图片上点击右键拷贝图片路径。点击编辑器的Insert picture按钮将图片路径复制进来插入图片
																<br />
															</div>								
														<div class="center">
															<form action="../product/UploadDetailImage.do" class="dropzone" id="dropzone1">
																<div class="fallback">
																	<input name="file" type="file" id="dropzonefile"/>
																</div>
																
															</form>
															<button class="btn" id="submit-all" disabled="disabled" style="margin:20px auto">点击开始上传商品详情图片</button>
															
														<div class="alert alert-info">
																<button type="button" class="close" data-dismiss="alert"  >
																	<i class="ace-icon fa fa-times"></i>
																</button>
																<strong>商品详情已上传图片预览,第一张为测试图片!</strong>
																<br />
															</div>	
															
															<ul class="ace-thumbnails clearfix" id="gread">
																		<!-- #section:pages/gallery -->
																		<li>
																			<a href="../assets/images/gallery/image-1.jpg" title="请选择图片右键拷贝图片路径" data-rel="colorbox">
																				<img width="150" height="150" alt="150x150" src="../assets/images/gallery/thumb-1.jpg" />
																			</a>
																		
																		</li>
																		<!-- /section:pages/gallery.caption -->

																
																	</ul>
														<!-- PAGE CONTENT BEGINS -->
																<h4 class="header green clearfix">
																	商品详情页面请在此编辑,确保图片已经上传至云端
																</h4>
																
																	<div class="wysiwyg-editor" id="editor1" style="max-height: 800px; height: 800px; border: 1px solid #BBC0CA; width: 450px;
	    															margin: 0px auto;">
    															</div>
    															
    															
    										
    															<button class="btn" onclick="geteditor()">显示全部内容</button>
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
														
			
			
			
			
								<!-- 添加商品model end -->
<script type="text/javascript">
jQuery(function($) {
	initproducteditorManager();
})
</script>