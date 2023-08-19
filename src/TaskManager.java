import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (tasks.isEmpty()) {
            tasks.add(task);
        } else {
            int insertIndex = 0;
            for (int i = 0; i < tasks.size(); i++) {
                if (task.getPrioridade() > tasks.get(i).getPrioridade()) {
                    insertIndex = i + 1;
                } else {
                    break;
                }
            }
            tasks.add(insertIndex, task);
        }
    }

    public void saveTasksToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Task task : tasks) {
                writer.write(task.getNome() + ", " +
                        task.getDescricao() + ", " +
                        task.getDataEntrega() + ", " +
                        task.getPrioridade() + ", " +
                        task.getCategoria() + "," +
                        task.getStatus() + "\n");
            }
            System.out.println("Tarefas salvas no arquivo " + filename);
        } catch (IOException e) {
            System.err.println("Erro ao salvar as tarefas no arquivo: " + e.getMessage());
        }
    }


}
