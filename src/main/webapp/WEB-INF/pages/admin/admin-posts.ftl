<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>文章管理</title>
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
                    <li class="active"><a href="/admin/posts">文章管理<i class="icon-chevron-right"></i></a></li>
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
                    <li ><a href="/admin/email">登陆邮箱<i class="icon-chevron-right"></i></a></li>
               </ul>
            </div>
          </div>
          <!-- back-left end ================================================== -->
          
          
          <!-- back-right start ================================================== -->
          <div class="span9 board back-right">
          		<ul class="breadcrumb" style="margin-bottom: 5px;">
                  <#include "/share/admin-nav.ftl" />
                  <#if categoryName?exists>
                  	 <li><a href="/admin/posts">文章管理</a> <span class="divider">/</span></li>
                  	 <li class="active">${categoryName}</li>
                  <#else>
                  	 <li class="active">文章管理</li>
                  </#if>
                </ul>
                <div class="back-right-inner">
                	<p class="text-info">共有 ${pager.totalRecords} 篇文章，每页显示 ${pager.pageSize} 个，共 ${pager.totalPages} 页
                        <span style="float:right;;display: inline-block;">
                          <select name="category" id="category-select">
							<option value="" selected="selected">全部</option>
							<#list categories as category>
								<#if category.id==categoryId>
									<option value="${category.id}" selected="selected">${category.name} (${category.postNum})</option>
								<#else>
									<option value="${category.id}" >${category.name} (${category.postNum})</option>
								</#if>
							</#list>
						  </select> 
                        </span>
                    </p>
                    <div class="divbord mg-t-30">
	                    <table class="table table-hover tbfix">
	                      <thead>
	                        <tr class="fc-008">
	                          <th width="37%">标题</th>
	                          <th width="20%">发表时间</th>
	                          <th width="15%">分类</th>
	                          <th width="7%">浏览</th>
	                          <th width="6%">回复</th>
	                          <th width="15%">管　理</th>
	                        </tr>
	                      </thead>
	                      <tbody>
	                      	<#list pager.resultList as entity>
		                        <tr id="item-tr-${entity.id}">
		                          <td>
		                          	<a href="/blog/${currentUser.name}/${entity.id}" title="点击查看文章" target="_blank" >${entity.title}</a>
		                          </td>
		                          <td>${entity.createTime?string("yyyy-MM-dd hh:mm")}</td>
		                          <td title="${entity.post_categories.name}">
		                          	<a href="/admin/posts?category=${entity.post_categories.id}">${entity.post_categories.name}</a>
		                          </td>
		                          <td>${entity.viewsNum}</td>
		                          <td>${entity.commentsNum}</td>
		                          <td>
		                          	<a href="/admin/post/edit?id=${entity.id}" title="修改文章"><i class="icon-edit"></i>&nbsp;</a>
		                          	<#if (entity.asTop==0) >
						          		<a href="javascript:;" id="${entity.id}_${entity.asTop}" class="item-top" title="置顶"><i class="icon-minus"></i>&nbsp;</a>
						          	<#elseif (entity.asTop==1)>
						          		<a href="javascript:;" id="${entity.id}_${entity.asTop}" class="item-top" title="取消置顶"><i class="icon-thumbs-up"></i>&nbsp;</a>
						          	</#if>
						          	<#if (entity.canComment==0) >
						          		<a href="javascript:;" id="${entity.id}_${entity.canComment}" class="item-lock" title="取消评论锁定"><i class="icon-volume-off"></i>&nbsp;</a>
						          	<#elseif (entity.canComment==1)>
						          		<a href="javascript:;" id="${entity.id}_${entity.canComment}" class="item-lock" title="评论锁定"><i class="icon-volume-up"></i>&nbsp;</a>
						          	</#if>
						          	<#if (entity.visible==0) >
						          		<a href="javascript:;" id="${entity.id}_${entity.visible}" class="item-visible" title="取消文章隐私"><i class="icon-eye-close"></i>&nbsp;</a>
						          	<#elseif (entity.visible==1)>
						          		<a href="javascript:;" id="${entity.id}_${entity.visible}" class="item-visible" title="隐藏文章"><i class="icon-eye-open"></i>&nbsp;</a>
						          	</#if>
						          	<a href="javascript:;" class="item-del" name="${entity.id}" title="删除"><i class="icon-remove"></i>&nbsp;</a>
		                          </td>
		                        </tr>
	                        </#list>
	                      </tbody>
	                    </table>
                    </div>
                    <#-- 分页标签 BEGIN -->
		    	    <#import "/share/pager_.ftl" as p>
				    <@p.page pager=pager link="/admin/posts?" showPageNum=9 anchor=""/>
				    <#-- 分页标签 END -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
      </div>
   </div>
   <!-- main end   ================================================== -->
   <script type="text/javascript" charset="utf-8">
		$(function() {
			$(".item-del").click(function() {
				var id = $(this).attr('name');
				if(id!=null&&confirm("确定删除此篇文章?删除之后无法恢复")) {ajax_post_json("/admin/posts/del",{id:id},function(data) {if(data.status) {$("#item-tr-"+id).fadeOut();}});}
			});
			
			$(".item-top").click(function(){
				var id_asTop = $(this).attr('id');
				var id = $(this).attr('id').split("_")[0];
				var asTop = $(this).attr('id').split("_")[1];
				if(asTop!=null && asTop==0 && id!=null) {//置顶操作
					ajax_post_json("/admin/posts/top",{id:id},function(data) {
						if(data.status) {
							$(".item-top[id="+id_asTop+"] i").attr("class","icon-thumbs-up");
							$(".item-top[id="+id_asTop+"]").attr("title","取消置顶");
							$(".item-top[id="+id_asTop+"]").attr("id",id+"_1");
						}					
					});
				}else if(asTop!=null && asTop==1 && id!=null) {//取消置顶操作
					ajax_post_json("/admin/posts/untop",{id:id},function(data) {
						if(data.status) {
							$(".item-top[id="+id_asTop+"] i").attr("class","icon-minus");
							$(".item-top[id="+id_asTop+"]").attr("title","置顶");
							$(".item-top[id="+id_asTop+"]").attr("id",id+"_0");
						}					
					});
				}
			});
			
			$(".item-lock").click(function(){
				var id_canCommnet = $(this).attr('id');
				var id = $(this).attr('id').split("_")[0];
				var canComment = $(this).attr('id').split("_")[1];
				if(canComment!=null && canComment==0 && id!=null) {//解锁操作
					ajax_post_json("/admin/posts/unlock",{id:id},function(data) {
						if(data.status) {
							$(".item-lock[id="+id_canCommnet+"] i").attr("class","icon-volume-up");
							$(".item-lock[id="+id_canCommnet+"]").attr("title","锁定");
							$(".item-lock[id="+id_canCommnet+"]").attr("id",id+"_1");
						}					
					});
				}else if(canComment!=null && canComment==1 && id!=null) {//锁定操作
					ajax_post_json("/admin/posts/lock",{id:id},function(data) {
						if(data.status) {
							$(".item-lock[id="+id_canCommnet+"] i").attr("class","icon-volume-off");
							$(".item-lock[id="+id_canCommnet+"]").attr("title","取消锁定");
							$(".item-lock[id="+id_canCommnet+"]").attr("id",id+"_0");
						}					
					});
				}
			});
			
			$(".item-visible").click(function(){
				var id_visible = $(this).attr('id');
				var id = $(this).attr('id').split("_")[0];
				var visible = $(this).attr('id').split("_")[1];
				if(visible!=null && visible==0 && id!=null) {//解除隐藏
					ajax_post_json("/admin/posts/unvisible",{id:id},function(data) {
						if(data.status) {
							$(".item-visible[id="+id_visible+"] i").attr("class","icon-eye-open");
							$(".item-visible[id="+id_visible+"]").attr("title","隐藏");
							$(".item-visible[id="+id_visible+"]").attr("id",id+"_1");
						}					
					});
				}else if(visible!=null && visible==1 && id!=null) {//隐藏
					ajax_post_json("/admin/posts/visible",{id:id},function(data) {
						if(data.status) {
							$(".item-visible[id="+id_visible+"] i").attr("class","icon-eye-close");
							$(".item-visible[id="+id_visible+"]").attr("title","公开");
							$(".item-visible[id="+id_visible+"]").attr("id",id+"_0");
						}					
					});
				}
			});
			
			$('#category-select').change(function() {
				var categoryId = $(this).val();
				if(categoryId=="") {location.href="/admin/posts";}else {location.href="/admin/posts?category="+categoryId;}
			});
		});
	</script>
   <!-- footer start   ================================================== -->
   <#include "/share/foot.ftl" />
   <!-- footer end   ================================================== -->
</body>
</html>
