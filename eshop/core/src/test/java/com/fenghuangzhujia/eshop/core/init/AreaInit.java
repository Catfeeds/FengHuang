package com.fenghuangzhujia.eshop.core.init;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fenghuangzhujia.foundation.area.Area.AreaLevel;
import com.fenghuangzhujia.foundation.area.AreaService;
import com.fenghuangzhujia.foundation.area.dto.AreaDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AreaInit {

	@Autowired
	private AreaService areaService;
	
	@Test
	public void init() {
		AreaDto areaDto=new AreaDto();
		areaDto.setName("朝阳");
		areaDto.setLevel(AreaLevel.AREA);
		areaService.add(areaDto);
	}
}