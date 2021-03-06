package com.buaa.comm.meetingdocument.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Meetingdocument;
import com.buaa.comm.meetingdocument.manager.MeetingdocumentManager;

@Component("meetingdocumentPR")
public class MeetingdocumentPR{

    @Resource
	private MeetingdocumentManager meetingdocumentManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryMeetingdocument(Page<Meetingdocument> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    meetingdocumentManager.queryMeetingdocument(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveMeetingdocument(Map<String, Collection> dataItems) throws Exception {
	    meetingdocumentManager.saveMeetingdocument(dataItems);
	 }
	
}
