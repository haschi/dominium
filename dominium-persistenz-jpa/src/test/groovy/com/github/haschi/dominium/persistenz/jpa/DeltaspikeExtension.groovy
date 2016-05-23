package com.github.haschi.dominium.persistenz.jpa

import org.apache.deltaspike.cdise.api.CdiContainer
import org.apache.deltaspike.cdise.api.CdiContainerLoader
import org.apache.deltaspike.core.api.projectstage.ProjectStage
import org.apache.deltaspike.core.util.ProjectStageProducer
import org.apache.deltaspike.jpa.api.transaction.TransactionScoped
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

import javax.enterprise.context.RequestScoped
import javax.enterprise.context.SessionScoped
import javax.enterprise.context.spi.CreationalContext
import javax.enterprise.inject.Typed
import javax.enterprise.inject.spi.AnnotatedType
import javax.enterprise.inject.spi.BeanManager
import javax.enterprise.inject.spi.InjectionTarget

class DeltaspikeExtension extends  AbstractAnnotationDrivenExtension<Deltaspike> {

    boolean deltaspikeSpec = false;


    void visitSpecAnnotation(Deltaspike annotation, SpecInfo spec) {
        this.deltaspikeSpec = true
    }

    void visitFeatureAnnotation(Deltaspike annotation, FeatureInfo feature) {
        //timedFeatures << feature.name
    }

    void visitSpec(SpecInfo spec) {


        IMethodInterceptor interceptor = new StartContainer(spec, this)
        // spec.addSetupSpecInterceptor(interceptor)
        spec.addSetupInterceptor(interceptor)

        IMethodInterceptor cleanupInterceptor = new StopContainer(this)
        // spec.addCleanupInterceptor(cleanupInterceptor)
    }

    protected static volatile CdiContainer cdiContainer;
    // nice to know, since testng executes tests in parallel.
    protected static int containerRefCount = 0;

    @Typed
    protected static ProjectStage runInProjectStage() {
        ProjectStage.valueOf("UnitTest")
    }

    public static CdiContainer getCdiContainer() {
        return cdiContainer;
    }

    public void init() {
            containerRefCount++;

            if (cdiContainer == null) {
                // setting up the Apache DeltaSpike ProjectStage
                ProjectStage projectStage = runInProjectStage();
                ProjectStageProducer.setProjectStage(projectStage);

                cdiContainer = CdiContainerLoader.getCdiContainer();

                cdiContainer.boot();
                cdiContainer.getContextControl().startContexts();
            }
            else {
                cleanInstances();
            }
        }

    // @AfterMethod
    public final void afterMethod() throws Exception {
        if (cdiContainer != null) {
            cleanInstances();
            containerRefCount--;
        }
    }

    /**
     * clean the NormalScoped contextual instances by stopping and restarting
     * some contexts. You could also restart the ApplicationScoped context
     * if you have some caches in your classes.
     */
    public final void cleanInstances() throws Exception {
        //cdiContainer.getContextControl().stopContext(RequestScoped.class);
        cdiContainer.getContextControl().startContext(RequestScoped.class);
        //cdiContainer.getContextControl().stopContext(SessionScoped.class);
        cdiContainer.getContextControl().startContext(SessionScoped.class);
        cdiContainer.getContextControl().startContext(TransactionScoped.class)
    }

    // @AfterSuite
    public synchronized void shutdownContainer() throws Exception {
        if (cdiContainer != null) {
            cdiContainer.shutdown();
            cdiContainer = null;
        }
    }

    void start(SpecInfo spec, IMethodInvocation iMethodInvocation) {
        init();

        // perform injection into the very own test class
        BeanManager beanManager = cdiContainer.getBeanManager();

        CreationalContext creationalContext = beanManager.createCreationalContext(null);

        AnnotatedType annotatedType = beanManager.createAnnotatedType(spec.reflection);

        InjectionTarget injectionTarget = beanManager.createInjectionTarget(annotatedType);
        injectionTarget.inject(iMethodInvocation.instance, creationalContext);

        // this is a trick we use to have proper DB transactions when using the entitymanager-per-request pattern
        //cleanInstances();
        // cleanUpDb();
        //cleanInstances();
    }
}

