package com.codestates.seb.ha3server.Repository;

import com.codestates.seb.ha3server.Domain.LoginSignUp;
import com.codestates.seb.ha3server.Entity.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class LoginRepository {

    private final EntityManager entityManager;

    @Autowired
    public LoginRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List<ServiceUser> FindUserList(){
        // DB service_user 테이블에 모든 유저 정보를 리턴합니다.
        // TODO :
        return null;
    }

    public ServiceUser FindById(long id){
        // DB service_user 테이블에 매개변수 id와 일치하는 유저 정보를 리턴합니다.
        // TODO :
        return null;
    }

    public List<ServiceUser> FindByEmail(String email){
        // DB service_user 테이블에 매개변수 email과 일치하는 유저 정보를 리턴합니다.
        // TODO :
        return null;
    }

    public void CreateServiceUser(LoginSignUp loginSignUp, Long id){
        // DB service_user 테이블에 매개변수 loginSignUp과 id에 데이터를 사용하여 유저 정보를 저장합니다.
        // TODO :
        ServiceUser serviceUser = null;
    }


}
