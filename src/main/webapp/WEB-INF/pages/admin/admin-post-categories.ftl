<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>分类管理</title>
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
                    <li ><a href="/admin/posts">文章管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/drafts">草稿箱&nbsp;&nbsp;<span class="badge badge-info">${currentUser.blogs.draftsNum}</span><i class="icon-chevron-right"></i></a></li>
                    <li class="active" ><a href="/admin/post-categories">分类管理<i class="icon-chevron-right"></i></a></li>
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
                    <li ><a href="/admin/email">登陆邮箱<i class="icon-chevron-right"></i></a></li>
               </ul>
            </div>
          </div>
          <!-- back-left end ================================================== -->
          
          
          <!-- back-right start ================================================== -->
          <div class="span9 board back-right">
          		<ul class="breadcrumb" style="margin-bottom: 5px;">
                  <#include "/share/admin-nav.ftl" />
                  <li class="active">文章分类	</li>
                </ul>
                <div class="back-right-inner">
                	<div class="input-append" style="text-align:center;">
                      <input type="text"  id="c_name"  class="span3" name="name" placeholder="此处输入分类名称" maxlength="20">
                      <button id="category_add" class="btn btn-primary">添加新分类</button>
                    </div>
                    <div class="divbord mg-t-30">
	                    <table class="table table-hover tbfix">
	                      <thead>
	                        <tr class="fc-008">
	                          <th width="50%">标题</th>
	                          <th width="19%">文章数量</th>
	                          <th width="19%">管　理</th>
	                          <th width="12%">排序</th>
	                        </tr>
	                      </thead>
	                      <tbody>
	                      <#assign pagesize=pager?size />
	        			  <#list pager as entity>
	                        <tr id="tr_${entity.id}" name="${entity.id}">
	                          <td>
	                          	<a href="javascript:category_edit(${entity.id})" id="c_text_${entity.id}" class="category_name" title="修改分类名称">${entity.name}</a>
	                          </td>
	                          <td>${entity.postNum}</td>
	                          <td>
	                          	<a class="category_edit" href="javascript:category_edit(${entity.id})"><i class="icon-edit"></i></a> |
		          				<a class="category_del" href="javascript:;" name="${entity.id}_${entity.postNum}"><i class="icon-remove"></i></a>
	                          </td>
	                          <td>
	                          	<!-- 如果只有一个元素,就不显示排序按钮 -->
					          	<#if (pagesize==1) >
					          		<#elseif (pagesize>1)>
					          		<#if (entity_index==0)>
					          			<a href="/admin/category-sort-down?currentCategoryId=${entity.id}" title="下移"><i class="icon-arrow-down"></i></a>
					          			<#elseif (entity_index==pagesize-1)>
					          				<a href="/admin/category-sort-up?currentCategoryId=${entity.id}" title="上移"><i class=" icon-arrow-up"></i></a>
					          			<#else>
						          			<a href="/admin/category-sort-up?currentCategoryId=${entity.id}" title="上移"><i class="icon-arrow-up"></i></a>
							          		<a href="/admin/category-sort-down?currentCategoryId=${entity.id}" title="下移"><i class="icon-arrow-down"></i></a>
					          		</#if>
					          	</#if>
	                          </td>
	                        </tr>
	                        <tr id="hidden_${entity.id}" class="hide" bgcolor="#ffff99">
					          <td colspan="2">
					            <input type="hidden" name="id" value="${entity.id}"/>
				            	<div class="input-append">
				            	  <input type="text" name="name" value="${entity.name}" id="hidden_c_name_${entity.id}"  class="c_edit_name_${entity.id} span3" maxlength="20"/>
								  <button class="btn btn-primary category_edit_do " name="${entity.id}"/>修改
								  <button id="category_edit_cancel_${entity.id}" class=" btn btn-primary category_edit_cancel" name="${entity.id}" />取消
								</div>
					          </td>
					          <td colspan="2" bgcolor="#ffff99" style="color: red;font-weight: bold;"><span class="edit_msg_${entity.id}"></span></td>
					        </tr>
	                      </#list>
	                      </tbody>
	                    </table>
                    </div>
                </div>
          </div>
          <!-- back-right end ================================================== -->
          <script type="text/javascript">
			$(function(){
				$(".category_edit_cancel").click(function(){
					var id = $(this).attr("name");
					$("#hidden_"+id).hide();
					$("#tr_"+id).fadeIn('fast');
					$(".edit_msg_"+id).html("");
				});
				
				//添加分类
				$("#category_add").click(function() {
					var c_name = $.trim($("#c_name").attr('value'));
					$.jBox.tip("正在进行操作...", 'loading');
					if(c_name=="") {
						$.jBox.tip("分类名称不能为空！",'error',{timeout:1000});
						$("#c_name").focus();
					}else {
						ajax_post_json("/admin/category-add",{name:c_name},function(data) {
							if(!data.status) {
				        		$.jBox.closeTip();
				        		$("#c_name").focus();
				        		$.jBox.tip(data.msg,'info',{timeout:1000});
				        	}else{
				        		$.jBox.tip("添加成功!",'success',{timeout:300,closed:function(){location.reload();}});
				        	}			
						});
					}
				});
				
				//修改分类
				$(".category_edit_do").click(function() {
					var c_id = $(this).attr('name');
					var c_old_name = $("#c_text_"+c_id).text();
					var c_name = $.trim($(".c_edit_name_"+c_id).attr('value'));
					if(c_name=="") {
						$.jBox.tip("分类名称不能为空！",'info',{timeout:1000});
						$(".c_edit_name_"+c_id).focus();
					}else {
						if(c_old_name==c_name) {
							$("#category_edit_cancel_"+c_id).trigger("click");
						}else {
							$.jBox.tip("正在进行操作...", 'loading');
							$(this).attr("disabled",true);
							$(this).attr("value","正在提交...");
							ajax_post_json("/admin/category-edit",{id:c_id,name:c_name},function(data) {
								if(!data.status) {
					        		$.jBox.closeTip();
					        		$(".c_edit_name_"+c_id).focus();
					        		$.jBox.tip(data.msg,'info',{timeout:1000});
					        		$(".category_edit_do[name="+c_id+"]").attr("disabled",false);
					        		$(".category_edit_do[name="+c_id+"]").attr("value","修改");
					        	}else{
					        		$.jBox.tip("修改成功!",'success',{timeout:300,closed:function(){location.reload();}});
					        	}				
							});
						}
					}
				});
				
				//删除分类
				$('.category_del').click(function() {
					var c_id = $(this).attr('name').split('_')[0];
					var c_entry_num = $(this).attr('name').split('_')[1];
					if(confirm("确定删除此分类吗?")) {
						if(c_entry_num > 0) {
							$.jBox.tip("此分类下有文章,无法进行删除!",'info',{timeout:1000});
						}else if(c_entry_num == 0) {
							$.jBox.tip("正在进行删除操作...", 'loading');
							ajax_post_json("/admin/category-del",{id:c_id},function(data) {
								if(!data.status) {
									$.jBox.closeTip();
									$.jBox.tip(data.msg,'info',{timeout:1000});
								}else {
									$.jBox.tip("删除成功!",'success',{timeout:300,closed:function(){location.reload();}});
								}					
							});
						}
					}
				});
			});
			function category_edit(id) {
				$("#tr_"+id).hide();
				$("#hidden_"+id).fadeIn('fast');
				var c_name = $("#c_text_"+id).text();
				$("#hidden_c_name_"+id).attr("value",c_name);
			}
			$(function() {
      			$(".category_edit_do").attr("disabled",false);
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
