import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj ile wątków chcesz uruchomić: ");
        int initialThreads = scanner.nextInt();

        TaskManager manager = new TaskManager(Math.max(1, initialThreads));

        for (int i = 0; i < initialThreads; i++) {
            manager.submitTask(i);
        }

        while (true) {
            System.out.println("\nWybierz akcję:");
            System.out.println("1. Dodaj nowe zadanie (szukanie liczb podzielnych przez 97)");
            System.out.println("2. Sprawdź status zadania");
            System.out.println("3. Anuluj zadanie");
            System.out.println("4. Pobierz wynik zadania");
            System.out.println("5. Wyświetl wszystkie zadania");
            System.out.println("6. Wyjście");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    int id = manager.getTaskCount();
                    manager.submitTask(id);
                }
                case 2 -> {
                    System.out.print("Podaj ID zadania: ");
                    int id = scanner.nextInt();
                    manager.checkTaskStatus(id);
                }
                case 3 -> {
                    System.out.print("Podaj ID zadania do anulowania: ");
                    int id = scanner.nextInt();
                    manager.cancelTask(id);
                }
                case 4 -> {
                    System.out.print("Podaj ID zadania do pobrania wyniku: ");
                    int id = scanner.nextInt();
                    manager.getTaskResult(id);
                }
                case 5 -> manager.listAllTasks();
                case 6 -> {
                    System.out.println("Zamykanie programu...");
                    manager.shutdown();
                    return;
                }
                default -> System.out.println("Nieznana opcja. Spróbuj ponownie.");
            }
        }
    }
}