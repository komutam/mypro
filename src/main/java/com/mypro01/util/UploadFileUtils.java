package com.mypro01.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import com.mypro01.controller.BoardController;
//파일업로드 클래스 : 업로드 시에 자동적인 폴더의 생성이 가능하며 파일 저장은 UUID를 이용해서 처리하고 썸네일 이미지를 생성하는 기능처리가 되도록
public class UploadFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception{
		UUID uid = UUID.randomUUID();
		
		String savedName = uid.toString() + "_" + originalName;
		String savedPath = calcPath(uploadPath); //저장될 경로 계산
		File target = new File(uploadPath + savedPath, savedName);
		FileCopyUtils.copy(fileData, target);//원본파일 저장하는 부분
		
		String formatName=originalName.substring(originalName.lastIndexOf(".")+1);
		String uploadedFileName = null;
		
		//이미지 타입의 파일인 경우에는 썸네일을 생성하고 그렇지 않은 경우에는 makeIcon()를 통해서 결과를 만들어 낸다.
		if(MediaUtils.getMediaType(formatName) != null) {
			uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
		}else {
			uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
		}
		return uploadedFileName;
	}
	
	//makeIcon은 경로 처리를 하는 문자열의 치환용도
	private static String makeIcon(String uploadPath, String path, String fileName) {
		String iconName = uploadPath + path + File.pathSeparator + fileName;
		
		// 섬네일이 존재하는 윈도우 전체 경로에서 윈도우의 파일 구분자인 \ 를 브라우저에서 제대로 인식하지 못하기 때문에 U에서 사용하는 / 로 치환한 다음 반환.
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		makeDir(uploadPath, yearPath, monthPath, datePath);
		logger.info(datePath);
		return datePath;
	}
	
	//위의 makeDir(uploadPath, yearPath, monthPath, datePath); 여기로 보내서 폴더가 있는지 여부 확인후 없으면 만들도록하는 함수
	private static void makeDir(String uploadPath, String... paths) {
		if(new File(paths[paths.length-1]).exists()) {
			return;
		}
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			if(!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}
	
	//썸네일 생성작업
	//기본경로(uploadPath)와 년/월/일 폴더(path) 현재 업로드 된 파일의 이름을 이용
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception{
		//BufferedImage는 실제 이미지가 아닌 메모리상의 이미지를 의미하는 객체로 원본 파일을 메모리상으로 로딩하고 정해진 크기에 맞게 작은 이미지 파일에 원본 이미지를 복사
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath+path, fileName));
		//'FIT_TO_HEIGHT' 이 설정은 썸네일 이미지 파일의 높이를 뒤에 지정된 100PX로 동일하게 만들어주는 역할
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		
		//썸네일 이미지의 파일명에는 UUID 값이 사용된 이후에 반드시 's_'로 시작하도록 설정, s_'로 시작하면 썸네일 이미지이고 's_'를 제외했을 떄는 원본파일의 이름이 되도록 하기 위해서
		String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;
		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile); // toUpperCase() 함수는 입력받은 인자 값을 영문 대문자로 변환하여 리턴
		
		//메소드의 리턴 시 문자열을 치환하는 이유는 브라우저에서 윈도우 경로로 사용하는 '\' 문자가 정상적인 경로로 인식되지 않기 떄문에 '/'로 치환
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
}
