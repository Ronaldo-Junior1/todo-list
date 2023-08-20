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
            System.out.println("2. Listar Tarefas por Categoria");
            System.out.println("3. Listar Tarefas por Prioridade");
            System.out.println("4. Listar Tarefas por Status");
            System.out.println("5. Remover Tarefa");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();



            switch (escolha) {
                case 1:
                    tasksList.clear();
                    scanner.nextLine();
                    System.out.print("Nome da tarefa: ");
                    String nome = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Data de término (dd/mm/aaaa): ");
                    String dueDate = scanner.nextLine();
                    System.out.print("Prioridade (1 a 5): ");
                    int prioridade = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();
                    System.out.print("Status (todo, doing, done): ");
                    String status = scanner.nextLine();

                    Task newTask = new Task(nome, descricao, dueDate, prioridade, categoria, status);
                    tasksList.add(newTask);
                    taskManager.saveTasksToFile("src/Task.txt");

                    System.out.println("Tarefa adicionada com sucesso!");
                    break;
                case 2:
                    System.out.print("Digite a categoria: ");
                    System.out.println();
                    String category = scanner.next();
                    taskManager.searchTasksByCategory(category);
                    break;
                case 3:
                    System.out.println("Digite a prioridade");
                    int priority = scanner.nextInt();
                    taskManager.searchTasksByPriority(priority);
                case 4:
                    System.out.println("Digite o status");
                    String statusSearch = scanner.next();
                    taskManager.searchTasksByStatus(statusSearch);
            }
        }while (escolha != 6);
        }
    }
