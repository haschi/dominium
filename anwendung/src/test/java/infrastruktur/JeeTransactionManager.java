package infrastruktur;

import org.axonframework.unitofwork.TransactionManager;

public class JeeTransactionManager
        implements TransactionManager<DummyTransaction>
{
    @Override
    public DummyTransaction startTransaction()
    {
        return new DummyTransaction();
    }

    @Override
    public void commitTransaction(final DummyTransaction dummyTransaction)
    {

    }

    @Override
    public void rollbackTransaction(final DummyTransaction dummyTransaction)
    {

    }
}
