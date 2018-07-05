package com.mypro01.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.mypro01.util.MediaUtils;
import com.mypro01.util.UploadFileUtils;

@Controller
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
	public void updateForm() {

	}
	
	@RequestMapping(value="/uploadForm", method=RequestMethod.POST)
	public String updateForm(MultipartFile file, Model model) throws Exception{
		logger.info("originalName : "+file.getOriginalFilename());
		logger.info("size : "+file.getSize());
		logger.info("contentType :"+file.getContentType());
		
		String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
		model.addAttribute("savedName", savedName);
		
		return "uploadResult";
	}
	
	private String uploadFile(String originalFilename, byte[] fileData) throws Exception{
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_"+originalFilename;
		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(fileData, target);
		return savedName;
	}
	
	@RequestMapping(value="/uploadAjax", method=RequestMethod.GET)
	public void uploadAjax() {
		
	}
	
	/*최종적인 리턴값은 내부적으로 UploadFileUtils의 uploadFile()을 사용하도록 수정.
	위의 코드를 이용해서 다시 /uploadAjax를 호출하면 모든 첨부파일이 /년/월/일로 시작하는 문자열을 전달받는 것을확인할 수 있다*/
	@ResponseBody
	@RequestMapping(value="/uploadAjax", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception{
		logger.info("originalName : " + file.getOriginalFilename());
		
		return new ResponseEntity<>(UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
	}
	
	//파일전송 기능
	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{//브라우저에서 전송받기를 원하는 파일의 이름을 받는다. 파일의 이름은 '/년/월/일/파일명' 형태로 입력받는다.
		//displayFile()의 리턴타입은 ResponseEntity<byte[]>로 작성. 결과는 실제로 파일의 데이터가 된다.
		//메소드의 선언부에는 @ResponseBody를 이용해서 byte[] 데이터가 그대로 전송될 것임을 명시.
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		logger.info("FILE NAME: " + fileName);
		
		try {
			//파일 이름에서 확장자를 추출하고
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders(); //헤드 => 이진데이터를 어떠한 type으로 표현할 것인가?
			in = new FileInputStream(uploadPath + fileName);
			
			//이미지 타입의 파일인 경우는 적절한 MIME 타입을 지정
			if(mType != null) {
				headers.setContentType(mType);
			}else {//이미지가 아닌 경우는 MIME 타입을 다운로드 용으로 사용되는 'application/octet-stream'으로 지정
				//브라우저는 이 MIME 타입을 보고 사용자에게 자동으로 다운로드 창을 열어준다.
				fileName = fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition",
						"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			//new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			//다운로드 할 때 사용자에게 보이는 파일의 이름이므로 한글 처리를 해서 전송해야한다.
			//한글 파일의 경우 다운로드 하면 파일의 이름이 깨져서 나오기 때문에 반드시 인코딩 처리를 할 필요가 있음
			}
			//실제로 데이터 읽는 부분은 commons 라이브러리의 기능을 활용해서 대상 파일에서 데이터를 읽어내는 IOUtils.toByteArray() 이다.
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			in.close();
		}
		return entity;
	}
	
	//이미지의 경우는 썸네일의 이름이고 일반파일은 실제 이름이 된다.
	//이미지 파일이 확인되면 원본 파일을 먼저 삭제하고 이후에 파일을 삭제하는 방식으로 작성
	@ResponseBody
	@RequestMapping(value="/deleteFile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName) throws Exception{
		logger.info("delete file : " + fileName);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		if(mType != null) {
			String front = fileName.substring(0, 12);
			String end = fileName.substring(14);
			new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete();	
		}
		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
}
