package live.ipso.provider.exception;

/**
 * 系统自定义RuntimeException异常
 */
public class SystemException extends RuntimeException{
    public SystemException(String msg) {
        super(msg);
    }

    public SystemException() {
        super();
    }
}
