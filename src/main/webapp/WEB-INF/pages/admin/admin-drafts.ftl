<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>草稿管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" charset="utf-8">
		
	</script>
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
                    <li class="active"><a href="/admin/drafts">草稿箱&nbsp;&nbsp;<span class="badge badge-info">${currentUser.blogs.draftsNum}</span><i class="icon-chevron-right"></i></a></li>
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
                    <li ><a href="/admin/email">登陆邮箱<i class="icon-chevron-right"></i></a></li>
               </ul>
            </div>
          </div>
          <!-- back-left end ================================================== -->
          
          
          <!-- back-right start ================================================== -->
          <div class="span9 board back-right">
          		<ul class="breadcrumb" style="margin-bottom: 5px;">
                  <#include "/share/admin-nav.ftl" />
                  <li class="active">草稿箱</li>
                </ul>
                <div class="back-right-inner">
                	<p class="text-info">共有 ${pager?size} 篇草稿</p>
                	<div class="divbord mg-t-30">
	                    <table class="table table-hover tbfix">
	                      <thead>
	                        <tr class="fc-008">
	                          <th width="53%">标题</th>
					          <th width="20%">发表时间</th>
					          <th width="15%">分类</th>
					          <th width="12%">管　理</th>
	                        </tr>
	                      </thead>
	                      <tbody>
	                      	<#list pager as entity>
		                        <tr id="item-tr-${entity.id}">
		                          <td>
		                          	<a href="/admin/drafts/edit?id=${entity.id}" title="点击继续编辑">${entity.title}</a>
		                          </td>
		                          <td>${entity.createTime?string("yyyy-MM-dd hh:mm")}</td>
		                          <td><a href="/admin/posts?category=${entity.post_categories.id}">${entity.post_categories.name}</a></td>
		                          <td>
		                          	<a href="/admin/drafts/edit?id=${entity.id}" name="${entity.id}" title="继续编辑"><i class="icon-edit"></i></a>
		                          	&nbsp;&nbsp;
			          				<a href="javascript:;" class="item-del" name="${entity.id}" title="删除"><i class="icon-remove"></i></a>
		                          </td>
		                        </tr>
	                        </#list>
	                      </tbody>
	                    </table>
	            	</div>
                </div>
          </div>
          <script type="text/javascript">
			$(function() {
				$(".item-del").click(function() {
					var id = $(this).attr('name');
					if(id!=null&&confirm("确定删除此篇文章?")) {
						ajax_post_json("/admin/drafts/del",{id:id},function(data) {
							if(data.status) {
								$("#item-tr-"+id).fadeOut();
							}					
						});
					}
				});
			});
		</script>
          <!-- back-right end ================================================== -->
      </div>
   </div>
   <!-- main end   ================================================== -->
   
   <!-- footer start   ================================================== -->
   <#include "/share/foot.ftl" />
   <!-- footer end   ================================================== -->
</body>
</html>
