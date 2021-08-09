package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ThisEntity;
import com.example.demo.repository.ThisRepository;

@Service
public class ThisService {
	@Autowired
	ThisRepository repository;
	
	
	
	
	public ThisEntity find(String userid,String userpw) {
//		System.out.println(userid+" "+userpw);
		return repository.findUser(userid,userpw);
	}

//	public ThisEntity update(String userid) {
//		repository.update(userid);
//		// TODO Auto-generated method stub
//		return null ;
//	}

	

	public void update(ThisEntity entity,String userid,String timestamp) {
//		ThisEntity entity=new ThisEntity();
//		System.out.println(userid+" "+timestamp);
		entity.setUserid(userid);
		entity.setLastlogindatetime(timestamp);
		
		repository.save(entity);
		
		
	}
	
	
	

}
