package contract.inheritance;

public class InMemoryShelveTest extends BookShelveContract {

    @Override
    protected BookShelve emptyShelve() {
        // 3 Ansätze:
        // - Mockito (schnell)
        // - Anonymous inner class oder lambda (minimaler Code)
        //   Ich will das TestApi und die Testautomatisierung und das Building treiben
        // - Fake, InmemoryRepository
        //   Ich schreibe sofort ein InmemoryRepository weil ich weiß, dass das in anderen integrativen Tests vorkommen würde.
        return new InMemoryBookShelve();
    }

}