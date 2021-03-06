package com.buaa.fly.dailyacc.manager;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.buaa.fly.domain.Dailyacc;
import com.buaa.fly.domain.Fighterinfo;
import com.buaa.fly.domain.Outlineexecution;
import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.domain.Tasklist;
import com.buaa.fly.fighterinfo.dao.FighterinfoDao;
import com.buaa.fly.sfstatistic.vo.Total;
import com.buaa.fly.sfstatistic.vo.TotalService;
import com.common.FileHelper;
import com.common.WriteDocument;

@Component("exportDailyPaper")
public class ExportDailyPaper {
	@Resource
	private TotalService totalService;
	@Resource
	private FighterinfoDao fighterinfoDao;

	/**
	 * 生成word模板方法
	 *
	 * @param sfstatistic
	 */
	public void wordTemplate(Collection<Sfstatistic> sfstatistic,String dateStr) {
		WriteDocument wd = new WriteDocument();
		wd.setVisible(false);
		wd.createNewDocument();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(new Date());
		wd.insertDirectory("科研试飞每日简报（" + str + ")");
		wd.setAlignment(1);
		int miji = 0;
		for (Sfstatistic item : sfstatistic) {
			if(miji < item.getMiji()){
				miji = item.getMiji();
			}
		}
		if(miji == 0){
			wd.insertText("密级：公开");
		}
		if(miji == 1){
			wd.insertText("密级：内部");
		}
		if(miji == 2){
			wd.insertText("密级：普通商密");
		}
		if(miji == 3){
			wd.insertText("密级：核心商密");
		}
		if(miji == 4){
			wd.insertText("密级：秘密");
		}
		if(miji == 5){
			wd.insertText("密级：机密");
		}
		wd.setAlignment(2);
		wd.inserTitle("一、飞行情况（注：红色为当日最新内容）", "标题 2");
		wd.insertRedText("至" + str);
		wd.insertText("飞机共有" + sfstatistic.size()
				+ "架，其中  __已转场试飞院； __ 在成飞。均已飞行");
		int i = 1;
		for (Sfstatistic item : sfstatistic) {
			wd.inserTitle("(" + i + ")#" + item.getFighterno(), "标题 2");
			Fighterinfo fighter = fighterinfoDao.queryFighterinfo1(item
					.getFighterno());
			wd.insertText("本机于" + format.format(fighter.getFirstFDate())
					+ "日首飞，在成都地区共飞行 __个起落。本机总共飞行__个起落");
			if(miji < item.getMiji())
				miji = item.getMiji();
			i++;
		}
		wd.inserTitle("(" + i + ")飞行概况", "标题 2");
		wd.insertRedText("截至" + str + "的飞行概况");
		wd.insertTable("表格", i, 9);
		String[] str1 = new String[9];
		int j = 1;
		for (Sfstatistic item : sfstatistic) {
			str1[0] = String.valueOf(j);
			str1[1] = item.getFighterno();
			str1[2] = format.format(item.getFdate());
			Fighterinfo fighter = fighterinfoDao.queryFighterinfo1(item
					.getFighterno());
			str1[3] = format.format(fighter.getFirstFDate());
			Total total = totalService.loadflight(item.getFighterno());
			str1[4] = total.getThisyearcount();// 近年起落架次
			str1[5] = total.getCount();// 总飞行架次
			str1[6] = total.getTime();// 总飞行时间
			str1[8] = item.getSummary();
			wd.addContentRow(1, str1, j + 1);
			j++;
		}
		wd.inserTitle("二、 现场工作（注：红色为当日最新内容）", "标题 3");
		int row = i * 2 - 1;
		wd.insertTable("表格1", row, 4);
		if (i != 2) {
			wd.mergeCell(2, 2, 1, i, 1);
			wd.mergeCell(2, i + 1, 1, row, 1);
		}
		wd.insertText("现场报告：          " + "整理：          "
				+ "审核：                  " + "审定：             "
				+ "主管副总师：       ");
		String path = "\\Fly_DailyAcc\\" + dateStr;
		String filePath = ContextHolder.getRequest().getRealPath("/")
				+ "upload"+path;
		String fileName = "日报";
		File folder=new File(filePath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		/*wd.saveAs(filePath + "//" + fileName);
		wd.closeWord();*/
		System.out.println("生成的日报路径在：" + filePath);
		wd.saveWordFile(filePath + "//" + fileName);
		//用户手动保存
/*		String path = "/DailyAcc/" + str + "日报.doc";
		String filePath = ContextHolder.getRequest().getRealPath("/")
				+ "upload" + path;
		wd.saveAs(filePath);*/
	}

}
