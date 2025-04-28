import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;



public class TaskManager {
    private final ExecutorService executorService;
    private final List<FutureTask<String>> tasks = new ArrayList<>();

    public TaskManager(int threadPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void submitTask(int id) {
        FutureTask<String> futureTask = new FutureTask<>(new DivisibleBy97Task()) {
            @Override
            protected void done() {
                if (isCancelled()) {
                    System.out.println("[Słuchacz] Zadanie " + id + " zostało anulowane!");
                } else {
                    System.out.println("[Słuchacz] Zadanie " + id + " zakończyło się!");
                    try {
                        String result = get();
                        System.out.println("[Słuchacz] Wynik zadania " + id + ": " + result);
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("[Słuchacz] Błąd w zadaniu " + id + ": " + e.getMessage());
                    }
                }
            }
        };
        executorService.submit(futureTask);
        tasks.add(futureTask);
        System.out.println("Dodano nowe zadanie. ID: " + (tasks.size() - 1));
    }

    public void checkTaskStatus(int id) {
        if (isValidId(id)) {
            FutureTask<String> future = tasks.get(id);
            if (future.isCancelled()) {
                System.out.println("Zadanie " + id + " zostało anulowane.");
            } else if (future.isDone()) {
                System.out.println("Zadanie " + id + " jest zakończone.");
            } else {
                System.out.println("Zadanie " + id + " wciąż trwa...");
            }
        } else {
            System.out.println("Nieprawidłowy ID zadania.");
        }
    }

    public void cancelTask(int id) {
        if (isValidId(id)) {
            boolean result = tasks.get(id).cancel(true);
            if (result) {
                System.out.println("Anulowano zadanie " + id + ".");
            } else {
                System.out.println("Nie udało się anulować zadania " + id + ".");
            }
        } else {
            System.out.println("Nieprawidłowy ID zadania.");
        }
    }

    public void getTaskResult(int id) {
        if (isValidId(id)) {
            FutureTask<String> future = tasks.get(id);
            if (future.isDone() && !future.isCancelled()) {
                try {
                    String result = future.get();
                    System.out.println("Wynik zadania " + id + ": " + result);
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Błąd podczas pobierania wyniku: " + e.getMessage());
                }
            } else if (future.isCancelled()) {
                System.out.println("Zadanie " + id + " zostało anulowane, brak wyniku.");
            } else {
                System.out.println("Zadanie " + id + " jeszcze się nie zakończyło.");
            }
        } else {
            System.out.println("Nieprawidłowy ID zadania.");
        }
    }

    public void listAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Brak zadań.");
            return;
        }
        System.out.println("\nLista wszystkich zadań:");
        for (int i = 0; i < tasks.size(); i++) {
            FutureTask<String> future = tasks.get(i);
            String status;
            if (future.isCancelled()) {
                status = "Anulowane";
            } else if (future.isDone()) {
                status = "Zakończone";
            } else {
                status = "W toku";
            }
            System.out.println("ID: " + i + " | Status: " + status);
        }
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public void shutdown() {
        executorService.shutdownNow();
    }

    private boolean isValidId(int id) {
        return id >= 0 && id < tasks.size();
    }
}

