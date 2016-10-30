package infrastruktur;

import org.axonframework.unitofwork.DefaultUnitOfWork;
import org.axonframework.unitofwork.UnitOfWork;
import org.axonframework.unitofwork.UnitOfWorkFactory;

/**
 * Created by matthias on 29.10.16.
 */
public class MyUnitIfWorkFactory
        implements UnitOfWorkFactory
{
    @Override
    public UnitOfWork createUnitOfWork()
    {
        return DefaultUnitOfWork.startAndGet(new JeeTransactionManager());
    }
}
