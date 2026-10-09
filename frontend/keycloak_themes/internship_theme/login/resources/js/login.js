document.addEventListener('DOMContentLoaded', function() {
  const eye = document.getElementById('eye');
  const password = document.getElementById('password');
  const eyeConfirm = document.getElementById('eye-confirm');
  const passwordConfirm = document.getElementById('password-confirm');
  
  var registerALink = document.getElementById("registerButton");

  if (registerALink) {
      registerALink.addEventListener('click', function(event) {
          event.preventDefault(); // Evita la redirección por defecto del enlace
  
          // Mostrar alerta y luego redirigir
          alert("El registro de usuarios es solo para usuarios empresas. Si eres un estudiante ingresa directamente con el boton de google.");
          window.location.href = registerALink.href; // Redirigir a la URL del enlace
      });
  } else {
      console.error("Elemento con ID 'registerButton' no encontrado.");
  }

  eye.addEventListener("click", function(){
      this.className = this.className === "zmdi zmdi-eye" ? "zmdi zmdi-eye-off" : "zmdi zmdi-eye"
      const type = password.getAttribute("type") === "password" ? "text" : "password"
      password.setAttribute("type", type)
  })
  
  eyeConfirm.addEventListener("click", function(){
      this.className = this.className === "zmdi zmdi-eye" ? "zmdi zmdi-eye-off" : "zmdi zmdi-eye"
      const type = passwordConfirm.getAttribute("type") === "password" ? "text" : "password"
      passwordConfirm.setAttribute("type", type)
  })
});

document.addEventListener('DOMContentLoaded', function() {

});