package cn.cforfun.shiro.sevice;

import cn.cforfun.shiro.dao.RoleDao;
import cn.cforfun.shiro.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create By C  2019-09-14 14:44
 */
@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public SysRole findById(int id){
        if(roleDao.findById(id)!=null){
            return roleDao.findById(id).get();
        }
        else
            return null;
    }
}
