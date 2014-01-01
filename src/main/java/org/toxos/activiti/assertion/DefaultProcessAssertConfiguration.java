package org.toxos.activiti.assertion;

import java.util.Locale;

import org.activiti.engine.EngineServices;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.test.ActivitiRule;

public class DefaultProcessAssertConfiguration implements ProcessAssertConfiguration {

	public static final Locale DEFAULT_LOCALE = new Locale("en", "US");

	private Locale locale;

	private EngineServices engineServices;

	public DefaultProcessAssertConfiguration() {
		super();
		this.locale = DEFAULT_LOCALE;
		this.engineServices = ProcessEngines.getDefaultProcessEngine();
	}

	public DefaultProcessAssertConfiguration(final Locale locale) {
		super();
		this.locale = locale;
		this.engineServices = ProcessEngines.getDefaultProcessEngine();
	}

	public DefaultProcessAssertConfiguration(final ActivitiRule activitiRule) {
		super();
		this.locale = DEFAULT_LOCALE;
		this.engineServices = activitiRule.getProcessEngine();
	}

	public DefaultProcessAssertConfiguration(final Locale locale, final ActivitiRule activitiRule) {
		super();
		this.locale = locale;
		this.engineServices = activitiRule.getProcessEngine();
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public EngineServices getEngineServices() {
		return engineServices;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public void setEngineServices(final EngineServices engineServices) {
		this.engineServices = engineServices;
	}

	public void setActivitiRule(final ActivitiRule activitiRule) {
		this.engineServices = activitiRule.getProcessEngine();
	}

}
