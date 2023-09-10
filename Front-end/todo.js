    const form = document.getElementById("todo-form");
    const draggables = document.querySelectorAll(".task");
    const droppables = document.querySelectorAll(".swim-lane");

    // -- Formulario de cadastro -- 
    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const name = document.getElementById("name").value;
        const description = document.getElementById("description").value;
        const dueDate = document.getElementById("due-date").value;
        const priority = document.getElementById("priority").value;
        const category = document.getElementById("category").value;
        const status = document.getElementById("status").value;

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
        task.appendChild(taskName);

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
        task.appendChild(taskDetails);

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
        const todoLane = document.getElementById("todo-lane");
        todoLane.appendChild(task);

        form.reset();
    });
    // -- Função drag and drop --
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
            

  