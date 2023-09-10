const form = document.getElementById("todo-form");
const draggables = document.querySelectorAll(".task");
const droppables = document.querySelectorAll(".swim-lane");

let tarefaEditando = null;

function preencherFormulario(tarefa) {
    const taskDetails = tarefa.querySelector(".task-details");
    const name = taskDetails.querySelector("p:nth-child(1)").textContent.split(": ")[1];
    const description = taskDetails.querySelector("p:nth-child(2)").textContent.split(": ")[1];
    const dueDate = taskDetails.querySelector("p:nth-child(3)").textContent.split(": ")[1];
    const priority = taskDetails.querySelector("p:nth-child(4)").textContent.split(": ")[1];
    const category = taskDetails.querySelector("p:nth-child(5)").textContent.split(": ")[1];
    const status = taskDetails.querySelector("p:nth-child(6)").textContent.split(": ")[1];

    document.getElementById("name").value = name;
    document.getElementById("description").value = description;
    document.getElementById("due-date").value = dueDate;
    document.getElementById("priority").value = priority;
    document.getElementById("category").value = category;
    document.getElementById("status").value = status;
}

form.addEventListener("submit", function (e) {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const description = document.getElementById("description").value;
    const dueDate = document.getElementById("due-date").value;
    const priority = document.getElementById("priority").value;
    const category = document.getElementById("category").value;
    const status = document.getElementById("status").value;

    if (tarefaEditando) {
        tarefaEditando.querySelector(".task-name").textContent = name;

        const taskDetails = tarefaEditando.querySelector(".task-details");
        taskDetails.innerHTML = `
            <p>Nome: ${name}</p>
            <p>Descrição: ${description}</p>
            <p>Data de Entrega: ${dueDate}</p>
            <p>Prioridade: ${priority}</p>
            <p>Categoria: ${category}</p>
            <p>Status: ${status}</p>
        `;

        form.reset();
        document.querySelector("#todo-form button").textContent = "Adicionar";
        tarefaEditando = null;
    } else {
        const task = document.createElement("div");
        task.classList.add("task");
        task.draggable = true;
        task.addEventListener("dragstart", () => {
            task.classList.add("is-dragging");
        });
        task.addEventListener("dragend", () => {
            task.classList.remove("is-dragging");
        });

        const taskName = document.createElement("p");
        taskName.classList.add("task-name");
        taskName.textContent = name;

        const taskDeleteButton = document.createElement("button");
        taskDeleteButton.classList.add("delete-button");
        taskDeleteButton.textContent = "Excluir";
        taskDeleteButton.addEventListener("click", function () {
            task.remove();
        });

        const taskEditButton = document.createElement("button");
        taskEditButton.classList.add("edit-button");
        taskEditButton.textContent = "Editar";
        taskEditButton.addEventListener("click", function () {
            preencherFormulario(task);

            tarefaEditando = task;
            document.querySelector("#todo-form button").textContent = "Salvar";
        });

        const taskDetails = document.createElement("div");
        taskDetails.classList.add("task-details");
        taskDetails.innerHTML = `
            <p>Nome: ${name}</p>
            <p>Descrição: ${description}</p>
            <p>Data de Entrega: ${dueDate}</p>
            <p>Prioridade: ${priority}</p>
            <p>Categoria: ${category}</p>
            <p>Status: ${status}</p>
        `;

        taskDetails.style.display = "none";

        taskName.addEventListener("click", function () {
            taskName.style.display = "none";
            taskDetails.style.display = "block";
        });

        taskDetails.addEventListener("click", function () {
            if (taskDetails.style.display === "none") {
                taskName.style.display = "none";
                taskDetails.style.display = "block";
            } else {
                taskName.style.display = "block";
                taskDetails.style.display = "none";
            }
        });

        task.appendChild(taskName);
        task.appendChild(taskDeleteButton);
        task.appendChild(taskEditButton);
        task.appendChild(taskDetails);

        const todoLane = document.getElementById("todo-lane");
        todoLane.appendChild(task);

        form.reset();
    }
});

draggables.forEach((task) => {
    task.addEventListener("dragstart", () => {
        task.classList.add("is-dragging");
    });
    task.addEventListener("dragend", () => {
        task.classList.remove("is-dragging");
    });
});

droppables.forEach((zone) => {
    zone.addEventListener("dragover", (e) => {
        e.preventDefault();

        const bottomTask = insertAboveTask(zone, e.clientY);
        const curTask = document.querySelector(".is-dragging");

        if (!bottomTask) {
            zone.appendChild(curTask);
        } else {
            zone.insertBefore(curTask, bottomTask);
        }
    });
});

const insertAboveTask = (zone, mouseY) => {
    const els = zone.querySelectorAll(".task:not(.is-dragging)");

    let closestTask = null;
    let closestOffset = Number.NEGATIVE_INFINITY;

    els.forEach((task) => {
        const { top } = task.getBoundingClientRect();

        const offset = mouseY - top;

        if (offset < 0 && offset > closestOffset) {
            closestOffset = offset;
            closestTask = task;
        }
    });

    return closestTask;
};
