package org.toxos.processassertions.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.toxos.processassertions.api.SupportedLocale;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.toxos.processassertions.api.internal.Assert.assertThat;

/**
 * Test cases for {@link ProcessAssertActivitiConfiguration}.
 *
 * @author Tiese Barrell
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessAssertActivitiConfigurationTest {

    private ProcessAssertActivitiConfiguration classUnderTest;

    @Mock
    private ProcessEngineImpl processEngineMock;

    @Mock
    private ActivitiRule activitiRuleMock;

    @Mock
    private ProcessEngineConfigurationImpl processEngineConfigurationMock;

    private Locale locale = new Locale("en", "US");

    @Before
    public void before() {
        when(activitiRuleMock.getProcessEngine()).thenReturn(processEngineMock);
        when(processEngineMock.getProcessEngineConfiguration()).thenReturn(processEngineConfigurationMock);
    }

    @Test
    public void engineConstructorSetsEngineAndInstance() {
        classUnderTest = new ProcessAssertActivitiConfiguration(processEngineMock);
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        assertThat(ProcessAssertActivitiConfiguration.INSTANCE, is(sameInstance(classUnderTest)));
    }

    @Test
    public void ruleConstructorSetsEngineAndInstance() {
        classUnderTest = new ProcessAssertActivitiConfiguration(activitiRuleMock);
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        assertThat(ProcessAssertActivitiConfiguration.INSTANCE, is(sameInstance(classUnderTest)));
    }

    @Test
    public void engineAndLocaleConstructorSetsEngineLocaleAndInstance() {
        classUnderTest = new ProcessAssertActivitiConfiguration(SupportedLocale.ENGLISH_US, processEngineMock);
        assertThat(classUnderTest.getLocale(), is(locale));
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        assertThat(ProcessAssertActivitiConfiguration.INSTANCE, is(sameInstance(classUnderTest)));
    }

    @Test
    public void ruleAndLocaleConstructorSetsEngineLocalAndInstance() {
        classUnderTest = new ProcessAssertActivitiConfiguration(SupportedLocale.ENGLISH_US, activitiRuleMock);
        assertThat(classUnderTest.getLocale(), is(locale));
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        assertThat(ProcessAssertActivitiConfiguration.INSTANCE, is(sameInstance(classUnderTest)));
    }

    @Test
    public void settingProcessEngineChangesEngine() {
        classUnderTest = new ProcessAssertActivitiConfiguration(processEngineMock);
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        final ProcessEngineImpl otherEngine = mock(ProcessEngineImpl.class);
        when(otherEngine.getProcessEngineConfiguration()).thenReturn(processEngineConfigurationMock);
        classUnderTest.setProcessEngine(otherEngine);
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) otherEngine)));
    }

//    @Test
//    public void settingProcessEngineToNullHasDefaultProcessEngine() {
//        classUnderTest = new ProcessAssertActivitiConfiguration(processEngineMock);
//        classUnderTest.setProcessEngine(null);
//        assertThat(classUnderTest.getProcessEngine(), is(sameInstance(null)));
//    }
}
