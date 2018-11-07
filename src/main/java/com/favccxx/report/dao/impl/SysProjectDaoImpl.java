package com.favccxx.report.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysProjectDao;
import com.favccxx.report.model.SysProject;

@Repository
public class SysProjectDaoImpl implements SysProjectDao {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void addSysProject(SysProject sysProject) {
		hibernateTemplate.save(sysProject);
	}

	@Override
	public void saveSysProject(SysProject sysProject) {
		hibernateTemplate.saveOrUpdate(sysProject);
	}

	@Override
	public void deleteSysProject(long projectId) {
		hibernateTemplate.delete(findbyId(projectId));
	}

	@Override
	public void updateSysProject(SysProject sysProject) {
		hibernateTemplate.update(sysProject);
	}

	@Override
	public List<SysProject> listProjects(SysProject sysProject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysProject findbyId(long projectId) {
		return hibernateTemplate.get(SysProject.class, projectId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysProject> listAllProjects() {
		List<SysProject> list = (List<SysProject>) hibernateTemplate.find("from SysProject");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysProject> listAllValidProjects() {
		StringBuffer sb = new StringBuffer("from SysProject where projectStatus='INUSE'");
		List<SysProject> list = (List<SysProject>) hibernateTemplate.find(sb.toString());
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysProject> listProjectsByUserId(long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct  sp from SysProject sp, SysUserProject sup where sp.createUserId=").append(userId)
				.append(" or (sp.id=sup.projectId and sup.userId=").append(userId).append(")");
		List<SysProject> list = (List<SysProject>) hibernateTemplate.find(sb.toString());
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysProject> listValidProjectsByUserId(long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct  sp from SysProject sp, SysUserProject sup where sp.createUserId=").append(userId)
				.append(" and sp.projectStatus='INUSE' ").append(" or (sp.id=sup.projectId and sup.userId=")
				.append(userId).append(")");
		List<SysProject> list = (List<SysProject>) hibernateTemplate.find(sb.toString());
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysProject> listProjects(long userGroupId, boolean allStatus) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct  sp from SysProject sp, SysProjectLinkUserGroup pug where sp.id=pug.projectId")
				.append(" and pug.userGroupId=").append(userGroupId);
		if (!allStatus) {
			sb.append(" and sp.projectStatus='INUSE' ");
		}

		List<SysProject> list = (List<SysProject>) hibernateTemplate.find(sb.toString(), userGroupId);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysProject> listByGroupIds(List<Long> groupIds, boolean allStatus) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct  sp from SysProject sp, SysProjectLinkUserGroup plug, SysUserGroup sug where sp.id=plug.projectId and plug.userGroupId=sug.id");
		sb.append(" and ( ");
		
		for(int i=0; i<groupIds.size(); i++) {
			sb.append(" plug.userGroupId=").append(groupIds.get(i));
			
			if(i+1 < groupIds.size()) {
				sb.append(" or ");
			}
		}
		
		sb.append(")");
		
		if(!allStatus) {
			sb.append(" and sp.projectStatus='INUSE'");
		}
		
		List<SysProject> list = (List<SysProject>) hibernateTemplate.find(sb.toString());			
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysProject> listByUserIdOrGroupId(long userId, List<Long> groupIds, boolean allStatus) {
		List<SysProject> list = (List<SysProject>) hibernateTemplate.execute(new HibernateCallback() {
						
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer sb = new StringBuffer();
				sb.append("select distinct * from (")
					.append(" select sp0.* from SYS_PROJECT sp0 where sp0.create_user_id=")
					.append(userId)
					.append("  union all ")
					.append(" select sp.* from SYS_PROJECT sp, SYS_PROJECT_LINK_USER_GROUP plug, SYS_USER_GROUP sug ")
					.append(" where sp.id=plug.PROJECT_ID and plug.USER_GROUP_ID=sug.id and ( ");				
				
				for(int i=0; i<groupIds.size(); i++) {
					sb.append(" plug.USER_GROUP_ID=").append(groupIds.get(i));
					
					if(i+1 < groupIds.size()) {
						sb.append(" or ");
					}
				}
				
				sb.append(") ) as bb");
				
				if(!allStatus) {
					sb.append(" where PROJECT_STATUS='INUSE' order by UPDATE_TIME DESC ");
				}else {
					sb.append(" order by UPDATE_TIME DESC ");
				}
				NativeQuery query = session.createNativeQuery(sb.toString());
				query.addEntity(SysProject.class);
				
				List<SysProject> projectList = query.list();
				return projectList;
			}
		});  
		
		return list;
	}

	

}
