package org.toxos.processassertions.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.impl.ProcessEngineImpl;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.history.HistoryLevel;
import org.flowable.engine.test.FlowableRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.toxos.processassertions.api.SupportedLocale;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.*;
import static org.toxos.processassertions.api.internal.Assert.assertThat;

/**
 * Test cases for {@link ProcessAssertFlowableConfiguration}.
 *
 * @author Tiese Barrell
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessAssertActivitiConfigurationTest {

    private ProcessAssertFlowableConfiguration classUnderTest;

    @Mock
    private ProcessEngineImpl processEngineMock;

    @Mock
    private FlowableRule activitiRuleMock;

    @Mock
    private ProcessEngineConfigurationImpl processEngineConfigurationMock;

    private Locale locale = new Locale("en", "US");

    @Before
    public void before() {
        when(activitiRuleMock.getProcessEngine()).thenReturn(processEngineMock);
        when(processEngineMock.getProcessEngineConfiguration()).thenReturn(processEngineConfigurationMock);
        when(processEngineMock.getName()).thenReturn("Unit test mock engine");
        when(processEngineConfigurationMock.getHistoryLevel()).thenReturn(HistoryLevel.FULL);
    }

    @Test
    public void engineConstructorSetsEngineAndInstance() {
        classUnderTest = new ProcessAssertFlowableConfiguration(processEngineMock);
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        assertThat(ProcessAssertFlowableConfiguration.INSTANCE, is(sameInstance(classUnderTest)));
        verify(processEngineConfigurationMock, times(1)).setProcessEngineLifecycleListener(isA(ProcessEngineCloseListener.class));
    }

    @Test(expected = NullPointerException.class)
    public void engineConstructorFailsForNullEngine() {
        classUnderTest = new ProcessAssertFlowableConfiguration((ProcessEngine) null);
    }

    @Test
    public void ruleConstructorSetsEngineAndInstance() {
        classUnderTest = new ProcessAssertFlowableConfiguration(activitiRuleMock);
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        assertThat(ProcessAssertFlowableConfiguration.INSTANCE, is(sameInstance(classUnderTest)));
        verify(processEngineConfigurationMock, times(1)).setProcessEngineLifecycleListener(isA(ProcessEngineCloseListener.class));
    }

    @Test(expected = NullPointerException.class)
    public void ruleConstructorFailsForNullEngine() {
        classUnderTest = new ProcessAssertFlowableConfiguration((FlowableRule) null);
    }

    @Test
    public void engineAndLocaleConstructorSetsEngineLocaleAndInstance() {
        classUnderTest = new ProcessAssertFlowableConfiguration(SupportedLocale.ENGLISH_US, processEngineMock);
        assertThat(classUnderTest.getLocale(), is(locale));
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        assertThat(ProcessAssertFlowableConfiguration.INSTANCE, is(sameInstance(classUnderTest)));
        verify(processEngineConfigurationMock, times(1)).setProcessEngineLifecycleListener(isA(ProcessEngineCloseListener.class));
    }

    @Test(expected = NullPointerException.class)
    public void engineAndLocaleConstructorFailsForNullEngine() {
        classUnderTest = new ProcessAssertFlowableConfiguration(SupportedLocale.ENGLISH_US, (ProcessEngine) null);
    }

    @Test(expected = NullPointerException.class)
    public void engineAndLocaleConstructorFailsForNullLocale() {
        classUnderTest = new ProcessAssertFlowableConfiguration(null, processEngineMock);
    }

    @Test
    public void ruleAndLocaleConstructorSetsEngineLocalAndInstance() {
        classUnderTest = new ProcessAssertFlowableConfiguration(SupportedLocale.ENGLISH_US, activitiRuleMock);
        assertThat(classUnderTest.getLocale(), is(locale));
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        assertThat(ProcessAssertFlowableConfiguration.INSTANCE, is(sameInstance(classUnderTest)));
        verify(processEngineConfigurationMock, times(1)).setProcessEngineLifecycleListener(isA(ProcessEngineCloseListener.class));
    }

    @Test(expected = NullPointerException.class)
    public void ruleAndLocaleConstructorFailsForNullEngine() {
        classUnderTest = new ProcessAssertFlowableConfiguration(SupportedLocale.ENGLISH_US, (FlowableRule) null);
    }

    @Test(expected = NullPointerException.class)
    public void ruleAndLocaleConstructorFailsForNullLocale() {
        classUnderTest = new ProcessAssertFlowableConfiguration(null, activitiRuleMock);
    }

    @Test
    public void settingProcessEngineChangesEngine() {
        classUnderTest = new ProcessAssertFlowableConfiguration(processEngineMock);
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        final ProcessEngineImpl otherEngine = mock(ProcessEngineImpl.class);
        when(otherEngine.getProcessEngineConfiguration()).thenReturn(processEngineConfigurationMock);
        when(otherEngine.getName()).thenReturn("Unit test alternative mock engine");
        classUnderTest.setProcessEngine(otherEngine);
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) otherEngine)));
        verify(processEngineConfigurationMock, times(2)).setProcessEngineLifecycleListener(isA(ProcessEngineCloseListener.class));
    }

    @Test(expected = NullPointerException.class)
    public void settingProcessEngineFailsForNullEngine() {
        classUnderTest = new ProcessAssertFlowableConfiguration(processEngineMock);
        classUnderTest.setProcessEngine(null);
    }

    @Test
    public void settingActivitiRuleChangesEngine() {
        classUnderTest = new ProcessAssertFlowableConfiguration(processEngineMock);
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        classUnderTest.setActivitiRule(activitiRuleMock);
        assertThat(classUnderTest.getProcessEngine(), is(sameInstance((ProcessEngine) processEngineMock)));
        verify(processEngineConfigurationMock, times(2)).setProcessEngineLifecycleListener(isA(ProcessEngineCloseListener.class));
    }

    @Test(expected = NullPointerException.class)
    public void settingActivitiRuleFailsForNullRule() {
        classUnderTest = new ProcessAssertFlowableConfiguration(processEngineMock);
        classUnderTest.setActivitiRule(null);
    }

    @Test
    public void assertFactoryIsCreated() {
        classUnderTest = new ProcessAssertFlowableConfiguration(processEngineMock);
        assertThat(classUnderTest.getAssertFactory(), is(instanceOf(AssertFactoryImpl.class)));
    }

    @Test
    public void deInitializeResetsProcessEngine() {
        classUnderTest = new ProcessAssertFlowableConfiguration(processEngineMock);
        classUnderTest.deInitialize();
        assertThat(classUnderTest.getProcessEngine(), is(nullValue()));
    }

    @Test
    public void configuredHistoryLevelIsReturned() {
        classUnderTest = new ProcessAssertFlowableConfiguration(processEngineMock);
        assertThat(classUnderTest.getConfiguredHistoryLevel(), is(HistoryLevel.FULL));
    }


}
