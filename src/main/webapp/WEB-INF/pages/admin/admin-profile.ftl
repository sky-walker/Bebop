<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>个人资料设置</title>
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
                    <li class="active"><a href="/admin/profile">个人资料<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/portraint">头像管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/password">登陆密码<i class="icon-chevron-right"></i></a></li>
                    <li><a href="/admin/email">登陆邮箱<i class="icon-chevron-right"></i></a></li>
               </ul>
            </div>
          </div>
          <!-- back-left end ================================================== -->
          
          
          <!-- back-right start ================================================== -->
          <div class="span9 board back-right">
          		<ul class="breadcrumb" style="margin-bottom: 5px;">
                  <#include "/share/admin-nav.ftl" />
                  <li class="active">个人信息设置</li>
                </ul>
                <div class="back-right-inner">
                    <!-- profile start ================================================== -->
                    <div class="box" style="padding:20px;">
						<form action='/admin/profile/edit' method='post' id="profile_edit_form" class="form-horizontal" style="padding-top:20px;">
				            <div class="control-group">
				              <label class="control-label muted">电子邮箱</label>
				              <div class="controls">
				                <span class="span3 uneditable-input"> ${user.email} </span>
				                <a href="/admin/email" target="_blank">»更改电子邮箱</a>
				              </div>
				            </div>
				            <div class="control-group">
				              <label class="control-label muted" for="nickname">昵称</label>
				              <div class="controls">
				                <input type="text" id="nickname" class="span3" name="nickname" value="${user.nickname?html}" maxlength="16"> <span class="muted">不能超过8个字</span>
				              </div>
				            </div>
				            <div class="control-group">
				              <label class="control-label muted">性别</label>
				              <div class="controls muted">
				                <input type="radio" name="gender" value="1" <#if user.gender==1>checked="checked"</#if>>猛男
								&nbsp;&nbsp;<input type="radio" name="gender" value="2" <#if user.gender==2>checked="checked"</#if>>靓女
				              </div>
				            </div>
				            <div class="control-group">
				              <label class="control-label muted" for="province">居住地区</label>
					              <div class="controls">
					                <select onchange="showcity(this.value, document.getElementById('city'));" name="province" id="province">
				                        <option value="">--请选择省份--</option>
				                        <option value="北京">北京</option>
				                        <option value="上海">上海</option>
				                        <option value="广东">广东</option>
				                        <option value="江苏">江苏</option>
				                        <option value="浙江">浙江</option>
				                        <option value="重庆">重庆</option>
				                        <option value="安徽">安徽</option>
				                        <option value="福建">福建</option>
				                        <option value="甘肃">甘肃</option>
				                        <option value="广西">广西</option>
				                        <option value="贵州">贵州</option>
				                        <option value="海南">海南</option>
				                        <option value="河北">河北</option>
				                        <option value="黑龙江">黑龙江</option>
				                        <option value="河南">河南</option>
				                        <option value="湖北">湖北</option>
				                        <option value="湖南">湖南</option>
				                        <option value="江西">江西</option>
				                        <option value="吉林">吉林</option>
				                        <option value="辽宁">辽宁</option>
				                        <option value="内蒙古">内蒙古</option>
				                        <option value="宁夏">宁夏</option>
				                        <option value="青海">青海</option>
				                        <option value="山东">山东</option>
				                        <option value="山西">山西</option>
				                        <option value="陕西">陕西</option>
				                        <option value="四川">四川</option>
				                        <option value="天津">天津</option>
				                        <option value="新疆">新疆</option>
				                        <option value="西藏">西藏</option>
				                        <option value="云南">云南</option>
				                        <option value="香港">香港特别行政区</option>
				                        <option value="澳门">澳门特别行政区</option>
				                        <option value="台湾">台湾</option>
				                        <option value="海外">海外</option>
				                    </select>
		                    	  	<script src="/scripts/getcity.js"></script><span class='nodisp'></span>　
		                    	  	<select name="city" id="city"></select>
		                    	  	<script language='javascript'>
			                    	  	$(function() {
			                    	  		init_province_and_city(document.getElementById('province'),'${user.province?html}',document.getElementById('city'),'${user.city?html}');init_select(document.getElementById('user_occ'),'${user.occupation?html}');init_select(document.getElementById('user_industry'),'${user.industry?html}');init_select(document.getElementById('user_workyear'),'${user.workyear?html}');
			                    	  	});
		                    	  	</script>
					        	</div>
				            </div>
				            
				            <div class="control-group">
				              <label class="control-label muted" for="time">加入时间</label>
				              <div class="controls fs-15  muted" style="margin-top:5px;">
				               	${user.regtime?string('yyyy-MM-dd HH:mm')}
				              </div>
				            </div>
				            
				            <div class="control-group">
				              <label class="control-label muted" for="signature">个性签名</label>
				              <div class="controls fs-15" style="margin-top:5px;">
				               	<textarea id="signature" maxlength="35" name="signature" style="width:300px;height:80px;" class="TEXT">${user.signature}</textarea>
				               	<p class="muted">个性签名不能长于于32个字 (正好可以做一首诗)</p>
				              </div>
				            </div>
				            
				            <div class="control-group">
				              <label class="control-label muted" for="user_occ">职业</label>
				              <div class="controls fs-15" style="margin-top:5px;">
				               	<select id="user_occ" name="occupation">
									<option value="">--请选择职业--</option>
									<option value="CEO/总裁">CEO/总裁</option>
									<option value="CTO/CIO/技术总监">CTO/CIO/技术总监</option>
									<option value="部门经理/部门主管">部门经理/部门主管</option>
									<option value="项目经理/项目主管">项目经理/项目主管</option>
									<option value="高级软件架构师">高级软件架构师</option>
									<option value="高级软件工程师">高级软件工程师</option>
									<option value="需求分析师">需求分析师</option>
									<option value="咨询师">咨询师</option>
									<option value="售前工程师">售前工程师</option>
									<option value="软件实施顾问">软件实施顾问</option>
									<option value="软件工程师">软件工程师</option>
									<option value="软件测试">软件测试</option>
									<option value="技术支持/维护工程师">技术支持/维护工程师</option>
									<option value="系统工程师SA">系统工程师SA</option>
									<option value="数据库DBA">数据库DBA</option>
									<option value="其他">其他</option>
								</select>
				              </div>
				            </div>
				            
				            <div class="control-group">
				              <label class="control-label muted" for="user_industry">行业</label>
				              <div class="controls fs-15" style="margin-top:5px;">
				               	<select id="user_industry" name="industry">
									<option value="">--请选择行业--</option>
									<option value="金融">金融</option>
									<option value="电信">电信</option>
									<option value="互联网">互联网</option>
									<option value="物流">物流</option>
									<option value="电子政务">电子政务</option>
									<option value="旅游">旅游</option>
									<option value="制造">制造</option>
									<option value="教育">教育</option>
									<option value="医疗">医疗</option>
									<option value="交通">交通</option>
									<option value="移动和手机应用">移动和手机应用</option>
									<option value="嵌入式">嵌入式</option>
									<option value="网络游戏">网络游戏</option>
									<option value="咨询">咨询</option>
									<option value="餐饮零售">餐饮零售</option>
									<option value="欧美外包">欧美外包</option>
									<option value="日本外包">日本外包</option>
									<option value="原厂商">原厂商</option>
									<option value="SOHO">SOHO</option>
									<option value="其他">其他</option>
								</select>
				              </div>
				            </div>
				            
				            <div class="control-group">
				              <label class="control-label muted" for="user_workyear">工作年限</label>
				              <div class="controls fs-15" style="margin-top:5px;">
				               	<select id="user_workyear" name="workyear">
									<option value="">--请选择工作年限--</option>
									<option value="在读学生">在读学生</option>
									<option value="应届毕业生">应届毕业生</option>
									<option value="一年以上">一年以上</option>
									<option value="两年以上">两年以上</option>
									<option value="三年以上">三年以上</option>
									<option value="五年以上">五年以上</option>
									<option value="八年以上">八年以上</option>
									<option value="十年以上">十年以上</option>
									<option value="十五年以上">十五年以上</option>
									<option value="二十年以上">二十年以上</option>
								</select>
				              </div>
				            </div>
				            
				            <div class="control-group">
				              <div class="controls">
				                <input type="button" class="btn btn-primary" id="profile_edit" value="保存修改">
				              </div>
				            </div>
				          </form>
                    </div>
                    <!-- profile end ================================================== -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
          <script src="http://bebop.qiniudn.com/scripts/jquery.form.js" type="text/javascript"></script>
          <script type="text/javascript">
			$(function() {
				$("#profile_edit").click(function() {
					$("#profile_edit").attr("value","正在保存修改...");
					$('#profile_edit').attr("disabled",true);
					$('#profile_edit_form').ajaxSubmit({dataType:'json',
				        success: function(data) {
				        	if(!data.status) {
				        		$.jBox.tip(data.msg,'info',{timeout:1000});
				        		$("#profile_edit").attr("value","保存修改");
				        		$('#profile_edit').attr("disabled",false);
				        	}else{
				        		$.jBox.tip("修改成功!",'success',{timeout:1000});
				        		location.href="/admin/profile";
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
