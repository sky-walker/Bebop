<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${user.nickname} 的首页</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
  	<!-- header start ================================================== -->
    <#include "/share/front-top.ftl" />
   	<!-- header end ================================================== -->
   <!-- banner start ================================================== -->
   <#include "/front/blog-banner.ftl" />
   <!-- banner end ================================================== -->
   
   <!-- main start ================================================== -->
   <div id="main">
   	<div class="row">
      <div class="container" style="width:1170px">
          <!-- back-left start ================================================== -->
          <#include "/front/blog-left.ftl" />
          <!-- back-left end ================================================== -->
          <div>
  			<div id='shangxia'>
		    <div id='shang'></div>
		    <div id='xia'></div>
          </div>
          <!-- back-right start ================================================== -->
          <div class="span9 board style="float:right">
                <div class="front-right-inner">
                    <!-- post-item start ================================================== -->
                    <#if pager.totalRecords=0>
                    	<div class="datanull">暂无博客文章...</div>
                    <#else>
                    	 <#list pager.resultList as entity>
		                    <div class="post-item divbord">
		                    	<p class="post-item-date">${entity.createTime?string('yyyy-MM-dd')}</p>
		                        <p class="post-item-title">
		                        	<#if entity.visible==0>
						        		<img src="http://bebop.qiniudn.com/images/icons/glyphicons_052_eye_close.png?imageView/2/w/15" title="隐私"></img>
									</#if>
			                        <#if entity.asTop==1>
						        		<img src="http://bebop.qiniudn.com/images/icons/glyphicons_343_thumbs_up.png?imageView/2/w/15" title="置顶">
				      				</#if>
				      				<#if entity.canComment==0>
						        		<img src="http://bebop.qiniudn.com/images/icons/glyphicons_203_lock.png?imageView/2/w/11" title="无法评论"></img>
									</#if>
							        <a href="/blog/${user.name}/${entity.id}" title="${entity.title}">
							        	${entity.title}
							        </a>
		                        </p>
		                        <#if currentUser!="" & entity.visible==0 & currentUser.id!=entity.blogs.id>
		                        	<p>　</p>
		                        <#elseif currentUser="" & entity.visible==0>
		                        	<p>　</p>
		                        <#else>
		                        	<div class="post-item-body">
			                        	<#if (entity.content?length>300)>
							        		${jsoupUtils.htmlProcess(entity.content[0..300],"/blog/${user.name}/${entity.id}")}  <br/>　<a href="/blog/${user.name}/${entity.id}" class="more">查看全部...</a>
							        	<#else>
							        		${jsoupUtils.htmlProcess(entity.content,"/blog/${user.name}/${entity.id}")}
							        	</#if>
			                        </div>
			                    </#if>
			                    <p class="post-item-info">
		                        	<span class="post-item-info-iterm"><i class="icon-time"></i>&nbsp;${entity.createTime?string('HH:mm:ss')}</span>
		                            <span class="post-item-info-iterm"> | </span>
		                            <span class="post-item-info-iterm">浏览 <span class="badge badge-info">${entity.viewsNum}</span></span>
		                            <span class="post-item-info-iterm"> | </span> 
		                            <span class="post-item-info-iterm"><a href="/blog/${user.name}/${entity.id}#comments">评论&nbsp;<span class="badge badge-info">${entity.commentsNum}</span></a></span>
		                            <span class="post-item-info-iterm"> | </span>
		                            <span class="post-item-info-iterm"><i class="icon-tag"></i><a href="/blog/${user.name}?category=${entity.post_categories.id}">${entity.post_categories.name}</a></span>
		                        </p>
		                    </div>
		                    <br/>
		                </#list>
		                <#-- 分页标签 BEGIN -->
					    <#import "/share/pager_.ftl" as p>
					    <@p.page pager=pager link="/blog/${user.name}?" showPageNum=9 anchor=""/>
					    <#-- 分页标签 END -->
                    </#if>
                    <!-- post-item end ================================================== -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
      </div>
   </div>
   <div id="bottom"></div>
   <!-- main end   ================================================== -->
   <!-- footer start   ================================================== -->
  	<#include "/share/foot.ftl" />
   <!-- footer end   ================================================== -->
</body>
</html>
