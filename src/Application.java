import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasksList = new ArrayList<>(); // Crie uma lista vazia de tarefas
        TaskManager taskManager = new TaskManager(tasksList);



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
                    System.out.print("Nome da tarefa: ");
                    String nome = scanner.next();
                    System.out.print("Descrição: ");
                    String descricao = scanner.next();
                    System.out.print("Data de término (dd/mm/aaaa): ");
                    String dueDate = scanner.next();
                    LocalDate dataEntrega = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    System.out.print("Prioridade (1 a 5): ");
                    int prioridade = scanner.nextInt();
                    System.out.print("Categoria: ");
                    String categoria = scanner.next();
                    System.out.print("Status (todo, doing, done): ");
                    String status = scanner.next();

                    Task newTask = new Task(nome, descricao, dataEntrega, prioridade, categoria, status);
                    taskManager.addTask(newTask);
                    taskManager.saveTasksToFile("src/Task.txt");

                    System.out.println("Tarefa adicionada com sucesso!");
                    break;

            }
        }while (escolha != 6);
        }
    }
