<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${entity.title}</title>
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
		    <div id="comt"></div>
		    <div id='xia'></div>
          </div>
          <!-- back-right start ================================================== -->
          <div class="span9 board style="float:right">
                <div class="front-right-inner">
            		
                    <!-- post-item start ================================================== -->
                    <div class="post-item divbord">
                    	<p class="post-item-date">${entity.createTime?string('yyyy-MM-dd')}</p>
                        <p class="post-item-title">
                        <#if entity.visible==0>
			        		<img src="http://bebop.qiniudn.com/images/icons/glyphicons_052_eye_close.png?imageView/2/w/15"></img>
						</#if>
                        <#if entity.asTop==1>
			        		<img src="http://bebop.qiniudn.com/images/icons/glyphicons_343_thumbs_up.png?imageView/2/w/15">
	      				</#if>
	      				<#if entity.canComment==0>
			        		<img src="http://bebop.qiniudn.com/images/icons/glyphicons_203_lock.png?imageView/2/w/11" title="无法评论"></img>
						</#if>
				        <a href="/blog/${user.name}/${entity.id}" title="${entity.title}">
				        	${entity.title}
				        </a>
                        </p>
                        <#if currentUser!="" & entity.visible==0 & currentUser.id!=entity.blogs.id>
                        	<div class="datanull">用户设置了隐私,您无权查看..</div>
                        <#elseif currentUser="" & entity.visible==0>
                        	<div class="datanull">用户设置了隐私,您无权查看..</div>
                        <#else>
                        	<div class="post-item-body">
	                        	${entity.content}
	                        </div>
	                        <p class="post-item-info">
	                        	<span class="post-item-info-iterm"><i class="icon-time"></i>&nbsp;${entity.createTime?string('HH:mm:ss')}</span>
	                            <span class="post-item-info-iterm"> | </span>
	                            <span class="post-item-info-iterm">浏览 <span class="badge badge-info">${entity.viewsNum}</span></span>
	                            <span class="post-item-info-iterm"> | </span> 
	                            <span class="post-item-info-iterm"><a href="/blog/${user.name}/${entity.id}#comments">评论&nbsp;<span class="badge badge-info">${entity.commentsNum}</span></a></span>
	                            <span class="post-item-info-iterm"> | </span>
	                            <span class="post-item-info-iterm"><i class="icon-tag"></i><a href="/blog/${user.name}?category=${entity.post_categories.id}">${entity.post_categories.name}</a></span>
	                            <#assign currentUser = Session["OSEYRUSER"]>
	      						<#if currentUser?exists>
	      						  <span class="post-item-info-iterm"> | </span>
							      <span class="post-item-info-iterm"><i class="icon-bookmark"></i><a href="javascript:add_favorite(0,${entity.id})" id="favorite_add" title="收藏">收藏</a></span>
							    </#if>
							    <#if currentUser!="" & currentUser.id=user.id>
	      						  <span class="post-item-info-iterm"> | </span>
							      <span class="post-item-info-iterm"><i class="icon-edit"></i><a href="/admin/post/edit?id=${entity.id}" id="favorite_add" title="编辑" target="_blank" style="color: #990000;">编辑</a></span>
							    </#if>
	                        </p>
	                         <!-- comments start ================================================== -->
	                         <script language="javascript">
						     	function replyInline(blog,post,parent){
									var div_id = "inline_reply_of_"+blog+"_"+post+"_"+parent;
									$("div[id^=inline_reply_of]").hide();
									$("span[id^=inline_comment_add_]").html("").hide();
									$("#"+div_id).show();
									$("#inline_comment_content_"+parent).focus();
									var btn_comment = "btn_comment_"+parent;
									var inline_comment_content_id = "inline_comment_content_"+parent;
									$("#"+btn_comment).click(function() {
										$("#"+btn_comment).attr("value","正在提交回复...");
										$("#"+btn_comment).attr("disabled",true);
										ajax_post_html("/blog/comment/reply",{blog:'${blog.id}',post:'${entity.id}',parent:parent,content:$.trim($("#"+inline_comment_content_id).val())},
												function(data) {
													$("#tempdiv").html("");
													$("#tempdiv").html(data);
													if($("div[id=tempdiv] #errMsg").length>0) {
														$("#inline_comment_add_"+parent).html("");
														$("#inline_comment_add_"+parent).html(data).fadeIn();
													}else if(("div[id=tempdiv] .comment").length>0) {
														$("#blog_commments_content").append(data);
														$(".timeago").prettyDate();
														$("#"+inline_comment_content_id).val("");
														$("div[id^=inline_reply_of]").hide();
														SyntaxHighlighter.highlight();
														$("#xia").trigger("click");
													}
													$("#"+btn_comment).attr("value","回复");
													$("#"+btn_comment).attr("disabled",false);
													
												});
									});
								}
								function replyClose(blog,post,parent) {
									var div_id = "inline_reply_of_"+blog+"_"+post+"_"+parent;
									var inline_comment_content_id = "inline_comment_content_"+parent;
									var btn_comment = "btn_comment_"+parent;
									$("#"+inline_comment_content_id).val("");
									$("#"+btn_comment).attr("value","回复");
									$("#"+btn_comment).attr("disabled",false);
									$("#inline_comment_add_"+parent).hide();
									$("#inline_comment_add_"+parent).html("");
									$("#"+div_id).hide();
								}
						      </script>
		                        <div id="comments" class="comments">
		                            <h5>
		                            	评论
		                            	<#if entity.canComment==0>
		                            		　　<span class="label label-important"><i class="icon-lock icon-white"></i>&nbsp;&nbsp;文章被锁定,无法进行评论!</span>
										</#if>
		                            </h5>
		                            <!-- comments-item start================================================== -->
		                            <div id="blog_commments_content">
			                            <#list pager.resultList as comment>
			                            	<div class="comments-item divbord comment" id="${comment.id}">
			                                    <table>
			                                      <tr>
			                                        <td rowspan="2" valign="top" width="45px">
			                                            <div class="comment-item-head">
			                                                <a href="/blog/${comment.users.name}" target="blank" class="thumbnail" >
			                                                    <img src="${comment.users.portraint}" width="35px" height="35px" border="0">
			                                                </a>
			                                            </div>
			                                        </td>
			                                        <td colspan="2" width="800px">
			                                             <div class="pm-item-summary">
			                                              <p>
			                                                   <a href="/blog/${comment.users.name}" target="_blank">@${comment.users.nickname}</a> 
			                                                   <span class="muted">发表于</span> <span class="timeago muted" title="${comment.createTime?string('yyyy-MM-dd HH:mm:ss')}">${comment.createTime}</span>
			                                                   <#if comment.visible==1 && entity.canComment==1>
																	<a href="javascript:replyInline(${user.id},${entity.id},${comment.id})">引用</a>
															   </#if>                                                                                                      
			                                              </P> 
			                                              <div class="quotewap">
			                                                  <#import "/share/comments-nest.ftl" as nest>
															  <#if comment.visible==0>
																 	<span style="text-align:center;">此评论已被隐藏..</span>
															  <#else>
																 	<@nest.commentnest comment=comment />
																 	 ${comment.content}
															  </#if>
			                                              </div>
			                                            </div>
			                                        </td>
			                                      </tr>
			                                      <tr>
			                                        <td></td>
			                                        <td>
			                                        	<div id="inline_reply_of_${blog.id}_${entity.id}_${comment.id}" class="inline_reply hide">
															<div class="BlogCommentForm">
																<div class="alert alert-info" style="width:510px;">
																  	评论内容:　(不小于5个字符,最大800个字符) 
																</div>
																<textarea name="content" id="inline_comment_content_${comment.id}" style="width:550px;height:60px;"></textarea><br>
																<input type="submit" value="回复" id="btn_comment_${comment.id}" class="btn btn-primary btn-small"> 
																<input type="button" value="关闭" id="btn_close_inline_reply" class="btn btn-primary btn-small" onclick="javascript:replyClose(${blog.id},${entity.id},${comment.id})"> 
																<span id="inline_comment_add_${comment.id}" class="hide alert alert-error"></span>
															</div>
														</div>
			                                        </td>
			                                      </tr>
			                                    </table>
			                                    <br/>
			                                </div>
			                            </#list>
			                        </div>
			                        <div id="bottom"></div>
		                            <#-- 分页标签 BEGIN -->
								    <#import "/share/pager_.ftl" as p>
								    <@p.page pager=pager link="/blog/${user.name}/${entity.id}?" showPageNum=9 anchor="#comments"/>
								    <#-- 分页标签 END -->
		                            <!-- comments-item end ================================================== -->
		                            <br/>
		                            <br/>
		                            <br/>
		                            <br/>
		                            <#if entity.canComment==1>
										<div id="addcomment">
											<div class="alert alert-info" style="width:610px;">
											  	评论内容:　(不小于5个字符,最大800个字符) 
											</div>
											<p style="display:none;">
												评论内容:　(不小于5个字符,最大800个字符) 您当前输入了 <span id="word_count">0</span> 个文字。
											</p>
											<textarea name="content" id="comment_content" style="width:660px;height:150px;"></textarea><br/>
											<input type="button" id="comment_add_bt" value="发表评论" class="btn btn-primary" style="font-weight: bold;"/>　
											<span id="comment_add_error" class="alert alert-error hide"></span>
										</div>
										<script language="javascript">
											$(function() {
												var editor;
												var content_count;
												var inline_reply_of = $("div[id^=inline_reply_of]");
												$.getScript('http://bebop.qiniudn.com/editor/kind/kindeditor-min.js', function() {
													KindEditor.basePath = '/editor/kind/';
													editor = KindEditor.create("textarea[id='comment_content']",
													{
														items:['emoticons','code'],
											     		cssPath:"/styles/ke-content.css",resizeType : 1,allowImageUpload:false,syncType:"auto",filterMode : true,
											     		afterChange : function() {content_count=this.count('text');$('#word_count').html(this.count('text'));},
											     		afterFocus : function() {inline_reply_of.hide();}
													});
												});
												$("#comment_add_bt").click(function() {
													editor.sync();
													$.jBox.tip("正在发表评论...", 'loading');
													$("#comment_add_error").fadeOut();
													var comment_content = $.trim($("#comment_content").val());
													if(content_count<5 || content_count>800) {$.jBox.tip("请检查输入的字符数!",'info',{timeout:500});return;}
													$("#comment_add_bt").attr("value","正在提交评论...");
													$("#comment_add_bt").attr("disabled",true);
													$.post("/blog/comment/add",{blog:'${blog.id}',post:'${entity.id}',content:comment_content},
														function(data) {
															$("#tempdiv").html("");
															$("#tempdiv").html(data);
															if($("div[id=tempdiv] #errMsg").length>0) {
																$.jBox.closeTip()
																$("#comment_add_error").html("");	
																$("#comment_add_error").html(data).fadeIn();
															}else if(("div[id=tempdiv] .comment").length>0) {
																$.jBox.closeTip()
																$("#blog_commments_content").append(data);
																$(".timeago").prettyDate();
																editor.html("");
																SyntaxHighlighter.highlight();
																$("#xia").trigger("click");
															}
															$("#comment_add_bt").attr("value","发表评论");
															$("#comment_add_bt").attr("disabled",false);
														});
												});
											});
								      </script>
							      </#if>
	                        </div>
	                        <!-- comments end ================================================== -->	
                        	
                        </#if>
                    </div>
                    <!-- post-item end ================================================== -->
                </div>
                <div id="tempdiv" style="display:none;"></div> 
          </div>
          <!-- back-right end ================================================== -->
            <script type="text/javascript">
				$(function() {
					SyntaxHighlighter.all();
				});
			</script>
			<link href="http://bebop.qiniudn.com/codelighter/styles/shCore.css" rel="stylesheet" type="text/css"/>
			<link href="http://bebop.qiniudn.com/codelighter/styles/shThemeDefault.css" rel="stylesheet" type="text/css"/>
			<script src="http://bebop.qiniudn.com/codelighter/scripts/shCore.js" type="text/javascript"></script>
			<script src="http://bebop.qiniudn.com/codelighter/scripts/shBrushJava.js" type="text/javascript"></script>
      </div>
   </div>
   <!-- main end   ================================================== -->
   
   <!-- footer start   ================================================== -->
  	<#include "/share/foot.ftl" />
   <!-- footer end   ================================================== -->
</body>
</html>
