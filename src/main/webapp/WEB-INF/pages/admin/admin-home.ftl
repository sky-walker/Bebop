<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>个人中心</title>
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
                	<li class="active"><a href="/admin/home">个人中心<i class="icon-chevron-right"></i></a></li>
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
                  <#if type=='follows'>
                  	<li class="active">我关注的动态</li>
                  </#if>
                  <#if type=='my'>
                  	<li class="active">我的动态</li>
                  </#if>
                  <#if type=='all'>
                  	<li class="active">全站动态</li>
                  </#if>
                </ul>
                <div class="back-right-inner">
                	
                    <!-- pm-item start ================================================== -->
                    <ul class="nav nav-tabs">
                      <li <#if type=='follows'>class="active"</#if>>
                        <a href="/admin/home">我关注的动态&nbsp;<#if type=='follows'><span class="badge badge-info">${pager.totalRecords}</span></#if></a>
                      </li>
                      <li <#if type=='my'>class="active"</#if>><a href="/admin/home/my">我的动态&nbsp;<#if type=='my'><span class="badge badge-info">${pager.totalRecords}</span></#if></a></li>
                      <li <#if type=='all'>class="active"</#if>><a href="/admin/home/all">全站动态&nbsp;<#if type=='all'><span class="badge badge-info">${pager.totalRecords}</span></#if></a></li>
                    </ul>
                    
                    <ul class="nav nav-pills">
					  <li <#if !optType?exists>class="active"</#if>>
					    <a href="/admin/home/${type}">全部</a>
					  </li>
					  <li <#if optType==1>class="active"</#if>><a href="/admin/home/${type}?optType=1">发表文章</a></li>
					  <li <#if optType==2>class="active"</#if>><a href="/admin/home/${type}?optType=2">博客评论</a></li>
					  <li <#if optType==3>class="active"</#if>><a href="/admin/home/${type}?optType=3">添加关注</a></li>
					  <li <#if optType==4>class="active"</#if>><a href="/admin/home/${type}?optType=4">文章收藏</a></li>
					  <li <#if optType==5>class="active"</#if>><a href="/admin/home/${type}?optType=5">评论回复</a></li>
					</ul>
                    <!-- pm-item start ================================================== -->
                    <#list pager.resultList as entity>
		  		  	  <#include "/share/feed-template.ftl" />
				  	</#list>
                    <!-- pm-item end ================================================== -->
                    <#-- 分页标签 BEGIN -->
		    	    <#import "/share/pager_.ftl" as p>
				    <@p.page pager=pager link="/admin/home/${type}?" showPageNum=9 anchor=""/>
				    <#-- 分页标签 END -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
      </div>
   </div>
   <!-- main end   ================================================== -->
   
   	<!-- footer start   ================================================== -->
   	<#include "/share/foot.ftl" />
   	<!-- footer end   ================================================== -->
</body>
</html>
