import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks;

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
                } else {
                    System.out.println("Linha com dados incompletos: " + linha);
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


}
