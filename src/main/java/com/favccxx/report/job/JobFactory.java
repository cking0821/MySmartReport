package com.favccxx.report.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * 将AUTOWIRED注解注册到JOB工厂
 * @author chenxu
 * @date Jan 12, 2018
 * @version 1.0
 */
public class JobFactory extends AdaptableJobFactory {

	@Autowired
	AutowireCapableBeanFactory capableBeanFactory;

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		Object jobInstance = super.createJobInstance(bundle);
		capableBeanFactory.autowireBean(capableBeanFactory);
		return jobInstance;
	}

}
