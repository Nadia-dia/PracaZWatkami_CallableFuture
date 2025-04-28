import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class DivisibleBy97Task implements Callable<String> {
    @Override
    public String call() throws Exception {
        List<Integer> divisibleBy97 = new ArrayList<>();
        for (int i = 0; i <= 5000; i++) {
            if (i % 97 == 0) {
                divisibleBy97.add(i);
            }
            Thread.sleep(10);
        }
        return "Znaleziono liczby podzielne przez 97: " + divisibleBy97;
    }
}