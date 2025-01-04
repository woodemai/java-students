document.addEventListener('DOMContentLoaded', function() {
    loadGroups();
});

function loadGroups() {
    fetch('/student-tasks/groups')
        .then(response => response.json())
        .then(groups => {
            const select = document.getElementById('groupSelect');
            groups.forEach(group => {
                const option = document.createElement('option');
                option.value = group.id;
                option.textContent = group.groupName;
                select.appendChild(option);
            });
        });

    document.getElementById('groupSelect').addEventListener('change', function(e) {
        if (e.target.value) {
            loadStudentsByGroup(e.target.value);
        }
    });
}

function loadStudentsByGroup(groupId) {
    fetch(`/student-tasks/students?groupId=${groupId}`)
        .then(response => response.json())
        .then(students => {
            const studentsList = document.getElementById('studentsList');
            studentsList.innerHTML = '';
            students.forEach(student => {
                const div = document.createElement('div');
                div.className = 'student-card';
                div.textContent = `${student.lastName} ${student.firstName}`;
                div.onclick = () => loadStudentTasks(student.id);
                studentsList.appendChild(div);
            });
        });
}

function loadStudentTasks(studentId) {
    fetch(`/student-tasks/tasks?studentId=${studentId}`)
        .then(response => response.json())
        .then(tasks => {
            const tasksList = document.getElementById('tasksList');
            tasksList.innerHTML = '';
            tasks.forEach(task => {
                const div = document.createElement('div');
                div.className = 'task-item';
                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.checked = task.completed;
                checkbox.onchange = () => updateTaskStatus(task.id, checkbox.checked);
                div.appendChild(checkbox);
                div.appendChild(document.createTextNode(`Задача ${task.taskNumber}`));
                tasksList.appendChild(div);
            });
        });
}

function updateTaskStatus(taskId, completed) {
    fetch(`/student-tasks/tasks/${taskId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ completed: completed })
    });
} 