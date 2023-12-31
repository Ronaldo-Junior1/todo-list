import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks;
    private Scanner scanner = new Scanner(System.in);

    public TaskManager(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(List<Task> tasks) {
        tasks.clear();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome da tarefa: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        String dueDateInput = "";
        Date dueDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        while (dueDate == null) {
            System.out.print("Data de entrega (dd/mm/aaaa): ");
            dueDateInput = scanner.nextLine();
            try {
                dateFormat.setLenient(false);
                dueDate = dateFormat.parse(dueDateInput);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido. Utilize o formato dd/mm/aaaa.");
            }
        }
        int prioridade = 0;
        while (prioridade < 1 || prioridade > 5) {
            System.out.print("Prioridade (1 a 5): ");
            try {
                prioridade = Integer.parseInt(scanner.nextLine());
                if (prioridade < 1 || prioridade > 5) {
                    System.out.println("Prioridade deve estar entre 1 e 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um número válido.");
            }

        }

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();
        String status = "";
        while (!status.equalsIgnoreCase("todo") && !status.equalsIgnoreCase("doing") && !status.equalsIgnoreCase("done")) {
            System.out.print("Status (todo, doing, done): ");
            status = scanner.nextLine().toLowerCase();
            if (!status.equalsIgnoreCase("todo") && !status.equalsIgnoreCase("doing") && !status.equalsIgnoreCase("done")) {
                System.out.println("Status inválido. Insira um dos valores permitidos: todo, doing, done.");
            }
        }

        Task newTask = new Task(nome, descricao, dueDateInput, prioridade, categoria, status);
        tasks.add(newTask);
        saveTasksToFile("src/Task.txt");

        System.out.println("Tarefa adicionada com sucesso!");
    }

    public void listAllTasksByPriority() {
        System.out.println("Lista de todas as tarefas em ordem de prioridade:");

        List<Task> sortedTasks = new ArrayList<>(tasks);
        sortedTasks.sort(Comparator.comparingInt(Task::getPrioridade).reversed());

        for (int i = 0; i < sortedTasks.size(); i++) {
            Task task = sortedTasks.get(i);
            System.out.println(task);
        }
    }

    public void listAllTasksById() {
        System.out.println("Lista de todas as tarefas:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println("Índice: " + (i + 1) + " "+ task);
        }
    }

    public void saveTasksToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            for (Task task : tasks) {
                writer.println(task.getNome() + "," +
                        task.getDescricao() + "," +
                        task.getDataEntrega() + "," +
                        task.getPrioridade() + "," +
                        task.getCategoria() + "," +
                        task.getStatus() + System.lineSeparator());
            }
            writer.close();
            System.out.println("Tarefas salvas no arquivo " + filename);
        } catch (IOException e) {
            System.err.println("Erro ao salvar as tarefas no arquivo: " + e.getMessage());
        }
    }

    public void loadTask(){
        try {
            String linha = "";
            BufferedReader loader = new BufferedReader(new FileReader("src/Task.txt"));

            while ((linha = loader.readLine()) != null) {
                String[] taskDetails = linha.split(",");

                if (taskDetails.length >= 6) {
                    String nome = taskDetails[0];
                    String descricao = taskDetails[1];
                    String dataEntrega = taskDetails[2];
                    int prioridade = Integer.parseInt(taskDetails[3]);
                    String categoria = taskDetails[4];
                    String status = taskDetails[5];

                    Task task = new Task(nome, descricao, dataEntrega, prioridade, categoria, status);
                    tasks.add(task);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchTasksByCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a categoria (ou 0 para voltar ao menu): ");
        String category = scanner.nextLine();

        if (category.equals("0")) {
            return;
        }

        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getCategoria().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        if (filteredTasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada na categoria: " + category);
        } else {
            System.out.println("Tarefas da categoria " + category + ":");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }

    public void searchTasksByPriority() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a prioridade (ou 0 para voltar ao menu): ");
        int priority = scanner.nextInt();

        if (priority == 0) {
            return;
        }

        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getPrioridade() == priority)
                .collect(Collectors.toList());

        if (filteredTasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada com prioridade: " + priority);
        } else {
            System.out.println("Tarefas com Prioridade " + priority + ":");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }


    public void searchTasksByStatus() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o status: todo, doing ou done (ou 0 para voltar ao menu): ");
        String status = scanner.next().toLowerCase();

        if (status.equals("0")) {
            return;
        }

        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        if (filteredTasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada com status: " + status);
        } else {
            System.out.println("Tarefas com Status " + status + ":");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }



    public void deleteTask() {
        boolean error = true;

        if (!tasks.isEmpty()) {
            do {
                System.out.println("Que tarefa deseja excluir ? Digite 0 para cancelar");
                listAllTasksById();
                if (scanner.hasNextInt()) {
                    int opcao = scanner.nextInt();
                    scanner.nextLine();
                    if (opcao >= 1 && opcao <= tasks.size()) {
                        tasks.remove(opcao - 1);
                        error = false;
                    } else if (opcao == 0) {
                        error = false;
                    } else {
                        System.out.println("Valor inválido");
                    }
                } else {
                    scanner.nextLine();
                    System.out.println("Valor inválido");
                }
            } while (error);
        } else {
            System.out.println("Nenhuma tarefa cadastrada");
        }
    }

    public void overwriteFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                writer.println(task.getNome() + "," +
                        task.getDescricao() + "," +
                        task.getDataEntrega() + "," +
                        task.getPrioridade() + "," +
                        task.getCategoria() + "," +
                        task.getStatus() + System.lineSeparator());
            }
            System.out.println("Tarefas salvas no arquivo " + filename);
        } catch (IOException e) {
            System.err.println("Erro ao salvar as tarefas no arquivo: " + e.getMessage());
        }
    }



}
