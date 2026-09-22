function confirmarExclusao() {
    return confirm("Deseja realmente excluir este produto?");
}

document.addEventListener("DOMContentLoaded", function () {
    const formularios = document.querySelectorAll("form[data-validar='true']");
    formularios.forEach(function (formulario) {
        formulario.addEventListener("submit", function (event) {
            let valido = true;
            formulario.querySelectorAll("[required]").forEach(function (campo) {
                if (campo.value.trim() === "") {
                    valido = false;
                    campo.classList.add("is-invalid");
                } else {
                    campo.classList.remove("is-invalid");
                }
            });
            if (!valido) {
                event.preventDefault();
                alert("Preencha todos os campos obrigatórios.");
            }
        });
    });
});
