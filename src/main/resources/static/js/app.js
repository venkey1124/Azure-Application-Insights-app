const loginForm = document.getElementById("loginForm");
const loginMessage = document.getElementById("loginMessage");
const logoutButton = document.getElementById("logoutButton");

if (loginForm) {
    loginForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ username, password })
            });

            const data = await response.json();

            if (!response.ok) {
                loginMessage.textContent = data.message || "Login failed";
                return;
            }

            localStorage.setItem("studentId", data.studentId);
            window.location.href = "dashboard.html";
        } catch (error) {
            loginMessage.textContent = "Unable to connect to the server";
        }
    });
}

if (logoutButton) {
    logoutButton.addEventListener("click", () => {
        localStorage.removeItem("studentId");
        window.location.href = "index.html";
    });
}

document.addEventListener("DOMContentLoaded", () => {
    const pageNeedsLogin = document.body.querySelector(".dashboard") !== null;

    if (!pageNeedsLogin) {
        return;
    }

    const studentId = localStorage.getItem("studentId");

    if (!studentId) {
        window.location.href = "index.html";
        return;
    }

    loadCurrentPage(studentId);
});

function loadCurrentPage(studentId) {
    if (document.querySelector('[data-page="dashboard"]')) {
        loadDashboard(studentId);
    }

    if (document.getElementById("profile")) {
        loadProfile(studentId);
    }

    if (document.getElementById("marks")) {
        loadMarks(studentId);
    }

    if (document.getElementById("attendance")) {
        loadAttendance(studentId);
    }

    if (document.getElementById("announcements")) {
        loadAnnouncements();
    }
}

async function loadDashboard(studentId) {
    try {
        const [profile, marks, attendance] = await Promise.all([
            getJson(`/api/students/${studentId}`),
            getJson(`/api/students/${studentId}/marks`),
            getJson(`/api/students/${studentId}/attendance`)
        ]);

        setText("dashboardName", firstName(profile.name));
        setText("averageMarks", `${average(marks, "marks")}%`);
        setText("averageAttendance", `${average(attendance, "percentage")}%`);
        setText("studentDepartment", profile.department);
        setText("studentYear", `Year ${profile.year}`);
    } catch (error) {
        setText("dashboardName", "Student");
        setText("averageMarks", "--");
        setText("averageAttendance", "--");
        setText("studentDepartment", "Unavailable");
        setText("studentYear", "Try refreshing");
    }
}

async function loadProfile(studentId) {
    try {
        const profile = await getJson(`/api/students/${studentId}`);
        document.getElementById("profile").innerHTML = `
            <div class="profile-grid">
                ${detailTile("Full Name", profile.name)}
                ${detailTile("Email", profile.email)}
                ${detailTile("Department", profile.department)}
                ${detailTile("Academic Year", `Year ${profile.year}`)}
            </div>
        `;
    } catch (error) {
        showError("profile", "Could not load profile.");
    }
}

async function loadMarks(studentId) {
    try {
        const marks = await getJson(`/api/students/${studentId}/marks`);
        document.getElementById("marks").innerHTML = `
            ${buildMarksSummary(marks)}
            ${buildPerformanceTable("Marks", marks, "marks", "%")}
        `;
    } catch (error) {
        showError("marks", "Could not load marks.");
    }
}

async function loadAttendance(studentId) {
    try {
        const attendance = await getJson(`/api/students/${studentId}/attendance`);
        document.getElementById("attendance").innerHTML = `
            ${buildAttendanceSummary(attendance)}
            ${buildPerformanceTable("Attendance", attendance, "percentage", "%")}
        `;
    } catch (error) {
        showError("attendance", "Could not load attendance.");
    }
}

async function loadAnnouncements() {
    try {
        const announcements = await getJson("/api/announcements");
        document.getElementById("announcements").innerHTML = `
            <div class="announcement-list">
                ${announcements.map((item, index) => `
                    <article class="announcement">
                        <span class="metric-label">Notice ${index + 1}</span>
                        <h3>${escapeHtml(item.title)}</h3>
                        <p>${escapeHtml(item.body)}</p>
                    </article>
                `).join("")}
            </div>
        `;
    } catch (error) {
        showError("announcements", "Could not load announcements.");
    }
}

async function getJson(url) {
    const response = await fetch(url);

    if (!response.ok) {
        throw new Error(`Request failed: ${url}`);
    }

    return response.json();
}

function buildMarksSummary(rows) {
    const topSubject = rows.reduce((best, row) => row.marks > best.marks ? row : best, rows[0]);

    return `
        <div class="summary-strip">
            ${miniStat("Average", `${average(rows, "marks")}%`)}
            ${miniStat("Top Subject", topSubject ? topSubject.subject : "--")}
            ${miniStat("Subjects", rows.length)}
        </div>
    `;
}

function buildAttendanceSummary(rows) {
    const watchCount = rows.filter(row => Number(row.percentage) < 85).length;

    return `
        <div class="summary-strip">
            ${miniStat("Overall", `${average(rows, "percentage")}%`)}
            ${miniStat("Subjects", rows.length)}
            ${miniStat("Needs Focus", watchCount)}
        </div>
    `;
}

function buildPerformanceTable(label, rows, field, suffix) {
    const rowsHtml = rows
        .map(row => {
            const value = Number(row[field]);
            return `
                <tr>
                    <td>${escapeHtml(row.subject)}</td>
                    <td class="score-cell">
                        <strong>${formatNumber(value)}${suffix}</strong>
                        <div class="progress-track" aria-hidden="true">
                            <div class="progress-fill" style="width: ${clamp(value)}%"></div>
                        </div>
                    </td>
                    <td>${statusPill(value)}</td>
                </tr>
            `;
        })
        .join("");

    return `
        <table>
            <thead>
                <tr>
                    <th>Subject</th>
                    <th>${label}</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>${rowsHtml}</tbody>
        </table>
    `;
}

function statusPill(value) {
    if (value >= 90) {
        return '<span class="status-pill">Excellent</span>';
    }

    if (value >= 80) {
        return '<span class="status-pill warning">Steady</span>';
    }

    return '<span class="status-pill danger">Focus</span>';
}

function detailTile(label, value) {
    return `
        <article class="profile-tile">
            <span class="detail-label">${label}</span>
            <strong>${escapeHtml(String(value))}</strong>
        </article>
    `;
}

function miniStat(label, value) {
    return `
        <article class="mini-stat">
            <span class="metric-label">${label}</span>
            <strong>${escapeHtml(String(value))}</strong>
        </article>
    `;
}

function average(rows, field) {
    if (!rows.length) {
        return "--";
    }

    const total = rows.reduce((sum, row) => sum + Number(row[field]), 0);
    return formatNumber(total / rows.length);
}

function formatNumber(value) {
    return Number.isInteger(value) ? value : value.toFixed(1);
}

function clamp(value) {
    return Math.max(0, Math.min(100, value));
}

function firstName(name) {
    return name ? name.split(" ")[0] : "Student";
}

function setText(id, value) {
    const element = document.getElementById(id);

    if (element) {
        element.textContent = value;
    }
}

function showError(elementId, message) {
    document.getElementById(elementId).innerHTML = `<p class="message">${message}</p>`;
}

function escapeHtml(value) {
    return value
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}
