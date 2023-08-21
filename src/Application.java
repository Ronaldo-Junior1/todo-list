import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> tasksList = new ArrayList<>();
        TaskManager taskManager = new TaskManager(tasksList);

        taskManager.loadTask();



        int escolha;
        do {
            System.out.println("Menu:");
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Listar Todas as Tarefas");
            System.out.println("3. Listar Tarefas por Categoria");
            System.out.println("4. Listar Tarefas por Prioridade");
            System.out.println("5. Listar Tarefas por Status");
            System.out.println("6. Remover Tarefa");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    taskManager.addTask(tasksList);
                    break;
                case 2:
                    taskManager.listAllTasks();
                    break;
                case 3:
                    System.out.print("Digite a categoria: ");
                    System.out.println();
                    String category = scanner.next();
                    taskManager.searchTasksByCategory(category);
                    break;
                case 4:
                    System.out.println("Digite a prioridade");
                    int priority = scanner.nextInt();
                    taskManager.searchTasksByPriority(priority);
                    break;
                case 5:
                    System.out.println("Digite o status");
                    String statusSearch = scanner.next();
                    taskManager.searchTasksByStatus(statusSearch);
                    break;
                case 6:
                   taskManager.deleteTask();
                   taskManager.overwriteFile("src/Task.txt");
            }
        }while (escolha != 7);
        taskManager.loadTask();
        }
    }
