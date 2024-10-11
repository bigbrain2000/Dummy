import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        Method m = Main.class.getDeclaredMethod("printMessage", Callable.class);
        m.setAccessible(true);

        m.invoke(null, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return Stream.of('H', 'e', 'l', 'l', 'o', ',', ' ', 'W', 'o', 'r', 'l', 'd', '!')
                        .map(Object::toString)
                        .reduce((x, y) -> x + y)
                        .orElseThrow(() -> new RuntimeException("Failed to print message."));
            }
        });
    }

    private static void printMessage(Callable<Object> message) throws Exception {
        Thread.sleep(1000);

        try {
            try {
                System.out.println(message.call());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("Finally block that does nothing.");
            }
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception", t);
        }
    }
}
