package org.cgz.oseye.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.coobird.thumbnailator.Thumbnails;

import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.UsersService;
import org.cgz.oseye.utils.FileUploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.qbox.Config;
import com.qiniu.qbox.auth.DigestAuthClient;
import com.qiniu.qbox.rs.PutAuthRet;
import com.qiniu.qbox.rs.RSClient;
import com.qiniu.qbox.rs.RSService;

@Controller("fileUploadController")
@RequestMapping("/admin")
public class FileUploadController extends BaseController {
	
	private final List<String> allowImageType = Arrays.asList("image/bmp","image/gif","image/jpg","image/jpeg","image/pjpeg","image/png","image/n-png","image/x-png"); //允许上传的图片类型
	private final long postAllowImageSize = 200*1024; //博客文章允许上传的图片大小
	private final long portraintAllowImageSize = 1000*1024; //头像允许上传的图片大小
	
	private final String bucketName  = "bebop";
	
	private final String QINIUDOMAIN = "http://bebop.qiniudn.com";
	
	@Resource
	private UsersService usersService;
	
	/**
	 * 编辑器上传图片
	 * kindeditor上传图片 图片名称必须是imgFile
	 */
	@RequestMapping(value="/upload/editor-image",method = RequestMethod.POST)
	public void editorImageUpload(@RequestParam(required=false) MultipartFile imgFile,HttpServletResponse response,HttpServletRequest request) {
		String errMsg = validateImage(imgFile, response);
		if(errMsg!=null) {
			returnJson(response, errMsg);
			return;
		}
		if(!validateFileSize(imgFile, postAllowImageSize)) {
			returnJson(response, "{\"error\":1,\"message\":\"文件大小不能超过200k!\"}");
			return;
		}
		Users user = getSessionUsers();
		if(user!=null) {
			Integer userId = user.getId();
			String uuid = UUID.randomUUID().toString();
			//String saveFileName = uuid + "_" +userId+"_post"
			String savePath = "/uploads/user/"+(userId/2000)+"/"+userId;//每2000个用户一个目录
			String realPath = getSession().getServletContext().getRealPath(savePath)+"/";
			String uploadImageFileName = uuid+"_"+userId+"_post"+FileUploadUtils.getFileExt(imgFile.getOriginalFilename());
			File file = new File(realPath,uploadImageFileName);
			if(!file.exists()) file.mkdirs();
			try {
				//Integer imgWidth = FileUploadUtils.getImgWidth(imgFile.getInputStream());
				try {
					imgFile.transferTo(file);
				} catch (Exception e) {
					System.out.println("图片上传产生异常....");
				}
//				if(imgWidth >900) {
//					Thumbnails.of(imgFile.getInputStream()).size(900, 900).toFile(file);
//				}else {
//					try {
//						imgFile.transferTo(file);
//					} catch (Exception e) {
//						System.out.println("图片上传产生异常....");
//					}
//				}
				//七牛上传
				DigestAuthClient conn = new DigestAuthClient();
				RSService rs = new RSService(conn, bucketName);
				PutAuthRet putAuthRet = rs.putAuth();
				String uploadUrl = putAuthRet.getUrl();
				RSClient.putFile(uploadUrl, bucketName, uploadImageFileName, "",realPath+"/"+uploadImageFileName, "", null);
			} catch (Exception e1) {
				e1.printStackTrace();
				returnJson(response, "{\"error\":1,\"message\":\"图片上传异常!\"}");
				return;
			}
			returnJson(response, "{\"error\":0,\"url\":\""+QINIUDOMAIN+"/"+uploadImageFileName+"\",\"message\":\"上传成功!\"}");
		}
	}
	
	/**
	 * 头像上传
	 * @param imgFile
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @param response
	 */
	@RequestMapping(value="/upload/portraint-upload",method = RequestMethod.POST)
	public void portraintImageUpload(@RequestParam MultipartFile imgFile,
									 HttpServletResponse response) {
		String errMsg = validateImage(imgFile, response);
		if(errMsg!=null) {
			returnJson(response, errMsg);
			return;
		}
		if(!validateFileSize(imgFile, portraintAllowImageSize)) {
			returnJson(response, "{\"error\":1,\"message\":\"文件大小不能超过1M!\"}");
			return;
		}
		Users user = getSessionUsers();
		if(user!=null) {
			Integer userId = user.getId();
			String savePath = "/uploads/user/"+(userId/2000)+"/"+userId;//每2000个用户一个目录
			String realPath = getSession().getServletContext().getRealPath(savePath);
			String portraintName = userId+"_portraint_temp"+FileUploadUtils.getFileExt(imgFile.getOriginalFilename());
			File file = new File(realPath,portraintName);
			if(!file.exists()) file.mkdirs();
			try {
				imgFile.transferTo(file);
			} catch (Exception e) {
				System.out.println("图片上传产生异常....");
			}
			returnJson(response, "{\"error\":0,\"message\":\""+savePath+"/"+portraintName+"?r="+Math.random()+"\"}");
		}
	}
	
