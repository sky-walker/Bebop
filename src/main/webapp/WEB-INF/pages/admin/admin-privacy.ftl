<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>隐私设置</title>
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
                    <li class="active"><a href="/admin/privacy">隐私管理<i class="icon-chevron-right"></i></a></li>
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
                  <li class="active">隐私设置</li>
                </ul>
                <div class="back-right-inner">
                	<#assign entity=userSettings>
                    <!-- privacy start ================================================== -->
                    <div class="privacy-item">
						<span class="privacy-title">
							是否记录我 <span class="label label-info">发表博客</span> 的动态
						</span>
						<span class="privacy_opt" id="privacy_blog_add_0" <#if entity.privacy_blog_add==0>style="display:none;"</#if> >
							<button class="btn btn-small" type="button" disabled="disabled">显示</button> 
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_blog_add',0)">隐藏</button>
						</span>
						<span class="privacy_opt" id="privacy_blog_add_1" <#if entity.privacy_blog_add==1>style="display:none;"</#if> >
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_blog_add',1)">显示</button>
							<button class="btn btn-small" type="button" disabled="disabled">隐藏</button> 
						</span>
					</div>
                     <div class="privacy-item">
						<span class="privacy-title">
							是否记录我 <span class="label label-info">评论博客</span> 的动态
						</span>
						<span class="privacy_opt" id="privacy_comment_add_0" <#if entity.privacy_comment_add==0>style="display:none;"</#if> >
							<button class="btn btn-small" type="button" disabled="disabled">显示</button> 
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_comment_add',0)">隐藏</button>
						</span>
						<span class="privacy_opt" id="privacy_comment_add_1" <#if entity.privacy_comment_add==1>style="display:none;"</#if> >
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_comment_add',1)">显示</button>
							<button class="btn btn-small" type="button" disabled="disabled">隐藏</button> 
						</span>
					</div>
                     <div class="privacy-item">
						<span class="privacy-title">
							是否记录我 <span class="label label-info">回复评论</span> 的动态
						</span>
						<span class="privacy_opt" id="privacy_comment_reply_0" <#if entity.privacy_comment_reply==0>style="display:none;"</#if> >
							<button class="btn btn-small" type="button" disabled="disabled">显示</button> 
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_comment_reply',0)">隐藏</button>
						</span>
						<span class="privacy_opt" id="privacy_comment_reply_1" <#if entity.privacy_comment_reply==1>style="display:none;"</#if> >
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_comment_reply',1)">显示</button>
							<button class="btn btn-small" type="button" disabled="disabled">隐藏</button> 
						</span>
					</div>
                     <div class="privacy-item">
						<span class="privacy-title">
							是否记录我 <span class="label label-info">关注TA人</span> 的动态
						</span>
						<span class="privacy_opt" id="privacy_concern_add_0" <#if entity.privacy_concern_add==0>style="display:none;"</#if> >
							<button class="btn btn-small" type="button" disabled="disabled">显示</button> 
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_concern_add',0)">隐藏</button>
						</span>
						<span class="privacy_opt" id="privacy_concern_add_1" <#if entity.privacy_concern_add==1>style="display:none;"</#if> >
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_concern_add',1)">显示</button>
							<button class="btn btn-small" type="button" disabled="disabled">隐藏</button> 
						</span>
					</div>
                     <div class="privacy-item">
						<span class="privacy-title">
							是否记录我 <span class="label label-info">收藏文章</span> 的动态
						</span>
						<span class="privacy_opt" id="privacy_blog_post_favorite_0" <#if entity.privacy_blog_post_favorite==0>style="display:none;"</#if> >
							<button class="btn btn-small" type="button" disabled="disabled">显示</button> 
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_blog_post_favorite',0)">隐藏</button>
						</span>
						<span class="privacy_opt" id="privacy_blog_post_favorite_1" <#if entity.privacy_blog_post_favorite==1>style="display:none;"</#if> >
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_blog_post_favorite',1)">显示</button>
							<button class="btn btn-small" type="button" disabled="disabled">隐藏</button> 
						</span>
					</div>
                     <div class="privacy-item">
						<span class="privacy-title">
							是否显示我 <span class="label label-info">在线状态</span> 
						</span>
						<span class="privacy_opt" id="privacy_user_status_0" <#if entity.privacy_user_status==0>style="display:none;"</#if> >
							<button class="btn btn-small" type="button" disabled="disabled">显示</button> 
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_user_status',0)">隐藏</button>
						</span>
						<span class="privacy_opt" id="privacy_user_status_1" <#if entity.privacy_user_status==1>style="display:none;"</#if> >
							<button class="btn btn-success btn-small" type="button" onclick="javascript:privacy('privacy_user_status',1)">显示</button>
							<button class="btn btn-small" type="button" disabled="disabled">隐藏</button> 
						</span>
                        ( 只显示我是否在线,不显示我正在干什么 )
					</div>
                    <!-- privacy end ================================================== -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
          <script type="text/javascript">
			function privacy(type,value) {
				ajax_post_json("/admin/privacy/"+type,{value:value},function(data) {
					if(data.status) {
						if(value==0) {
							$("#"+type+"_0").hide();
							$("#"+type+"_1").show();
						}else if(value==1) {
							$("#"+type+"_0").show();
							$("#"+type+"_1").hide();
						}
					}
				});
			}
		</script>
      </div>
   </div>
   <!-- main end   ================================================== -->
   
  	<!-- footer start   ================================================== -->
   	<#include "/share/foot.ftl" />
   	<!-- footer end   ================================================== -->
</body>
</html>
