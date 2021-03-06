package com.buaa.fly.dailyacc.manager;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Dailyacc;
import com.buaa.fly.dailyacc.dao.DailyaccDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;
import com.common.FileHelper;

@Component("dailyaccManager")
public class DailyaccManager {

	@Resource
	private DailyaccDao dailyaccDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;

	/**
	 * 分页查询信息，带有criteria 将criteria转换为一个Map
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryDailyacc(Page<Dailyacc> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		dailyaccDao.queryDailyacc(page, parameter, criteria);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveDailyacc(Map<String, Collection> dataItems)
			throws Exception {
		Collection<Dailyacc> details = (Collection<Dailyacc>) dataItems
				.get("dsDailyacc");
		this.saveDailyacc(details);
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveDailyacc(Collection<Dailyacc> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Dailyacc item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					//fileManager(item);把附件以二进制的形式保存到数据库中，暂时不用
					dailyaccDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对现场汇报表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					//fileManager(item);把附件以二进制的形式保存到数据库中，暂时不用
					dailyaccDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对现场汇报表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					dailyaccDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对现场汇报表删除选定记录");
					FileHelper.deleteFile("/Fly_Dailyacc/" + item.getId());// 删除相关文件
				} else if (state.equals(EntityState.NONE)) {
				}
			}
		}
	}

	// 处理相关文件
	private void fileManager(Dailyacc item) {
		String path = "/Fly_Dailyacc/" + item.getId() + "/"
				+ item.getFilename();
		FileHelper.fileToData(path);
		if (FileHelper.bytes != 0) {
			item.setBytes(FileHelper.bytes);
			item.setDatablock(FileHelper.datablock);// 文件存储到数据库中
			FileHelper.bytes = 0;
			FileHelper.datablock = null;
		}
	}

	// 下载文件
	@Expose
	public String downloadFile(int id, String fname) throws IOException {
		String path = "/Fly_Dailyacc/" + id + "/";
		if (!FileHelper.existFile(path, fname)) {
			Dailyacc dailyacc = dailyaccDao.queryById(id);
			byte[] datablock = dailyacc.getDatablock();
			FileHelper.createFile(path, fname, datablock);
		}
		return fname;
	}

}
