package cn.cforfun.shiro.dao;

import cn.cforfun.shiro.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoDao extends JpaRepository<UserInfo,Integer> {

    /**通过username查找用户信息*/
    public UserInfo findByUsername(String username);


    @Query("select u from UserInfo u where githubid=?1")
    UserInfo findByGithubId(String githubid);
}
