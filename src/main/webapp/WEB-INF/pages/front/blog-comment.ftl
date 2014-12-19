<div class="comments-item divbord comment" id="${comment.id}">
    <table>
      <tr>
        <td rowspan="2" valign="top" width="50px">
            <div  class="comment-item-head">
                <a href="/blog/${auth.name}" target="blank" class="thumbnail">
                    <img src="${auth.portraint}" style="width:35px;height:35px;" border="0">
                </a>
            </div>
        </td>
        <td colspan="2" width="800px">
             <div class="pm-item-summary">
              <p>
                   <a href="/blog/${comment.users.name}" target="_blank" >@${comment.users.nickname}</a> 
                   <span class="muted">发表于</span> <span class="timeago muted" title="${comment.createTime?string('yyyy-MM-dd HH:mm:ss')}">${comment.createTime?string('yyyy-MM-dd HH:mm:ss')}</span>
                   <#if comment.visible==1 && post.canComment==1>
						<a href="javascript:replyInline(${blog.id},${post.id},${comment.id})">引用</a>
				   </#if>                                                                                                      
              </P> 
              <p>
              	<div class="quotewap">
                  <#import "/share/comments-nest.ftl" as nest>
				  <#if comment.visible==0>
					 	<span style="text-align:center;">此评论已被隐藏..</span>
					 	<#else>
					 	<@nest.commentnest comment=comment />
					 	 ${comment.content}
				  </#if>
			   </div>
              </p>
            </div>
        </td>
      </tr>
      <tr>
        <td></td>
        <td>
        	<div id="inline_reply_of_${blog.id}_${post.id}_${comment.id}" class="inline_reply hide">
				<div class="BlogCommentForm">
					<div class="alert alert-info" style="width:510px;">
					  	评论内容:　(不小于5个字符,最大800个字符) 
					</div>
					<textarea name="content" id="inline_comment_content_${comment.id}" style="width:550px;height:60px;"></textarea><br>
					<input type="submit" value="回复" id="btn_comment_${comment.id}" class="btn btn-primary btn-small"> 
					<input type="button" value="关闭" id="btn_close_inline_reply" class="btn btn-primary btn-small" onclick="javascript:replyClose(${blog.id},${post.id},${comment.id})"> 
					<span id="inline_comment_add_${comment.id}" class="alert alert-error hide"></span>
				</div>
			</div>
        </td>
      </tr>
    </table>
    <br/>
</div>