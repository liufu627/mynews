package alfredfliu.app.mynews.base;

public interface Promise<P,R> {
  //  R request(P... args);
    R pass(P... args);
    R fail(P... args);
}
