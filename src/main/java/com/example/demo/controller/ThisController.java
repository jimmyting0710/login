package com.example.demo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.ThisEntity;
import com.example.demo.service.ThisService;
import com.google.code.kaptcha.impl.DefaultKaptcha;

@Controller
public class ThisController {

	@Autowired
    DefaultKaptcha defaultKaptcha;
	@Autowired
	ThisService service;

	@RequestMapping("/")
	public String login() {
		return "/MainPage";
	}
@ResponseBody
	@PostMapping(value = "/doLogin")
	public String find(Model model, ThisEntity entity,HttpServletRequest httpServletRequest) {
		
        //判斷驗證碼
		String captchaId = (String) httpServletRequest.getSession().getAttribute("vrifyCode");
        String parameter = httpServletRequest.getParameter("code");
//        System.out.println(captchaId+","+parameter);
//        log.info("Session  vrifyCode ---->"+captchaId+"---- form code --->"+parameter);
        if (!captchaId.equals(parameter)) {
//            log.info("錯誤的驗證碼");
        	
            return "errorcode";
        } else {
//           System.out.println(entity.getUserid()+" "+entity.getUserpw());
//		ThisEntity newupdate = service.update(entity.getUserid());
		ThisEntity xx = service.find(entity.getUserid(), entity.getUserpw());
	
		if (xx == null) {
			return "erroruserinfo";

		} else {
//			model.addAttribute("tt", xx);
			//抓最後登入時間
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
//	        System.out.println(timeStamp);
	        service.update(entity,entity.getUserid() ,timeStamp);
//	        System.out.println("a");
	        return "true";
			
		}
        }
	}

	@GetMapping(value = "/MainMenu")
	public String name() {
		return "/MainMenu";
		
	}
	
	
	
	
	
	//獲取驗證碼
	  @RequestMapping("/getCode")
	    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
	        byte[] captchaChallengeAsJpeg = null;
	        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
	        try {
	            //生產驗證碼字串並儲存到session中
	            String createText = defaultKaptcha.createText();
	            httpServletRequest.getSession().setAttribute("vrifyCode", createText);
	            //使用生產的驗證碼字串返回一個BufferedImage物件並轉為byte寫入到byte陣列中
	            BufferedImage challenge = defaultKaptcha.createImage(createText);
	            ImageIO.write(challenge, "jpg", jpegOutputStream);
	            System.out.println(createText);
	            Cookie cookie = new Cookie("createText",createText);
	            httpServletResponse.addCookie(cookie);
	        } catch (IllegalArgumentException e) {
	            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
	            return;
	        }
	        //定義response輸出型別為image/jpeg型別，使用response輸出流輸出圖片的byte陣列
	        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
	        httpServletResponse.setHeader("Cache-Control", "no-store");
	        httpServletResponse.setHeader("Pragma", "no-cache");
	        httpServletResponse.setDateHeader("Expires", 0);
	        httpServletResponse.setContentType("image/jpeg");
	        ServletOutputStream responseOutputStream =
	                httpServletResponse.getOutputStream();
	        responseOutputStream.write(captchaChallengeAsJpeg);
	        responseOutputStream.flush();
	        responseOutputStream.close();
	    }
	  
//	//驗證碼驗證
//	    @RequestMapping("/checkCode")
//	  
////	    @ResponseBody
//	    public String imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
//	        String captchaId = (String) httpServletRequest.getSession().getAttribute("vrifyCode");
//	        String parameter = httpServletRequest.getParameter("code");
//	        System.out.println(captchaId+","+parameter);
////	        log.info("Session  vrifyCode ---->"+captchaId+"---- form code --->"+parameter);
//	        if (!captchaId.equals(parameter)) {
////	            log.info("錯誤的驗證碼");
//	        	
//	            return "redirect:/";
//	        } else {
//	            return "redirect:/doLogin";
//	        }
//	    }
}
