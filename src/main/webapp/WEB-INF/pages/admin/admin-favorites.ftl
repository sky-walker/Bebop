<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>收藏管理</title>
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
                    <li><a href="/admin/messages">站内消息<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/reminds">我的通知<i class="icon-chevron-right"></i></a></li>
                    <li class="active"><a href="/admin/favorites">收藏管理<i class="icon-chevron-right"></i></a></li>
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
                  <#if tag?exists>
                  	<li><a href="/admin/favorites">我的收藏</a> <span class="divider">/</span></li>
                  	<li class="active">${tag?html}</li>
				  <#else>
				  	<li class="active">我的收藏</li>
				  </#if>
                </ul>
                <div class="back-right-inner">
                	<div class="tabbable tabs-left">
		              <ul class="nav nav-tabs">
		                <li class="active"><a href="#favs"  data-toggle="tab">我的收藏&nbsp;<span class="badge badge-info">${pager.totalRecords}</span></a></li>
		                <li><a href="#fav-tags"  data-toggle="tab">标签管理&nbsp;<span class="badge badge-info">${tagsPager.totalRecords}</span></a></li>
		                <li><a href="#fav-add" data-toggle="tab">添加收藏</a></li>
		              </ul>
		              <div class="tab-content">
		              	<!-- favorites start ================================================== -->
		              	<#assign ose_urlcode=JspTaglibs["/WEB-INF/URLCodeTag.tld"]/>
		                <div class="tab-pane active" id="favs">
		                
		                <!-- favorites-tags start ================================================== -->
		                <span class="fav_tag" style="margin-left:8px;">
						 	<a href="/admin/favorites"  class="mediumtag">
						 		<span class="badge badge-info tagwrap pt5 pb5 mg-t-5 mg-b-5">
									全部
								</span>
						 	</a>
					 	</span>
		                <#list tagsPager.resultList as entity>
						 	<span class="fav_tag" style="margin-left:8px;">
							 	<a href="/admin/favorites/?tag=<@ose_urlcode.URLCode str='${entity.name}' deOrEncode='encode'/>"  class="mediumtag">
							 		<#if entity.name = tag?html>
							 			<span class="badge badge-inverse tagwrap pt5 pb5 mg-t-5 mg-b-5">
											${entity.name?html}(${entity.favorite_num})
										</span>
									<#else>
										<span class="badge badge-info tagwrap pt5 pb5 mg-t-5 mg-b-5">
											${entity.name?html}(${entity.favorite_num})
										</span>
							 		</#if>
							 	</a>
						 	</span>
						 </#list>
		                 <!-- favorites-tags end ================================================== -->
		                
		                  <table class="table table-hover tbfix">
				              <thead>
				                <tr class="fc-008">
				                  <th width="80%">标题</th>
				                  <th width="10%">类别</th>
				                  <th width="10%">操作</th>
				                </tr>
				              </thead>
				              <tbody>
				              	<#list pager.resultList as entity>
					              	<tr id="favorite_show_${entity.id}">
					                  <td>
					                  	<a href="${entity.url}" target="_blank" title="${entity.title}">
											${entity.title}
										</a>
										<br/>
										<#list entity.favorite_tags as tag>
											<a href="/admin/favorites/?tag=<@ose_urlcode.URLCode str='${tag.name}' deOrEncode='encode'/>"  class="minitag"><span class="badge badge-info tagwrap">${tag.name?html}</span></a>
							          	</#list>
					                  </td>
					                  <td>
					                  	<#if entity.isOuterLink==1>
											站内文章
										<#else>
											自定义
										</#if>
					                  </td>
					                  <td>
					                  	<a href="javascript:editfav(${entity.id})" class="edit_link"><i class="icon-edit"></i></a> |
										<a href="javascript:delfav(${entity.id})" class="links_del"><i class="icon-remove"></i></a>
					                  </td>
					                </tr>
					                <tr id="favorite_hide_${entity.id}" class="hide warning">
					                	<td colspan="3" class="hide-hover-bg">
					                		<#if entity.isOuterLink==1>
					                			 <form action="/favorites/edit" method="post" id="links_edit_form_${entity.id}" class="my-form">
					                			 	<input type="hidden" name="id" value="${entity.id}"/>
			          	  							<input type="hidden" id="oldtags_${entity.id}" value='<#list entity.favorite_tags as tag>${tag.name?html} </#list>'/>
					                			 	<div class="input-prepend  input-append">
													  <span class="add-on">标签</span>
													  <input name="tags" type="text" id="newtags_${entity.id}" class="span4" value='<#list entity.favorite_tags as tag>${tag.name?html} </#list>' />
													  <span class="add-on">&nbsp;&nbsp;多个标签请以'空格' 隔开,标签最多只能有5个</span>
													</div>
													<div class="control-group">
													    <div class="controls">
													      <input type="button" id="edit_save_${entity.id}" class="btn btn-primary fav-hide-edit-bt"value="修改" onclick="javascript:editsave(${entity.id})"/>
				                						  <input type="button" class="btn btn-primary" value="取消" onclick="javascript:editcancel(${entity.id})"/>
													    </div>
													 </div>
					                			 </form>
					                		<#else>
					                			<form action="/favorites/edit" method="post" id="links_edit_form_${entity.id}" >
							                		<input type="hidden" name="id" value="${entity.id}"/>
					          	  					<input type="hidden" id="oldtags_${entity.id}" value='<#list entity.favorite_tags as tag>${tag.name} </#list>'/>
							                		<div class="input-prepend">
													  <span class="add-on">标题</span>
													  <input name="title" type="text" class="span4"  value="${entity.title}" />
													</div>
													<div class="input-prepend">
													  <span class="add-on">链接</span>
													  <input name="url" type="text" class="span4"  value="${entity.url}" />
													</div>
													<div class="input-prepend  input-append">
													  <span class="add-on">标签</span>
													  <input name="tags" type="text" id="newtags_${entity.id}" class="span4" value='<#list entity.favorite_tags as tag>${tag.name?html} </#list>'/>
													  <span class="add-on">&nbsp;&nbsp;多个标签请以'空格' 隔开,标签最多只能有5个</span>
													</div>
													<div class="control-group">
													    <div class="controls">
													      <input type="button" id="edit_save_${entity.id}" class="btn btn-primary  fav-hide-edit-bt" value="修改" onclick="javascript:editsave(${entity.id})"/>
				                						  <input type="button" class="btn btn-primary" value="取消" onclick="javascript:editcancel(${entity.id})"/>
													    </div>
													 </div>
												</form>
					                		</#if>
					                	</td>
					                </tr>
				              	</#list>
				              </tbody>
				            </table>
				            <#-- 分页标签 BEGIN -->
						    <#import "/share/pager_.ftl" as p>
						    <@p.page pager=pager link="/admin/favorites?" showPageNum=9 anchor=""/>
						    <#-- 分页标签 END -->
		                </div>
		                <!-- favorites end ================================================== -->
		                <!-- favorites-tags start ================================================== -->
		                <div class="tab-pane" id="fav-tags">
		                  <#list tagsPager.resultList as entity>
						 	<span id="show_fav_tags_${entity_index}" class="fav_tag">
							 	<a href="/admin/favorites/?tag=<@ose_urlcode.URLCode str='${entity.name}' deOrEncode='encode'/>"  class="mediumtag">
							 		<span class="badge badge-info tagwrap pt5 pb5 mg-t-5 mg-b-5">
										${entity.name?html}(${entity.favorite_num})
									</span>
							 	</a>
							 	<a href="javascript:edittag('${entity_index}','${entity.name}')" ><img alt="Edit_link" src="/images/edit_link.gif" title="修改标签"></a>
							 	<a href="javascript:deltag('${entity.name}')" ><img alt="Edit_link" src="/images/del_link.gif" title="删除标签"></a>
						 	</span>
						 	<span id="hide_fav_tags_${entity_index}" class="fav_tag" style="display:none;">
							 	<input type='text' name="tag" value="${entity.name}" class="fav_tag_input" maxlength="12" id="fav_tag_input_${entity_index}">
							 	<a href="javascript:savetag('${entity_index}','${entity.name}')" ><img alt="Edit_link" src="/images/small-ok.gif" title="修改"></a>
							 	<a href="javascript:canceledittag('${entity_index}')" ><img alt="Edit_link" src="/images/small-cancel.gif" title="取消"></a>
						 	</span>
						 </#list>
		                </div>
		                 <!-- favorites-tags end ================================================== -->
		                <div class="tab-pane" id="fav-add">
							<div id="favorite_add_hide" style="padding-top: 1px;">
								 <form action="/favorites/add" method="post" id="favorite_add_form">
								 		<div class="input-prepend">
										  <span class="add-on">标题</span>
										  <input class="span4" name="title" type="text" id="favorite_add_form_title">
										</div>
										<br/>
										<div class="input-prepend">
										  <span class="add-on">链接</span>
										  <input name="url" type="text" id="favorite_add_form_url" class="span4" value="http://"/>
										</div>
										<br/>
										<div class="input-prepend ">
										  <span class="add-on">标签</span>
										  <input name="tags" type="text" id="favorite_add_tags" class="span5" placeholder="多个标签请以'空格' 隔开,标签最多只能有5个"/>
										  <#if tagsPager.totalRecords=0>
										  	  <button class="btn" type="button">
										  		暂无标签可选
										  	  </button>
										  <#else>
										  	  <button class="btn" type="button" onclick="javascript:getTags()">
											  	<a id="TagsSwitcher" status="off">选择标签↓</a>
											  </button>
										  </#if>
										  <div id="fav_tags_list"></div>
										</div>
										<br/>
						              	<input type="button" class="btn btn-primary" id="favorite_add_btn" value="保存" onclick="javascript:favorite_add_do()"/>
						              	<input type="button" class="btn btn-primary" id="favorite-add-btn-reset" value="清空标签" onclick="javascript:favorite_add_reset()"/>
						          </form>
							</div>
		                </div>
		              </div>
		            </div>
                </div>
          </div>
          <!-- back-right end ================================================== -->
          <script src="http://bebop.qiniudn.com/scripts/bootstrap.min.js" type="text/javascript"></script>
          <script src="http://bebop.qiniudn.com/scripts/jquery.form.js" type="text/javascript"></script>
          <script type="text/javascript">
          		$(function() {
          			$(".fav-hide-edit-bt").attr("disabled",false);
          		});
				function editfav(id) {
					$("#favorite_show_"+id).hide();$("#favorite_hide_"+id).show();
				}
				function editcancel(id) {
					$("#favorite_hide_"+id).hide();$("#favorite_show_"+id).show();
				}
				function editsave(id) {
					$("#edit_save_"+id).attr("value","正在保存...");
					$.jBox.tip("正在进行保存操作...", 'loading');
					$("#edit_save_"+id).attr("disabled",true);
					$("#links_edit_form_"+id).ajaxSubmit({ 
				    	dataType:'json',
				        success: function(data) {
				        	if(data.status) {
				        		location.reload();
				        	}else {
				        		$.jBox.closeTip();
				        		$.jBox.tip(data.msg,'error',{timeout:1000});
				        		$("#edit_save_"+id).attr("value","保存");
								$("#edit_save_"+id).attr("disabled",false);
				        	}
				        }
				    });
				}
				
				function deltag(tag) {
					if(confirm("确定要删除此标签?")) {
						ajax_post_json("/admin/favorite_tags/del",{tag:tag},function(data) {
							if(data.status) {
								location.href="/admin/favorites";
							}					
						});
					}
				}
				
				function edittag(index,tag) {
					$("#favorite_tags span[id*=hide_fav_tags_]").hide();
					$("#favorite_tags span[id*=show_fav_tags_]").show();
					$("#show_fav_tags_"+index).hide();
					$("#fav_tag_input_"+index).attr("value",tag);
					$("#hide_fav_tags_"+index).show();
					$("#fav_tag_input_"+index).focus();
				}
				
				function canceledittag(index) {
					$("#show_fav_tags_"+index).show();
					$("#hide_fav_tags_"+index).hide();
				}
				
				function savetag(index,tag) {
					var totag = $("#fav_tag_input_"+index).val();
					if($.trim(tag)!="" && $.trim(totag)!="") {
						if($.trim(tag)==$.trim(totag)) {
							$("#hide_fav_tags_"+index).hide();
							$("#show_fav_tags_"+index).show();
							return;
						}else {
							$.jBox.tip("正在进行保存操作...", 'loading');
							ajax_post_json("/admin/favorite_tags/save",{tag:$.trim(tag),totag:$.trim(totag)},function(data) {
								if(data.status) {
									location.href="/admin/favorites";
								}
								$.jBox.closeTip();					
							});	
						}
					}else {
						$("#fav_tag_input_"+index).focus();
					}
				}
				function addFav() {
					$("#favorite_add_hide").show();
					$("#favorite_add_form_title").focus();
				}
				function fav_add_cancel() {
					$("#favorite_add_hide").hide();
					$('#favorite_add_msg').html("");
				}
				function favorite_add_do() {
					$("#favorite_add_btn").attr("value","正在保存...");
					$.jBox.tip("正在进行保存操作...", 'loading');
					$("#favorite_add_btn").attr("disabled",true);
					$("#favorite_add_form").ajaxSubmit({ 
				    	dataType:'json',
				        success: function(data) {
				        	if(data.status) {
				        		location.href="/admin/favorites";
				        	}else {
				        		$.jBox.closeTip();
				        		$.jBox.tip(data.msg,'error',{timeout:1000});
				        		$("#favorite_add_btn").attr("value","保存");
								$("#favorite_add_btn").attr("disabled",false);
				        	}
				        }
				    });
				}
				function delfav(id) {
					if(confirm("确定要取消此收藏?")) {
						ajax_post_json("/admin/favorites/del",{id:id},function(data) {
							if(data.status) {
								location.href="/admin/favorites";
							}					
						});
					}
				}
				function favorite_add_reset() {
          			$("#favorite_add_tags").attr("value","");
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
