package com.example.demo.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ThisEntity;
@Repository
public interface ThisRepository extends CrudRepository<ThisEntity, String>{
	//找使用者
	@Query(value="select userid,userpw,lastlogindatetime from userinfo c where c.userid=?1 and c.userpw=?2",nativeQuery=true)
	 ThisEntity findUser(String userid,String userpw);

//	@Query(value="UPDATE userinfo c SET c.lastlogindatetime=?2 WHERE c.userid=?1",nativeQuery=true)
//	ThisEntity update(String userid, String timestamp);
	
	//mysql
//	@Transactional
//	@Modifying
//	@Query(value="UPDATE userinfo SET lastlogindatetime=now() WHERE userid=?1",nativeQuery=true)
//	void update(String userid);
	


	
}
