package org.cgz.oseye.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
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

@Controller("fileUploadByQiNiuController")
public class FileUploadByQiNiuController extends BaseController{

	private final List<String> allowImageType = Arrays.asList("image/bmp","image/gif","image/jpg","image/jpeg","image/pjpeg","image/png","image/n-png","image/x-png"); //允许上传的图片类型
	private final long postAllowImageSize = 200*1024; //博客文章允许上传的图片大小
	private final long portraintAllowImageSize = 1000*1024; //头像允许上传的图片大小
	
	private final String bucketName  = "bebop";
	
	@Resource
	private UsersService usersService;
	
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
				String dstRealPath = getSession().getServletContext().getRealPath(filePath.replace("_temp",""));
				String fileRealPath = getSession().getServletContext().getRealPath(filePath);
				
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
				} catch (IOException e) {
					e.printStackTrace();
					returnJson(response, false, "头像更新出错!");
				}
				usersService.saveUserPortraint(getSessionUsers().getId(), filePath.replace("_temp", ""));
				Users user = getSessionUsers();
				if(user!=null) {
					user.setPortraint(usersService.findById(user.getId()).getPortraint());//更新session中用户的头像地址
					getSession().setAttribute(SystemConstant.SESSIONUSER, user);
				}
				returnJson(response, true, "头像更新成功!");
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
