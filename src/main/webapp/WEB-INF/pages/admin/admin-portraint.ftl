<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>上传头像</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
  	<!-- header start ================================================== -->
    <#include "/share/top.ftl" />
   	<!-- header end ================================================== -->
   
   <!-- main start ================================================== -->
   <div id="main">
   	<div class="row">
      <div class="container">
          <!-- back-left start ================================================== -->
          <div class="span2">
            <div class="back-left">
                <ul class="nav nav-tabs nav-stacked">
                	<li ><a href="/admin/home">个人中心<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/post/new">发表文章<i class="icon-chevron-right"></i></a></li>
                    <li><a href="/admin/posts">文章管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/drafts">草稿箱&nbsp;&nbsp;<span class="badge badge-info">${currentUser.blogs.draftsNum}</span><i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/post-categories">分类管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/comments">博客评论<i class="icon-chevron-right"></i></a></li>
                    <li><a href="/admin/messages">站内消息<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/reminds">我的通知<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/favorites">收藏管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/concern">关注管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/visits">访客记录<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/privacy">隐私管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/profile">个人资料<i class="icon-chevron-right"></i></a></li>
                    <li class="active"><a href="/admin/portraint">头像管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/password">登陆密码<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/email">登陆邮箱<i class="icon-chevron-right"></i></a></li>
               </ul>
            </div>
          </div>
          <!-- back-left end ================================================== -->
          
          
          <!-- back-right start ================================================== -->
          <div class="span9 board back-right">
          		<ul class="breadcrumb" style="margin-bottom: 5px;">
                  <#include "/share/admin-nav.ftl" />
                  <li class="active">修改头像</li>
                </ul>
                <div class="back-right-inner">
                    <!-- portraint start ================================================== -->
                    	<div id="portraint">
                          <table width="735" border="0" class="portraint_tb">
                              <tr>
                              	<td rowspan="2"><img src="${currentUser.portraint}" style="width:120px;height:120px;" class="img-polaroid thumbnail"/></td>
                                <td height="52">
                                	<div class="alert alert-info" style="width:300px;">
                                      选择新的头像图片: 支持 jpg,png,gif格式的图片.　　上传图片的大小不得超过1M.
                                    </div>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                    <form id="portraint_up_form" enctype="multipart/form-data" method="post" action="/admin/upload/portraint-upload">
                                        <span id='bt_upload'>
                                            <input name="imgFile" type="file" size="30" id="upload_file" />
                                            <input type="button" id='portraint_up_bt' value='上传' class="btn btn-primary"/> 上传后需要进行头像剪裁<br/>
                                        </span>
                                        <span id='bt_save' style='display:none'>
                                            <input type='button' value='保存选中区域' id='save_region_bt' class="btn btn-small btn-primary"/>
                                            <input type='button' value='保存全图' id='save_all_bt'/ class="btn btn-small btn-primary"> 选择"保存全图"头像可能会变形
                                        </span>
                                    </form>
                                    <form id='portraint_save_form' action="/admin/upload/portraint-save" method="post" style='display:none;'>
                                        <input type="hidden" name="filePath" id="portraintimgpath" value=""/>
                                        <input type='hidden' id='img_left' name='left' value='0'/>
                                        <input type='hidden' id='img_top' name='top' value='0'/>
                                        <input type='hidden' id='img_width' name='width' value='0'/>
                                        <input type='hidden' id='img_height' name='height' value='0'/>
                                    </form>
                                </td>
                              </tr>
                              <tr>
                                <td colspan="2">
                                    <img src="" id="crop_img"/>
                                </td>
                              </tr>
                            </table>
                        </div>
                    <!-- portraint end ================================================== -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
          <link href="http://bebop.qiniudn.com/styles/jquery.Jcrop.min.css" rel="stylesheet" type="text/css"/>
          <script src="http://bebop.qiniudn.com/scripts/jquery.form.js" type="text/javascript"></script>
		  <script src="http://bebop.qiniudn.com/scripts/jquery.Jcrop.min.js" type="text/javascript"></script>
          <script type="text/javascript">
			$(function() { 
				$('#portraint_up_bt').attr("disabled",false);
				$('#crop_img').hide();
				$('#upload_file').val("");
				function showCoords(c){
					$('#img_left').val(c.x);
					$('#img_top').val(c.y);
					$('#img_width').val(c.w);
					$('#img_height').val(c.h);
				};
				$('#portraint_up_bt').click(function() {
					$.jBox.tip("正在进行上传...", 'loading');
					$(this).attr("value","正在上传...");
					$(this).attr("disabled",true);
					$('#portraint_up_form').ajaxSubmit({ 
				    	dataType:'json',
				        success: function(data) {
				        	$.jBox.closeTip();
				        	if(data.error==0) {
				        		$("#up_err").html("");
				        		$('#bt_upload').hide();
				        		$('#bt_save').show();
				        		$('#crop_img').attr("src",data.message+Math.random());
				        		$('#crop_img').show();
				        		$('#crop_img').load(function(){
			        				jQuery.Jcrop($('#crop_img'),{
			        					setSelect:[10,10,200,200],
			        			        aspectRatio: 1,
			        			        onChange: showCoords,
			        					onSelect: showCoords
			        				});
			        			});
				        	}else{
				        		$.jBox.tip(data.message,'info');
				        		$('#portraint_up_bt').attr("value","上传");
				        		$('#portraint_up_bt').attr("disabled",false);
				        	}
				        } 
				    });
				});
				
				$('#save_region_bt').click(function(){
					$('#portraintimgpath').attr('value',$('#crop_img').attr('src'));
					$(this).attr("value","正在处理,请稍后...");
					$.jBox.tip("正在处理,请稍后...", 'loading');
					$(this).attr("disabled",true);
					$('#portraint_save_form').ajaxSubmit({ 
				    	dataType:'json',
				        success: function(data) {
					        if(data.status) {
					        	$.jBox.closeTip();
					        	$("#save_region_bt").attr("value",data.msg);
					        	$.jBox.tip(data.msg,'success', { timeout:1000,closed: function () { location.reload(); } });
					        }
				        } 
				    });
				});
				$('#save_all_bt').click(function(){
					$('#portraintimgpath').attr('value',$('#crop_img').attr('src'));
					$(this).attr("value","正在处理,请稍后...");
					$.jBox.tip("正在处理,请稍后...", 'loading');
					$(this).attr("disabled",true);
					$('#portraint_save_form').ajaxSubmit({ 
				    	dataType:'json',
				    	url:'/admin/upload/portraint-saveall',
				        success: function(data) {
				        	if(data.status) {
					        	$("#save_all_bt").attr("value",data.msg);
					        	$.jBox.tip(data.msg,'success', { timeout:1000,closed: function () { location.reload(); } });
					        }
				        } 
				    });
				});
			});
			</script>
      </div>
   </div>
   <!-- main end   ================================================== -->
   
   <!-- footer start   ================================================== -->
   <#include "/share/foot.ftl" />
   <!-- footer end   ================================================== -->
</body>
</html>
