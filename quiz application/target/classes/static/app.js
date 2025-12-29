// Global utility functions

function checkAuth() {
    const token = localStorage.getItem('token');
    if (!token && !window.location.pathname.includes('index.html') && 
        !window.location.pathname.includes('login.html') && 
        !window.location.pathname.includes('register.html')) {
        window.location.href = 'login.html';
        return false;
    }
    updateNavBar();
    return true;
}

function updateNavBar() {
    const navBar = document.getElementById('navBar');
    if (!navBar) return;

    const user = JSON.parse(localStorage.getItem('user') || '{}');
    const token = localStorage.getItem('token');

    if (token && user.username) {
        const isAdmin = user.roles && user.roles.includes('ROLE_ADMIN');
        
        navBar.innerHTML = `
            <span>Welcome, ${user.username}</span>
            ${isAdmin ? '<a href="admin.html">Admin Panel</a>' : ''}
            <a href="quizzes.html">Quizzes</a>
            <a href="index.html">Home</a>
            <button onclick="logout()">Logout</button>
        `;
    } else {
        navBar.innerHTML = `
            <a href="index.html">Home</a>
            <a href="login.html">Login</a>
            <a href="register.html">Register</a>
        `;
    }
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = 'index.html';
}

function loadPage(page) {
    window.location.href = page;
}

// Initialize navigation on page load
document.addEventListener('DOMContentLoaded', function() {
    updateNavBar();
});

