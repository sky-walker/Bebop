<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>草稿编辑</title>
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
                    <li><a href="/admin/post/new">发表文章<i class="icon-chevron-right"></i></a></li>
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
                  <li><a href="/admin/posts">草稿管理</a> <span class="divider">/</span></li>
                  <li class="active">草稿编辑</li>
                </ul>
                <div class="post-form-wrapper">
                	<form action="" method="post" id="entry-form">
                		<input type="hidden" name="id" value="${draft.id}" />
                    	<p class="text-info">标题(必填)</p>
                        <input type="text" name="title" style="width:615px;" maxlength="80" placeholder="此处输入文章标题" value="${draft.title}">
                        分类于
                          <select name="category" id="entity_category"  class="span2">
							<#list categories as entity>
								<#if entity.id==draft.post_categories.id>
									<option value="${entity.id}" selected="selected">${entity.name} (${entity.postNum})</option>
									<#else>
									<option value="${entity.id}" >${entity.name} (${entity.postNum})</option>
								</#if>
							</#list>
					      </select> 
                          <div>
                          	<p class="text-info">内容(必填)
                                <span style="float:right;display: inline-block;">
                                	<input id="entity-add" type="button" class="btn btn-small btn-primary" value="发　表 " />
                                	<input id="save-as-draft" type="button" class="btn btn-small btn-primary" value="存为草稿"/>
									<input id="back-to-manage" type="button" class="btn btn-small btn-primary" value="返回草稿管理页面"/>
                                </span>
                            </p>
                          </div>
                          <textarea id="entry_content" name="content"  style="width:100%;height:400px;">
                          	${draft.content?html}
                          </textarea>
                          <br />
                          <p>
                            文章类型：  
                            <#if (draft.postType==0)>
								<input type="radio" name="postType"  value="0" checked="checked"/> 原创 
								<input type="radio" name="postType"  value="1"/> 翻译
								<input type="radio" name="postType"  value="2"/> 转帖
							</#if>
							<#if (draft.postType==1)>
								<input type="radio" name="postType"  value="0" /> 原创 
								<input type="radio" name="postType"  value="1" checked="checked"/> 翻译
								<input type="radio" name="postType"  value="2"/> 转帖
							</#if>
							<#if (draft.postType==2)>
								<input type="radio" name="postType"  value="0"/> 原创 
								<input type="radio" name="postType"  value="1"/> 翻译
								<input type="radio" name="postType"  value="2" checked="checked"/> 转帖
							</#if>
							<#if (draft.postType==2)>
								<div id="reLink" class="input-prepend inblock">
								  <span class="add-on">原文链接</span>
								  <input class="span6" type="text" name="reLink" placeholder="此处输入转载原文的链接" value="${draft.reLink?html}">
								</div>
							<#else>
								<div id="reLink" class="input-prepend inblock hide">
								  <span class="add-on">原文链接</span>
								  <input class="span6" type="text" name="reLink" placeholder="此处输入转载原文的链接" >
								</div>
							</#if>
                          </p>
                          <script type="text/javascript">
                            $(function() {
                                $(":radio[name=postType][value!=2]").click(function() {$("#reLink").hide();});
                                $(":radio[name=postType][value=2]").click(function() {$("#reLink").show();});
                            });
                          </script>
                          <p>
						隐私设置：
						<#if (draft.visible==0)>
							<input type="radio" name="visible"  value="1"/> 对所有人可见 
							<input type="radio" name="visible"  value="0"  checked="checked"/> 保密(只对自己可见)
						</#if>
						<#if (draft.visible==1)>
							<input type="radio" name="visible"  value="1" checked="checked"/> 对所有人可见 
							<input type="radio" name="visible"  value="0" /> 保密(只对自己可见)
						</#if>
					</p>
					<p>
						<#if (draft.asTop==0)>
							设为置顶：<input type="checkbox" name="asTop" id="checkbox" value="0"/>　　
						</#if>
						<#if (draft.asTop==1)>
							设为置顶：<input type="checkbox" name="asTop" id="checkbox" value="1" checked="checked"/>　　
						</#if>
						
						<#if (draft.canComment==0)>
							评论设置：
							<input type="radio" name="canComment" value="0" checked="checked"/>禁止评论
							<input type="radio" name="canComment" value="1" />允许评论
						</#if>
						<#if (draft.canComment==1)>
							评论设置：
							<input type="radio" name="canComment" value="0" />禁止评论
							<input type="radio" name="canComment" value="1" checked="checked"/>允许评论
						</#if>
					</p>
                    </form>
                </div>
                <script src="http://bebop.qiniudn.com/scripts/jquery.form.js" type="text/javascript"></script>
			    <script src="http://bebop.qiniudn.com/editor/kind/kindeditor-min.js" type="text/javascript"></script>
			    <script type="text/javascript">
					var editor;
					KindEditor.ready(function(K) {
					        editor = K.create('#entry_content',{
					        	items:[
					             'formatblock', 'fontname', 'fontsize','forecolor', 'hilitecolor', 'bold','italic', 'underline',
					             'justifyleft', 'justifycenter', 'justifyright',,'insertorderedlist', 'insertunorderedlist',
					             'indent', 'outdent',
					             'wordpaste','image',
					             'flash','table', 'hr', 'emoticons','code',
					             'link', 'unlink','source','preview','fullscreen'
					     		],
					     		cssPath:"/styles/ke-content.css",
					     		resizeType : 1,
					     		allowFlashUpload:false,
					     		syncType:"auto",
					     		uploadJson : "/admin/upload/editor-image"
					        });
					});
					$(function(){
						var checkContent = true;
						window.onbeforeunload = function() {
							editor.sync();
							var content = $.trim($("#entry_content").val());
							if(!checkContent){
								return;
							}
							if(content.length==0) {
								return;
							}
							return "确认要放弃此编辑文章吗？";
						};
						$("#entity-add").click(function() {
								editor.sync();
								$("#entry-form").attr("action","/admin/post/draft-to-post-do");
								$("#entry-add").attr("value","正在保存...");
								$.jBox.tip("正在进行保存...", 'loading',{top:'182px'});
								$('#entry-add').attr("disabled",true);
								$('#entry-form').ajaxSubmit({dataType:'json',
							        success: function(data) {
							        	if(!data.status) {
							        		$.jBox.tip(data.msg,'error',{top:'182px'});
							        		$("#entry-add").attr("value","发   表");
							        		$('#entry-add').attr("disabled",false);
							        	}else{
							        		checkContent = false;
							        		location.href="/admin/posts";
							        	}
							        } 
							    });
							});
						
							$('#save-as-draft').click(function(){
								editor.sync();
								$("#entry-form").attr("action","/admin/drafts/edit-do");
								$("#save-as-draft").attr("value","正在保存...");
								$.jBox.tip("正在进行保存...", 'loading',{top:'182px'});
								$('#save-as-draft').attr("disabled",true);
								$('#entry-form').ajaxSubmit({dataType:'json',
							        success: function(data) {
							        	if(!data.status) {
							        		$.jBox.tip(data.msg,'error',{top:'182px'});
							        		$("#save-as-draft").attr("value","存为草稿");
							        		$('#save-as-draft').attr("disabled",false);
							        	}else{
							        		if(confirm("已保存到草稿箱,是否转到草稿箱?")) {
							        			checkContent = false;
							        			location.href="/admin/drafts";
							        		}
							        		$.jBox.closeTip();
							        		$("#save-as-draft").attr("value","存为草稿");
							        		$('#save-as-draft').attr("disabled",false);
							        	}
							        } 
							    });
							 });
							 
							$("#back-to-manage").click(function() {
								window.location.href="/admin/drafts";
							});
					});
				</script>
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
