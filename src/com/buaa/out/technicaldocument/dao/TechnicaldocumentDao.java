package com.buaa.out.technicaldocument.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.bdf2.core.view.user.QueryUserData;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.out.domain.Technicaldocument;

@Repository("technicaldocumentDao")
public class TechnicaldocumentDao extends HibernateBaseDao {
	@Resource
	private QueryUserData userService;
	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryTechnicaldocument(Page<Technicaldocument> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Technicaldocument.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        }
		
		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, "a");
			if (null != result) {
				coreHql.append(" and "+ result.getAssemblySql());
				args.putAll(result.getValueMap());
			}
		}

        
        String countHql = "select count(*) " + coreHql.toString();
        String hql = coreHql.toString();
        hql = userService.checkUser(hql);
		this.pagingQuery(page, hql, countHql, args);
	}
	
	/**                  
	* 搜索计划开始时间到结束时间涉及本年度的保障计划
	* @param parameter    
	* @throws Exception
	*/
	public Collection<Technicaldocument> queryDocument(Map<String, Object> parameter) throws Exception {
		String hql="from "+ Technicaldocument.class.getName() +" u where 1=1";
		Integer sendStatus = (Integer)parameter.get("status");
		if(sendStatus >= 2){
			hql += " and u.sendStatus <= '" + sendStatus + "'";
		}
		else{
			hql += " and u.sendStatus = '" + sendStatus + "'";
		}
		return this.query(hql);						
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Technicaldocument detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 数据修改
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Technicaldocument detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.update(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 数据删除
	 * @param detail
	 * @throws Exception
	 */
	public void deleteData(Technicaldocument detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	public String technicaldocumentIsExists(String oid,String number) {
		String hql = "select count(*) from " + Technicaldocument.class.getName()
				+ " u where u.number = :number";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("number", number);
		if (oid != null) {
			hql += " and u.oid != :oid";
			parameterMap.put("oid", oid);
		}
		int count = this.queryForInt(hql, parameterMap);

		String returnStr = null;
		if (count > 0) {
			returnStr = "此编号已存在！";
		}
		return returnStr;
	}             
}