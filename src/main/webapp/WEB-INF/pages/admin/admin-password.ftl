<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>登录密码管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
  	<!-- header start ================================================== -->
	<#include "/share/top.ftl" />
    <!-- header end ================================================== -->
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
                    <li ><a href="/admin/posts">文章管理<i class="icon-chevron-right"></i></a></li>
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
                    <li class="active"><a href="/admin/password">登陆密码<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/email">登陆邮箱<i class="icon-chevron-right"></i></a></li>
               </ul>
            </div>
          </div>
          <!-- back-left end ================================================== -->
          
          
          <!-- back-right start ================================================== -->
          <div class="span9 board back-right">
          		<ul class="breadcrumb" style="margin-bottom: 5px;">
                  <#include "/share/admin-nav.ftl" />
                  <li class="active">密码修改</li>
                </ul>
                <div class="back-right-inner">
                    <!-- pwd start ================================================== -->
                    	<div class="box" style="padding:20px;">
							<form action="/admin/password-change" method="post" id="pw_chg_form" class="form-horizontal" style="margin-left:80px;padding-top:20px;">
					            <div class="control-group">
					              <label class="control-label muted" for="oldPassword">旧登录密码</label>
					              <div class="controls">
					                <input type="password" id="oldPassword" class="span4" name="oldPassword">
					                <a href="/user/password-reset" target="_blank">忘记登录密码?</a>
					              </div>
					            </div>
					            <div class="control-group">
					              <label class="control-label muted" for="newPassword">新密码</label>
					              <div class="controls">
					                <input type="password" id="newPassword" class="span4" name="newPassword">
					              </div>
					            </div>
					            <div class="control-group">
					              <label class="control-label muted" for="reNewPassword">再次输入新密码</label>
					              <div class="controls">
					                <input type="password" name="reNewPassword" id="reNewPassword" class="span4">
					                <span id="oK"></span>
					              </div>
					            </div>
					            <div class="control-group">
					              <div class="controls">
					                <input type="button" class="btn btn-primary" id="pw_chg_bt" value="保存修改">
					                <span id="oK"></span>
					              </div>
					            </div>
					          </form>
                        </div>
                    <!-- pwd end ================================================== -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
            <script src="http://bebop.qiniudn.com/scripts/jquery.form.js" type="text/javascript"></script>
			<script type="text/javascript">
				$(function() {
					$('#pw_chg_bt').attr("disabled",false);
					$('#oldPassword').focus();
					$('#pw_chg_form input[type=password]').val("");
					function FormField(name,label) {
						this.name=name;//表单元素属性名称
						this.label=label;//表单元素属性值
					}
					function verifyForm() {
						var list = new Array(new FormField('oldPassword','旧密码'),new FormField('newPassword','新密码'),new FormField('reNewPassword','重新输入的新密码'));
						for(var i=0;i<list.length;i++) {
							var formField = $("#"+list[i].name);
							if($.trim(formField.val()).length <5 || $.trim(formField.val()).length>12) {
								$.jBox.tip(list[i].label+"长度必须在5~12个字符之间",'info',{timeout:500});
								$("#"+list[i].name).focus();
								return false;
							}
						}
						if($.trim($("#"+list[1].name).val())!=$.trim($("#"+list[2].name).val())) {
							$.jBox.tip("新密码前后不一致!",'info',{timeout:500});
							$("#"+list[1].name).focus();
							return false;
						}
						return true;
					}
					$('#pw_chg_bt').click(function(){
						if(!verifyForm()) {
							return false;
						}
						$('#pw_chg_form').ajaxSubmit({ 
					    	dataType:'json',
					        success: function(data) {
					        	if(data.status) {
					        		$("#oK").fadeOut('fast',function() {
				        				$("#oK").html(data.msg);
				        				$("#oK").fadeIn('fast');
				        			});
					        	}else {
					        		$('#pw_chg_form input[type=password]').val("");
						        	$('#oldPassword').focus();
						        	$.jBox.tip(data.msg,'info',{timeout:500});
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
