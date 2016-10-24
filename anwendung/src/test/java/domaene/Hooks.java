package domaene;

import cucumber.api.java.Before;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.core.util.ProjectStageProducer;

import javax.enterprise.context.RequestScoped;

public class Hooks
{

    @Before
    public void configure()
    {
        final ContextControl ctxCtrl = BeanProvider.getContextualReference(ContextControl.class);
        ctxCtrl.stopContexts();
        ctxCtrl.startContext(RequestScoped.class);

        ProjectStageProducer.setProjectStage(ProjectStage.UnitTest);
    }
}
