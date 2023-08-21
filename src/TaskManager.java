import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks;
    private Scanner scanner = new Scanner(System.in);

    public TaskManager(List<Task> tasks) {
        this.tasks = tasks;
    }

//    public void addTask(Task task) {
//        if (tasks.isEmpty()) {
//            tasks.add(task);
//        } else {
//            int insertIndex = 0;
//            for (int i = 0; i < tasks.size(); i++) {
//                if (task.getPrioridade() > tasks.get(i).getPrioridade()) {
//                    insertIndex = i + 1;
//                } else {
//                    break;
//                }
//            }
//            tasks.add(insertIndex, task);
//        }
//    }

    public void listAllTasks() {
        System.out.println("Lista de todas as tarefas:");
        for (Task task : tasks) {
            System.out.println(task);
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

    public void searchTasksByCategory(String category) {
        List<Task> tarefasFiltradas = tasks.stream()
                .filter(task -> task.getCategoria().equals(category))
                .collect(Collectors.toList());
        System.out.println("Tarefas de:" + category);
        for (Task task : tarefasFiltradas) {
            System.out.println(task);
        }
    }

    public void searchTasksByPriority(int priority) {
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getPrioridade() == priority)
                .collect(Collectors.toList());
        System.out.println("Tarefas com Prioridade " + priority + ":");
        for (Task task : filteredTasks) {
            System.out.println(task);
        }
    }

    public void searchTasksByStatus(String status) {
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
        System.out.println("Tarefas com Status " + status + ":");
        for (Task task : filteredTasks) {
            System.out.println(task);
        }
    }


    public void deleteTask() {

        boolean error = true;

        if (!tasks.isEmpty()) {
            do {
                System.out.println("Que tarefa deseja excluir ? Digite 0 para cancelar");
                listAllTasks();
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
