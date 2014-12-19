<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>邮箱管理</title>
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
                    <li ><a href="/admin/messages">站内消息<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/reminds">我的通知<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/favorites">收藏管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/concern">关注管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/visits">访客记录<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/privacy">隐私管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/profile">个人资料<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/portraint">头像管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/password">登陆密码<i class="icon-chevron-right"></i></a></li>
                    <li class="active"><a href="/admin/email">登陆邮箱<i class="icon-chevron-right"></i></a></li>
               </ul>
            </div>
          </div>
          <!-- back-left end ================================================== -->
          
          
          <!-- back-right start ================================================== -->
          <div class="span9 board back-right">
          		<ul class="breadcrumb" style="margin-bottom: 5px;">
                  <#include "/share/admin-nav.ftl" />
                  <li class="active">更换邮箱</li>
                </ul>
                <div class="back-right-inner">
                    <!-- email start ================================================== -->
                    	<div class="box" style="padding:20px;">
                    		<div class="alert alert-info divcenter" style="width:450px;">
								 系统将将向您的新邮箱发送一封验证邮件,  请点击邮件中的链接来完成修改.<br>
								 如果没收到邮件,请确认是否填写正确的邮箱地址,   或者在垃圾箱里检查一下.
							</div>
							<form class="form-horizontal" style="margin-left:80px;padding-top:20px;">
					            <div class="control-group">
					              <label class="control-label muted">当前邮箱</label>
					              <div class="controls">
					                <span class="span4 uneditable-input"> ${currentUser.email} </span>
					              </div>
					            </div>
					            <div class="control-group">
					              <label class="control-label muted" for="newEmail">新邮箱</label>
					              <div class="controls">
					                <input type="text" id="newEmail" class="span4" name="email">
					              </div>
					            </div>
					            <div class="control-group">
					              <label class="control-label muted" for="vcode">验证码</label>
					              <div class="controls">
					                <input type="text" name="vcode" id="vcode" style="width:110px">
					                <span id="oK"></span>
					              </div>
					            </div>
					            <div class="control-group">
					              <div class="controls">
					                <span style='border: 2px solid #CCC;display:inline-block;'>
						    			<a href="javascript:change_vcode()" id="vcode_change">
											<img src="/user/captcha" title="点击更换图片" id="v_code_image" style="border: 0px;">
								  		</a>
								  		<script type="text/javascript">
											function change_vcode() {
												$('#v_code_image').attr('src',"/user/captcha?r="+Math.random(1000));
											}
									  	</script>
						    		</span>
					              </div>
					            </div>
					            <div class="control-group">
					              <div class="controls">
					                <input type="button" class="btn btn-primary" id="chg_email_bt" value="保存修改">
					              </div>
					            </div>
					          </form>
                        </div>
                    <!-- email end ================================================== -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
          <script type="text/javascript">
			$(function() {
				$('#chg_email_bt').attr("disabled",false);
				$('#newEmail').focus();
				$('#chg_email_bt').click(function() {
					$("#chg_email_bt").attr("disabled",true);
					$("#chg_email_bt").attr("value","正在操作...");
					var email = $.trim($("#newEmail").val());
					var vcode= $.trim($("#vcode").val());
					ajax_post_json("/admin/prepare-change-email",{email:email,vcode:vcode},function(data) {
						if(data.status) {
							$('#chg_email_bt').attr("disabled",true);
		        			$('#newEmail').html("");
		        			$("#oK").fadeOut('fast',function() {
		        				$("#chg_email_bt").attr("value","操作成功...");
		        				$("#oK").html(data.msg);
		        				$("#oK").fadeIn('fast');
		        			});
						}else {
							$("#chg_email_bt").attr("value","保存修改");
							$('#chg_email_bt').attr("disabled",false);
							$.jBox.tip(data.msg,'info',{timeout:500});
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
