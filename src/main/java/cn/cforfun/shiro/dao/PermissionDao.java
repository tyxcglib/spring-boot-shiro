package cn.cforfun.shiro.dao;

import cn.cforfun.shiro.model.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Create By C  2019-09-14 14:30
 */
public interface PermissionDao  extends JpaRepository<SysPermission,Integer> {
    /**
     *  根据角色名查找权限
     */
    @Query(nativeQuery = true,value = "select  p.* from sys_role r,sys_role_permission pr,sys_permission p where r.role=?1 and r.id=pr.role_id and pr.permission_id=p.id;")
    public List<SysPermission> findPerssionByRoleName(String roleName);
 }
