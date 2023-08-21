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
                    tasksList.clear();
                    taskManager.loadTask();
                    taskManager.listAllTasks();
                    break;
                case 3:
                    tasksList.clear();
                    taskManager.loadTask();
                    taskManager.searchTasksByCategory();
                    break;
                case 4:
                    tasksList.clear();
                    taskManager.loadTask();
                    taskManager.searchTasksByPriority();
                    break;
                case 5:
                    tasksList.clear();
                    taskManager.loadTask();
                    taskManager.searchTasksByStatus();
                    break;
                case 6:
                   taskManager.deleteTask();
                   taskManager.overwriteFile("src/Task.txt");
            }
        }while (escolha != 7);
        taskManager.loadTask();
        }
    }
