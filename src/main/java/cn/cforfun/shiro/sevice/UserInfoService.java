package cn.cforfun.shiro.sevice;

import cn.cforfun.shiro.dao.UserInfoDao;
import cn.cforfun.shiro.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    public UserInfo findByUsername(String username) {
        return userInfoDao.findByUsername(username);
    }

    public void addUser(UserInfo info){
        userInfoDao.save(info);
    }
    public UserInfo findByGithubId(String githubid) {
        return userInfoDao.findByGithubId(githubid);
    }
}
