package cn.cforfun.shiro.sevice;

import cn.cforfun.shiro.dao.PermissionDao;
import cn.cforfun.shiro.model.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By C  2019-09-14 14:33
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    public List<SysPermission> findPerssionByRoleName(String roleName){
        return permissionDao.findPerssionByRoleName(roleName);
    }

}
