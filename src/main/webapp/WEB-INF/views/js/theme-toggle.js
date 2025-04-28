document.addEventListener("DOMContentLoaded", function () {
  const toggleBtn = document.querySelector(".theme-toggle-btn");
  const body = document.body;

  // Apply dark mode if previously saved
  if (localStorage.getItem("dark-mode") === "true") {
    body.classList.add("dark-mode");
  }

  // Toggle event
  toggleBtn?.addEventListener("click", () => {
    body.classList.toggle("dark-mode");
    localStorage.setItem("dark-mode", body.classList.contains("dark-mode"));
  });
});