	/**
	 * 头像保存
	 * @param imgFile
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @param response
	 */
	@RequestMapping(value="/upload/portraint-save{type}",method = RequestMethod.POST)
	public void portraintImageSave(@PathVariable("type") String type,
								   @RequestParam String filePath,
								   @RequestParam Integer left,
								   @RequestParam Integer top,
								   @RequestParam Integer width,
								   @RequestParam Integer height,
								   HttpServletResponse response) {
		
			if(filePath!=null && !"".equals(filePath)) {
				filePath = filePath.substring(0, filePath.lastIndexOf("?"));
				//683_portraint.jpg
				String fileName = filePath.replace("_temp","").substring(filePath.lastIndexOf("/")+1);
				String dstRealPath = getSession().getServletContext().getRealPath(filePath.replace("_temp",""));
				String fileRealPath = getSession().getServletContext().getRealPath(filePath);
				
				String uuid = UUID.randomUUID().toString();
				//xxxxxxxxxx_683_portraint.jpg
				String saveFileName = uuid+"_"+fileName;
				try {
					if(type==null || "".equals(type)) {	  //保存选中区域
						if(width>150) {	//如果图片尺寸大于150 就压缩成150像素大小
							Thumbnails.of(fileRealPath).sourceRegion(left, top, width, height).size(150, 150).toFile(dstRealPath);
						}else {
							Thumbnails.of(fileRealPath).sourceRegion(left, top, width, height).size(width, height).toFile(dstRealPath);
						}
						Thumbnails.of(fileRealPath).sourceRegion(left, top, width, height).size(50, 50).toFile(getSession().getServletContext().getRealPath(filePath.replace("_temp","_50")));
					}
					if(type!=null && "all".equals(type)) {//保存整张图片
						Thumbnails.of(fileRealPath).size(150, 150).toFile(dstRealPath);
						Thumbnails.of(fileRealPath).size(50, 50).toFile(getSession().getServletContext().getRealPath(filePath.replace("_temp","_50")));
					}
					//七牛上传
					DigestAuthClient conn = new DigestAuthClient();
					RSService rs = new RSService(conn, bucketName);
					PutAuthRet putAuthRet = rs.putAuth();
					String uploadUrl = putAuthRet.getUrl();
					RSClient.putFile(uploadUrl, bucketName, saveFileName, "",dstRealPath, "", null);
				} catch (Exception e) {
					e.printStackTrace();
					returnJson(response, "{\"status\":false,\"msg\":\"头像更新出错!\"}");
				}
				String urlSaveFileName = "http://"+Config.DEMO_DOMAIN + "/" + saveFileName;
				usersService.saveUserPortraint(getSessionUsers().getId(), urlSaveFileName);
				Users user = getSessionUsers();
				if(user!=null) {
					user.setPortraint(usersService.findById(user.getId()).getPortraint());//更新session中用户的头像地址
					getSession().setAttribute(SystemConstant.SESSIONUSER, user);
				}
				returnJson(response, "{\"status\":true,\"msg\":\"头像更新成功!\"}");
			}
	}
	
	
	/**
	 * 图片文件校验
	 * @param imgFile
	 * @param response
	 */
	private String validateImage(MultipartFile imgFile,HttpServletResponse response) {
		//上传的文件不能为空
		if(imgFile.isEmpty()) {
			return "{\"error\":1,\"message\":\"请添加需要上传的文件!\"}";
		}
		//文件必须是图片类型
		try {
			if(FileUploadUtils.validateFileType(imgFile.getContentType(), allowImageType)==false || FileUploadUtils.isImage(imgFile.getInputStream())==false) {
				return "{\"error\":1,\"message\":\"文件类型有误!\"}";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "{\"error\":1,\"message\":\"上传文件失败!\"}";
		}
		return null;
	}
	
	/**
	 * 验证文件的大小
	 * @param allowSize
	 * @return
	 */
	private boolean validateFileSize(MultipartFile file,long allowSize) {
		if(file.getSize() <allowSize && file.getSize()>0) {
			return true;
		}
		return false;
	}
}
