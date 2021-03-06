package com.buaa.comm.resourcedownload.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Resourcedownload;
import com.buaa.comm.resourcedownload.manager.ResourcedownloadManager;

@Component("resourcedownloadPR")
public class ResourcedownloadPR{

    @Resource
	private ResourcedownloadManager resourcedownloadManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryResourcedownload(Page<Resourcedownload> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    resourcedownloadManager.queryResourcedownload(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveResourcedownload(Map<String, Collection> dataItems) throws Exception {
	    resourcedownloadManager.saveResourcedownload(dataItems);
	 }
	
}
