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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final static Logger logger = LoggerFactory.getLogger(ThisController.class);

	@RequestMapping("/")
	public String login() {
		logger.info("進入login頁面");
		return "MainPage";
	}

	@ResponseBody
	@PostMapping(value = "/doLogin")
	public String find(Model model, ThisEntity entity, HttpServletRequest httpServletRequest) {
		// 判斷驗證碼
		String captchaId = (String) httpServletRequest.getSession().getAttribute("vrifyCode");
		String parameter = httpServletRequest.getParameter("code");
		logger.info("前端輸入的驗證碼=" + captchaId + "," + "產生出的驗證碼=" + parameter);
		if (!captchaId.equals(parameter)) {
			logger.info("錯誤的驗證碼");
			return "errorcode";
		} else {
			ThisEntity xx = service.find(entity.getUserid(), entity.getUserpw());
			logger.info("輸入的帳號" + entity.getUserid() + "," + "輸入的密碼" + entity.getUserpw());
			if (xx == null) {
				logger.info("錯誤的帳號與密碼");
				return "erroruserinfo";
			} else {
				// 抓最後登入時間
				String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
				logger.info("登入成功抓取現在登入時間=" + timeStamp);
				service.update(entity, entity.getUserid(), timeStamp);
				return "true";
			}
		}
	}

	@GetMapping(value = "/MainMenu")
	public String name() {
		logger.info("跳轉至下一頁");
		return "MainMenu";
	}

	// 獲取驗證碼
	@RequestMapping("/getCode")
	public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// 生產驗證碼字串並儲存到session中
			String createText = defaultKaptcha.createText();
			httpServletRequest.getSession().setAttribute("vrifyCode", createText);
			logger.info("驗證碼存進session");
			// 使用生產的驗證碼字串返回一個BufferedImage物件並轉為byte寫入到byte陣列中
			BufferedImage challenge = defaultKaptcha.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
			logger.info("產生的驗證碼=" + createText);
			// 存進cookie給前端比對
			Cookie cookie = new Cookie("createText", createText);
			httpServletResponse.addCookie(cookie);
		} catch (IllegalArgumentException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// 定義response輸出型別為image/jpeg型別，使用response輸出流輸出圖片的byte陣列
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		httpServletResponse.setHeader("Cache-Control", "no-store");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		httpServletResponse.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
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
